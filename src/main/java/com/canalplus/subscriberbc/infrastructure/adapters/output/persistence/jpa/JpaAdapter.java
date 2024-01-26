package com.canalplus.subscriberbc.infrastructure.adapters.output.persistence.jpa;

import com.canalplus.subscriberbc.application.ports.output.SubscriberRepository;
import com.canalplus.subscriberbc.domain.model.Subscriber;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaAdapter implements SubscriberRepository {

    SubscriberJpaRepository jpaRepository;
    dbModelMapper modelMapper;

    @Autowired
    public JpaAdapter(SubscriberJpaRepository jpaRepository, dbModelMapper modelMapper) {
        this.jpaRepository = jpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<Subscriber> findById(UUID id) {
        return jpaRepository.findById(id).map(subscriberEntity -> modelMapper.toSubscriber(subscriberEntity));
    }

    @Override
    public Subscriber save(Subscriber subscriber) {
        return modelMapper.toSubscriber(jpaRepository.save(modelMapper.toEntity(subscriber)));
    }

    @Override
    public List<Subscriber> findAll(SubscriberSearchCriteria searchCriteria, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<SubscriberEntity> page = jpaRepository.findAll(SubscriberSpecifications.getSubscribers(searchCriteria), pageable);

        return page.getContent().stream()
                .map(modelMapper::toSubscriber)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Subscriber> findByEmailOrPhone(String mail, String phone) {
        return jpaRepository.findByMailIgnoreCaseOrPhoneIgnoreCase(mail, phone)
                .map(modelMapper::toSubscriber);
    }

    @Override
    public Boolean update(Subscriber updatedSubscriber) {
        Optional<SubscriberEntity> existingSubscriber = jpaRepository.findById(updatedSubscriber.getSubscriberId());

        if (existingSubscriber.isPresent()) {
            SubscriberEntity entityToUpdate = modelMapper.toEntity(updatedSubscriber);
            jpaRepository.save(entityToUpdate);
            return true;
        }

        return false;
    }
}
