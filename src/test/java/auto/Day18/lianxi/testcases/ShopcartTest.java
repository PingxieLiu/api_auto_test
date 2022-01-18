package auto.Day18.lianxi.testcases;

import auto.Day18.lianxi.apidefinition.ApiCall;
import auto.Day18.lianxi.common.BaseTest;
import auto.Day18.lianxi.service.BusinessFlow;
import auto.Day18.lianxi.util.Environment;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author 歪歪欧巴
 * @Description TODO
 * @date 2021/12/27 21:37
 * @Copyright 湖南省零檬信息技术有限公司. All rights reserved.
 */
public class ShopcartTest extends BaseTest {

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
        //String shopCartData="{\"basketId\":0,\"count\":1,\"prodId\":"+ Environment.getEmmap("prodId") +",\"shopId\":1,\"skuId\":"+skuId+"}";
        String shopCartData = "{\"basketId\":0,\"count\":1,\"prodId\":\"#prodId#\",\"shopId\":1,\"skuId\":#skuId#}";

        Environment.saveEmmap("skuId",skuId);
        Response shopCartRes = ApiCall.addShopCart(shopCartData, Environment.getEmmap("token")+"");
        //3、断言
        Assert.assertEquals(shopCartRes.getStatusCode(),200);

    }
}
