for D in ../../*; do
    if [ -d "${D}" ];  then
      if [ -f "${D}/.git" ]; then
        echo "############################################"
        echo "git pull ${D}"
        echo "############################################"
        pushd "${D}"
        git pull
        popd
      fi
    fi
done