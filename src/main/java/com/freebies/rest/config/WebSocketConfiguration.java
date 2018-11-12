/* package com.freebies.rest.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
@Configuration
@EnableWebSocketMessageBroker
public class MobileWebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer  {

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/livedata").setAllowedOrigins("*").withSockJS();
    }

  

}*/