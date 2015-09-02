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
Java Development Kit 7 (We aren't using Java 8 for this lab)		| [OpenJDK](http://openjdk.java.net/install/), [Oracle JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
Apache Maven (Latest) 			| [link](http://maven.apache.org/download.cgi)
Git 							| [link](https://git-scm.com/downloads)
Tomcat 7.0				| [link](http://apache.mirrors.lucidnetworks.net/tomcat/tomcat-7/v7.0.63/bin/apache-tomcat-7.0.63.zip) [link2](https://tomcat.apache.org/download-70.cgi)
JBoss Enterprise Application Platform 6.4	| [link](https://access.redhat.com/jbossnetwork/restricted/softwareDownload.html?softwareId=37393)
JBoss Developer Studio 8.1			| [link](https://access.redhat.com/jbossnetwork/restricted/listSoftware.html?downloadType=distributions&product=jbossdeveloperstudio&version=8.1.0)
OpenShift Command Line Tools 	| [link](https://developers.openshift.com/en/managing-client-tools.html)
MongoDB 2.6			| [link](http://docs.mongodb.org/manual/tutorial/install-mongodb-on-red-hat/) [fedora22] (http://unix.stackexchange.com/questions/208493/problem-installing-mongodb-on-fedora)
Nodejs and NPM                    	| [link](https://github.com/joyent/node/wiki/installing-node.js-via-package-manager)

##Day 1 - OpenShift Application Management##
###Goals###
1. Learn how to use OpenShift Online to enable quick POCs (Proof-of-Concepts)
1. To get familiar with both the UI and CLI experience in OpenShift
1. To get familiar with the format for the lab content in the remaining days of the Bootcamp

###Instructions###
1. Run the following commands on the command line to create a new EWS (Enterprise Web Server) project called "nchlab" with large gears in OpenShift:
```
	rhc app-create nchlab jbossews-2.0 -g large -s
```
These commands will output the generated credentials and locations for the OpenShift Git repository our application will use. Save this information in a text file for safekeeping.

If you don't have large gears available, the other options are small and medium, however large gears are recommended.

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
The code will be pushed to OpenShift, where OpenShift will run a Maven build on the project, copy the built deployment into the JBoss EWS container, and start the container. 

1. In your browser, navigate to https://nchlab-YOURDOMAIN.rhcloud.com/
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

Now let's set up a local server to test out our application. An overview of the Maven structure and functional diagrams can be found [here](http://redhat.slides.com/jcohler/nchbootcamplaboverview/live#/)

1. Make sure that you have locally installed Tomcat 7.0.x and have a local MongoDB database running.
	* Instructions for MongoDB installation are located [here](http://docs.mongodb.org/manual/tutorial/install-mongodb-on-red-hat/). Version 2.6 is preferred, but installing v3.0 should work fine as well.
2. In the "Servers" view of JBDS, right click and click on New->Server...
3. In the following dialogues, select a new Tomcat 7 server and point to the Tomcat installation on your machine.
4. On the "Add and Remove" screen, add the lab-web project in the "Available" column to the "Configured" column and click finish.
5. In the web.xml of the lab-web project, under the "spring.profiles.active" context-param, change "openshift" to "default" to switch the Spring profile to your local configuration.
6. Right click on the lab-web project in the Project Editor and click "Properties".
7. Click into the Web Project Settings Properties menu on the left and set the Context root to "/". (Without quotes) 
8. Start the new server by right clicking on the new server and clicking "Start"
9. Point to localhost:8080/ in your web browser and you should have the application running on your local machine with a local database.

##Day 2 - Business Rules and Process Modeling##
###Goals###
1. To get familiar with BDD (Behavior-Driven-Development) and Buisness Requirements Gathering
2. Learn how to use the Cucumber framework to build scenarios
3. Learn how to write Business Rules, and touch integration endpoints in Business Processes

###Note###
All exercises in the code are marked by the 'XXX' label, which shows up by default in the JBDS Tasks view. To expose this view in JBDS, in the top toolbar, click Window->Show View->Other..., and under "General", open "Tasks".

You can also search for 'XXX' in the File Search. In JBDS, in the top toolbar, click Search->File... and search on "XXX" in the "Containing Text" field.

###Instructions###
1. To check out today's repository branch, pull all of the branches from the upstream bootcamp repository into the directory you created yesterday:
```
	git fetch upstream
```
1. Next, checkout the Day 2 branch of the bootcamp repository:
```
	git checkout day2
```
1. Open JBDS and build the project as you did yesterday. Note that the tests run in the project are skipped during the build. 

The first part of today will be a mock client situation. We will work together in order to help define new functionality for our application.  

The specifics for this activity can be found in the [lab resource section](https://github.com/justincohler/nch-bootcamp/blob/master/lab%20resources/README.md) of the github  


The second goal of the day is to learn some basic way of working with Cucumber, a popular BDD tool we use frequently on projects. The framework uses text files containing application "features", and connects the steps of each feature to a corresponding JUnit test, called a "step". There are a number of test features found in the following location:  
```
	lab-test-harness/src/test/resources/features/lab.feature  
```

The JUnit tests which implement these features are found at the following location:
```
	lab-test-harness/src/test/java/com/rhc/lab/test/cucumber/BaseSteps.java
```
A very brief overview can been found as part of the [lab resource section](https://github.com/justincohler/nch-bootcamp/blob/master/lab%20resources/README.md) of the github  

1. To start today's exercises inside of BaseSteps.java, there are two methods which have to be implemented. They are marked by the 'XXX' comment. Fill in each of these methods according to the instructions in the comments, and run the following Cucumber test to verify your results: 
```
	lab-test-harness/src/test/java/com/rhc/lab/test/cucumber/RunCukesTest.java
```
	* In the Junit window, the features should still fail, but the "Given" steps should all pass successfully. Why is this the case?

The second goal of the day is to get some practice writing business rules in the Drools Rules Language. You will implement the rules and process that will confirm or revoke a venue booking request. 

2. Take a look at the Business Process Model found at the following location to ensure the ruleflow groups used in the project are defined correctly. You can open the file if you have successfully installed the BPMS tooling and look in the 'Properties' view of JBDS.
```
	lab-knowledge/src/main/resources/rules/bookingProcess.bpmn2
```
3. Locate the business rules at the following location:  
```
	lab-knowledge/src/main/resources/rules/createBooking.drl
```

4. Several empty rules have to be implemented. They are marked by the 'XXX' comments. Fill in each of these rules according to the instructions in the comments, and run RunCukesTest.java to verify the rules pass the features written.

5. Run the RunCukesTest.java again to make sure all features are passing, and throw in some log print lines to ensure your steps are executing as expected.
6. Verify the project builds successfully by running a Maven build.
7. Once the project builds, make sure that your local application can save booking requests. 
8. Then run the following Git commands to commit the files to your local repository and push the new code to your OpenShift instance: 
```
	git add . 
	git commit -m "YOUR COMMIT MESSAGE" 
	git push origin master 
```

Your application is now back to a known good state and you've completed the exercises for Day 2. If you have time left over, add some features to the lab.feature file and create some rules of your own.

##Day 3 - Integrating Services with Camel##
###Goals###
1. Learn how to write Camel routes for code-less integration

###Instructions
1. To check out today's repository branch, pull all of the branches from the upstream bootcamp repository into the directory you created yesterday:
```
	git fetch upstream
```
1. Next, checkout the Day 3 branch of the bootcamp repository:
```
	git checkout day3
```
1. Open JBDS and build the project as you did yesterday. The project should now fail to build.

The goal for today is to create a Camel route that takes a BookingRequest object created by a web form in the UI, runs that request through a series of business rules, and saves a confirmed Booking object to a MongoDB database. The Camel context, which defines the route we will be writing is found at the following location:
```
	lab-camel-services/src/main/resources/camel-context.xml		
```
You will also be tasked with configuring the camel context in the servlet container. The web.xml for the project is found at the following location:
```
	lab-web/src/main/webapp/WEB-INF/web.xml
```
For reference, read through this [example](http://camel.apache.org/servlet-tomcat-example.html) on Camel in web applications.

In this branch, there are a series of exercises marked by the "XXX" marker describing the components needed to implement the route described above. Complete the marked exercises and then test the application locally and in your OpenShift instance:

1. Verify the project builds successfully by running a Maven build.
2. Once the project builds, make sure that your local application can save booking requests. 
3. Then run the following Git commands to commit the files to your local repository and push the new code to your OpenShift instance: 
```
	git add . 
	git commit -m "YOUR COMMIT MESSAGE" 
	git push origin master 
```

##Day 4 - Continuous Integration and Delivery##
###Goals###
1. Learn how to add and use plugins in Jenkins

###Instructions###
Today we will set up a local Jenkins instance to build and test our project.

1. Download the latest Jenkins WAR (Web Archive) [here](https://updates.jenkins-ci.org/download/war/) or get our USB copy. 
1. Download the EAP 6.4.0 Zip archive and unzip (or get our USB copy). 
1. Drop the jenkins.war file inside the $EAP_HOME/standalone/deployments directory. 
1. Create an empty file in the deployments directory suffixed with "dodeploy" with the following command:
```
	touch jenkins.war.dodeploy
```
This will tell the EAP server to deploy this application on startup of the container.

1. Start the server and navigate to http://localhost:8080/jenkins. (Make sure your tomcat instance isn't running. If so, you will have port conflict issues). 
1. Navigate to the Manage Jenkins screen.
1. In the list of options on the management screen, click "Manage Plugins" (http://localhost:8080/jenkins/pluginManager).
1. Since this is the first time we're adding plugins, go to the "Advanced" tab of the Plugin Manager and in the bottom right corner, click "Check now" to update the list of available plugins.
1. Once the check has completed, navigate to the "Available" tab and select the "Cucumber Plugin", the "Cucumber Reports Plugin" and the "GitHub Plugin".
1. Click "Install without Restart". Once the installations have completed, check the box to restart Jenkins when no builds are running.
1. Navigate back to the homepage, and add a new Freestyle Software Job. This will take you to the configuration screen for the job.
1. To pull in our source code, select the Git radio button on the job page and point to our GitHub master branch. Also add this to the Github field at the top of the job config.
1. Add a new shell build step with a simple Maven install:
```
	mvn clean install
```
1. At the bottom of the job page, add a new Post-build Action "Publish cucumber results as a report". Clicking the "Advanced" button in this action reveals granular settings that can set the build to fail if, for example, there are pending Cucumber steps. Which of these do you think could be useful for different phases of a development cycle?
1. Click "Apply" then "Save" to ensure the changes are reflected, then rebuild the project with the "Build Now" button in the job page or on the homepage.

##Day 5 - Breakfix Playground##
###Goals###
1. Get familiar navigating a multi-module application to look for common breaks.

###Instructions###
1. To check out today's repository branch, pull all of the branches from the upstream bootcamp repository into the directory you created yesterday:
```
	git fetch upstream
```
1. Next, checkout the Day 5 branch of the bootcamp repository:
```
	git checkout day5
```
1. Open JBDS and build the project as you did yesterday. The project should now fail to build.

Today's lab will be a series of breakfixes in our application. Several components are currently broken in the application, and it is your job to get the application back to the known good state. The "XXX" marker has been placed in several spots of the application with hints to get you on the right track. Use the 'File Search' functionality in JBDS to locate these markers and be sure to build with Maven frequently to determine the source of the errors.

1. Verify the project builds successfully by running a Maven build.
2. Once the project builds, make sure that your local application can save booking requests. 
3. Then run the following Git commands to commit the files to your local repository and push the new code to your OpenShift instance: 
```
	git add . 
	git commit -m "YOUR COMMIT MESSAGE" 
	git push origin master 
```
