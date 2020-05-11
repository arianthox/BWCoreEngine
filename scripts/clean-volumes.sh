./cmd-stop-all.sh
docker volume rm $(docker volume ls|awk '{print $2}')
