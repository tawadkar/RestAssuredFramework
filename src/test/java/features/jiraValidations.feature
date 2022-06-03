Feature: Validate Jira API's
@JiraLogin
  Scenario: Validate JIRA Login Scenario
  Given User logs in with "username" and "password"
  When  User calls "loginJiraAPI" with "POST" request
  Then  Login should be successful with status code 200



