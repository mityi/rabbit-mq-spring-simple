package rda.rabbitmq.topics;

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
@EnableScheduling
@EnableRabbit
@Configuration
public class RabbitConfiguration {

    public static final String TOPIC_EXCHANGE = "topic-exchange";

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
        template.setExchange(TOPIC_EXCHANGE);
        return template;
    }

    @Bean
    public Queue queueWelcome() {
        return new Queue(QueueName.welcome);
    }

    @Bean
    public Queue queueOffer() {
        return new Queue(QueueName.offer);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding bindingIn() {
        return BindingBuilder.bind(queueOffer()).to(topicExchange()).with(QueueName.in);
    }

    @Bean
    public Binding bindingEnd() {
        return BindingBuilder.bind(queueWelcome()).to(topicExchange()).with(QueueName.end);
    }

    @Bean
    public Binding bindingStart() {
        return BindingBuilder.bind(queueWelcome()).to(topicExchange()).with(QueueName.start);
    }

}