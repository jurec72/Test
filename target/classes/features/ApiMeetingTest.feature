Feature: ApiMeetingTest
  Description: this feature file contains scenarios related to api
  testing of meeting endpoint.

  Background:
    Given api authentication

  @apiTest
  Scenario: Set up a valid meeting
    When request body is prepared for creating a new validMeeting
    And request to create a new meeting is sent
    Then response body and status code are validated
    And validMeeting object is saved into json file
    And  validMeeting is retrieved from database
    Then retrieved response and status code are validated with expected validMeeting result

  @apiTest
  Scenario: Set up a meeting without id
    When request body is prepared for creating a new withoutIdMeeting
    And request to create a new meeting is sent
    Then response body and status code are validated with withoutIdMeeting error result

  @apiTest
  Scenario: Set up a meeting with record date set in the past
    When request body is prepared for creating a new invalidRecordDateMeeting
    And request to create a new meeting is sent
    Then response body and status code are validated with invalidRecordDateMeeting error result

  @apiTest
  Scenario: Set up a meeting with record date after meeting date
    When request body is prepared for creating a new invalidRecordAndMeetingDate
    And request to create a new meeting is sent
    Then response body and status code are validated with invalidRecordAndMeetingDate error result
