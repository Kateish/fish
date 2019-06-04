Feature: send get request to fishtext api and get the expected result

  @success
  Scenario Outline: when a user sends get requests with the params from the table, the results match resulting column values
    When a user sends a GET request with number like "<number>", type like "<type>", format like "<format>" and gets "<result>" items in response
    Examples:
      | number | type      | format | result |
      | 2      | sentence  | html   | 2      |
      | 3      | paragraph | html   | 3      |


