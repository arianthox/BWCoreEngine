cmd-stop-all.bat && docker volume rm $(docker volume ls|awk '{print $2}')
