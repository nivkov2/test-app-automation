package api.controller.player;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite
    public void globalSetup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//        uncomment to debug request/responses
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

}
