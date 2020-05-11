for D in ../../*; do
    if [ -d "${D}" ];  then
      if [ -f "${D}/cmd-clean-build.sh" ]; then
        echo "Building ${D}"
        pushd "${D}"
        ./cmd-clean-build.sh
        popd
      fi
    fi
done 