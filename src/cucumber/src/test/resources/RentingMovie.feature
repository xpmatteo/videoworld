Feature: Renting Movie

  Scenario: Renting multiple movies

    Given I am logged in as "Wamika"
    When I choose movie:
      | Logan       |
      | John Wick 2 |
    And I choose "2" days
    Then I get a receipt with message:
      | Amount charged is $8.0"                          |
      | You have a new total of 3 frequent renter points |