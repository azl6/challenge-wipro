Feature: meme feature

  Scenario: user passes a valid CEP and gets a valid address back
    Given a valid CEP
      | numeroCep |
      | 01001-000 |
    When the user sends the valid request
    Then a valid address is returned

  Scenario: user passes a blank CEP and gets MethodArgumentNotValidException
    Given a blank CEP
    When the user sends the request with the blank CEP
    Then he gets MethodArgumentNotValidExcepion with Regex and Blank errors

  Scenario: user passes a null CEP and gets MethodArgumentNotValidException
    Given a null CEP
    When the user sends the request with the null CEP
    Then he gets MethodArgumentNotValidExcepion with Null and Blank errors

  Scenario: user passes a CEP out of the XXXXXXXX or XXXXX-XXX format
    Given a CEP out of the valid formats
      | numeroCep |
      | 0-1-0-0-1-0-0-0 |
    When the user sends a request with the CEP of invalid format
    Then he gets MethodArgumentNotValidExcepion with Regex errors