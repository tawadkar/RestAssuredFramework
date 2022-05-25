Feature: Validate Place API's

  Scenario: Verify addPlace API
    Given Add Place Payload
    When User calls "addPlace" api with POST Request
    Then API call is successful with status code 200
    And "Status" in response body is "OK"