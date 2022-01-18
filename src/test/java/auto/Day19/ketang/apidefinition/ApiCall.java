package auto.Day19.ketang.apidefinition;

import auto.Day19.ketang.util.Environment;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
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
     * 接口请求通用方法封装
     * @param method 请求方法（get/post/put/delete...）
     * @param url 接口请求地址
     * @param headersMap 请求头，存放到Map结构中
     * @param inputParams 请求参数
     * @return 接口响应结果
     */
    public static Response request(String method, String url, Map<String,Object> headersMap,String inputParams){
        //把所有的接口日志（请求+响应）重定向到本地指定文件中
        /*PrintStream fileOutPutStream = null;
        try {
            fileOutPutStream = new PrintStream(new File("log/test_all.log"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RestAssured.filters(new RequestLoggingFilter(fileOutPutStream),new ResponseLoggingFilter(fileOutPutStream));*/

        //每个接口请求的日志单独的保存到本地的每一个文件中(重定向)
        /*PrintStream fileOutPutStream = null;
        //设置日志文件的地址
        String logFileDir = "target/log/";
        File file = new File(logFileDir);
        if(!file.exists()){
            file.mkdirs();
        }
        String logFilePath = logFileDir+"test_"+System.currentTimeMillis()+".log";
        try {
            fileOutPutStream = new PrintStream(new File(logFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(fileOutPutStream));
*/

        //参数化替换
        //1、接口入参做参数化替换
        inputParams = Environment.replaceParams(inputParams);
        //2、接口请求头参数化替换
        headersMap = Environment.replaceParams(headersMap);
        //3、接口请求地址参数化替换
        url = Environment.replaceParams(url);
        Response res = null;
        if("get".equalsIgnoreCase(method)){
            res = given().log().all().headers(headersMap).when().get(url+"?"+inputParams).then().log().all().extract().response();
        }else if("post".equalsIgnoreCase(method)){
            res = given().log().all().headers(headersMap).body(inputParams).when().post(url).then().log().all().extract().response();
        }else if("put".equalsIgnoreCase(method)){
            res = given().log().all().headers(headersMap).body(inputParams).when().put(url).then().log().all().extract().response();
        }else if("delete".equalsIgnoreCase(method)){
            //TODO
        }else {
            System.out.println("接口请求方法非法，请检查你的请求方法");
        }
        //添加日志信息到Allure报表中
        /*try {
            Allure.addAttachment("接口的请求/响应信息",new FileInputStream(logFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        return res;
    }

    /**
     * 登录接口请求定义
     * @param inputParams 传入的接口入参
     * {"principal":"waiwai","credentials":"lemon123456","appType":3,"loginType":0}
     * @return
     */
    public static Response login(String inputParams){
        Map<String,Object> headMap = new HashMap<>();
        headMap.put("Content-Type","application/json");
        return request("post","http://mall.lemonban.com:8107/login",headMap,inputParams);
    }

    /**
     * 搜索商品接口请求定义
     * @param inputParams 接口请求入参
     * prodName=&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12
     * @return 响应信息
     */
    public static Response searchProduct(String inputParams){
        inputParams = Environment.replaceParams(inputParams);
        Map<String,Object> headMap = new HashMap<>();
        headMap.put("Content-Type","application/json");
        return request("get","http://mall.lemonban.com:8107/search/searchProdPage",headMap,inputParams);
    }

    /**
     * 商品信息接口请求定义
     * @param prodId 商品ID
     * @return 响应结果
     */
    public static Response productInfo(int prodId){
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("Content-Type","application/json");
        return request("get","http://mall.lemonban.com:8107/prod/prodInfo",headMap,"prodId="+prodId);

    }

    /**
     * 添加购物车接口请求
     * @param inputParams 请求入参
     * {"basketId":0,"count":1,"prodId":"83","shopId":1,"skuId":415}
     * @param token 鉴权token值，从登录接口返回的
     * @return 响应数据
     */
    public static Response addShopCart(String inputParams,String token){
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("Content-Type","application/json");
        headMap.put("Authorization","bearer"+token);
        return request("post","http://mall.lemonban.com:8107/p/shopCart/changeItem",headMap,inputParams);
    }

    /**
     * 注册验证码发送接口请求
     * @param inputParams 请求参数
     * {"mobile":"13323234543"}
     * @return 响应数据
     */
    public static Response sendRegisterSms(String inputParams){
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("Content-Type","application/json");
        return request("put","http://mall.lemonban.com:8107/user/sendRegisterSms",headMap,inputParams);
    }

    /**
     * 校验注册验证码接口请求
     * @param inputParams 请求入参
     * {"mobile":"13323234543","validCode":"815435"}
     * @return
     */
    public static Response checkRegisterSms(String inputParams){
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("Content-Type","application/json");
        return request("put","http://mall.lemonban.com:8107/user/checkRegisterSms",headMap,inputParams);
    }

    /**
     * 注册接口请求
     * @param inputParams 请求入参
     * {"appType":3,"checkRegisterSmsFlag":"3cbc5a0c45564c91915388db268d6559",
     *                    "mobile":"13323234543","userName":"lemontester02",
     *                    "password":"123456","registerOrBind":1,"validateType":1}
     * @return
     */
    public static Response register(String inputParams){
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("Content-Type","application/json");
        return request("put","http://mall.lemonban.com:8107/user/registerOrBindUser",headMap,inputParams);
    }
}
