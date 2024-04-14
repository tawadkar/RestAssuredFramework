package resources;
import io.cucumber.java.en.Given;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;

public class graphQL {
    public static void main (String [] args){
        String response = given().log().all().header("Content-Type","application/json")
                /***
                 * String in the body is captured from Network Tab >>Payload >> View Source
                 */
                .body("{\"query\":\"{\\n  allBooks {\\n    id\\n    title\\n    desc\\n    price\\n    author\\n  }\\n}\"}")
                .when()
                .post("http://localhost:8009/graphql")
                .then().extract().response().asString();
        
        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String title  = js.getString("data.allBooks[0].title");
        Assert.assertEquals(title,"GraphQL Basics");

      /*  Response:
      {
	"data": {
		"allBooks": [
			{
				"id": "1",
				"title": "GraphQL Basics",
				"desc": "Learn GrapthQL from Basic",
				"price": 4000.0,
				"author": "TestGraphQL"
			},
			{
				"id": "2",
				"title": "Python Basics",
				"desc": "Learn Python from Basic",
				"price": 5000.0,
				"author": "TestPyton"
			},
			{
				"id": "3",
				"title": "GraphQL Mutation",
				"desc": "About Mutation",
				"price": 7000.0,
				"author": "Alex"
			}
		]
	}
}
      Practice GraphQL Server: https://graphql.org/swapi-graphql
       */


    }
}
