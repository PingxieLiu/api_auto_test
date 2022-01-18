package auto.Day15.homework.com.testapi;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * @Classname ApiInterface
 * @Description TODO
 * @Date 12/27/21 10:16 PM
 * @Created by lhk
 */
public class ApiInterface {

    /**
     * 登录功能
     * @param inputParameters
     * @return
     */
    public static Response loginTest(String inputParameters){

    Response res =
        given().
                contentType("application/json").
                body(inputParameters).
        when().
                post("http://mall.lemonban.com:8107/login").
        then().
                log().all().
                extract().response();
        return res;
    }

    /**
     * 商品搜索功能
     * @param inputParameters
     * "prodName=&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12")
     * @return
     */
    public static Response searchProductListTest(String inputParameters){
        Response res =
                given().
                when().
                        get("http://mall.lemonban.com:8107/search/searchProdPage?" +inputParameters).
                then().
                        log().body().
                        extract().response();
        return res;
    }

    /**
     * 商品信息查询
     * @param id  商品id
     * @return
     */
    public static Response selectProductTest(int id){
        Response res =
            given().
                queryParam("prodId", id).
            when().
                get("http://mall.lemonban.com:8107/prod/prodInfo").
            then().
                log().body().
                extract().response();
         return res;
    }


    public static Response shoppingCartTest(String token_type,String access_token,String inputParameter){
        Response res =
                given().
                        contentType("application/json").
                        header("Authorization", token_type+access_token).
                        body(inputParameter).
                when().
                        post("http://mall.lemonban.com:8107/p/shopCart/changeItem").
                then().
                        log().body().
                        extract().response();
        return res;
    }
}
