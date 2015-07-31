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
MongoDB 2.6			| [link](http://docs.mongodb.org/manual/tutorial/install-mongodb-on-red-hat/)

##Day 1 - OpenShift Application Management##
###Goals###
1. Learn how to use OpenShift Online to enable quick POCs (Proof-of-Concepts)
1. To get familiar with both the UI and CLI experience in OpenShift
1. To get familiar with the format for the lab content in the remaining days of the Bootcamp

###Instructions###
1. Run the following commands on the command line to create a new EAP 6 project called "nchlab" with large gears in OpenShift:
```
	rhc app-create nchlab jbosseap -g large 
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
The code will be pushed to OpenShift, where OpenShift will run a Maven build on the project, copy the built deployment into the JBoss EAP container, and start the container. 

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

No let's set up a local server to test out our application.

1. Make sure that you have locally installed JBoss EAP 6.4 and have a local MongoDB database running.
	* Instructions for MongoDB installation are located [here](http://docs.mongodb.org/manual/tutorial/install-mongodb-on-red-hat/). Version 2.6 is preferred, but installing v3.0 should work fine as well.
2. In the "Servers" view of JBDS, right click and click on New->Server...
3. In the following dialogues, select a new JBoss EAP 6 server and point to the EAP installation on your machine.
4. On the "Add and Remove" screen, add the lab-web project in the "Available" column to the "Configured" column and click finish.
5. In the web.xml of the lab-web project, under the "spring.profiles.active" context-param, change "openshift" to "default" to switch the Spring profile to your local configuration.
6. Start the new server by right clicking on the new server and clicking "Start"
7. Point to localhost:8080/ in your web browser and you should have the application running on your local machine with a local database.

##Day 2 - Business Rules and Process Modeling##
###Goals###
1. To get familiar with BDD (Behavior-Driven-Development) by using the Cucumber test Framework
1. Learn how to write Business Rules, and touch integration endpoints in Business Processes

###Note###
All exercises in the code are marked by the 'XXX' label, which shows up by default the JBDS Tasks view. To expose this view in JBDS, in the top toolbar, click Window->Show View->Other..., and under "General", open "Tasks".

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
1. Open JBDS and build the project as you did yesterday. The project should now fail to build. Now comes the fun part!

The first goal of the day is to learn some basic concepts of BDD. Cucumber is a popular BDD tool we use frequently on projects. The framework uses text files containing application "features", and connects the steps of each feature to a corresponding JUnit test, called a "step". There are a number of test features found in the following location:  
```
	lab-test-harness/src/test/resources/features/lab.feature  
```

The JUnit tests which implement these features are found at the following location:
```
	lab-test-harness/src/test/java/com/rhc/lab/test/cucumber/BaseSteps.java
```

1. To start today's exercises inside of BaseSteps.java, there are two methods which have to be implemented. They are marked by the 'XXX' comment. Fill in each of these methods according to the instructions in the comments, and run the following Cucumber test to verify your results: 
```
	lab-test-harness/src/test/java/com/rhc/lab/test/cucumber/RunCukesTest.java
```
	* In the Junit window, the features should still fail, but the "Given" steps should all pass successfully.

The second goal of the day is to get some practice writing business rules in the Drools Rules Language.

2. You will implement the rules that will confirm or revoke a venue booking request. Locate the business rules at the following location:  
```
	lab-knowledge/src/main/resources/rules/createBooking.drl
```

3. Several empty rules have to be implemented. They are marked by the 'XXX' comments. Fill in each of these rules according to the instructions in the comments, and run RunCukesTest.java to verify the rules pass the features written. At this point the rules will not pass. Take a look at the Business Process Model found at the following location to ensure the ruleflow groups are defined correctly:
```
	lab-knowledge/src/main/resources/rules/bookingProcess.bpmn2
```

4. Verify the project builds successfully by running a Maven build.

5. Once the project builds, make sure that your local application can save booking requests. 
6. Then run the following Git commands to commit the files to your local repository and push the new code to your OpenShift instance: 
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
1. Learn how to add and use plugins in Jenkins on top of OpenShift
1. Learn best practices in Deployment Pipelines from Justin Holmes

###Instructions###
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
