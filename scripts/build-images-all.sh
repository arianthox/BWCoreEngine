for D in ../../*; do
    echo "Directory: ${D}"
    if [ -d "${D}" ];  then
      if [ -f "${D}/cmd-build-image.sh" ]; then
        echo "Building ${D}"
        pushd "${D}"
        ./cmd-build-image.sh
        popd
      fi
    fi
done