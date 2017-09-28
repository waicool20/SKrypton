########################################################################################
# Qt Library files

file(READ ${CMAKE_CURRENT_SOURCE_DIR}/../src/main/resources/nativeLibraries-windows.txt RequiredQtLibs)
string(REPLACE "\n" ";" RequiredQtLibs "${RequiredQtLibs}")

foreach (_LIB ${RequiredQtLibs})
    file(GLOB _LIB_FILES "${Qt_BinariesPath}/${_LIB}.dll")

    if (NOT _LIB_FILES)
        message(FATAL_ERROR "No library files were found for \"${_LIB}\"")
    endif ()
    foreach (_LIB_FILE ${_LIB_FILES})
        get_filename_component(_LIB_FILE ${_LIB_FILE} NAME)
        set(_IN_FILE "${Qt_BinariesPath}/${_LIB_FILE}")
        set(_OUT_DIR "${OUTPUT_DIR}/lib")
        file(TO_NATIVE_PATH ${_IN_FILE} _IN_FILE)
        file(TO_NATIVE_PATH "${_OUT_DIR}/${_LIB_FILE}" _OUT_DIR)
        add_custom_command(
                TARGET CopyQtDependencies PRE_BUILD
                COMMAND ${CMAKE_COMMAND} -E copy_if_different "${_IN_FILE}" "${_OUT_DIR}"
                COMMENT "[COPY LIB] ${_IN_FILE} to ${_OUT_DIR}"
        )
    endforeach ()
endforeach ()

########################################################################################
# OpenGL needs to be copied too

set(CMAKE_FIND_LIBRARY_SUFFIXES "*.dll")
find_library(_EGL
        NAMES "EGL" "libEGL" "libEGL.dll"
        PATHS "${Qt_BinariesPath}")
if (_EGL)
    get_filename_component(_EGL_NAME ${_EGL} NAME)
    set(_IN_FILE "${_EGL}")
    set(_OUT_DIR "${OUTPUT_DIR}/lib")
    file(TO_NATIVE_PATH ${_IN_FILE} _IN_FILE)
    file(TO_NATIVE_PATH "${_OUT_DIR}/${_EGL_NAME}" _OUT_DIR)
    add_custom_command(
            TARGET CopyQtDependencies PRE_BUILD
            COMMAND ${CMAKE_COMMAND} -E copy_if_different "${_IN_FILE}" "${_OUT_DIR}"
            COMMENT "[COPY EGL] ${_IN_FILE} to ${_OUT_DIR}"
    )
else ()
    message(FATAL_ERROR "Could not find EGL library file")
endif ()

########################################################################################
# Qt Plugin files

set(
        RequiredQtPlugins
        "bearer"
        "imageformats"
        "platforms"
        "printsupport"
        "xcbglintegrations"
)

foreach (_QtPlugin ${RequiredQtPlugins})
    set(_IN_FILE "${Qt_PluginsPath}/${_QtPlugin}")
    set(_OUT_DIR "${OUTPUT_DIR}/plugins")
    file(TO_NATIVE_PATH ${_IN_FILE} _IN_FILE)
    file(TO_NATIVE_PATH "${_OUT_DIR}/${_QtPlugin}" _OUT_DIR)
    add_custom_command(
            TARGET CopyQtDependencies PRE_BUILD
            COMMAND ${CMAKE_COMMAND} -E copy_directory "${_IN_FILE}" "${_OUT_DIR}"
            COMMENT "[COPY PLUGIN] ${_IN_FILE} to ${_OUT_DIR}"
    )
endforeach ()

########################################################################################
# Qt LibExec files

set(RequiredQtLibExecFiles "QtWebEngineProcess.exe" "QtWebEngineProcessd.exe")

foreach (_QtLibExecFile ${RequiredQtLibExecFiles})
    set(_IN_FILE "${Qt_LibraryExecutablesPath}/${_QtLibExecFile}")
    set(_OUT_DIR "${OUTPUT_DIR}/libexec")
    file(TO_NATIVE_PATH ${_IN_FILE} _IN_FILE)
    file(TO_NATIVE_PATH "${_OUT_DIR}/${_QtLibExecFile}" _OUT_DIR)
    add_custom_command(
            TARGET CopyQtDependencies PRE_BUILD
            COMMAND ${CMAKE_COMMAND} -E copy_if_different "${_IN_FILE}" "${_OUT_DIR}"
            COMMENT "[COPY LIBEXEC] ${_IN_FILE} to ${_OUT_DIR}"
    )
endforeach ()

########################################################################################
# Qt Translation files

set(RequiredQtTranslationFiles "qtwebengine_locales")

foreach (_QtTranslationDir ${RequiredQtTranslationFiles})
    set(_IN_FILE "${Qt_TranslationsPath}/${_QtTranslationDir}")
    set(_OUT_DIR "${OUTPUT_DIR}/translations")
    file(TO_NATIVE_PATH ${_IN_FILE} _IN_FILE)
    file(TO_NATIVE_PATH "${_OUT_DIR}/${_QtTranslationDir}" _OUT_DIR)
    add_custom_command(
            TARGET CopyQtDependencies PRE_BUILD
            COMMAND ${CMAKE_COMMAND} -E copy_directory "${_IN_FILE}" "${_OUT_DIR}"
            COMMENT "[COPY TRANSLATION] ${_IN_FILE} to ${_OUT_DIR}"
    )
endforeach ()

########################################################################################
# Qt Resource files

set(RequiredQtResourceFiles "resources")

foreach (_QtResourceDir ${RequiredQtResourceFiles})
    set(_IN_FILE "${Qt_DataPath}/${_QtResourceDir}")
    set(_OUT_DIR "${OUTPUT_DIR}")
    file(TO_NATIVE_PATH ${_IN_FILE} _IN_FILE)
    file(TO_NATIVE_PATH "${_OUT_DIR}/${_QtResourceDir}" _OUT_DIR)
    add_custom_command(
            TARGET CopyQtDependencies PRE_BUILD
            COMMAND ${CMAKE_COMMAND} -E copy_directory "${_IN_FILE}" "${_OUT_DIR}"
            COMMENT "[COPY RESOURCE] ${_IN_FILE} to ${_OUT_DIR}"
    )
endforeach ()

########################################################################################
# Qt Conf files

file(TO_NATIVE_PATH "${CMAKE_SOURCE_DIR}/qtconf" CONF_FILES)
file(TO_NATIVE_PATH ${OUTPUT_DIR} _OUT_DIR)
add_custom_command(
        TARGET CopyQtDependencies PRE_BUILD
        COMMAND ${CMAKE_COMMAND} -E copy_directory "${CONF_FILES}" "${_OUT_DIR}"
        COMMENT "[COPY CONF] {CMAKE_SOURCE_DIR}/qtconf to ${_OUT_DIR}"
)
