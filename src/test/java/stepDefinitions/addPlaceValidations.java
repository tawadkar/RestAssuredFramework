package stepDefinitions;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoClasses.Location;
import pojoClasses.addLocation;
import resources.TestDataBuilder;
import resources.commonUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class addPlaceValidations extends commonUtils {
    RequestSpecification res;
    ResponseSpecification respec;
    Response response;
    TestDataBuilder data = new TestDataBuilder();
    @Given("Add Place Payload")
    public void add_place_payload() throws IOException {

        //Request Body, since this class is inheriting commonUtils we can use method from utils class directly without creating any object
        res = given().spec(requestSpecification())
                .body(data.addPlacePayload());
    }
    @When("User calls {string} api with POST Request")
    public void user_calls_api_with_post_request(String string) {
        respec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
         response= res.when().post("maps/api/place/add/json")
        .then().spec(respec).extract().response();
    }
    @Then("API call is successful with status code {int}")
    public void api_call_is_successful_with_status_code(Integer int1) {
      assertEquals(200,response.getStatusCode());

    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {
      String status = response.asString();
        JsonPath js = new JsonPath(status);
       // String actualValue = js.get(keyValue).toString();
       // assertEquals(expectedValue,actualValue);
        System.out.println(status);

    }

}
