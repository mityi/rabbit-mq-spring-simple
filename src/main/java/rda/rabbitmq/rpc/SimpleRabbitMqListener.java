package rda.rabbitmq.rpc;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rda.rabbitmq.QueueName;

import java.util.concurrent.TimeUnit;

@Component
public class SimpleRabbitMqListener {
    public static final Logger logger = Logger.getLogger(SimpleRabbitMqListener.class);

    @RabbitListener(queues = QueueName.welcome)
    public String welcome(String message) throws InterruptedException {
        logger.info(message);
        if ("Corleone".equals(message)){
            return "Welcome to the family.";
        } else {
            return "We'll make you an offer you can't refuse.";
        }
    }

}
