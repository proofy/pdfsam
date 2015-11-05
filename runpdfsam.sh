#!/bin/sh
# running PdfSam 
RUNJAR=pdfsam-community-3.0.0.RELEASE-SNAPSHOT.jar
if [ -f ./pdfsam-community/target/$RUNJAR ] 
then
	java -jar ./pdfsam-community/target/$RUNJAR
else
	mvn clean install -Dmaven.test.skip=true -Prelease
	if [ -f ./pdfsam-community/target/$RUNJAR ] 
	then
		java -jar ./pdfsam-community/target/$RUNJAR
	else
		echo "Can't start PdfSam"
		exit 1
	fi
fi	
