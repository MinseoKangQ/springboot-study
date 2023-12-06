package com.MinseoKangQ.subscriber.config;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriberConfig {

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("boot.fanout.exchange");
    }

    @Bean // 메세지를 받는 주체이므로 queue 생성
    public Queue autoGenQueue() {
        return new AnonymousQueue();
    }

    // queue 와 exchange 연결
    @Bean
    public Binding binding(Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(queue)
                .to(fanoutExchange);
    }

}