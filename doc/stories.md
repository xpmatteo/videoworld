# Stories

Sources of information:

 - The original pptx presentation
 - The docx "detailed agenda"
 - Stories.pptx



## Pairing
#### Story #9

As a customer, I want to see my frequent renter points for this order in the Transaction History,  So I know when I'm eligible for a free rental

Acceptance criteria
  - One point per rental
  - Display on statement history

Story #9 ALT - As a customer, I want to see my current frequent renter points so I know when I am eligible to receive a free rental.

*This should be a very basic story to get up and running as well as understanding the code base. There should be minimal required refactoring/testing.*

Lessons: Pairing

## TDD

#### Story #1

As the marketing coordinator
I want to introduce a new pricing model for new releases giving 1 free day for week rental
So that I can encourage longer rentals

*This is the start of learning about refactoring. Before refactoring in order to complete this story, we need to make sure the students enhance the existing tests to ensure we do not break functionality.*

Lessons: More pairing ideas & Some Refactoring

#### Story #11
As a content provider
I want to have a promotion where every regular 3 day rental gets an extra day
So that I can encourage longer rentals

## Refactoring

#### Refactoring exercise

Refactor the comments in Customer.statement

Identify and refactor the violations of the LoD  in Customer.statement


#### Story #7

As a customer
I want to see previous receipts
So that I can balance my checkbook

*This brings refactoring & TDD together. For the refactoring, the focus is on using Design Patterns to help improving the design*

## Redesign

## Mocking

#### Mocking exercise

Test the total ViewCurrentRentalsAction Stubbing the rentalRepository

ALT VERSION Test the total ViewCurrentRentalsAction, Mocking the customer or the transactions

ALT VERSION Login and Logout controller unit tests


#### Story #10

As a customer
I want to see previous receipts
So that I can balance my checkbook

ALT VERSION
I want to see how much total I’ve spent
So I can manage my budget




## Functional Testing

#### Story #2

As a content provider
I want to have a promotion where every three-day rental gets an extra day for children’s releases
So that I can encourage longer rentals


## Continuous Integration
#### Story #8

As a sales manager
I want to allow a free regular movie when the customer has 5 frequent renter points
So that I can encourage repeat customer




## Others from detailed agenda.docx

#### Story #3 - As a customer, I want to see how many points I earned for my latest rentals I can see how fast I'm accruing points

*This will focus mostly on TDD (although there will clearly be some additional refactoring to patterns around this)*

Lessons: TDD and more Design Patterns

#### Story #2

As the movie buyer, I want to be able to add newly purchased movies so customers can rent them

*The purpose of this story is to introduce automated acceptance testing.  In order to minimize complexity of new technologies, we will introduce Twist 2.0 using Sahi and its recorder capability. The story complexity will be minimized so that the Developers can focus on writing the integration test.
Lessons: More Design Patterns and Integration testing*


#### Story #10

As a store manager, I want to rename the section on the site from 'Current Rentals' to 'My Rentals' so that users are aware of the extra information

*The major purpose of this story is around keeping domain language consistent within integration tests and code. We will also comment on the different approaches of writing integration tests (i.e. test first, finish story before updating tests). Pairs can decide which they do. We'll also show them an already running Go instance and hook up CC tray. Each pair will have a pipeline & we will encourage them to check in when they finish the story.*

Lessons: Integration testing & CI

#### Story #8

As a store manager, I want to prevent customers from renting movies that are no longer in stock to prevent complaints

*This story will already be checked in and will represent work completed by an external dev team. It will have broken the build, but in a tricky way. Basically, we want the test to have to tear down, which will cause an individual test to succeed, but running the suite will fail. The pairs will have to fix the build.*

Lessons: CI


#### Story #6

As a customer, I want to return a movie so I don't get charged an overage

*This is mostly recap of other things and also an introduction into build pipelining. After checking in, we will show them a continuous deployment setup that installs to a staging environment.*

Lessons: Deployment Pipeline

## Others from combined_deck.pptx


#### Story #1

As a marketing executive
I want to have different pricing for newly released movies
So that I can increase my profit

Details:

Pricing Model for newly released movies:

Up to 2 days $3 and after that everyday $2

e.g.
1 day rental - $3
2 day rental - $3
3 day rental - $5 ( $3 + $2)
4 day rental - $7 ($3 + (2*$2))

#### Story #2

As a marketing executive
I want to have different pricing for classic movies
So that customer can enjoy classic movies for long duration with low rentals

Details:

Pricing Model for classic movies:
Everyday $0.5, for a week $3 and after than everyday $0.5

e.g.
1 day rental - $0.5
2 day rental - $1
7 day rental - $3
8 day rental - $3.5 ($3 + $0.5)

Note: enhance application to allow upto 10 days of rental

#### Story #3

As a marketing executive
to have frequent renter points program
So that so that customer can win free movies and we get recurring customers

Details:

Newly Released Movies: 2 points per day
All others:            1 point per $ spent

#### Story #6

As a customer
I want to to return my video (single video return using current rentals screen)
So I complete my transaction


#### Story #7

As a store manager
I want to to manage video inventory per movie basis
So that I know when run out of stock
