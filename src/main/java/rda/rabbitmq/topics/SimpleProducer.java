package rda.rabbitmq.topics;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
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
    }

    @Scheduled(fixedRate = 6000)
    public void send() {
        long time = System.currentTimeMillis();
        logger.info(time);

        template.convertAndSend(queueName.end("f", "s"), "End");
        template.convertAndSend(queueName.in("f", "l"), "In");
        template.convertAndSend(queueName.start("q", "w", "s", "d"), "Start");
        template.convertAndSend(queueName.start("in", "end"), "Start.In.End");

// not in topics
        template.convertAndSend(queueName.end("f", "s") + "D", "Problem #0");
        template.convertAndSend(queueName.in("f", "s") + ".D", "Problem #1");
        template.convertAndSend("d." + queueName.start("f", "s"), "Problem #2");
    }

}
