package com.ygq.mail;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitConfig
 *
 * @author Yin Guiqing
 * @version 1.0
 * @date 2022-09-09 20:19
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue queue() {
        return new Queue("mail.welcome", true);
    }
}
