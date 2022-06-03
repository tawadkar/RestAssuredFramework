package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.runner.Request;
import org.testng.Assert;
import resources.TestDataBuilder;
import resources.commonUtils;
import resources.endPoints;

import java.io.IOException;
import stepDefinitions.addPlaceValidations;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.junit.Assert.assertEquals;

public class libraryValidations extends commonUtils {
    RequestSpecification req;
    ResponseSpecification res;
    Response response;
    TestDataBuilder data = new TestDataBuilder();
    static  String book_id ;

    @Given("User adds book with {string},{string},{string} and {string} details")
    public void user_adds_book_with_and_details(String name, String isbn, String aisle, String author) throws IOException {
        req = given().spec(libraryRequestSpec())
                .body(data.addBookPayload(name,isbn,aisle,author));
    }

    @When("The User calls {string} api with {string} request")
    public void the_user_calls_api_with_request(String endPointName, String httpMethod) {
        endPoints ep =endPoints.valueOf(endPointName);
        System.out.println(ep.getEndPoint());
        res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(httpMethod.equalsIgnoreCase("POST"))
            response= req.when().post(ep.getEndPoint());
        else if (httpMethod.equalsIgnoreCase("GET")){

            response= req.when().get(ep.getEndPoint());
        }

    }
    @Then("The API call is successful with status code {int}")
    public void the_api_call_is_successful_with_status_code(Integer int1) {
        assertEquals(200,response.getStatusCode());
    }

    @Then("Verify book_id created after its added to verify its {string} using {string}")
    public void verify_book_id_created_after_its_added_to_verify_its_using(String expectedName, String endPointName ) throws IOException {
        book_id = getJsonPath(response,"ID");
        req = given().spec(libraryRequestSpec()).queryParam("ID",book_id);
        the_user_calls_api_with_request(endPointName,"GET");
        String actualName = getJsonPath(response,"book_name");
        Assert.assertEquals(actualName,expectedName);
    }
}
