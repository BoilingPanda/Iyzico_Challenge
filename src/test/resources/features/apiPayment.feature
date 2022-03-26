Feature: Transactions via Api

  Background:
    Given Api specifications is set

  Scenario: Successful payment with api request for 2 different basket item
    Given User wants to use valid 4543590000000006 card to checkout
    And An api payment request should be created
    When Payment request is sent
    Then Transaction should be completed "successfully" via "api"

  Scenario: Check successful api payment's result
    Given User wants to use valid 4543590000000006 card to checkout
    And An api payment request should be created
    When Payment request is sent
    And Payment request is retrieved
    Then Retrieving should be completed

  Scenario: Refund a payment via api
    Given User wants to use valid 4543590000000006 card to checkout
    And An api payment request should be created
    And Payment request is sent
    When Refund request is sent for item in order 1
    Then Refund transaction should be completed
