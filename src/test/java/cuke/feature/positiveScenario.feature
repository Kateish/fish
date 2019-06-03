Feature: send get request to fishtext api and get the expected result

  Scenario Outline: when a user sends get requests with the params from the table, the results match resulting column values
    When a user sends a GET request with number like "<number>", type like "<type>", format like "<format>"
    Then the result string is
#    Then the user gets "<result>" entities
#    And the status is OK
    Examples:
      | number | type     | format | result |
      | 2      | sentence | html   | 2      |
#      | 3      | sentence | html   | 3      |
#      | 2      | sentence                 |           |
#      | 2      |                          | html      |
#      | 2      |                          | json      |
#      | 2      | paragraph                | json      |
#      | 3      | title                    | json      |
#      | 2      | sentence,paragraph,title | html,json |
#

