package auto.Day18.lianxi.apidefinition;

import auto.Day18.lianxi.util.Environment;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @author 歪歪欧巴
 * @Description TODO
 * @date 2021/12/27 21:40
 * @Copyright 湖南省零檬信息技术有限公司. All rights reserved.
 */
public class ApiCall {

    /**
     * 接口请求通用的方法的封装
     *
     * @param 、caseName   用例名字
     * @param mathonName  请求方法
     * @param url         请求路径
     * @param mapHeade    请求头中的参数
     * @param inputParams 请求参数
     * @return 返回值
     */
    public static Response request(String mathonName, String url, Map mapHeade, String inputParams) {


        //将log文件设置在target目录下，每次运行后自动删除
        String logFileDir = "target/log";
        //判断log文件是否存在
        File file = new File(logFileDir);
        if (!file.exists()){
            file.mkdir();
        }

        //设置文件地址
        String logTestPath = logFileDir+"/test_" + System.currentTimeMillis() + ".log";

        //每个接口的请求都保存在一个本地日志文件中（重定向）
        PrintStream fileOutPutStream = null;
        try {
//            Date date =new Date();
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            String format1 = format.format(date);

            fileOutPutStream = new PrintStream(new File(logTestPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(fileOutPutStream));

        //对接口的入参进行替换
        inputParams = Environment.replaceParams(inputParams);
        //对请求头进行参数替换
        mapHeade = Environment.replaceParams(mapHeade);
        //对请求地址进行参数化替换
        url = Environment.replaceParams(url);

        Response res = null;
        if ("get".equalsIgnoreCase(mathonName)) {
            res = given().log().all().when().get(url + "?" + inputParams).then().log().body().extract().response();

        } else if ("post".equalsIgnoreCase(mathonName)) {
            res = given().log().all().headers(mapHeade).body(inputParams).when().post(url).then().log().all().extract().response();
        } else if ("put".equalsIgnoreCase(mathonName)) {
            res = given().log().all().headers(mapHeade).body(inputParams).when().put(url).then().log().all().extract().response();
        } else if ("delete".equalsIgnoreCase(mathonName)) {
            res = given().log().all().headers(mapHeade).body(inputParams).when().delete(url).then().log().all().extract().response();
        }else {
            System.out.println("接口请求非法，请确认请求是否的方法是否正确");
        }
        //Allure 报表的生成，添加日志信息添加表到报表中
        try {
            Allure.addAttachment("接口的请求/响应数据", new FileInputStream(logTestPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * 登录接口请求定义
     *
     * @param inputParams 传入的接口入参
     *                    {"principal":"waiwai","credentials":"lemon123456","appType":3,"loginType":0}
     * @return
     */
    public static Response login(String inputParams) {
//        Response res =
//                given().header("Content-Type", "application/json").
//                        body(inputParams).
//                        when().
//                        post("http://mall.lemonban.com:8107/login").
//                        then().
//                        log().all().
//                        extract().response();
//        return res;
        Map map = new HashMap();
        map.put("Content-Type", "application/json");
        Response request = request("post", "http://mall.lemonban.com:8107/login", map, inputParams);
        return request;
    }

    ;

    /**
     * 搜索商品接口请求定义
     *
     * @param inputParams 接口请求入参
     *                    prodName=&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12
     * @return 响应信息
     */
    public static Response searchProduct(String inputParams) {
        inputParams = Environment.replaceParams(inputParams);
//
//        Response res =
//                given().
//                        when().
//                        get("http://mall.lemonban.com:8107/search/searchProdPage?" + inputParams).
//                        then().
//                        log().all().
//                        extract().response();
//        return res;
        Map map = new HashMap();
        map.put("Content-Type", "application/json");
        Response res =request("get","http://mall.lemonban.com:8107/search/searchProdPage",map,inputParams);
        return res;
    }

    /**
     * 商品信息接口请求定义
     *
     * @param prodId 商品ID
     * @return 响应结果
     */
    public static Response productInfo(int prodId) {
        Map map = new HashMap();
        Response res =
//                given().
//                        when().
//                        get("http://mall.lemonban.com:8107/prod/prodInfo?" + "prodId=" + prodId).
//                        then().
//                        log().all().
//                        extract().response();
        request("get","http://mall.lemonban.com:8107/prod/prodInfo",map,"prodId="+prodId);
        return res;
    }

    /**
     * 添加购物车接口请求
     *
     * @param inputParams 请求入参
     *                    {"basketId":0,"count":1,"prodId":"83","shopId":1,"skuId":415}
     * @param token       鉴权token值，从登录接口返回的
     * @return 响应数据
     */

    public static Response addShopCart(String inputParams, String token) {
//
//        Response res =
//                given().
//                        header("Content-Type", "application/json").
//                        header("Authorization", "bearer" + token).
//                        body(inputParams).
//                        when().
//                        post("http://mall.lemonban.com:8107/p/shopCart/changeItem").
//                        then().
//                        log().all().
//                        extract().response();
//        return res;
        Map map = new HashMap();
        map.put("Content-Type", "application/json");
        map.put("Authorization", "bearer" + token);
        Response res = request("post", "http://mall.lemonban.com:8107/p/shopCart/changeItem", map, inputParams);
        return res;
    }


}
