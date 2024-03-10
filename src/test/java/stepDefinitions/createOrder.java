package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoClasses.Orders;
import pojoClasses.loginRequest;
import pojoClasses.loginResponse;
import pojoClasses.orderDetails;
import resources.TestDataBuilder;
import resources.commonUtils;
import resources.endPoints;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static resources.commonUtils.getGlobalValues;

public class createOrder {
    RequestSpecification req;
    RequestSpecification reqLogin;
    RequestSpecification addProductBaseReq;
    RequestSpecification createOrderBaseReq;
    RequestSpecification createOrderReq;
    String token;
    String userId;
    String addProductResponse;
    String productId;
    commonUtils utils = new commonUtils();
    @Given("User is authenticated with {string} and {string} using {string}")
    public void user_is_authenticated_with_and_using(String userEmail, String userPassword, String endPointName) throws IOException {
        endPoints ep =endPoints.valueOf(endPointName);
        req = new RequestSpecBuilder().setBaseUri(getGlobalValues("baseUrl"))
                .setContentType(ContentType.JSON)
                .build();

        loginRequest LoginRequest  = new loginRequest();
        LoginRequest.setUserEmail(userEmail);
        LoginRequest.setUserPassword(userPassword);

        reqLogin = RestAssured.given().log().all().spec(req).body(LoginRequest);
        loginResponse LoginResponse =  reqLogin.when().post(ep.getEndPoint()).then().log().all().extract().response().as(loginResponse.class);
        token= LoginResponse.getToken();
        System.out.println("Token" + LoginResponse.getToken());
        userId = LoginResponse.getUserId();
        System.out.println("User Id"+ LoginResponse.getUserId());

        addProductBaseReq = new RequestSpecBuilder().setBaseUri(getGlobalValues("baseUrl"))
                .addHeader("authorization",token)
                .build();
    }

  /*  @When("User calls {string} api with {string} Request")
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

    }*/

    @When("User adds Order using {string}")
    public void user_adds_order_using(String endPointName) {
        endPoints ep =endPoints.valueOf(endPointName);
        RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq)
                .param("productName","Laptop")
                .param("productAddedBy",userId)
                .param("productCategory","fashion")
                .param("productSubCategory","shirts")
                .param("productPrice","11500")
                .param("productDescription","Lenovo")
                .param("productFor","men")
                .multiPart("productImage",new File("C:\\Users\\tanma\\Downloads\\pcep.png"));

        addProductResponse = reqAddProduct.when().post(ep.getEndPoint())
                .then().log().all().extract().response().asString();
        System.out.println("ADD PRODUCT RESPONSE" +addProductResponse) ;
       productId = utils.getJsonPath(addProductResponse,"productId");
    }

    @Then("API call is successful with message {string}")
    public void api_call_is_successful_with_message(String message) {
      String messagefromResponse = utils.getJsonPath(addProductResponse,"message");
        assertEquals(message,messagefromResponse);
    }

    @Then("User creates Order using {string}")
    public void user_creates_order_using(String endPointName) throws IOException {
        endPoints ep =endPoints.valueOf(endPointName);
        createOrderBaseReq = new RequestSpecBuilder().setBaseUri(getGlobalValues("baseUrl"))
                .addHeader("authorization",token)
                .setContentType(ContentType.JSON)
                .build();

        orderDetails OrderDetails =  new orderDetails();
        OrderDetails.setCountry("India");
        OrderDetails.setProductOrderId(productId);

        List<orderDetails> oderDetailList = new ArrayList<orderDetails>();
        oderDetailList.add(OrderDetails);

        Orders orders = new Orders();
        orders.setOrders(oderDetailList);

        createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);

        String orderResponse = createOrderReq.when().post(ep.getEndPoint()).then().log().all().extract().response().asString();

        System.out.println("Create Order Response" + orderResponse);

    }
}
