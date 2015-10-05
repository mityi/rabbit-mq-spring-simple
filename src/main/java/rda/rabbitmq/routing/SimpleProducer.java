package rda.rabbitmq.routing;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import rda.rabbitmq.QueueName;

import javax.annotation.PostConstruct;

@Service
@DependsOn({"simpleRabbitMqListener"})
public class SimpleProducer {
    public static final Logger logger = Logger.getLogger(SimpleProducer.class);

    @Autowired
    RabbitTemplate template;

    @PostConstruct
    private void init() {
        logger.info("Emit to queue");
        send();
    }

    public void send() {

        template.convertAndSend(QueueName.info, "Info#1");
        template.convertAndSend(QueueName.problem, "Problem#1");
        template.convertAndSend(QueueName.info, "Info#2");
        template.convertAndSend(QueueName.problem, "Problem #2");
    }

}
