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

That's it! You now have a web application running business rules and Camel services on top of a MongoDB database.

##Day 2 - Business Rules and Process Modeling##
###Goals###
1. Learn how to write Business Rules, and touch integration endpoints in Business Processes
1. To get familiar with BDD (Behavior-Driven-Development) by using the Cucumber test Framework

###Instructions###

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
