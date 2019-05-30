Feature: send get request to fishtext api and get the expected result

  Scenario: when a user sends get requests with the params from the table, the results match resulting column values
    When a user sends a GET request with number like "<number>", type like "<type>", format like "<format>"
    Then the result string matches "<result>"
    And the status is OK
      | number | type     | format | result |
      | null   | sentence | html   |        |
      | 2      |          |        |        |
      | 3      |          |        |        |
      | 2      |          |        |        |
      | 2      |          |        |        |
      | 2      |          |        |        |
      | 2      |          |        |        |
      |        |          |        |        |
      |        |          |        |        |
      |        |          |        |        |
      |        |          |        |        |
      |        |          |        |        |
      |        |          |        |        |

