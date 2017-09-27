cmake_minimum_required(VERSION 3.9)
project(SKryptonNative)

set(CMAKE_CXX_STANDARD 17)
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

file(GLOB_RECURSE SOURCE_FILES "src/*.c" "src/*.cpp")
file(GLOB_RECURSE INC_FILES "inc/*.h")
file(GLOB_RECURSE RESOURCES "rsc/*.qrc")
include_directories(inc inc/jni inc/webview inc/jni/headers ${JNI_INCLUDE_DIRS})

add_library(${CMAKE_PROJECT_NAME} SHARED ${SOURCE_FILES} ${INC_FILES} ${RESOURCES})
add_dependencies(${CMAKE_PROJECT_NAME} JNI_Headers)
set_target_properties(${CMAKE_PROJECT_NAME} PROPERTIES AUTOGEN_TARGET_DEPENDS JNI_Headers)
target_link_libraries(${CMAKE_PROJECT_NAME}
        Qt5::Core
        Qt5::Network
        Qt5::Widgets
        Qt5::WebEngine
        Qt5::WebEngineCore
        Qt5::WebEngineWidgets
        ${JNI_LIBRARIES})

set(OUTPUT_DIR "${CMAKE_CURRENT_SOURCE_DIR}/build/resources/main/skrypton")
set_target_properties(${CMAKE_PROJECT_NAME} PROPERTIES LIBRARY_OUTPUT_DIRECTORY ${OUTPUT_DIR})

include(copyQt.cmake)

########################################################################################
# Scan for JNI classes and generate headers

set(PARENT_DIR "${CMAKE_CURRENT_SOURCE_DIR}/../")
set(CLASS_SEARCH_DIR "${PARENT_DIR}/build/classes/main")
file(GLOB_RECURSE JAVA_CLASSES "${CLASS_SEARCH_DIR}/*.class")

foreach (CLASS ${JAVA_CLASSES})
    if (NOT ${CLASS} MATCHES ".*\\$.*" AND ${CLASS} MATCHES "jni/objects/.*")
        STRING(REGEX REPLACE "${CLASS_SEARCH_DIR}/" "" CLASS ${CLASS})
        STRING(REGEX REPLACE "/" "." CLASS ${CLASS})
        STRING(REGEX REPLACE ".class" "" CLASS ${CLASS})
        list(APPEND JNI_CLASSES ${CLASS})
        message(STATUS "[JAVAH] Will generate headers for class: \"${CLASS}\"")
    endif ()
endforeach ()

if (NOT JNI_CLASSES)
    message(FATAL_ERROR "Could not find any suitable Java classes for header generation")
endif ()

create_javah(
        TARGET JNI_Headers
        DEPENDS Clean_JNI_Headers
        CLASSES ${JNI_CLASSES}
        CLASSPATH ${CLASS_SEARCH_DIR} ${CMAKE_CURRENT_SOURCE_DIR}/build/kstdlib
        OUTPUT_DIR ${CMAKE_CURRENT_SOURCE_DIR}/inc/jni/headers
)

########################################################################################
# Add a target to clean the JNI Headers

add_custom_target(
        Clean_JNI_Headers
        COMMAND ${CMAKE_COMMAND} -E remove_directory "${CMAKE_CURRENT_SOURCE_DIR}/inc/jni/headers"
        COMMAND ${CMAKE_COMMAND} -E make_directory "${CMAKE_CURRENT_SOURCE_DIR}/inc/jni/headers"
)

########################################################################################
# Task that copies the Kotlin Standard Library Function files, this is required by
# javah program to generate the JNI Headers. Ran automatically when generating cmake files

if (UNIX)
    set(GRADLE "${PARENT_DIR}/gradlew")
else ()
    set(GRADLE "${PARENT_DIR}/gradlew.bat")
endif ()

set(FUNCTION_CLASSES "${CMAKE_CURRENT_SOURCE_DIR}/build/kstdlib/kotlin/Function.class")
foreach (NUM RANGE 22)
    list(APPEND FUNCTION_CLASSES "${CMAKE_CURRENT_SOURCE_DIR}/build/kstdlib/kotlin/jvm/functions/Function${NUM}.class")
endforeach ()

foreach (FILE ${FUNCTION_CLASSES})
    if (NOT EXISTS ${FILE})
        execute_process(
                COMMAND "${GRADLE}" native:copyKotlinStdLib
                WORKING_DIRECTORY "${PARENT_DIR}"
                TIMEOUT 30
        )
        break()
    endif ()
endforeach ()

########################################################################################
# Compiler flags

if (MSVC)
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} /std:c++latest")
else ()
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++1z -Wall")
endif ()
