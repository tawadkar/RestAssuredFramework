Feature: Validate Create Order API's
  @createOrder
  @Capture
  Scenario Outline: Verify createOrder API
    Given User is authenticated with "<username>" and "<password>" using "loginAPI"
   # When User calls "loginAPI" api with "POST" Request
    When User adds Order using "addProductAPI"
    Then API call is successful with message "Product Added Successfully"
    And User creates Order using "createOrderAPI"

#User Name keeps on changing , update it from V76
    Examples:
      | username            | password     |
      |anshika@gmail.com|Iamking@000   |