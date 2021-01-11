#! /bin/sh

# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
#title		: smacs-service.sh
#description   : This script manages smacs-service.
#author           : Linkeddots development team
#version         : 1.0   
#usage           : bash smacs-service.sh
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

export JAVA_HOME=/opt/linkeddots/jdk1.8.0_121/
#export JAVA_HOME=/opt/linkeddots/jdk1.8.0_111
#export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home
#Local changes

SMACS_SERVICE_VERSION='1.0.0'
PWD=`pwd`
SCRIPT_HOME=$(dirname "$0")
export SMACS_SERVICE_HOME=/opt/linkeddots/smacs-service
export SMACS_SERVICE_CONF=$SMACS_SERVICE_HOME'/conf'
export LOG_HOME=$SMACS_SERVICE_HOME'/log'
export LOG_CONFIG_FILE=$SMACS_SERVICE_CONF'/smacs-service-log4j2.xml'
export CONFIG_FILE=$SMACS_SERVICE_CONF'/smacs-service.properties'

#exporting config file
export HIBERNATE_CONNECTION_URL='jdbc:postgresql://164.52.193.168:5432/clmtest'
export HIBERNATE_CONNECTION_USERNAME='sa'
export HIBERNATE_CONNECTION_PASSWORD='Dots@490' 
export SMACS_SERVICE_PORT='9090'
export SMACS_SERVICE_HOST='0.0.0.0' 
export SMACS_SERVICE_KEYSTOREFILEPATH='/opt/linkeddots/smacs-service/cer/smacs-service-keystore.jks' 
export SMACS_SERVICE_KEYSTOREPASSWORD='ENC(Rqj7fKlvvpt3cYIGIshTLnxbx5pcpGCl)'
export SMACS_SERVICE_ENABLESSL='false'
export SMACS_REDIS_CACHE_HOSTNAME='172.5.0.3'
export SMACS_REDIS_CACHE_PORT='6379'
export SMACS_REDIS_CACHE_DURATION_INSEC='360'
export SMACS_REDIS_CACHE_DURATION_LIVEDATA='180'
export SMACS_REDIS_CACHE_DURATION_REPORTS_INSEC='60'
export SMACS_REDIS_CACHE_DURATION_COUNT_INSEC='60'
#export SITE_KEY = c3mapp
export SEND_EMAIL_ENABLED_LIVE=false
export SEND_EMAIL_ENABLED_DEVELOPMENT=true
export SMTP_USERNAME=AKIAIL7JGFPYUGM3UIPQ
export SMTP_PASSWORD=AicaC8RFKW18T+AB3hcUqaW13aQ50COFhrSJ9lr1Wr0A
export EMAIL_SERVER_PORT=587
export EMAIL_SERVER_HOST=email-smtp.us-east-1.amazonaws.com
export EMAIL_FROM_ID=dhandapani@linkeddots.com
export EMAIL_FROM_NAME=i3m
export SEND_SMS_ENABLED_LIVE=false
#export SEND_SMS_ENABLED_DEVELOPMENT=false
#export SMS_URL=https://alerts.solutionsinfini.com/api/v4/
#export SMS_API_KEY = A577e8ef07fd81de93bf4c61a532f86db
#export SMS_SENDER_ID=MTBJJV



# OS specific support.
cygwin=false
case "`uname`" in
   CYGWIN*) cygwin=true ;;
esac

if $cygwin ; then
    echo 'cygwin shell detected'
    LOG_HOME=`cygpath -w "$LOG_HOME"`
    LOG_CONFIG_FILE=`cygpath -w "$LOG_CONFIG_FILE"`
fi

CP=$(echo ../lib/*.jar | tr ' ' ':')
export CLASSPATH=$SMACS_SERVICE_CONF':'$CP

echo $CONFIG_FILE

case "$1" in
start)
	(
		pid=`pgrep -f 'com.linkeddots.smacs.service.Server'`
		if [ ! -z $pid ]; then 
			echo "process found with pid "$pid
			echo "use $0 stop"
		else 
	    	echo 'Starting...'
	    	$JAVA_HOME/bin/java -Dlog4j.configurationFile=$LOG_CONFIG_FILE -DCONFIG_FILE=$CONFIG_FILE com.linkeddots.smacs.service.Server > /opt/linkeddots/smacs-service/logs/smacs-service-sysout.log 2>&1
			echo $!
		fi
	) &
;;

status)
    pid=`pgrep -f 'com.linkeddots.smacs.service.Server'`
	if [ ! -z $pid ]; then 
		echo "process found with pid "$pid
	else 
		echo "com.linkeddots.smacs.service.Server process not found"
	fi
;;

stop)
	pid=`pgrep -f 'com.linkeddots.smacs.service.Server'`
	if [ ! -z $pid ]; then 
		echo "stopping ..."$pid
		pkill -f 'com.linkeddots.smacs.service.Server'
	else 
		echo "com.linkeddots.smacs.service.Server process not found"
	fi
;;

kill)
	pid=`pgrep -f 'com.linkeddots.smacs.service.Server'`
	if [ ! -z $pid ]; then 
		echo "killing ..."$pid
		pkill -9 -f 'com.linkeddots.smacs.service.Server'
	else 
		echo "com.linkeddots.smacs.service.Server process not found"
	fi
;;

restart)
    $0 stop
    $0 start
;;

*)
    echo "Usage: $0 {status|start|stop}"
    exit 1
esac
