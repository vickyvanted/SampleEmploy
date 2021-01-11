cd ~/employee/smacs-data
mvn clean install
sleep 2
cd ../smacs-service
mvn clean install
sleep 2
echo
echo "-------------------------------------------------------------------------------------------------------------"
echo "                                           Installing jar & Restarting                                       "
echo "-------------------------------------------------------------------------------------------------------------"

cp ~/employee/smacs-service/target/smacs-service-1.0.0.jar /opt/employee/smacs-service/lib
cp ~/employee/smacs-data/target/smacs-data-1.0.0.jar /opt/employee/smacs-service/lib


/opt/employee/smacs-service/bin/smacs-service.sh stop;
sleep 60;
#/opt/employee/smacs-service/bin/smacs-service.sh start;


echo "-------------------------------------------------------------------------------------------------------------"
echo "                                           completed installation                                            "
echo "-------------------------------------------------------------------------------------------------------------"


tail -f /opt/employee/smacs-service/logs/smacs-service.log


