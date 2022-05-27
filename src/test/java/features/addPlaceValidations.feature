Feature: Validate Place API's

  Scenario Outline: Verify addPlace API
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When User calls "addPlace" api with POST Request
    Then API call is successful with status code 200
    And "Status" in response body is "OK"

    Examples:
    | name   | language | address          |
    |TestUser1|English   |World Trade Center|
    |TestUser2|French    |World Cross Center|