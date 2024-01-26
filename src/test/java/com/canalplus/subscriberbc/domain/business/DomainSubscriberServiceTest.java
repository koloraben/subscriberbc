package com.canalplus.subscriberbc.domain.business;

import com.canalplus.subscriberbc.application.ports.output.SubscriberRepository;
import com.canalplus.subscriberbc.domain.exception.SubscriberAlreadyExistException;
import com.canalplus.subscriberbc.domain.exception.SubscriberAlreadyInactive;
import com.canalplus.subscriberbc.domain.exception.SubscriberNotFoundException;
import com.canalplus.subscriberbc.domain.model.Subscriber;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSearchCriteria;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DomainSubscriberServiceTest {

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private DomainSubscriberService subscriberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSubscriber_Success() throws SubscriberAlreadyExistException {
        // Arrange
        Subscriber newSubscriber = createSubscriber();
        when(subscriberRepository.findByEmailOrPhone(any(), any())).thenReturn(Optional.empty());
        when(subscriberRepository.save(any())).thenReturn(newSubscriber);

        // Act
        Optional<Subscriber> createdSubscriber = subscriberService.create(newSubscriber);

        // Assert
        assertTrue(createdSubscriber.isPresent());
        assertSubscriberEquals(newSubscriber, createdSubscriber.get());

        verify(subscriberRepository, times(1)).findByEmailOrPhone(any(), any());
        verify(subscriberRepository, times(1)).save(any());
    }

    @Test
    void createSubscriber_AlreadyExists() {
        // Arrange
        Subscriber existingSubscriber = createSubscriber();
        when(subscriberRepository.findByEmailOrPhone(any(), any())).thenReturn(Optional.of(existingSubscriber));

        // Act & Assert
        assertThrows(SubscriberAlreadyExistException.class, () -> subscriberService.create(existingSubscriber));

        verify(subscriberRepository, times(1)).findByEmailOrPhone(any(), any());
        verify(subscriberRepository, times(0)).save(any());
    }

    @Test
    void readSubscriber_Success() throws SubscriberNotFoundException {
        // Arrange
        UUID subscriberId = UUID.randomUUID();
        Subscriber existingSubscriber = createSubscriber();
        existingSubscriber.setSubscriberId(subscriberId);
        when(subscriberRepository.findById(subscriberId)).thenReturn(Optional.of(existingSubscriber));

        // Act
        Optional<Subscriber> readSubscriber = subscriberService.read(subscriberId);

        // Assert
        assertTrue(readSubscriber.isPresent());
        assertSubscriberEquals(existingSubscriber, readSubscriber.get());

        verify(subscriberRepository, times(1)).findById(subscriberId);
    }

    @Test
    void readSubscriber_NotFound() {
        // Arrange
        UUID subscriberId = UUID.randomUUID();
        when(subscriberRepository.findById(subscriberId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SubscriberNotFoundException.class, () -> subscriberService.read(subscriberId));

        verify(subscriberRepository, times(1)).findById(subscriberId);
    }

    @Test
    void updateSubscriber_Success() {
        // Arrange
        Subscriber updatedSubscriber = createSubscriber();
        UUID subscriberId = UUID.randomUUID();
        updatedSubscriber.setSubscriberId(subscriberId);
        when(subscriberRepository.findById(subscriberId)).thenReturn(Optional.of(updatedSubscriber));
        when(subscriberRepository.save(any())).thenReturn(updatedSubscriber);

        // Act
        Optional<Subscriber> result = subscriberService.update(updatedSubscriber);

        // Assert
        assertTrue(result.isPresent());
        assertSubscriberEquals(updatedSubscriber, result.get());

        verify(subscriberRepository, times(1)).findById(subscriberId);
        verify(subscriberRepository, times(1)).save(any());
    }

    @Test
    void updateSubscriber_NotFound() {
        // Arrange
        Subscriber updatedSubscriber = createSubscriber();
        UUID subscriberId = UUID.randomUUID();
        updatedSubscriber.setSubscriberId(subscriberId);
        when(subscriberRepository.findById(subscriberId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SubscriberNotFoundException.class, () -> subscriberService.update(updatedSubscriber));

        verify(subscriberRepository, times(1)).findById(subscriberId);
        verify(subscriberRepository, times(0)).save(any());
    }

    @Test
    void cancelSubscription_Success() {
        // Arrange
        Subscriber existingSubscriber = createSubscriber();
        UUID subscriberId = UUID.randomUUID();
        existingSubscriber.setSubscriberId(subscriberId);
        existingSubscriber.setActive(true);
        when(subscriberRepository.findById(subscriberId)).thenReturn(Optional.of(existingSubscriber));
        when(subscriberRepository.save(any())).thenReturn(existingSubscriber);

        // Act
        assertDoesNotThrow(() -> subscriberService.cancelSubscription(subscriberId));

        // Assert
        assertFalse(existingSubscriber.getIsActive());

        // Verify findById is called once
        verify(subscriberRepository, times(1)).findById(subscriberId);
        // Verify save is called once
        verify(subscriberRepository, times(1)).save(any());
    }

    @Test
    void cancelSubscription_NotFound() {
        // Arrange
        UUID subscriberId = UUID.randomUUID();
        when(subscriberRepository.findById(subscriberId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SubscriberNotFoundException.class, () -> subscriberService.cancelSubscription(subscriberId));

        // Verify findById is called once
        verify(subscriberRepository, times(1)).findById(subscriberId);
        // Verify save is not called
        verify(subscriberRepository, times(0)).save(any());
    }

    @Test
    void cancelSubscription_AlreadyInactive() {
        // Arrange
        Subscriber existingSubscriber = createSubscriber();
        UUID subscriberId = UUID.randomUUID();
        existingSubscriber.setSubscriberId(subscriberId);
        existingSubscriber.setActive(false);
        when(subscriberRepository.findById(subscriberId)).thenReturn(Optional.of(existingSubscriber));

        // Act & Assert
        assertThrows(SubscriberAlreadyInactive.class, () -> subscriberService.cancelSubscription(subscriberId));

        // Verify findById is called once
        verify(subscriberRepository, times(1)).findById(subscriberId);
        // Verify save is not called
        verify(subscriberRepository, times(0)).save(any());
    }

    @Test
    void searchSubscribers_Success() {
        // Arrange
        SubscriberSearchRequest searchRequest = createSearchRequest();
        List<Subscriber> expectedSubscribers = List.of(createSubscriber(), createSubscriber());
        when(subscriberRepository.findAll(any(), anyInt(), anyInt())).thenReturn(expectedSubscribers);

        // Act
        List<Subscriber> searchResult = subscriberService.searchSubscribers(searchRequest);

        // Assert
        assertNotNull(searchResult);
        assertEquals(expectedSubscribers.size(), searchResult.size());

        verify(subscriberRepository, times(1)).findAll(any(), anyInt(), anyInt());
    }

    // Helper methods remain unchanged

    private Subscriber createSubscriber() {
        Subscriber subscriber = new Subscriber();
        subscriber.setFirstName("John");
        subscriber.setLastName("Doe");
        subscriber.setMail("john.doe@example.com");
        subscriber.setPhone("123456789");
        subscriber.setActive(true);
        return subscriber;
    }

    private SubscriberSearchRequest createSearchRequest() {
        SubscriberSearchCriteria searchCriteria = new SubscriberSearchCriteria();
        searchCriteria.setFirstName("John");
        searchCriteria.setLastName("Doe");
        searchCriteria.setMail("john.doe@example.com");
        searchCriteria.setPhone("123456789");
        searchCriteria.setIsActive(true);
        return new SubscriberSearchRequest(searchCriteria, 1, 10);
    }

    private void assertSubscriberEquals(Subscriber expected, Subscriber actual) {
        assertEquals(expected.getSubscriberId(), actual.getSubscriberId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getMail(), actual.getMail());
        assertEquals(expected.getPhone(), actual.getPhone());
        assertEquals(expected.getIsActive(), actual.getIsActive());
    }
}
