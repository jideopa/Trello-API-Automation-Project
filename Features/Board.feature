Feature: Board
  Acceptance Criteria
  * User should be to create a board
  * User should be to get a board
  * User should be to delete a board

  #TODO Refactor create a board to delete board after it created
  Scenario: Create a Board
    Given a valid Post Method request is send to server
    Then a response body is received with 200 status

  Scenario: Get a board
    Given a valid Get Method request is send to server
    Then a response body is received with 200 status

  Scenario: delete broad
    Given a valid Delete Method is send to sever
    Then a response body is received with 200 status