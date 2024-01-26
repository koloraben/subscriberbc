package com.canalplus.subscriberbc.infrastructure.adapters.config;

import com.canalplus.subscriberbc.application.ports.output.SubscriberRepository;
import com.canalplus.subscriberbc.domain.business.DomainSubscriberService;
import com.canalplus.subscriberbc.infrastructure.adapters.output.persistence.memory.InMemorySubscriber;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SubscriberRepository SubscriberPersistenceAdapter() {
        return new InMemorySubscriber();
    }


    @Bean
    public DomainSubscriberService subscriberOperation(SubscriberRepository productPersistenceAdapter) {
        return new DomainSubscriberService(productPersistenceAdapter);
    }

}
