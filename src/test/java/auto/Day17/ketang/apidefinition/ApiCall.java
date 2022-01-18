package auto.Day17.ketang.apidefinition;

import auto.Day17.ketang.util.Environment;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * @author 歪歪欧巴
 * @Description TODO
 * @date 2021/12/27 21:40
 * @Copyright 湖南省零檬信息技术有限公司. All rights reserved.
 */
public class ApiCall {
    /**
     * 登录接口请求定义
     * @param inputParams 传入的接口入参
     * {"principal":"waiwai","credentials":"lemon123456","appType":3,"loginType":0}
     * @return
     */
    public static Response login(String inputParams){
        Response res=
                given().header("Content-Type","application/json").
                        body(inputParams).
                when().
                        post("http://mall.lemonban.com:8107/login").
                then().
                        log().all().
                        extract().response();
        return res;
    }

    /**
     * 搜索商品接口请求定义
     * @param inputParams 接口请求入参
     * prodName=&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12
     * @return 响应信息
     */
    public static Response searchProduct(String inputParams){
        inputParams = Environment.replaceParams(inputParams);

        Response res=
                given().
                when().
                        get("http://mall.lemonban.com:8107/search/searchProdPage?"+inputParams).
                then().
                        log().all().
                        extract().response();
        return res;
    }

    /**
     * 商品信息接口请求定义
     * @param prodId 商品ID
     * @return 响应结果
     */
    public static Response productInfo(int prodId){
        Response res=
                given().
                when().
                        get("http://mall.lemonban.com:8107/prod/prodInfo?"+"prodId="+prodId).
                then().
                        log().all().
                        extract().response();
        return res;
    }

    /**
     * 添加购物车接口请求
     * @param inputParams 请求入参
     * {"basketId":0,"count":1,"prodId":"83","shopId":1,"skuId":415}
     * @param token 鉴权token值，从登录接口返回的
     * @return 响应数据
     */
    public static Response addShopCart(String inputParams,String token){

        //对接口的入参进行替换
        inputParams = Environment.replaceParams(inputParams);

        Response res=
                given().
                        header("Content-Type","application/json").
                        header("Authorization","bearer"+token).
                        body(inputParams).
                when().
                        post("http://mall.lemonban.com:8107/p/shopCart/changeItem").
                then().
                        log().all().
                        extract().response();
        return res;
    }
}
