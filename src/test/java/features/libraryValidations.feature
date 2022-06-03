Feature: Validate Library API
@AddBook
  Scenario Outline: Validate add Book api
    Given User adds book with "name","isbn","aisle" and "author" details
    When The User calls "addBookAPI" api with "POST" request
    Then The API call is successful with status code 200
    And Verify book_id created after its added to verify its "<name>" using "getBookAPI"

  Examples:
  |name               |
  |Rest Assured Basics|