package br.com.lucas.projeto_teste_kafka;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var orderDispatcher = new KafkaDispatcher<Order>()) {
            try (var emailDispatcher = new KafkaDispatcher<Email>()) {
                for (var i = 1; i < 10; i++) {

                    var userID = UUID.randomUUID().toString();
                    var orderID = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);
                    var order = new Order(userID, orderID, amount);
                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", userID, order);

                    var email = new Email("Thank you for order!","Your order is begin pocesser");
                    emailDispatcher.send("ECOMMERCE_SAND_EMAIL", userID, email);
                }
            }
        }
    }
}
