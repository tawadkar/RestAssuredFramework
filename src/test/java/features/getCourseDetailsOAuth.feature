Feature: Validate Get Course Details using OAUth

  @GetPlace
  Scenario: Validate Get Course Details
    Given User is validated through OAuth
    When User calls getCourseDetailsAPI
    Then user should get CouseDetails with status code 200
