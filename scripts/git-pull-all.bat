echo off
for /d %%a in ("..\..\*") do (
    IF exist "%%a\cmd-clean-build.bat" (
        echo %%a
        PUSHD %%a
        git pull
        POPD
        echo off
    )
)
