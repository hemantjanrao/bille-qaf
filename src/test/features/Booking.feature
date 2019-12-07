Feature: Booking feature

  Scenario: Create new booking
    Given booking service is up
    When Booking creates booking
    Then Booking should be successfully created

  Scenario: Update booking
    Given booking service is up
    When Booking creates booking
    And Booking should be successfully created
    And Update the created booking
    Then Booking should be updated successfully

  Scenario: Delete booking
    Given booking service is up
    When Booking creates booking
    And Booking should be successfully created
    And Delete the created booking
    Then Booking should be deleted successfully
    And Deleted booking should not be accessible

  Scenario: Create new booking and access it
    Given booking service is up
    When Booking creates booking
    Then Booking should be successfully created
    And Get the same booking