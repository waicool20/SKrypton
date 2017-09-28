########################################################################################
# Try and find the Qt paths

find_program(QTDIAG qtdiag)
if (NOT QTDIAG)
    message(FATAL_ERROR "Could not find qtdiag executable, is it on PATH?")
endif ()
message(STATUS "------------------ Checking Qt environment ------------------")
execute_process(COMMAND "${QTDIAG}" OUTPUT_VARIABLE QTDIAG_OUT)

string(REPLACE "\n" ";" QTDIAG_OUT "${QTDIAG_OUT}")

set(Qt_Paths "PrefixPath" "DocumentationPath" "HeadersPath" "LibrariesPath"
        "LibraryExecutablesPath" "BinariesPath" "PluginsPath" "ImportsPath"
        "Qml2ImportsPath" "ArchDataPath" "DataPath" "TranslationsPath" "ExamplesPath"
        "TestsPath" "SettingsPath")

foreach (_STRING ${QTDIAG_OUT})
    string(STRIP ${_STRING} _STRIPPED)
    foreach (_PATH ${Qt_Paths})
        if (${_STRIPPED} MATCHES "${_PATH}: (.+)")
            string(REGEX REPLACE ".+: (.+)" "\\1" "Qt_${_PATH}" ${_STRIPPED})
        endif ()
    endforeach ()
endforeach ()

foreach (_PATH ${Qt_Paths})
    message(STATUS "[Qt] Found ${_PATH} at: ${Qt_${_PATH}}")
endforeach ()
message(STATUS "-------------------------------------------------------------")

########################################################################################
# Target that copies everything
add_custom_target(CopyQtDependencies)

########################################################################################
# Qt Library files

file(READ ${CMAKE_CURRENT_SOURCE_DIR}/../src/main/resources/nativeLibraries.txt RequiredQtLibs)
string(REPLACE "\n" ";" RequiredQtLibs "${RequiredQtLibs}")

foreach (_LIB ${RequiredQtLibs})
    if (${CMAKE_SYSTEM_NAME} MATCHES "Linux")
        if (${_LIB} MATCHES ".*Qt5.*")
            file(GLOB _LIB_FILES "${Qt_LibrariesPath}/*${_LIB}.so.5")
        else ()
            file(GLOB _LIB_FILES "${Qt_LibrariesPath}/*${_LIB}.so.5?")
        endif ()
    elseif (${CMAKE_SYSTEM_NAME} MATCHES "Windows")
        file(GLOB _LIB_FILES "${Qt_LibrariesPath}/${_LIB}*dll")
    elseif (${CMAKE_SYSTEM_NAME} MATCHES "Darwin")
        file(GLOB _LIB_FILES "${Qt_LibrariesPath}/${_LIB}*dylib")
    else ()
        message(FATAL_ERROR "Unsupported system")
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
    set(_OUT_DIR "${OUTPUT_DIR}/libexec")
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
        COMMENT "[COPY CONF] {CMAKE_SOURCE_DIR}/qtconf to ${OUTPUT_DIR}"
)
