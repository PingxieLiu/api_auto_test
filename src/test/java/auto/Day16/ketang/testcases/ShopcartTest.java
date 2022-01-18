package auto.Day16.ketang.testcases;

import auto.Day16.ketang.apidefinition.ApiCall;
import auto.Day16.ketang.service.BusinessFlow;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * @author 歪歪欧巴
 * @Description TODO
 * @date 2021/12/27 21:37
 * @Copyright 湖南省零檬信息技术有限公司. All rights reserved.
 */
public class ShopcartTest {

    @Test
    public void test_add_shopcart_sucess(){
        //添加购物车用例
        //1、准备测试数据
        //2、发起接口请求
        //2-1、登录
        /*String loginData="{\"principal\":\"waiwai\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}";
        Response loginRes = ApiCall.login(loginData);
        //提取token
        String token = loginRes.jsonPath().get("access_token");
        //2-2、搜索商品
        String searchData = "prodName=&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12";
        Response searchRes = ApiCall.searchProduct(searchData);
        //提取商品ID
        int prodId = searchRes.jsonPath().get("records[0].prodId");
        //2-3、商品信息
        Response infoRes = ApiCall.productInfo(prodId);*/
        Response infoRes = BusinessFlow.login_search_info();
        //提取skuId
        int skuId = infoRes.jsonPath().get("skuList[0].skuId");
        //2-4、添加到购物车
        String shopCartData="{\"basketId\":0,\"count\":1,\"prodId\":"+BusinessFlow.prodId+",\"shopId\":1,\"skuId\":"+skuId+"}";
        Response shopCartRes = ApiCall.addShopCart(shopCartData,BusinessFlow.token);
        //3、断言
        Assert.assertEquals(shopCartRes.getStatusCode(),200);

    }
}
