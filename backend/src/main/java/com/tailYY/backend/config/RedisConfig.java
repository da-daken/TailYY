package com.tailYY.backend.config;

import com.tailYY.backend.listener.OrderCancelListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author daken 2025/1/25
 **/
@Configuration
public class RedisConfig {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("__keyevent@*__:expired"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(OrderCancelListener receiver) {
        return new MessageListenerAdapter(receiver, "handleMessage");
    }

    @Bean
    OrderCancelListener receiver() {
        return new OrderCancelListener();
    }
}
