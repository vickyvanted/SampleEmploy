./smacs-service.sh stop
sleep 3
./smacs-service.sh start
sleep 2
tail -f ../logs/smacs-service.log

