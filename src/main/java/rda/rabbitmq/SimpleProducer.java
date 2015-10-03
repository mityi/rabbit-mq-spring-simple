package rda.rabbitmq;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@DependsOn(value = {"messageListenerContainer"})
public class SimpleProducer {
    public static final Logger logger = Logger.getLogger(SimpleProducer.class);

    @Autowired
    private AmqpTemplate template;

    @PostConstruct
    private void init() {
        logger.info("Emit to queue");
        template.convertAndSend("queue-hello-world", "We'll make you an offer you can't refuse");
    }
}
