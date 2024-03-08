Feature: Validate Create Order API's
  @createOrder
  @Capture
  Scenario Outline: Verify createOrder API
    Given User is authenticated with "<username>" and "<password>" using "loginAPI"
   # When User calls "loginAPI" api with "POST" Request
    And User creates Order using "addProductAPI"
    Then API call is successful with message "Product Added Successfully"


    Examples:
      | username            | password     |
      |rahulshetty@gmail.com|Iamking@000   |