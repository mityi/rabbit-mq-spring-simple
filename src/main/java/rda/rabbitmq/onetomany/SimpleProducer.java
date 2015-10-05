package rda.rabbitmq.onetomany;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import rda.rabbitmq.QueueName;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
@DependsOn({"simpleRabbitMqListener"})
public class SimpleProducer {
    public static final Logger logger = Logger.getLogger(SimpleProducer.class);

    @Autowired
    RabbitTemplate template;

    @PostConstruct
    private void init() {
        logger.info("Emit to queue");
        send("Mr. ");
    }

    public void send(String msg) {
        for (String number : Arrays.asList("D", "R", "A", "Mq")) {
            template.convertAndSend(QueueName.welcome, msg + number);
        }
    }

}
