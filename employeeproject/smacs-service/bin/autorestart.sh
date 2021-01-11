#!/bin/sh

status=`/opt/linkeddots/smacs-service/bin/smacs-service.sh status | grep found | awk '{print $2$3$4}'`
echo "current Status"
echo $status
echo
check="processnotfound"
if [ $status = $check ];then
   echo "...not found"
   /opt/linkeddots/smacs-service/bin/smacs-service.sh start
   exit
else
   echo "...found process skipping"
fi
exit

