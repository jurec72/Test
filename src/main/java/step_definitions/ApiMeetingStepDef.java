package step_definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import modules.ApiTestModule;

public class ApiMeetingStepDef {

    ApiTestModule module = new ApiTestModule();

    @Given("^api authentication$")
    public void authentication() throws Throwable {
        module.apiAuth();
    }

    @When("^request body is prepared for creating a new (.*)$")
    public void prepareRequestBodyForNewMeeting(String meetingNameKey) throws Throwable {
        module.prepareMeetingRequestBody(meetingNameKey);
    }

    @When("^request to create a new meeting is sent$")
    public void sendRequestToCreateMeeting() throws Throwable {
        module.sendMeetingRequest();
    }

    @Then("^response body and status code are validated with (.*) error result$")
    public void validateResponseAfterInvalidRequest(String responseErrorKey) throws Throwable{
        module.validateErrorResponseAfterInvalidRequest(responseErrorKey);
    }

    @Then("^response body and status code are validated$")
    public void validateResponseAfterPostRequest() throws Throwable {
        module.validateResponseFromCreateMeetingRequest();
    }

    @Then("^(.*) object is saved into json file$")
    public void saveResponseObjectIntoJsonFile(String meetingNameKey) throws Throwable {
       module.saveResponseObjectIntoJsonFile(meetingNameKey);
    }

    @Then("^(.*) is retrieved from database$")
    public void retrieveMeeting(String meetingNameKey) throws Throwable {
        module.retrieveMeeting(meetingNameKey);
    }

    @Then("^retrieved response and status code are validated with expected (.*) result$")
    public void validateResponseBodyAfterGetRequest(String meetingNameKey) throws Throwable {
        module.validateResponseFromRetrieveMeetingRequest(meetingNameKey);
    }

}
