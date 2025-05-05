#!/bin/sh
# refresh sources
git fetch upstream
git checkout master
git merge upstream/master

# running PdfSam 
RUNJAR=pdfsam-basic-5.0.4-SNAPSHOT.jar
if [ -f ./pdfsam-basic/target/$RUNJAR ] 
then
        cd ./pdfsam-basic/
        mvn exec:java -Dexec.mainClass="org.pdfsam.basic.App"
	#java -jar ./pdfsam-community/target/$RUNJAR
else
	#mvn clean install -Dmaven.test.skip=true -Prelease
	mvn clean install -Dmaven.test.skip=true
	if [ -f ./pdfsam-basic/target/$RUNJAR ] 
	then
		cd ./pdfsam-basic/ 
		mvn exec:java -Dexec.mainClass="org.pdfsam.basic.App"
		#java -jar ./pdfsam-community/target/$RUNJAR
	else
		echo "Can't start PdfSam"
		exit 1
	fi
fi	
