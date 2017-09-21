cmake_minimum_required(VERSION 3.9)
project(SKryptonNative)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_AUTOMOC ON)

find_package(Qt5 COMPONENTS
        Quick
        Core
        Network
        Widgets
        WebEngine
        WebEngineWidgets
        WebSockets)

find_package(Java)
find_package(JNI)
include(UseJava)

file(GLOB_RECURSE SOURCE_FILES "src/*.c" "src/*.cpp")
file(GLOB_RECURSE INC_FILES "inc/*.h")
include_directories(inc inc/jni inc/jni/headers ${JNI_INCLUDE_DIRS})

add_library(${CMAKE_PROJECT_NAME} SHARED ${SOURCE_FILES} ${INC_FILES} ${RESOURCES})
add_dependencies(${CMAKE_PROJECT_NAME} JNI_Headers)
target_link_libraries(${CMAKE_PROJECT_NAME}
        Qt5::Quick
        Qt5::Core
        Qt5::Network
        Qt5::Widgets
        Qt5::WebEngine
        Qt5::WebEngineWidgets
        Qt5::WebSockets
        ${JNI_LIBRARIES})

install(TARGETS ${CMAKE_PROJECT_NAME} DESTINATION ${CMAKE_CURRENT_SOURCE_DIR}/build)

# Scan for JNI classes and generate headers

get_filename_component(
        PARENT_DIR "${CMAKE_CURRENT_SOURCE_DIR}/../"
        REALPATH BASE_DIR "${CMAKE_BINARY_DIR}"
)

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

add_custom_target(
        Clean_JNI_Headers
        COMMAND ${CMAKE_COMMAND} -E remove_directory "${CMAKE_CURRENT_SOURCE_DIR}/inc/jni/headers"
        COMMAND ${CMAKE_COMMAND} -E make_directory "${CMAKE_CURRENT_SOURCE_DIR}/inc/jni/headers"
)

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
        )
        break()
    endif ()
endforeach ()

if (NOT EXISTS build/kstdlib)
    file(MAKE_DIRECTORY build/kstdlib)
endif ()

create_javah(
        TARGET JNI_Headers
        DEPENDS Clean_JNI_Headers
        CLASSES ${JNI_CLASSES}
        CLASSPATH ${CLASS_SEARCH_DIR} ${CMAKE_CURRENT_SOURCE_DIR}/build/kstdlib
        OUTPUT_DIR ${CMAKE_CURRENT_SOURCE_DIR}/inc/jni/headers
)

if (MSVC)
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} /std:c++latest")
endif ()
