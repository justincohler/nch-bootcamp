# Associate Consultant Bootcamp Lab Extra Materials #

##Helpful links overall##
[BRMS 6.1 docs](https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BRMS/)  
[Cucumber](https://cucumber.io/)  
[Camel](http://camel.apache.org/)  
[Basic Architecture of Lab Content](http://redhat.slides.com/jcohler/nchbootcamplaboverview/live#/)


##How to Add Print Statments##

#### Java ####
normal System.out.println() works fine

#### Cucumber ####
add System.out.println() statements within the step code (the java classes)

#### DRL ####
add System.out.println() statements in the then step of a rule to print out after a rule has fired

## Day 2 Supplimental Agile/BDD Materials ##

###Business Requirements Gathering ###

####Purpose####
To demonstrate what a requirements session would look like at a client.

####Potential pitfalls (no where near a complete list)####
purposefully vague feature  
conflicting ranking of priority the features  
misinformation  

####Setting####
 You are on a project at a client site and get called into a meeting. The Red Hat team has been working hard to put one of their applications up on Open Shift and now they have brought in new consultants to build out some new features. The new feature can be summed up into a single line “I don’t want a band and an orchestra to play on the same night”. Your job is to transform that request into an actionable story. 


####Established Process####
Story: As a _______, I need ____________ in order to _______________. (who, what, why)  
Acceptance Criteria: Given, When , Then (english description of the system)  
Cucumber Specification By Example: Given, When, Then (specific examples)  


Questions to think about:  
    Who? - who wants this and why is it important/who does it effect? Who is your audience?  
    What? - What does this request mean?  What are they asking for?  (swing set example)  
        do not leave any stone unturned. Any ambiguity causes gaps for questions  
    Why? - why do they want this? What is their motivation? Do they really need something else? Is this deadline artificial or strict?  
    How? - What is the tech stack? What does the domain look like? How does this fit with the current architecture? Can you reuse things?  

Now you are in a meeting with the client and you need to plan out the new feature. Work as a team to figure out what that request really means.  

###Brief Overview of Cucumber###

#####10,000 ft View#####
Behavior Driven Development (BDD) allows for a common language/framework for business and technical resources to discuss and define the behavior of a system. Common language facilitates clarity and helps relieve the risk of missed scope/erroneous estimates.

#####1000 ft overview of Cucumber#####
Cucumber is a BDD tool developed as a way to use documentation of system behavior as actual tests. It takes a gherkin feature file (with Given, When, Then scenarios) and uses a Step definition class as code to run it. The scenarios act as living documentation as it provides clear specific examples of the behavior and its also used as unit and system regression tests. 

#####500ft View#####
Cucumber.xml - Spring File that defines the Application Context and any necessary beans  
*Steps.java - Java code that maps to the english in the feature file  
*.feature - Gherkin that reads almost like plain english  
Runner(Junit) - java class that pieces the code together (use these features files with this step code)  

#####100ft View#####
Annotation Driven(@Given, @And, @When, @Then)  
Tests can be separated out into different runners by tags (@wip, @ignore)  
Tests only work as tests if you have Asserts in your @Then  
Reusing the same pattern in a feature files = less java code needed and faster scenario writing  
Setting up a background will allow easy base line for all scenarios in a file (think @Before in junit)  



