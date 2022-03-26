Feature: Create payment via Checkout Form

  Background:
    Given Api specifications is set

  Scenario Outline: Initialize a checkout form
    Given User wants to use valid <cardNumber> card to checkout
    When The request is sent
    Then Checkout form is initialized

    Examples:
      | cardNumber       |
      | 4603450000000000 |
      | 5170410000000004 |
      | 5504720000000003 |

  Scenario Outline: Successful payment after Checkout Form initialization
    Given User wants to use valid <cardNumber> card to checkout
    And The request is sent
    When User is directed to checkout form
    And User fills the form with <cardNumber>, "<cardName>", "<expDate>" and "<cvcNumber>"
    Then User should input sms code to confirm
    And Transaction should be completed "successfully" via "checkout form"
    Examples:
      | cardNumber       | cardName     | expDate | cvcNumber |
      | 4603450000000000 | Ahmet Mehmet | 06/25   | 071       |
      | 5170410000000004 | Hasan Osman  | 03/28   | 068       |
      | 5504720000000003 | Kemal Kamil  | 02/24   | 459       |

  Scenario: Complete payment with not sufficient funds error after Checkout Form initialization
    Given User wants to use valid 4111111111111129 card to checkout
    And The request is sent
    When User is directed to checkout form
    And User fills the form with 4111111111111129, "Ahmet Mehmet", "06/26" and "754"
    Then User should input sms code to confirm
    And Transaction should be completed "with error" via "checkout form"