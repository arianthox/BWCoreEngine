@echo off
for /d %%a in ("..\..\*") do (
    IF exist "%%a\cmd-build-image.bat" (
        echo +++++++++++++++++++++++++++++++++
        echo Building %%a
        echo +++++++++++++++++++++++++++++++++
        PUSHD %%a
        REM start "Build %%a" cmd-build-image.bat
        call cmd-build-image.bat > .logsi
        POPD
        @echo off
    ) 
)
echo "Completed!"
notepad build.log
del build.log