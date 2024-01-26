package com.canalplus.subscriberbc.infrastructure.adapters.input.rest;

import com.canalplus.subscriberbc.application.ports.input.crud.SubscriberOperation;
import com.canalplus.subscriberbc.application.ports.input.search.LookupSrvice;
import com.canalplus.subscriberbc.domain.model.Subscriber;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSaveRequest;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSearchCriteria;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSearchRequest;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.response.SubscriberSaveResponse;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.mapper.SubscriberRestMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/subscribers")
@RequiredArgsConstructor
public class SubscriberRestAdapter {
    private final SubscriberOperation subscriberOperation;
    private final SubscriberRestMapper subscriberRestMapper;
    private final LookupSrvice lookupSrvice;

    @PostMapping
    public ResponseEntity<?> createSubscriber(@RequestBody @Valid SubscriberSaveRequest subscriberSaveRequest) {
        Subscriber subscriber = subscriberRestMapper.toSubscriber(subscriberSaveRequest);

        Optional<Subscriber> createdSubscriber = subscriberOperation.create(subscriber);

        return createdSubscriber.map(s -> {
            SubscriberSaveResponse subscriberResponse = subscriberRestMapper.toSubscriberResponse(s);
            addSelfLink(subscriberResponse, s.getSubscriberId());
            return new ResponseEntity<>(subscriberResponse, HttpStatus.CREATED);
        }).orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubscriber(@PathVariable UUID id) {
        Optional<Subscriber> subscriber = subscriberOperation.read(id);

        return subscriber.map(s -> {
            SubscriberSaveResponse subscriberResponse = subscriberRestMapper.toSubscriberResponse(s);
            addSelfLink(subscriberResponse, id);
            return new ResponseEntity<>(subscriberResponse, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubscriber(@PathVariable UUID id, @RequestBody @Valid SubscriberSaveRequest subscriberSaveRequest) {
        Subscriber updatedSubscriber = subscriberRestMapper.toSubscriber(subscriberSaveRequest);

        updatedSubscriber.setSubscriberId(id);

        Optional<Subscriber> updated = subscriberOperation.update(updatedSubscriber);

        return updated.map(s -> {
            SubscriberSaveResponse subscriberResponse = subscriberRestMapper.toSubscriberResponse(s);
            addSelfLink(subscriberResponse, id);
            return new ResponseEntity<>(subscriberResponse, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelSubscription(@PathVariable UUID id) {
        Optional<Subscriber> cancelSubscription = subscriberOperation.cancelSubscription(id);

        return cancelSubscription.map(s -> {
            SubscriberSaveResponse subscriberResponse = subscriberRestMapper.toSubscriberResponse(s);
            addSelfLink(subscriberResponse, id);
            return new ResponseEntity<>(subscriberResponse, HttpStatus.ACCEPTED);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchSubscribers(@RequestParam(value = "phone", required = false) String phone,
                                               @RequestParam(value = "mail", required = false) String mail,
                                               @RequestParam(value = "isActive", required = false) boolean isActive,
                                               @RequestParam(value = "firstName", required = false) String firstName,
                                               @RequestParam(value = "lastName", required = false) String lastName,
                                               @RequestParam(value = "pageNumber", defaultValue = "1") @Min(1) int pageNumber,
                                               @RequestParam(value = "pageSize", defaultValue = "10") @Min(1) @Max(100) int pageSize) {
        SubscriberSearchCriteria searchCriteria = SubscriberSearchCriteria.builder()
                .phone(phone)
                .mail(mail)
                .firstName(firstName)
                .lastName(lastName)
                .isActive(isActive).build();
        SubscriberSearchRequest searchRequest = new SubscriberSearchRequest(searchCriteria, pageNumber, pageSize);

        List<Subscriber> subscribers = lookupSrvice.searchSubscribers(searchRequest);
        List<SubscriberSaveResponse> subscriberResponses = subscriberRestMapper.toListSubscriberResponse(subscribers);

        for (SubscriberSaveResponse response : subscriberResponses) {
            addSelfLink(response, response.getSubscriberId());
        }

        return ResponseEntity.ok(subscriberResponses);
    }

    private void addSelfLink(SubscriberSaveResponse subscriberResponse, UUID subscriberId) {
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubscriberRestAdapter.class).getSubscriber(subscriberId))
                .withRel("self");
        subscriberResponse.add(selfLink);
    }
}
