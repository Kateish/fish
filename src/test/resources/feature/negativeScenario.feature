
Feature: poll the fishapi url with invalid params and get errors

#  t6
  @fail
  Scenario: send a POST request to fishAPI URL
    When a user sends a POST request to fishAPI URL and gets the status Not Found


# t5
  @fail
  Scenario: send a GET request to fishAPI URL with too big number
    When a user sends a GET request to fishAPI URL with number = "100000", the response is "You requested too much content. Be more moderate"

