package oAuth;
import resources.commonUtils;

import static io.restassured.RestAssured.given;
public class oAuthTest {
    public static void main (String args[]) {
        commonUtils utils = new commonUtils();
        String response = given()
                .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type", "client_credentials")
                .formParams("scope", "trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        String accessToken = utils.getJsonPath(response, "access_token");
        System.out.println(accessToken);

        String getCourseResponse = given()
                .queryParam("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
        System.out.println(getCourseResponse);

    }
}
