package rda.rabbitmq.rpc.replyto;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import rda.rabbitmq.QueueName;

@ComponentScan("rda.rabbitmq.rpc.services")
@EnableRabbit
@EnableScheduling
@Configuration
public class RabbitConfiguration {
    public static final Logger logger = Logger.getLogger(RabbitConfiguration.class);

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setReplyQueue(queueOffer());
        logger.info("Use replyQueue " + QueueName.offer);
        template.setReplyTimeout(6 * 1000);
        return template;
    }


    @Bean
    public SimpleMessageListenerContainer replyListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(queueOffer());
        container.setMessageListener(rabbitTemplate());
        return container;
    }

    @Bean
    public Queue queueOffer() {
        return new Queue(QueueName.offer);
    }

    @Bean
    public Queue queueWelcome() {
        return new Queue(QueueName.welcome);
    }

}