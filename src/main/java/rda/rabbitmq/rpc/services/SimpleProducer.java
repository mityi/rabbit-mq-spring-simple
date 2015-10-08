package rda.rabbitmq.rpc.services;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rda.rabbitmq.QueueName;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SimpleProducer {
    public static final Logger logger = Logger.getLogger(SimpleProducer.class);

    @Autowired
    RabbitTemplate template;

    AtomicBoolean atomicBoolean = new AtomicBoolean(Boolean.FALSE);

    @PostConstruct
    private void init() throws InterruptedException {
        logger.info("Emit to queue");
    }

    @Scheduled(fixedRate = 6000)
    public void send() {
        logger.info(System.currentTimeMillis());
        boolean isGodfather = atomicBoolean.get();
        String response;
        if (isGodfather) {
            response = (String) template.convertSendAndReceive(QueueName.welcome, "Corleone");
        } else {
            response = (String) template.convertSendAndReceive(QueueName.welcome, "Soprano");
        }
        atomicBoolean.set(!isGodfather);
        logger.info(response);
    }
}
