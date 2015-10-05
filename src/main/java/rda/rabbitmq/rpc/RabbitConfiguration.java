package rda.rabbitmq.rpc;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import rda.rabbitmq.QueueName;

@ComponentScan
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
//        template.setReplyQueue(queueWelcome());
        template.setQueue(QueueName.welcome);
        template.setReplyTimeout(60 * 1000);
        //no reply to - we use direct-reply-to
        return template;
    }

    @Bean
    public Queue queueWelcome() {
        return new Queue(QueueName.welcome);
    }

}