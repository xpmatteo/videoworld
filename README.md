
Source code for the Agile Development Practices course by ThoughtWorks

This is a fork of part of the original repository: https://github.com/ThoughtWorksInc/TWTraining

This material is shared through a [CC BY-SA 4.0](https://creativecommons.org/licenses/by-sa/4.0/) license.


### Questions

 - why do stories talk about "orders" while the class is named "transaction?"

 - why the discrepancy between the order and content of tests in the pptx and in the "detailed agenda"?

 - what is story #8 "already checked in and it breaks the build"???

 - how could I show the customer name in the layout of the page (main.dec) ?  How to pass data to the decorator in freemarker?  Should I use the session object?



### Todo

 - Fix text of first user story

 - run jetty from main method not from war
   - makes it difficult to see css changes
   - requires reboot to see ftl changes

 - use proper currency type, not double
 - remove imports of junit 3
 - remove single reference to javax.persistence.Entity and lib/runtime/ejb3-persistence.jar

 - Transaction -> Order

 - maybe, eliminate RentalRepository and only use TransactionRepository




 - Set up a VM with Gitlab with a clone of this repo
 - Set up GO CI on same VM

###  Frameworks

* Struts -- struts.apache.org
* JUnit -- junit.org
* Freemarker -- freemarker.sourceforge.net
* Ant -- ant.apache.org
* Jetty -- jetty.codehaus.org

