package com.chat.HeyYo.config;

import com.chat.HeyYo.converter.ChatRoomConverter;
import com.chat.HeyYo.converter.ComplaintConverter;
import com.chat.HeyYo.converter.MessageConverter;
import com.chat.HeyYo.converter.UserConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }

    @Bean
    public UserConverter userConverter() {

        return new UserConverter();
    }

    @Bean
    public ComplaintConverter complaintConverter() {

        return new ComplaintConverter();
    }

    @Bean
    public ChatRoomConverter chatRoomConverter() {

        return new ChatRoomConverter();
    }

    @Bean
    public MessageConverter messageConverter() {

        return new MessageConverter();
    }

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {

        var validatorBeanFactory = new LocalValidatorFactoryBean();
        validatorBeanFactory.setValidationMessageSource(messageSource);

        return validatorBeanFactory;
    }
}
