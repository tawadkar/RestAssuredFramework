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

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class addPlaceValidations {
    RequestSpecification res;
    ResponseSpecification respec;
    Response response;
    @Given("Add Place Payload")
    public void add_place_payload() {
        pojoClasses.addLocation l = new addLocation();
        l.setAccuracy(50);
        l.setAddress("29 side layout cohen 09");
        l.setLanguage("French-IN");
        l.setPhone_number("(+91) 983 893 3937");
        l.setWebsite("https://rahulshettyacademy.com");
        l.setName("Tanmay Frontline house");

        //Set type has return type as List, therefore we created list before passing it to setTypes
        //types is a separate list of strings and do not contain key value pairs hence pojo is not created
        //  key=getter value=setter
        List<String> typeList = new ArrayList<>();
        typeList.add("shoe park");
        typeList.add("shop");
        l.setTypes(typeList);

        /*
         * Location is separate array having key value pairs therefore we created separate pojo class
         */
        Location lo = new Location();
        lo.setLat(-38.383494);
        lo.setLng(33.427362);
        l.setLocation(lo);

        /*
        Return type for RequestSpecBuilder is RequestSpecification
         */
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        /*
        Return type for ResponseSpecBuilder is ResponseSpecification
         */
         respec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        //Request Body
        res = given().spec(req)
                .body(l);
    }
    @When("User calls {string} api with POST Request")
    public void user_calls_api_with_post_request(String string) {
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
        assertEquals(expectedValue,js.get(keyValue).toString());
    }

}
