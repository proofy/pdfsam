#!/bin/sh
# refresh sources
git fetch upstream
git checkout master
git merge upstream/master

# running PdfSam 
RUNJAR=pdfsam-community-3.3.8-SNAPSHOT.jar
if [ -f ./pdfsam-community/target/$RUNJAR ] 
then
        cd ./pdfsam-community/
        mvn exec:java
	#java -jar ./pdfsam-community/target/$RUNJAR
else
	#mvn clean install -Dmaven.test.skip=true -Prelease
	mvn clean install -Dmaven.test.skip=true
	if [ -f ./pdfsam-community/target/$RUNJAR ] 
	then
		cd ./pdfsam-community/ 
		mvn exec:java
		#java -jar ./pdfsam-community/target/$RUNJAR
	else
		echo "Can't start PdfSam"
		exit 1
	fi
fi	
