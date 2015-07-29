# Associate Consultant Bootcamp #
##Prerequisites##
###Accounts###
Each new hire will need to set up the following accounts to start the lab content provided in this repository.

* Red Hat Access Portal Account (https://access.redhat.com)
* An OpenShift Online Account (https://www.openshift.com/app/account/new -- use your RH email address)

###Installations###
Each new hire will need the following installations downloaded and unzipped prior to the start of the New Hire Bootcamp.

Install				| URL
------------------------------- | ----------
Java Development Kit 7 			| [OpenJDK](http://openjdk.java.net/install/), [Oracle JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
Apache Maven (Latest) 			| [link](http://maven.apache.org/download.cgi)
Git 							| [link](https://git-scm.com/downloads)
JBoss EAP 6.4.0 				| [link](https://access.redhat.com/jbossnetwork/restricted/listSoftware.html?downloadType=distributions&product=appplatform&version=6.1.0&productChanged=yes)
JBoss BPM Suite 6.1.0 			| [link](https://access.redhat.com/jbossnetwork/restricted/listSoftware.html?downloadType=distributions&product=bpm.suite&productChanged=yes)
OpenShift Command Line Tools 	| [link](https://developers.openshift.com/en/managing-client-tools.html)

##Day 1 - OpenShift Application Management##
###Goals###
1. Learn how to use OpenShift Online to enable quick POCs (Proof-of-Concepts)
1. To get familiar with both the UI and CLI experience in OpenShift
1. To get familiar with the format for the lab content in the remaining days of the Bootcamp

###Instructions###
1. Run the following commands on the command line to create a new EAP 6 project in OpenShift:

	```
	rhc app-create nchlab jbossews-2.0 -g large 
	```
	* These commands will output the generated credentials and locations for the OpenShift Git repository our application will use. Save this information in a text file for safekeeping.

1. Enter the newly cloned git directory
	```
	cd nchlab/ 
	```

1. Connect the starter code on GitHub to the OpenShift repository:
	```
	git remote add upstream -m master git://github.com/justincohler/nch-bootcamp.git 
	git pull -s recursive -X theirs upstream master 
	```
	* An editor will ask you to enter a merge message. Enter the following to (w)rite the merge record and (q)uit out of the editor:
		```
		> :wq 
		```

1. Finally, push the starter code to OpenShift: 
	```
	git push
	```
	* This will output the generated users and passwords for the EAP Admin Console, which you can use to configure persistence in the webapp.

1. In your browser, navigate to https://nchlab-<YOUR-DOMAIN>.rhcloud.com/
	* You now have a web application running business rules and Camel services on top of a MongoDB database!

Now we will import the projects from the "nchlab" repository into the JBDS (JBoss Developer Studio) IDE.

1. Start JBDS and once you have opened a workspace, click File->Import...
1. In the Import wizard, Expand the "Maven" folder, and click "Existing Maven Projects"
1. Select the directory where you cloned your nchlab repository.
1. Select all the projects in the parent directory, and complete the wizard. In the Project Editor, you should have 9 projects imported.
1. Right click on the "lab" project, then click Run As->Maven Build...
1. In the Build popup, enter:
	* Goals: clean install
	* Profiles: openshift
1. Click 'Run' to perform the Maven build and ensure that the project build is successful.

No let's set up a local server to test out our application.

1. Make sure that you have locally installed either JBoss EWS 2 and have a local MongoDB database running.
2. In the "Servers" view of JBDS, right click and click on New->Server...
3. In the following dialogues, select a new Tomcat 7 server and point to the EWS installation on your machine.
4. On the "Add and Remove" screen, add the lab-web project in the "Available" column to the "Configured" column and click finish.
5. In the web.xml of the lab-web project, under the "spring.profiles.active" context-param, change "openshift" to "default" to switch the Spring profile to your local configuration.
6. Start the new server by right clicking on the new server and clicking "Start"
7. Point to localhost:8080/ in your web browser and you should have the application running on your local machine with a local database.

##Day 2 - Business Rules and Process Modeling##
###Goals###
1. To get familiar with BDD (Behavior-Driven-Development) by using the Cucumber test Framework
1. Learn how to write Business Rules, and touch integration endpoints in Business Processes

###Instructions###
1. To check out today's repository branch, pull all of the branches from the upstream bootcamp repository into the directory you created yesterday:
	```
	git fetch upstream
	```
1. Next, checkout the Day 2 branch of the bootcamp repository:
	```
	git checkout day2
	```
1. Open JBDS and build the project as you did yesterday. The project should now fail to build. Now comes the fun part!

The first goal of the day is to learn some basic concepts of BDD. A great BDD tool we use frequently is a framework called Cucumber, which takes text files containing application "features", and connects the steps of each feature to a corresponding JUnit test, called a "step". There are a number of test features found in the following location:

lab-test-harness/src/test/resources/features/lab.feature
 

###Instructions###
##Day 3 - Integrating Services with Camel##
###Goals###
1. Learn how to write Camel routes for code-less integration
1. Learn how to expose and consume Web Services with Camel

###Instructions
##Day 4 - Continuous Integration and Delivery##
###Goals###
1. Learn how to add and use plugins in Jenkins on top of OpenShift
1. Deploy our completed web application to OpenShift with Jenkins CI (Continuous Integration)

###Instructions###
##Day 5 - Breakfix Playground##
###Goals###
1. TODO

###Instructions###

