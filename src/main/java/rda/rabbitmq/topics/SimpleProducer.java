package rda.rabbitmq.topics;

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
    QueueName queueName = new QueueName() {
    };

    @PostConstruct
    private void init() {
        logger.info("Emit to queue");
        send();
    }

    public void send() {
        template.convertAndSend(queueName.end("f", "s"), "End#1");
        template.convertAndSend(queueName.in("f", "l"), "In#2");
        template.convertAndSend(queueName.start("q", "w", "s", "d"), "Start#3");

        template.convertAndSend(queueName.start("in", "end"), "*.*.*");

// not in topics
        template.convertAndSend(queueName.end("f", "s") + "D", "Problem #0");
        template.convertAndSend(queueName.in("f", "s") + ".D", "Problem #0");
        template.convertAndSend("d." + queueName.start("f", "s"), "Problem #0");
    }

}
