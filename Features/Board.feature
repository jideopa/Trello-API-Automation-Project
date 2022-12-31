@smoke @regression
Feature: Boards
  Acceptance Criteria
  * User should be able to create a board
  * User should be able to get a board
  * User should be able to delete a board

  Scenario: Create a Board
    Given a valid Post Method request is send to server
    Then a response body is received with 200 status

  Scenario: Get a board
    Given a valid Get Method request is send to server
    Then a response body is received with 200 status

  Scenario: delete broad
    When a valid Delete Method is send to sever
    Then a response body is received with 200 status