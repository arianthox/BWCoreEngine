docker rm $(docker ps -aq)
docker volume rm $(docker volume ls|awk '{print $2}')