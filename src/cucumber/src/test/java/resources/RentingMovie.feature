Feature: Renting a Movie

  Scenario: Renting a movie

    Given I am logged in as "Wamika"
    When I choose movie "Logan"
    And I choose "2" days
#    Then I get a receipt with message "Amount charged is $8.0"
#    Then I get a receipt with message "You have a new total of 3 frequent renter points"
