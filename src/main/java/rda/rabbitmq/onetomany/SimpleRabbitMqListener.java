package rda.rabbitmq.onetomany;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rda.rabbitmq.QueueName;

import java.util.concurrent.TimeUnit;

@Component
public class SimpleRabbitMqListener {
    public static final Logger logger = Logger.getLogger(SimpleRabbitMqListener.class);

    @RabbitListener(queues = QueueName.welcome)
    public void welcome(String message) throws InterruptedException {
        TimeUnit.SECONDS.sleep(7);
        logger.info("Welcome to the family. " + message);
    }

    @RabbitListener(queues = QueueName.welcome)
    public void offer(String message) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        logger.info("We'll make you an offer you can't refuse. " + message);
    }

}
