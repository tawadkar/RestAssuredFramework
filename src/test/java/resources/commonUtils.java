package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

import static org.codehaus.groovy.tools.shell.util.Logger.io;

public class commonUtils {
   public static RequestSpecification req;
    public RequestSpecification requestSpecification() throws IOException {

        /*
        Return type for RequestSpecBuilder is RequestSpecification
        logRequestTo return type is PrintStream, i.e where do we want to show the output
        Therefore PrintStream class is used to log into separate file
        FileOutputStream generates file at runtime
        Filter is applied to req object
        Since request specification is static, same req will be used for second iteration
        If not static it will consider req as null again and process all the variables again
         */
        if(req==null) {
            PrintStream log = new PrintStream(new FileOutputStream("RequestResponseLogs.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalValues("baseUrl")).addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        return  req;

    }

    //setBaseUri expects String as return type, made it static to access directly
    public static String getGlobalValues(String key) throws IOException {
        Properties fileValues = new Properties();
        FileInputStream filepath  =  new FileInputStream("T:\\restAssuredWorkSpace\\src\\test\\java\\resources\\global.properties");
        fileValues.load(filepath);
        return fileValues.getProperty(key);

    }

    public String getJsonPath(Response response, String key){

        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }

    public String getJsonPath(String response, String path){
        JsonPath jsonPath = new JsonPath(response);
        String accessToken  = jsonPath.getString(path);
        return accessToken;
    }

    public  RequestSpecification jiraRequestSpec() throws IOException {
        if(req==null) {
            PrintStream log = new PrintStream(new FileOutputStream("RequestResponseLogs.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalValues("jiraBaseUrl"))
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        return  req;

    }

    public  RequestSpecification libraryRequestSpec() throws IOException {
        if(req==null) {
            PrintStream log = new PrintStream(new FileOutputStream("RequestResponseLogs.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalValues("libraryBaseUrl"))
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        return  req;

    }


}
