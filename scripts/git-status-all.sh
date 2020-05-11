for D in ../../*; do
    if [ -d "${D}" ];  then
      if [ -f "${D}/.git/config" ]; then
        echo "############################################"
        echo "git status ${D}"
        echo "############################################"
        pushd "${D}"
        git status
        popd
      fi
    fi
done