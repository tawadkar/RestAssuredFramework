package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class commonUtils {
    RequestSpecification req;
    public RequestSpecification requestSpecification() throws FileNotFoundException {

        /*
        Return type for RequestSpecBuilder is RequestSpecification
        logRequestTo return type is PrintStream, i.e where do we want to show the output
        Therefore PrintStream class is used to log into separate file
        FileOutputStream generates file at runtime
        Filter is applied to req object
         */
        PrintStream log = new PrintStream(new FileOutputStream("RequestResponseLogs.txt"));
        req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .addFilter(RequestLoggingFilter.logRequestTo(log))
                .addFilter(ResponseLoggingFilter.logResponseTo(log))
                .setContentType(ContentType.JSON).build();
        return req;
    }
}
