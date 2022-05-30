package stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;
/* To execute some pre requisite Scenario before the scenario in @Before use Hooks
*/
public class Hooks {
    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        addPlaceValidations steps = new addPlaceValidations();
        if(addPlaceValidations.place_id==null) {
            steps.add_place_payload_with("TestUser2", "Spanish", "Europe");
            steps.user_calls_api_with_request("addPlaceAPI", "POST");
            steps.verify_place_id_from_created_map_to_using_api("TestUser2", "getPlaceAPI");
        }
    }
}
