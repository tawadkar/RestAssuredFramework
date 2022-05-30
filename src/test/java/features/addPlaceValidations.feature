Feature: Validate Place API's
@AddPlace
  Scenario Outline: Verify addPlace API
    Given Add Place Payload with "<name>" "<language>" "<address>"
    #addPlaceAPI name should be same as one given in Enum file
    When User calls "addPlaceAPI" api with "POST" Request
    Then API call is successful with status code 200
    And "Status" in response body is "OK"
    And verify place_id from created map to "<name>" using "getPlaceAPI" api

    Examples:
    | name   | language  | address          |
    |TestUser1|English   |World Trade Center|
   # |TestUser2|French    |World Cross Center|

  @DeletePlace
  Scenario: Verify deletePlaceAPI
    Given :User loads deletePlace payload
    When User calls "deletePlaceAPI" api with "POST" Request
    Then API call is successful with status code 200
