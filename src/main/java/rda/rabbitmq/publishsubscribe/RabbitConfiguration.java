package rda.rabbitmq.publishsubscribe;

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

@EnableRabbit
@EnableScheduling
@ComponentScan
@Configuration
public class RabbitConfiguration {

    public static final String FANOUT_EXCHANGE = "exchange-publish-subscribe";
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
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange(FANOUT_EXCHANGE);
        return rabbitTemplate;
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
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding bindingWelcome() {
        return BindingBuilder.bind(queueWelcome()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingFastSlow() {
        return BindingBuilder.bind(queueOffer()).to(fanoutExchange());
    }

}