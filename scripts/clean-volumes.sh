./cmd-stop-all.sh
docker volume rm $(docker volume ls|awk '{print $2}')
docker rmi $(docker images -a -q)