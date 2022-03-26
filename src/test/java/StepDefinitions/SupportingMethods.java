package StepDefinitions;

import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreateCheckoutFormInitializeRequest;
import com.iyzipay.request.CreatePaymentRequest;
import com.iyzipay.request.RetrieveCheckoutFormRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SupportingMethods {

    protected static Options options;
    CreateCheckoutFormInitializeRequest request;
    CreatePaymentRequest apiRequest;

    protected static void setUp() {
        options = new Options();
        options.setApiKey("sandbox-j0iL1Jsh6FTWQaMdYMHuZbVaAXOWaRDi");
        options.setSecretKey("sandbox-qOyTRXD7pAIU9zuf9e3oT2f5fAn1C1K5");
        options.setBaseUrl("https://sandbox-api.iyzipay.com");
    }

    protected static boolean isValid(long cardNumber) {
        int total = 0;
        int length = getLength(cardNumber);
        for (int i = 0; i < length; i++) {
            long digit = cardNumber % 10;
            cardNumber = cardNumber / 10;

            if (i % 2 == 0) {
                total += digit;
            } else {
                long doubledDigit = digit * 2;

                if (doubledDigit > 9) {
                    doubledDigit = (doubledDigit % 10) + (doubledDigit / 10);
                }
                total += doubledDigit;
            }
            if (cardNumber == 0) {
                break;
            }
        }

        if (total % 10 == 0) {
            return true;
        } else {
            return false;
        }


    }

    protected static int getLength(long cardNumber) {
        int length = 1;
        while (cardNumber > 9) {
            length++;
            cardNumber /= 10;
        }
        return length;
    }

    protected static void setBody(CreateCheckoutFormInitializeRequest request) {
        request.setLocale("TR");
        request.setConversationId("E1512");
        request.setPrice(new BigDecimal("147"));
        request.setPaidPrice(new BigDecimal("173.46"));
        request.setCurrency(Currency.TRY.name());
        request.setBasketId("I822");
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());
        request.setCallbackUrl("https://www.merchant.com/callback");
        request.setEnabledInstallments(new ArrayList<Integer>(Arrays.asList(2, 3, 6, 9)));
    }

    private static void setBuyer(CreateCheckoutFormInitializeRequest request) {
        Buyer buyer = new Buyer();
        buyer.setId("NA1512");
        buyer.setName("Ergin");
        buyer.setSurname("ARICI");
        buyer.setGsmNumber("+905066218175");
        buyer.setEmail("erginarici@gmail.com");
        buyer.setIdentityNumber("31567845632");
        buyer.setLastLoginDate("2022-03-23 02:12:09");
        buyer.setRegistrationDate("2022-03-23 03:12:09");
        buyer.setRegistrationAddress("Gebze , Yenikent Mh. 2464 Sk. No:4/C");
        buyer.setIp("127.0.0.1");
        buyer.setCity("Kocaeli");
        buyer.setCountry("Turkey");
        buyer.setZipCode("41400");
        request.setBuyer(buyer);
    }

    private static void setAddress(CreateCheckoutFormInitializeRequest request) {
        Address address = new Address();
        address.setContactName("Ergin ARICI");
        address.setCity("Kocaeli");
        address.setCountry("Turkey");
        address.setAddress("Gebze , Yenikent Mh. 2464 Sk. No:4/C");
        address.setZipCode("41400");
        request.setShippingAddress(address);
        request.setBillingAddress(address);
    }

    private static void setBasket(CreateCheckoutFormInitializeRequest request) {
        List<BasketItem> basket = new ArrayList<BasketItem>();
        basket.add(setItem(basket, "I0001", new BigDecimal("128.25"), "Monitor", "Computer&Electronics", "Monitors", "PHYSICAL"));
        basket.add(setItem(basket, "I0002", new BigDecimal("18.75"), "Keyboard", "Computer&Electronics", "Keyboards", "PHYSICAL"));

        request.setBasketItems(basket);
    }

    private static BasketItem setItem(List<BasketItem> basket, String id, BigDecimal price, String name,
                                      String category1, String category2, String itemType) {
        BasketItem item = new BasketItem();
        item.setId(id);
        item.setPrice(price);
        item.setName(name);
        item.setCategory1(category1);
        item.setCategory2(category2);
        item.setItemType(itemType);
        return item;
    }

    protected static void InitializeCheckoutForm(CreateCheckoutFormInitializeRequest request) {
        setBody(request);
        setBuyer(request);
        setAddress(request);
        setBasket(request);
    }

    protected static CheckoutForm checkRequestResult(CheckoutFormInitialize checkoutFormInitialize) {
        RetrieveCheckoutFormRequest request = new RetrieveCheckoutFormRequest();
        request.setLocale(Locale.TR.getValue());
        request.setConversationId(checkoutFormInitialize.getConversationId());
        request.setToken(checkoutFormInitialize.getToken());

        return CheckoutForm.retrieve(request, options);
    }

    protected static void createPaymentWithApi(CreatePaymentRequest apiRequest){
        setBody(apiRequest);
        setPaymentCard(apiRequest);
        setBuyer(apiRequest);
        setAddress(apiRequest);
        setBasket(apiRequest);
    }

    protected static void setBody(CreatePaymentRequest request) {
        request.setLocale("TR");
        request.setConversationId("E1512");
        request.setPrice(new BigDecimal("147"));
        request.setPaidPrice(new BigDecimal("173.46"));
        request.setCurrency(Currency.TRY.name());
        request.setBasketId("API822");
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setInstallment(1);
    }

    private static void setBuyer(CreatePaymentRequest request) {
        Buyer buyer = new Buyer();
        buyer.setId("NA1512");
        buyer.setName("Ergin");
        buyer.setSurname("ARICI");
        buyer.setGsmNumber("+905066218175");
        buyer.setEmail("erginarici@gmail.com");
        buyer.setIdentityNumber("31567845632");
        buyer.setLastLoginDate("2022-03-23 02:12:09");
        buyer.setRegistrationDate("2022-03-23 03:12:09");
        buyer.setRegistrationAddress("Gebze , Yenikent Mh. 2464 Sk. No:4/C");
        buyer.setIp("127.0.0.1");
        buyer.setCity("Kocaeli");
        buyer.setCountry("Turkey");
        buyer.setZipCode("41400");
        request.setBuyer(buyer);
    }

    private static void setAddress(CreatePaymentRequest request) {
        Address address = new Address();
        address.setContactName("Ergin ARICI");
        address.setCity("Kocaeli");
        address.setCountry("Turkey");
        address.setAddress("Gebze , Yenikent Mh. 2464 Sk. No:4/C");
        address.setZipCode("41400");
        request.setShippingAddress(address);
        request.setBillingAddress(address);
    }

    private static void setBasket(CreatePaymentRequest request) {
        List<BasketItem> basket = new ArrayList<BasketItem>();
        basket.add(setItem(basket, "I0001", new BigDecimal("128.25"), "Monitor", "Computer&Electronics", "Monitors", "PHYSICAL"));
        basket.add(setItem(basket, "I0002", new BigDecimal("18.75"), "Keyboard", "Computer&Electronics", "Keyboards", "PHYSICAL"));

        request.setBasketItems(basket);
    }

    private static void setPaymentCard(CreatePaymentRequest request){
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName("Ergin ARICI");
        paymentCard.setCardNumber("5504720000000003");
        paymentCard.setExpireMonth("06");
        paymentCard.setExpireYear("2026");
        paymentCard.setCvc("465");
        paymentCard.setRegisterCard(0);
        request.setPaymentCard(paymentCard);
    }


}
