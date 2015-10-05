package rda.rabbitmq.routing;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rda.rabbitmq.QueueName;

import java.util.concurrent.TimeUnit;

@Component
public class SimpleRabbitMqListener {
    public static final Logger logger = Logger.getLogger(SimpleRabbitMqListener.class);

    @RabbitListener(queues = QueueName.welcome)
    public void welcome(String message) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        logger.info("Welcome I " + message);
    }

    @RabbitListener(queues = QueueName.offer)
    public void offer(String message) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        logger.info("Offer PI " + message);
    }

}
