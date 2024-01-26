package com.canalplus.subscriberbc.infrastructure.adapters.output.persistence.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriberJpaRepository extends JpaRepository<SubscriberEntity, UUID>, JpaSpecificationExecutor<SubscriberEntity> {
    Optional<SubscriberEntity> findByMailIgnoreCaseOrPhoneIgnoreCase(String mail, String phone);

    Page<SubscriberEntity> findAll(Specification<SubscriberEntity> spec, Pageable pageable);


}
