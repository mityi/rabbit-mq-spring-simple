package rda.rabbitmq;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
//@Import(rda.rabbitmq.welcome.RabbitConfiguration.class)
//@Import(rda.rabbitmq.onetomany.RabbitConfiguration.class)
//@Import(rda.rabbitmq.publishsubscribe.RabbitConfiguration.class)
//@Import(rda.rabbitmq.routing.RabbitConfiguration.class)
//@Import(rda.rabbitmq.topics.RabbitConfiguration.class)
@Import(rda.rabbitmq.rpc.directreplyto.RabbitConfiguration.class)
//@Import(rda.rabbitmq.rpc.replyto.RabbitConfiguration.class)
public class Boot {

    //TODO change all examples to scheduled as rpc

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Boot.class, args);
    }

}