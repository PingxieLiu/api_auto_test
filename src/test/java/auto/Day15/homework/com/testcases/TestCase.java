package auto.Day15.homework.com.testcases;

import auto.Day15.homework.com.testapi.ApiInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @Classname TestCase
 * @Description TODO
 * @Date 12/27/21 10:17 PM
 * @Created by lhk
 */
public class TestCase {

    @Test
    public void add_shoppingCart_Test() {

        //用户登录
        // 数据准备
        String loginData = "{\"principal\":\"waiwai\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}";
        // 用户登录请求
        Response loginRes = ApiInterface.loginTest(loginData);
        // 校验登录接口是否请求成功
        Assert.assertEquals(200, loginRes.getStatusCode());
        Assert.assertEquals("waiwai", loginRes.jsonPath().get("nickName"));
        // 获取token令牌
        String token_type = loginRes.jsonPath().get("token_type");
        String access_token = loginRes.jsonPath().get("access_token");


        // 商品搜索  
        // 数据准备  prodName=&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12
        String productName = "鸭鸭装饰";
        String param = "prodName=" + productName + "&categoryId=&sort=0&orderBy=0&current=1&isAllProdType=true&st=0&size=12";
        // 搜索商品接口
        Response searchProListRes = ApiInterface.searchProductListTest(param);
        //校验商品搜索接口是否请求成功
        Assert.assertEquals(200, searchProListRes.getStatusCode());
        Assert.assertEquals(productName, searchProListRes.jsonPath().get("records[0].prodName"));
        //获取商品的id
        int prodId = searchProListRes.jsonPath().get("records[0].prodId");


        //商品详情查看
        //根据商品id搜索商品详情
        Response selectProRes = ApiInterface.selectProductTest(prodId);
        Assert.assertEquals(200, selectProRes.getStatusCode());
        Assert.assertEquals(prodId, searchProListRes.jsonPath().get("records[0].prodId").toString());

        // 添加购物车
        //数据准备     "{\"basketId\":0,\"count\":1,\"prodId\":\"83\",\"shopId\":1,\"skuId\":415}";
        int shopId = selectProRes.jsonPath().get("shopId");
        int skuId = selectProRes.jsonPath().get("skuList[0].skuId");
        String shoppingCart = "{\"basketId\":0,\"count\":1,\"prodId\":"+prodId+",\"shopId\":"+shopId+",\"skuId\":"+skuId+"}";
        //  添加购物车请求接口
        Response shoppingCartRes = ApiInterface.shoppingCartTest(token_type, access_token, shoppingCart);
        Assert.assertEquals(200, shoppingCartRes.getStatusCode());


    }

}
