package auto.testcases;

import auto.common.ApiCall;
import auto.common.BaseTest;
import auto.pojo.CaseData;
import auto.service.BusinessFlow;
import auto.util.Environment;
import auto.util.ExcelUtli;
import auto.util.JDBCUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;

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
        String token=Environment.getEmmap("token")+"";
        Response shopCartRes = ApiCall.addShopCart(shopCartData, token);
        //3、响应断言
        Assert.assertEquals(shopCartRes.getStatusCode(),200);
        //数据库断言
        String assersql="SELECT * FROM tz_basket WHERE user_id=(SELECT user_id FROM tz_user WHERE user_name='Test9350');";
        Map<String,Object> data= JDBCUtils.queryOneData(assersql);
        //根据字段做断言；
        Assert.assertEquals(data.get("basket_count"),1);

        //4、删除购物车商品
        Object basket_id = data.get("basket_id");
        System.out.println(basket_id.getClass());
        Response response = ApiCall.deleteShopCarts(Arrays.asList(basket_id),token);



    }


    @Test(dataProvider = "readExcelShop")
    public static void shopCarTest(CaseData caseData){
        //登录获取token；
        String inputpermas="{\"principal\":\"Test9350\",\"credentials\":\"123456\",\"appType\":3,\"loginType\":0}";
        Response resLogin = ApiCall.login(inputpermas);
        String access_token = resLogin.jsonPath().get("access_token");
        String inputParams = caseData.getInputParams();
        //添加购物车
        Response addShopCart = ApiCall.addShopCart(inputParams, access_token);
        //断言
       assertRespone(addShopCart,caseData.getAssertResponse());
       //数据库断言
        asserDB(caseData.getAsserDB());
        //删除数据库
        String assersql="SELECT * FROM tz_basket WHERE user_id=(SELECT user_id FROM tz_user WHERE user_name='Test9350');";
        Map<String,Object> data= JDBCUtils.queryOneData(assersql);
        Object basket_id = data.get("basket_id");
        System.out.println(basket_id.getClass());
        Response response = ApiCall.deleteShopCarts(Arrays.asList(basket_id),access_token);




    }

    @DataProvider
    public Object[] readExcelShop(){
        return ExcelUtli.readExcel(2).toArray();
    }
}
