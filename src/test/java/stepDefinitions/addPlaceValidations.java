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
import org.testng.Assert;
import pojoClasses.Location;
import pojoClasses.addLocation;
import resources.TestDataBuilder;
import resources.commonUtils;
import resources.endPoints;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class addPlaceValidations extends commonUtils {
    RequestSpecification req;
    ResponseSpecification res;
    Response response;
    TestDataBuilder data = new TestDataBuilder();
    static String place_id; //place id is made static as same copy to be used across all the test


    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address) throws IOException {
        //Request Body, since this class is inheriting commonUtils we can use method from utils class directly without creating any object
        req = given().spec(requestSpecification())
                .body(data.addPlacePayload(name,language,address));
    }

    @When("User calls {string} api with {string} Request")
    public void user_calls_api_with_request(String endPointName, String httpMethod) {
        //valueOf invokes constructor for addPlace from enum class
        endPoints ep =endPoints.valueOf(endPointName);
        System.out.println(ep.getEndPoint());
        res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(httpMethod.equalsIgnoreCase("POST"))
        response= req.when().post(ep.getEndPoint());
        else if (httpMethod.equalsIgnoreCase("GET")){

            response= req.when().get(ep.getEndPoint());
        }


    }
    @Then("API call is successful with status code {int}")
    public void api_call_is_successful_with_status_code(Integer int1) {
      assertEquals(200,response.getStatusCode());

    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {
      String status = response.asString();

        System.out.println(status);

    }

    @Then("verify place_id from created map to {string} using {string} api")
    public void verify_place_id_from_created_map_to_using_api(String expectedName, String endPointName) throws IOException {
        place_id = getJsonPath(response,"place_id");
        req = given().spec(requestSpecification()).queryParam("place_id",place_id);
        user_calls_api_with_request(endPointName,"GET");
        String actualName = getJsonPath(response,"name");
        Assert.assertEquals(actualName,expectedName);
    }

    @Given(":User loads deletePlace payload")
    public void user_loads_delete_place_payload() throws IOException {
      req =given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
    }


}
