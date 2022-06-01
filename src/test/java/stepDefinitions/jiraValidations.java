package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.TestDataBuilder;
import resources.commonUtils;
import resources.endPoints;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class jiraValidations extends commonUtils {
    TestDataBuilder data = new TestDataBuilder();
    RequestSpecification req;
    ResponseSpecification res;
    Response response;
    @Given("User logs in with {string} and {string}")
    public void userLogsInWithAnd(String username, String password) throws IOException {
        req= given().spec(jiraRequestSpec()).body(data.jiraLoginPayload(username,password));
    }

    @When("User calls {string} with {string} request")
    public void userCallsWithRequest(String endPointName, String httpMethod) {
        endPoints ep =endPoints.valueOf(endPointName);
        System.out.println(ep.getEndPoint());
        res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(httpMethod.equalsIgnoreCase("POST"))
            response= req.when().post(ep.getEndPoint());
        else if (httpMethod.equalsIgnoreCase("GET")){

            response= req.when().get(ep.getEndPoint());
        }
    }

    @Then("Login should be successful with status code {int}")
    public void loginShouldBeSuccessfulWithStatusCode(int statusCode) {
        assertEquals(200,response.getStatusCode());
    }
}
