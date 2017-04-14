[![Build Status](https://travis-ci.org/xpmatteo/videoworld.svg?branch=master)](https://travis-ci.org/xpmatteo/videoworld)

Source code for the Agile Development Practices course by ThoughtWorks

This is a fork of part of the original repository: https://github.com/ThoughtWorksInc/TWTraining

This material is shared through a [CC BY-SA 4.0](https://creativecommons.org/licenses/by-sa/4.0/) license.

### Instructions

How to refresh the Eclipse project files:

    ./gradlew cleanEclipse eclipse

How to run in the IDE:

  * Execute in debug mode class com.thoughtworks.videorental.main.VideoWorldMain
  * Open the browser at http://localhost:8080

How to run from the command line

    ./gradlew appStart

and then open the browser at http://localhost:8081

How to run unit tests

    ./gradlew test

How to run end-to-end tests headless mode

    ./gradlew cucumber -Dmode=headless

How to run end-to-end tests browser mode

    ./gradlew cucumber

How to run a specific scenario

    ./gradlew cucumber -Dcucumber.options=src/cucumber/src/test/resources/**.feature

### Questions

 - why do stories talk about "orders" while the class is named "transaction?"

 - why the discrepancy between the order and content of tests in the pptx and in the "detailed agenda"?

 - what is story #8 "already checked in and it breaks the build"???

BEFORE TRAINING

 - Fix first user story
 - review the sequence of user stories (and didactic intent)
 - Set up a VM with Gitlab with a clone of this repo
 - Set up GO CI on same VM

