Feature: Subscriber Operations

  Scenario: Create a new subscriber
    Given the subscriber with the following details
      | firstName | lastName | mail                 | phone      |
      | John      | Doe      | john.doe@example.com | +123456789 |
    When I send a POST request to "/v1/subscribers" with the subscriber details
    Then the response status code should be 201
    And the response body should contain the subscriber details

  Scenario: Get an existing subscriber
    Given an existing subscriber with id "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"
    When I send a GET request to "/v1/subscribers/a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"
    Then the response status code should be 200
    And the response body should contain the subscriber details

  Scenario: Update an existing subscriber
    Given an existing subscriber with id "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"
    And the subscriber has the following updated details
      | firstName | lastName | mail                  | phone      |
      | karol     | Doe      | karol.doe@example.com | +987654321 |
    When I send a PUT request to "/v1/subscribers/a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11" with the updated subscriber details
    Then the response status code should be 200
    And the response body should contain the updated subscriber details

  Scenario: Cancel subscription for an existing subscriber
    Given an existing subscriber with id "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12"
    When I send a DELETE request to "/v1/subscribers/a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12"
    Then the response status code should be 202
    And the subscriber should be inactive

  Scenario: Search for subscribers
    Given multiple subscribers exist in the system
    When I send a GET request to "/v1/subscribers/search" with search criteria
      | phone       | mail            | isActive |
      | 33754474602 | sozan@gmail.com | true     |
    Then the response status code after searching should be 200
    And the response body should contain the matching subscribers
