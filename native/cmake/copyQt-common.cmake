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
