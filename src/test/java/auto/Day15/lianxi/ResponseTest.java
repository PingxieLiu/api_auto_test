package auto.Day15.lianxi;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * @Classname ResponseTest
 * @Description TODO
 * @Date 12/28/21 10:33 PM
 * @Created by lhk
 */
public class ResponseTest {

    @Test
    public  void  test01(){
        Response res =
                given().

                when().
                        get("http://httpbin.org/xml").
                then().
                        log().all().
                        extract().response();

        System.out.println(res.xmlPath().get("slideshow.slide[1].@type").toString());
        System.out.println(res.xmlPath().get("slideshow.slide[1].item[0].em").toString());

    }
}
