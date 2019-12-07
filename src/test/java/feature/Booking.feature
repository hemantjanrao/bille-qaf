Feature: Get car offer from auto1.com

  Scenario: Create new booking
    Given booking service is up
    When User creates booking
    Then User should be successfully created

  Scenario: Update booking
    Given booking service is up
    When User creates booking
    And User should be successfully created
    And Update the created booking
    Then Booking should be updated successfully
