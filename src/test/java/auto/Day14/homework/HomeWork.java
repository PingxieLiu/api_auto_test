package auto.Day14.homework;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * @Classname HomeWork
 * @Description TODO
 * @Date 12/24/21 10:22 PM
 * @Created by lhk
 */
public class HomeWork {

    //登录
    @Test
    public void lognTest() {

        String jsonDate = "{\"principal\":\"waiwai\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}";

        given().
                contentType("application/json").
                body(jsonDate).
                when().
                post("http://mall.lemonban.com:8107/login").
                then().
                log().body();

    }

    //商品搜索
    @Test
    public void searchProductTest() {

        given().
        when().
                get("http://mall.lemonban.com:8107/search/searchProdPage" +
                        "?prodName=&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12").
        then().
                log().body();
    }


    //选择商品
    @Test
    public void selectProductTest() {
        given().
                queryParam("prodId", 83).
        when().
                get("http://mall.lemonban.com:8107/prod/prodInfo").
        then().
                log().body();
    }

    //添加购物车
    @Test
    public void shoppingCartTest() {

        String jsonData = "{\"basketId\":0,\"count\":1,\"prodId\":\"83\",\"shopId\":1,\"skuId\":415}";
        given().
                contentType("application/json").
                header("Authorization", "bearer913c7ed7-4dba-4861-b3a2-4bf850050ee5").
                body(jsonData).
        when().
                post("http://mall.lemonban.com:8107/p/shopCart/changeItem").
        then().
                log().body();
    }
}
