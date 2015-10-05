package rda.rabbitmq.routing;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import rda.rabbitmq.QueueName;

@ComponentScan
@EnableRabbit
@Configuration
public class RabbitConfiguration {

    public static final String DIRECT_EXCHANGE_NAME = "direct-exchange";

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
        template.setExchange(DIRECT_EXCHANGE_NAME);
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
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingOfferCorleone() {
        return BindingBuilder.bind(queueOffer()).to(directExchange()).with(QueueName.problem);
    }

    @Bean
    public Binding bindingOfferSoprano() {
        return BindingBuilder.bind(queueOffer()).to(directExchange()).with(QueueName.info);
    }

    @Bean
    public Binding bindingWelcomeCorleone() {
        return BindingBuilder.bind(queueWelcome()).to(directExchange()).with(QueueName.info);
    }

}