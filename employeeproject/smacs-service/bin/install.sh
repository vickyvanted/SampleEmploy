echo "Updating existing code"
echo
echo
cp ~/connected3m/smacs-service/target/smacs-service-1.0.0.jar /opt/linkeddots/smacs-service/lib
/opt/linkeddots/smacs-service/bin/smacs-service.sh stop
sleep 10
/opt/linkeddots/smacs-service/bin/smacs-service.sh start
echo
echo
echo "Completed...Check the system now"

