cmake_minimum_required(VERSION 3.9)
project(SKryptonNative)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)

########################################################################################
# Find required packages

find_package(Qt5 REQUIRED
        COMPONENTS
        Core
        Network
        Widgets
        WebEngine
        WebEngineCore
        WebEngineWidgets)

find_package(Java REQUIRED)
find_package(JNI REQUIRED)
include(UseJava)

file(GLOB_RECURSE SOURCE_FILES "src/main/cpp/*.c" "src/main/cpp/*.cpp")
file(GLOB_RECURSE INC_FILES "inc/*.h")
file(GLOB_RECURSE RESOURCES "rsc/*.qrc")
include_directories(inc inc/jni inc/webview inc/jni/headers ${JNI_INCLUDE_DIRS})

add_library(${CMAKE_PROJECT_NAME} SHARED ${SOURCE_FILES} ${INC_FILES} ${RESOURCES})
#add_dependencies(${CMAKE_PROJECT_NAME} JNIHeaders)
#set_target_properties(${CMAKE_PROJECT_NAME} PROPERTIES AUTOGEN_TARGET_DEPENDS JNIHeaders)
target_link_libraries(${CMAKE_PROJECT_NAME}
        Qt5::Core
        Qt5::Network
        Qt5::Widgets
        Qt5::WebEngine
        Qt5::WebEngineCore
        Qt5::WebEngineWidgets
        ${JNI_LIBRARIES})

string(TOLOWER ${CMAKE_BUILD_TYPE} _BUILD_TYPE)
set(OUTPUT_DIR "${CMAKE_CURRENT_SOURCE_DIR}/src/${_BUILD_TYPE}/resources")
set_target_properties(${CMAKE_PROJECT_NAME} PROPERTIES LIBRARY_OUTPUT_DIRECTORY ${OUTPUT_DIR})

if (MSVC)
    add_custom_command(
            TARGET ${CMAKE_PROJECT_NAME} POST_BUILD
            COMMAND ${CMAKE_COMMAND} -E copy_if_different "$<TARGET_FILE:${CMAKE_PROJECT_NAME}>" "${OUTPUT_DIR}"
            COMMENT "[COPY DLL] $<TARGET_FILE:${CMAKE_PROJECT_NAME}> to ${OUTPUT_DIR}"
    )
endif ()

########################################################################################
# Copy library tasks

include(cmake/copyQt-common.cmake)
if (${CMAKE_SYSTEM_NAME} MATCHES "Linux")
    include(cmake/copyQt-linux.cmake)
elseif (${CMAKE_SYSTEM_NAME} MATCHES "Windows")
    include(cmake/copyQt-windows.cmake)
elseif (${CMAKE_SYSTEM_NAME} MATCHES "Darwin")
    message(FATAL_ERROR "Can't test mac stuff yet ¯\\_(ツ)_/¯")
else ()
    message(FATAL_ERROR "Unsupported system")
endif ()

########################################################################################
# Check compiler standard

if (NOT "${CMAKE_CXX_COMPILE_FEATURES}" MATCHES "cxx_std_17")
    message(FATAL_ERROR "Compiler does not support c++17 standards.")
endif ()

########################################################################################
# Task that copies the Kotlin Standard Library Function files, this is required by
# javah program to generate the JNI Headers. Ran automatically when generating cmake files

set(PARENT_DIR "${CMAKE_CURRENT_SOURCE_DIR}/../")
set(CLASS_SEARCH_DIR "${PARENT_DIR}/build/classes/main")
file(GLOB_RECURSE JAVA_CLASSES "${CLASS_SEARCH_DIR}/*.class")

if (UNIX)
    set(GRADLE "${PARENT_DIR}/gradlew")
else ()
    set(GRADLE "${PARENT_DIR}/gradlew.bat")
endif ()

set(FUNCTION_CLASSES "${CMAKE_CURRENT_SOURCE_DIR}/build/kstdlib/kotlin/Function.class")
foreach (_NUM RANGE 22)
    list(APPEND FUNCTION_CLASSES "${CMAKE_CURRENT_SOURCE_DIR}/build/kstdlib/kotlin/jvm/functions/Function${_NUM}.class")
endforeach ()

foreach (_FILE ${FUNCTION_CLASSES})
    if (NOT EXISTS ${_FILE})
        execute_process(
                COMMAND "${GRADLE}" native:copyKotlinStdLib
                WORKING_DIRECTORY "${PARENT_DIR}"
                TIMEOUT 30
        )
        break()
    endif ()
endforeach ()

########################################################################################
# Scan for JNI classes and generate headers

foreach (_CLASS ${JAVA_CLASSES})
    if (NOT ${_CLASS} MATCHES ".*\\$.*" AND ${_CLASS} MATCHES "jni/objects/.*")
        STRING(REGEX REPLACE "${CLASS_SEARCH_DIR}/" "" _CLASS ${_CLASS})
        STRING(REGEX REPLACE "/" "." _CLASS ${_CLASS})
        STRING(REGEX REPLACE ".class" "" _CLASS ${_CLASS})
        list(APPEND JNI_CLASSES ${_CLASS})
        message(STATUS "[JAVAH] Will generate headers for class: \"${_CLASS}\"")
    endif ()
endforeach ()

if (NOT JNI_CLASSES)
    message(FATAL_ERROR "Could not find any suitable Java classes for header generation")
endif ()

create_javah(
        TARGET JNIHeaders
        CLASSES ${JNI_CLASSES}
        CLASSPATH ${CLASS_SEARCH_DIR} ${CMAKE_CURRENT_SOURCE_DIR}/build/kstdlib
        OUTPUT_DIR ${CMAKE_CURRENT_SOURCE_DIR}/inc/jni/headers
)
# Using add_dependencies instead of the DEPENDS in create_javah as that always triggers
# CleanJNIHeaders for some reason

add_dependencies(JNIHeaders CleanJNIHeaders)

########################################################################################
# Add a target to clean the JNI Headers

file(GLOB_RECURSE _HEADERS "${CMAKE_CURRENT_SOURCE_DIR}/inc/jni/headers/*.h")

add_custom_target(CleanJNIHeaders)
foreach (_HEADER ${_HEADERS})
    add_custom_command(
            TARGET CleanJNIHeaders PRE_BUILD
            COMMAND ${CMAKE_COMMAND} -E remove ${_HEADER}
            COMMENT "Cleaning ${_HEADER}"
    )
endforeach ()

########################################################################################
# Compiler flags

if (MSVC)
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} /std:c++latest")
else ()
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++1z -Wall")
endif ()
