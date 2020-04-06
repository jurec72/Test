package modules;

import helpers.ApiDataHelper;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class ApiTestModule {

    private JsonPath responseJsonBody;
    private JsonPath expectedJsonBody;
    private String requestBody;
    private Response response;
    private String baseUri;

    public void apiAuth() throws Throwable {
        baseUri = ApiDataHelper.getBaseUri();
    }

    public void prepareMeetingRequestBody(String meetingName) throws Throwable {
        requestBody = ApiDataHelper.retrieveJsonObjectFromRequestJsonBodyFile(meetingName);
        expectedJsonBody = JsonPath.from(requestBody);
    }

    public void sendMeetingRequest() throws Throwable {
        String endpoint = baseUri + "/meetings";
        response = given().accept(ContentType.JSON).and().contentType(ContentType.JSON).when().body(requestBody).and().post(endpoint);
        responseJsonBody = response.jsonPath();
    }

    public void validateResponseFromCreateMeetingRequest() throws Throwable{
        Assert.assertTrue("Status code is invalid.", 201 == response.getStatusCode());
        Assert.assertFalse("Meeting id is not generated: ", responseJsonBody.get("meetingId")==null);
    }

    public void saveResponseObjectIntoJsonFile(String meetingName) throws Throwable{
        Object jsonBody = responseJsonBody.getObject("", Object.class);
        ApiDataHelper.saveObjectIntoJsonFile(meetingName, jsonBody);

    }

    public void retrieveMeeting(String meetingName) throws Throwable{
        expectedJsonBody = JsonPath.from(ApiDataHelper.retrieveJsonObjectFromResponseJsonBodyFile(meetingName));
        String endpoint = baseUri + "/meetings/" + expectedJsonBody.get("meetingId");
        response = given().accept(ContentType.JSON)
                .when().get(endpoint);
        responseJsonBody = response.jsonPath();
    }

    public void validateResponseFromRetrieveMeetingRequest(String meetingName) throws Throwable{
        String meetingObjectString = ApiDataHelper.retrieveJsonObjectFromRequestJsonBodyFile(meetingName);
        expectedJsonBody = JsonPath.from(meetingObjectString);
        int expectedMeetingId = JsonPath.from(ApiDataHelper.retrieveJsonObjectFromResponseJsonBodyFile(meetingName)).get("meetingId");
        Assert.assertTrue("Status code is invalid.", 200 == response.getStatusCode());
        Assert.assertEquals("Meeting id mismatch: ", expectedMeetingId, responseJsonBody.get("id"));
        Assert.assertEquals("Meeting type mismatch: ", expectedJsonBody.get("type"), responseJsonBody.get("type"));
        Assert.assertEquals("Issuer name mismatch: ", expectedJsonBody.get("issuer"), responseJsonBody.get("issuer.name"));
        Assert.assertEquals("Meeting type mismatch: ", expectedJsonBody.get("type"), responseJsonBody.get("type"));
        Assert.assertEquals("Location mismatch: ", expectedJsonBody.get("location"), responseJsonBody.get("location"));
        Assert.assertEquals("Url mismatch: ", expectedJsonBody.get("url"), responseJsonBody.get("meetingFileUrl"));
        Assert.assertEquals("Record date mismatch: ", expectedJsonBody.get("recordDate"), responseJsonBody.get("recordDate"));
        Assert.assertEquals("Deadline date mismatch: ", expectedJsonBody.get("deadlineDate"), responseJsonBody.get("deadlineTime"));
        Assert.assertEquals("Meeting date mismatch: ", expectedJsonBody.get("meetingDate"), responseJsonBody.get("meetingTime"));
        Assert.assertEquals("Voting options: vote for mismatch: ", expectedJsonBody.get("votingOptions.voteFor"), responseJsonBody.get("votingOptions.voteFor"));
        Assert.assertEquals("Voting options: vote against mismatch: ", expectedJsonBody.get("votingOptions.voteAgainst"), responseJsonBody.get("votingOptions.voteAgainst"));
        Assert.assertEquals("Voting options: abstain mismatch: ", expectedJsonBody.get("votingOptions.abstain"), responseJsonBody.get("votingOptions.abstain"));
        Assert.assertEquals("Voting options: proxy mismatch: ", expectedJsonBody.get("votingOptions.proxy"), responseJsonBody.get("votingOptions.proxy"));
        Assert.assertEquals("Voting options: action mismatch: ", expectedJsonBody.get("votingOptions.noAction"), responseJsonBody.get("votingOptions.noAction"));
        Assert.assertEquals("Voting options: split mismatch: ", expectedJsonBody.get("votingOptions.split"), responseJsonBody.get("votingOptions.split"));
    }

    public void validateErrorResponseAfterInvalidRequest(String responseErrorMessage) throws Throwable{
        expectedJsonBody = JsonPath.from(ApiDataHelper.retrieveJsonObjectFromResponseJsonBodyFile(responseErrorMessage));
        Assert.assertTrue("Status code is invalid.", 500 == response.getStatusCode());
        Assert.assertEquals("Error message mismatch: "+"\nExpected error: "+expectedJsonBody.get("error")
                +"\nActual error: "+responseJsonBody.get("error"), expectedJsonBody.get("error"), responseJsonBody.get("error"));
    }



}
