########################################################################################
# Qt Library files

file(READ ${CMAKE_CURRENT_SOURCE_DIR}/../src/main/resources/com/waicool20/skrypton/resources/nativeLibraries-linux.txt RequiredQtLibs)
string(REPLACE "\n" ";" RequiredQtLibs "${RequiredQtLibs}")

foreach (_LIB ${RequiredQtLibs})
    if (${_LIB} MATCHES ".*Qt5.*")
        file(GLOB _LIB_FILES "${Qt_LibrariesPath}/*${_LIB}.so.5")
    else ()
        file(GLOB _LIB_FILES "${Qt_LibrariesPath}/*${_LIB}.so.5?")
    endif ()

    if (NOT _LIB_FILES)
        message(FATAL_ERROR "No library files were found for \"${_LIB}\"")
    endif ()
    foreach (_LIB_FILE ${_LIB_FILES})
        get_filename_component(_LIB_FILE ${_LIB_FILE} NAME)
        set(_IN_FILE "${Qt_LibrariesPath}/${_LIB_FILE}")
        set(_OUT_DIR "${OUTPUT_DIR}/lib")
        add_custom_command(
                TARGET CopyQtDependencies PRE_BUILD
                COMMAND ${CMAKE_COMMAND} -E copy_if_different "${_IN_FILE}" "${_OUT_DIR}/${_LIB_FILE}"
                COMMENT "[COPY LIB] ${_IN_FILE} to ${_OUT_DIR}"
        )
    endforeach ()
endforeach ()

########################################################################################
# OpenGL needs to be copied too

find_library(_EGL
        NAMES "EGL" "libEGL" "libEGL.so" "libEGL.so.1"
        PATHS "${Qt_LibrariesPath}" "${Qt_BinariesPath}"
        "/usr/lib" "/usr/local/lib" "/usr/lib/x86_64-linux-gnu/"
        PATH_SUFFIXES "mesa-egl")
if (_EGL)
    get_filename_component(_EGL_NAME ${_EGL} NAME)
    set(_IN_FILE "${_EGL}")
    set(_OUT_DIR "${OUTPUT_DIR}/lib")
    add_custom_command(
            TARGET CopyQtDependencies PRE_BUILD
            COMMAND ${CMAKE_COMMAND} -E copy_if_different "${_IN_FILE}" "${_OUT_DIR}/${_EGL_NAME}"
            COMMENT "[COPY OPENGL] ${_IN_FILE} to ${_OUT_DIR}"
    )
else ()
    message(FATAL_ERROR "Could not find EGL library file, libEGL is a required component.")
endif ()

find_library(_GLES
        NAMES "GLESv2" "libGLESv2" "libGLESv2.so" "libGLESv2.so.2"
        PATHS "${Qt_LibrariesPath}" "${Qt_BinariesPath}"
        "/usr/lib" "/usr/local/lib" "/usr/lib/x86_64-linux-gnu/"
        PATH_SUFFIXES "mesa-egl")
if (_GLES)
    get_filename_component(_GLES_NAME ${_GLES} NAME)
    set(_IN_FILE "${_GLES}")
    set(_OUT_DIR "${OUTPUT_DIR}/lib")
    add_custom_command(
            TARGET CopyQtDependencies PRE_BUILD
            COMMAND ${CMAKE_COMMAND} -E copy_if_different "${_IN_FILE}" "${_OUT_DIR}/${_GLES_NAME}"
            COMMENT "[COPY OPENGL] ${_IN_FILE} to ${_OUT_DIR}"
    )
else ()
    message(FATAL_ERROR "Could not find GLES library file, libGLESv2 is a required component.")
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
    add_custom_command(
            TARGET CopyQtDependencies PRE_BUILD
            COMMAND ${CMAKE_COMMAND} -E copy_directory "${_IN_FILE}" "${_OUT_DIR}/${_QtPlugin}"
            COMMENT "[COPY PLUGIN] ${_IN_FILE} to ${_OUT_DIR}"
    )
endforeach ()

########################################################################################
# Qt LibExec files

set(RequiredQtLibExecFiles "QtWebEngineProcess")

foreach (_QtLibExecFile ${RequiredQtLibExecFiles})
    set(_IN_FILE "${Qt_LibraryExecutablesPath}/${_QtLibExecFile}")
    set(_OUT_DIR "${OUTPUT_DIR}/lib")
    add_custom_command(
            TARGET CopyQtDependencies PRE_BUILD
            COMMAND ${CMAKE_COMMAND} -E copy_if_different "${_IN_FILE}" "${_OUT_DIR}/${_QtLibExecFile}"
            COMMENT "[COPY LIBEXEC] ${_IN_FILE} to ${_OUT_DIR}"
    )
endforeach ()

########################################################################################
# Qt Translation files

set(RequiredQtTranslationFiles "qtwebengine_locales")

foreach (_QtTranslationDir ${RequiredQtTranslationFiles})
    set(_IN_FILE "${Qt_TranslationsPath}/${_QtTranslationDir}")
    set(_OUT_DIR "${OUTPUT_DIR}/translations")
    add_custom_command(
            TARGET CopyQtDependencies PRE_BUILD
            COMMAND ${CMAKE_COMMAND} -E copy_directory "${_IN_FILE}" "${_OUT_DIR}/${_QtTranslationDir}"
            COMMENT "[COPY TRANSLATION] ${_IN_FILE} to ${_OUT_DIR}"
    )
endforeach ()

########################################################################################
# Qt Resource files

set(RequiredQtResourceFiles "resources")

foreach (_QtResourceDir ${RequiredQtResourceFiles})
    set(_IN_FILE "${Qt_DataPath}/${_QtResourceDir}")
    set(_OUT_DIR "${OUTPUT_DIR}")
    add_custom_command(
            TARGET CopyQtDependencies PRE_BUILD
            COMMAND ${CMAKE_COMMAND} -E copy_directory "${_IN_FILE}" "${_OUT_DIR}/${_QtResourceDir}"
            COMMENT "[COPY RESOURCE] ${_IN_FILE} to ${_OUT_DIR}"
    )
endforeach ()

########################################################################################
# Qt Conf files

add_custom_command(
        TARGET CopyQtDependencies PRE_BUILD
        COMMAND ${CMAKE_COMMAND} -E copy_directory "${CMAKE_SOURCE_DIR}/qtconf" "${OUTPUT_DIR}"
        COMMENT "[COPY CONF] ${CMAKE_SOURCE_DIR}/qtconf to ${OUTPUT_DIR}"
)
