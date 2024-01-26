package com.canalplus.subscriberbc.integrationtest;

import com.canalplus.subscriberbc.domain.model.Subscriber;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSearchCriteria;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.response.SubscriberSaveResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SubscriberStepDefinitions {

    private static final Logger logger = LoggerFactory.getLogger(SubscriberStepDefinitions.class);

    @LocalServerPort
    private int port;
    private final RestTemplate restTemplate = new RestTemplate();

    private Subscriber subscriber;
    private Subscriber updatedSubscriber;
    private ResponseEntity<Subscriber> response;
    private ResponseEntity<SubscriberSaveResponse[]> searchResponse;
    private List<SubscriberSaveResponse> searchResults;

    @Given("^the subscriber with the following details$")
    public void theSubscriberWithTheFollowingDetails(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        subscriber = new Subscriber();
        subscriber.setFirstName(data.get(0).get("firstName"));
        subscriber.setLastName(data.get(0).get("lastName"));
        subscriber.setMail(data.get(0).get("mail"));
        subscriber.setPhone(data.get(0).get("phone"));
    }

    @When("^I send a POST request to \"([^\"]*)\" with the subscriber details$")
    public void iSendAPOSTRequestToWithTheSubscriberDetails(String endpoint) {
        response = restTemplate.postForEntity(subscriberEndpoint() + endpoint, subscriber, Subscriber.class);
    }

    @Then("^the response status code should be (\\d+)$")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCodeValue());
    }

    @Then("^the response status code after searching should be (\\d+)$")
    public void theResponseStatusCodeAfterSearchingShouldBe(int expectedStatusCode) {
        assertEquals(expectedStatusCode, searchResponse.getStatusCodeValue());
    }

    @And("^the response body should contain the subscriber details$")
    public void theResponseBodyShouldContainTheSubscriberDetails() {
        assertEquals(subscriber.getMail(), response.getBody().getMail());
        assertEquals(subscriber.getPhone(), response.getBody().getPhone());
        assertEquals(subscriber.getFirstName(), response.getBody().getFirstName());
    }

    @Given("^an existing subscriber with id \"([^\"]*)\"$")
    public void anExistingSubscriberWithId(String subscriberId) {
        response = restTemplate.getForEntity(subscriberEndpoint() + "/v1/subscribers/" + subscriberId, Subscriber.class);
        subscriber = response.getBody();
    }

    @When("^I send a GET request to \"([^\"]*)\"$")
    public void iSendAGETRequestTo(String endpoint) {
        response = restTemplate.getForEntity(subscriberEndpoint() + endpoint, Subscriber.class);
    }

    @And("^the subscriber should be inactive$")
    public void theSubscriberShouldBeInactive() {
        assertFalse(subscriber.getIsActive());
    }

    @And("^the subscriber has the following updated details$")
    public void theSubscriberHasTheFollowingUpdatedDetails(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        updatedSubscriber = new Subscriber();
        updatedSubscriber.setSubscriberId(subscriber.getSubscriberId());
        updatedSubscriber.setFirstName(data.get(0).get("firstName"));
        updatedSubscriber.setLastName(data.get(0).get("lastName"));
        updatedSubscriber.setMail(data.get(0).get("mail"));
        updatedSubscriber.setPhone(data.get(0).get("phone"));
    }

    @When("^I send a PUT request to \"([^\"]*)\" with the updated subscriber details$")
    public void iSendAPUTRequestToWithTheUpdatedSubscriberDetails(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Subscriber> requestEntity = new HttpEntity<>(updatedSubscriber, headers);
        restTemplate.put(subscriberEndpoint() + endpoint, requestEntity);
    }

    @And("^the response body should contain the updated subscriber details$")
    public void theResponseBodyShouldContainTheUpdatedSubscriberDetails() {
        response = restTemplate.getForEntity(subscriberEndpoint() + "/v1/subscribers/" + updatedSubscriber.getSubscriberId(), Subscriber.class);
        assertEquals(updatedSubscriber, response.getBody());
    }

    @When("^I send a DELETE request to \"([^\"]*)\"$")
    public void iSendADELETERequestTo(String endpoint) {
        response = restTemplate.exchange(subscriberEndpoint() + endpoint, HttpMethod.DELETE, null, Subscriber.class);
        assertEquals(202, response.getStatusCodeValue());
        subscriber = response.getBody();
    }

    @Given("^multiple subscribers exist in the system$")
    public void multipleSubscribersExistInTheSystem() {
        // Assume multiple subscribers exist in the system for search scenarios
    }

    @And("^I send a GET request to \"([^\"]*)\" with search criteria$")
    public void iSendAGETRequestToWithSearchCriteria(String endpoint, DataTable dataTable) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        SubscriberSearchCriteria searchCriteria = fromDataTable(dataTable);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(subscriberEndpoint() + endpoint)
                .queryParam("phone", searchCriteria.getPhone())
                .queryParam("mail", searchCriteria.getMail())
                .queryParam("isActive", searchCriteria.isActive());

        String requestUrl = builder.toUriString();
        logger.info("Sending GET request to: {}", requestUrl);

        searchResponse = restTemplate.exchange(requestUrl,
                HttpMethod.GET,
                null,
                SubscriberSaveResponse[].class);
        searchResults = Arrays.asList(searchResponse.getBody());
    }

    @And("^the response body should contain the matching subscribers$")
    public void theResponseBodyShouldContainTheMatchingSubscribers() {
        assertFalse(searchResults.isEmpty());
    }

    private static SubscriberSearchCriteria fromDataTable(DataTable dataTable) {
        Map<String, String> data = dataTable.transpose().asMap(String.class, String.class);

        return SubscriberSearchCriteria.builder()
                .firstName(data.get("firstName"))
                .lastName(data.get("lastName"))
                .mail(data.get("mail"))
                .phone(data.get("phone"))
                .isActive(Boolean.parseBoolean(data.get("isActive")))
                .build();
    }


    private String subscriberEndpoint() {
        String SERVER_URL = "http://localhost";
        return SERVER_URL + ":" + port;
    }

    // Reset scenario-specific data after each scenario
    @After
    public void resetScenarioData() {
        subscriber = null;
        updatedSubscriber = null;
        response = null;
        searchResults = null;
    }
}
