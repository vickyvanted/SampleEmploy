#!/bin/bash
#title           :smacs-service-healing.sh
#description     :This script will make a restart the smacs-service process if its hang.
#author		     :linkeddots
#notes           :don't run as sudo.
#*/10 * * * * /opt/linkeddots/smacs-service/bin/smacs-service-healing.sh >> /home/ubuntu/logs/cronlogs/smacs-service-healing.log
#==============================================================================
RAM_MEM=`free | grep Mem | awk '{print $2}'`

echo `date`
core_ps=`pgrep -f 'com.linkeddots.smacs.service.Server'`
status_code1=`curl -o /dev/null -s -w "%{http_code}\n" http://localhost:9090/smacs/service/health`
#status_code2=`curl -o /dev/null -s -w "%{http_code}\n" http://localhost:9091/smacs/service/health`

    if [ $status_code1 == 200 ];
    then
        echo "smacs-service is running on port 9090 "
    else
        echo "smacs-service is not running on port 9090 restarting the smacs-service"
        cd /opt/linkeddots/smacs-service/bin/ 
        ./smacs-service.sh stop
        sleep 30
        ./smacs-service.sh start
    fi

#sleep 20

#if [ $RAM_MEM -ge 5000000 ]; then

#echo "ram more then 4GB"

	#if [ $status_code2 == 200 ];
   	#then
        	#echo "smacs-service is running on port 9091 "
    	 #else
        	#echo "smacs-service is not running on port 9091 restarting the smacs-service"
      		#cd /opt/linkeddots/smacs-service/bin/ 
        	#./smacs-service2.sh stop
        	#sleep 30
        	#./smacs-service2.sh start
   	 #fi
#else
#echo "RAM is 4GB"
#fi


exit;
