Feature: Logout

  Scenario: Logging out the user
    Given I am logged in as "Luan"
    When I logout
    Then I am logged out