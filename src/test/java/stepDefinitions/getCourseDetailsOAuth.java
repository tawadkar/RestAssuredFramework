package stepDefinitions;

import com.google.gson.Gson;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import resources.commonUtils;
import resources.endPoints;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class getCourseDetailsOAuth {
 private String accessToken;
 private String getCourseResponse;

 @Given("User is validated through OAuth")
 public void user_is_validated_through_o_auth() {
  commonUtils utils = new commonUtils();
  String response = given()
          .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
          .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
          .formParams("grant_type", "client_credentials")
          .formParams("scope", "trust")
          .when().log().all()
          .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
  accessToken = utils.getJsonPath(response, "access_token");

 }

 /*@When("User calls {string}")
 public void user_calls(String endPointName) {
  endPoints ep =endPoints.valueOf(endPointName);
  System.out.println(ep.getEndPoint());

  getCourseResponse = given()
          .queryParams("access_token", accessToken)
          .when().log().all()
          .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails/oauthapi/getCourseDetails").asString();
  System.out.println(getCourseResponse);*/

  @When("User calls getCourseDetailsAPI")
  public void user_calls_get_course_details_api() {
   getCourseResponse = given()
           .queryParams("access_token", accessToken)
           .when().log().all()
           .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails/oauthapi/getCourseDetails").asString();
   System.out.println(getCourseResponse);
  }

 @Then("user should get CouseDetails with status code {int}")
 public void user_should_get_couse_details_with_status_code(Integer int1) {
  System.out.println("To Be Implemented");

 }


}
