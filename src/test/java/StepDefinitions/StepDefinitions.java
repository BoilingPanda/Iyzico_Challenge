package StepDefinitions;

import com.iyzipay.model.*;
import com.iyzipay.request.CreateCheckoutFormInitializeRequest;
import com.iyzipay.request.CreatePaymentRequest;
import com.iyzipay.request.CreateRefundRequest;
import com.iyzipay.request.RetrievePaymentRequest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import pages.*;

import static org.junit.Assert.*;
import static pages.Base.goToCheckoutForm;
import io.cucumber.java.en.*;
import java.math.BigDecimal;


public class StepDefinitions {

    CheckoutFormInitialize checkoutFormInitialize;
    CreatePaymentRequest apiRequest;
    Payment payment;
    Payment retrievedPayment;
    CreateRefundRequest refundRequest;
    Refund refund;
    private PaymentPage paymentPage;
    private ConfirmationCodePage confirmationCodePage;


    @Given("Api specifications is set")
    public void apiSpecificationsIsSet() {
        SupportingMethods.setUp();
    }

    @Given("User wants to use valid {long} card to checkout")
    public void userWantsToUseCardToCheckout(long cardNumber) {
        assertTrue("Credit card is invalid", SupportingMethods.isValid(cardNumber));
    }

    @When("Checkout form is initialized")
    public void checkoutFormIsInitialized() {
        assertEquals(Status.SUCCESS.getValue(), checkoutFormInitialize.getStatus());
        assertEquals("E1512", checkoutFormInitialize.getConversationId());
        assertNull(checkoutFormInitialize.getErrorCode());
        assertNull(checkoutFormInitialize.getErrorMessage());
        assertNull(checkoutFormInitialize.getErrorGroup());
        assertNotNull(checkoutFormInitialize.getCheckoutFormContent());
        assertNotNull(checkoutFormInitialize.getPaymentPageUrl());
        System.out.println(checkoutFormInitialize.getPaymentPageUrl());
    }

    @When("Payment request is sent")
    public void paymentRequestIsSent() {
        assertEquals("173.46", payment.getPaidPrice().toString());
        assertEquals(2, payment.getPaymentItems().size());
        assertEquals("147", payment.getPrice().toString());
        assertEquals("CREDIT_CARD", payment.getCardType());
        assertEquals("API822", payment.getBasketId());
        assertEquals("I0001", payment.getPaymentItems().get(0).getItemId());
        assertEquals("128.25", payment.getPaymentItems().get(0).getPrice().toString());
        assertEquals("I0002", payment.getPaymentItems().get(1).getItemId());
        assertEquals("18.75", payment.getPaymentItems().get(1).getPrice().toString());
    }

    @When("User is directed to checkout form")
    public void userIsDirectedToCheckoutForm() {
        Base.setUp();
        paymentPage = goToCheckoutForm(checkoutFormInitialize.getPaymentPageUrl());
    }

    @And("User fills the form with {long}, {string}, {string} and {string}")
    public void userFillsTheFormWithCardNumberAndCvcNumber(long cardNumber, String cardName, String expDate, String cvcNumber) {
        paymentPage.fillCardNameInput(cardName);
        paymentPage.fillCardInput(String.valueOf(cardNumber));
        paymentPage.fillExpDateInput(expDate);
        paymentPage.fillCvcInput(cvcNumber);
        paymentPage.clickThreeDS();

        confirmationCodePage = paymentPage.clickPaymentButton();
    }

    @Then("User should input sms code to confirm")
    public void userShouldInputSmsCodeToConfirm() throws InterruptedException {
        confirmationCodePage.fillSmsCodeInput();
        confirmationCodePage.clickSubmitButton();
    }

    @Then("Transaction should be completed {string} via {string}")
    public void transactionShouldBeCompleted(String arg0, String arg1) {
        if (arg1.equalsIgnoreCase("checkout form")) {
            CheckoutForm checkoutForm = SupportingMethods.checkRequestResult(checkoutFormInitialize);
            if (arg0.equalsIgnoreCase("successfully")) {
                assertEquals(Status.SUCCESS.getValue(), checkoutForm.getPaymentStatus().toLowerCase());
                assertEquals(Status.SUCCESS.getValue(), checkoutForm.getStatus());
                assertNull(checkoutForm.getErrorMessage());
                assertNull(checkoutForm.getErrorCode());
                assertNull(checkoutForm.getErrorGroup());
            } else if (arg0.equalsIgnoreCase("with error")) {
                assertEquals("FAILURE", checkoutForm.getPaymentStatus());
                assertEquals(Status.FAILURE.getValue(), checkoutForm.getStatus());
                assertEquals("NOT_SUFFICIENT_FUNDS", checkoutForm.getErrorGroup());
                assertEquals("10051", checkoutForm.getErrorCode());
                assertEquals("Kart limiti yetersiz, yetersiz bakiye", checkoutForm.getErrorMessage());
            }
        } else if (arg1.equalsIgnoreCase("api")) {
            if (arg0.equalsIgnoreCase("successfully")) {
                assertNotEquals(0, payment.getSystemTime());
                assertNull(payment.getErrorCode());
                assertNull(payment.getErrorMessage());
                assertNull(payment.getErrorGroup());
                assertEquals(Status.SUCCESS.getValue(), payment.getStatus());
                assertEquals(Locale.TR.getValue(), payment.getLocale());
                assertEquals("E1512", payment.getConversationId());
            } else if (arg0.equalsIgnoreCase("with error")) {
                //TODO
            }
        }
    }

    @Then("The request is sent")
    public void checkoutFormLinkShouldBeReturned() {
        CreateCheckoutFormInitializeRequest request = new CreateCheckoutFormInitializeRequest();
        SupportingMethods.InitializeCheckoutForm(request);
        checkoutFormInitialize = CheckoutFormInitialize.create(request, SupportingMethods.options);
    }

    @Given("An api payment request should be created")
    public void anApiPaymentRequestShouldBeCreated() {
        apiRequest = new CreatePaymentRequest();
        SupportingMethods.createPaymentWithApi(apiRequest);
        payment = Payment.create(apiRequest, SupportingMethods.options);
    }


    @And("Payment request is retrieved")
    public void paymentRequestIsRetrieved() {
        RetrievePaymentRequest retrievePaymentRequest = new RetrievePaymentRequest();
        retrievePaymentRequest.setLocale(payment.getLocale());
        retrievePaymentRequest.setConversationId(payment.getConversationId());
        retrievePaymentRequest.setPaymentId(payment.getPaymentId());

        retrievedPayment = Payment.retrieve(retrievePaymentRequest, SupportingMethods.options);
    }

    @Then("Retrieving should be completed")
    public void retrievingShouldBeCompleted() {
        assertNotEquals(0, retrievedPayment.getSystemTime());
        assertNull(retrievedPayment.getErrorCode());
        assertNull(retrievedPayment.getErrorMessage());
        assertNull(retrievedPayment.getErrorGroup());
        assertEquals(Status.SUCCESS.getValue(), retrievedPayment.getStatus());
        assertEquals(Locale.TR.getValue(), retrievedPayment.getLocale());
        assertEquals("E1512", retrievedPayment.getConversationId());

        assertEquals("173.46000000", retrievedPayment.getPaidPrice().toString());
        assertEquals(2, retrievedPayment.getPaymentItems().size());
        assertEquals("147.00000000", retrievedPayment.getPrice().toString());
        assertEquals("CREDIT_CARD", retrievedPayment.getCardType());
        assertEquals("API822", retrievedPayment.getBasketId());
        assertEquals("I0001", retrievedPayment.getPaymentItems().get(0).getItemId());
        assertEquals("128.25000000", retrievedPayment.getPaymentItems().get(0).getPrice().toString());
        assertEquals("I0002", retrievedPayment.getPaymentItems().get(1).getItemId());
        assertEquals("18.75000000", retrievedPayment.getPaymentItems().get(1).getPrice().toString());
    }

    @Then("Refund transaction should be completed")
    public void refundTransactionShouldBeCompleted() {
        assertEquals(Status.SUCCESS.getValue(), refund.getStatus());
        assertEquals(Locale.TR.getValue(), refund.getLocale());
        assertEquals("E1512", refund.getConversationId());
        assertNotEquals(0, refund.getSystemTime());
        assertNull(refund.getErrorCode());
        assertNull(refund.getErrorMessage());
        assertNull(refund.getErrorGroup());
        assertEquals(refundRequest.getConversationId(), refund.getConversationId());
        assertEquals(refundRequest.getPaymentTransactionId(), refund.getPaymentTransactionId());
        assertEquals(refundRequest.getPrice(), refund.getPrice());
    }

    @When("Refund request is sent for item in order {int}")
    public void refundRequestIsSentForItemInOrder(int arg0) {
        int index = arg0 - 1;
        refundRequest = new CreateRefundRequest();
        refundRequest.setLocale(Locale.TR.getValue());
        refundRequest.setConversationId(payment.getConversationId());
        refundRequest.setPaymentTransactionId(payment.getPaymentItems().get(index).getPaymentTransactionId());
        refundRequest.setPrice(new BigDecimal(payment.getPaymentItems().get(index).getPrice().toString()));
        refundRequest.setCurrency(Currency.TRY.name());
        refundRequest.setIp("127.0.0.1");

        refund = Refund.create(refundRequest, SupportingMethods.options);
    }


}
