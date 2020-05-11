echo off
for /d %%a in ("..\..\*") do (
    IF exist "%%a\cmd-clean-build.bat" (
        echo %%a
        PUSHD %%a
        cmd-clean-build.bat
        echo %%a\cmd-clean-build.bat
        POPD
        echo off
    )
)
