package auto.testcases;


import auto.common.ApiCall;
import auto.common.BaseTest;
import auto.pojo.CaseData;
import auto.util.ExcelUtli;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author 歪歪欧巴
 * @Description TODO
 * @date 2021/12/27 21:37
 * @Copyright 湖南省零檬信息技术有限公司. All rights reserved.
 */
public class LoginTest extends BaseTest {
    /**
     * 方法一：直接登录
     */
    @Test
    public void test_login_success(){
        //1、准备测试数据测试
        String jsonData="{\"principal\":\"admin\",\"credentials\":\"123456\",\"appType\":3,\"loginType\":0}";
        //2、直接调用登录的接口请求
        Response res = ApiCall.login(jsonData);
        //3、断言
        int statusCode = res.getStatusCode();
        Assert.assertEquals(statusCode,200);
        String nickName = res.jsonPath().get("nickName");
        Assert.assertEquals(nickName,"admin");
    }

    @DataProvider
    public Object[] getLoginDatas(){
        //一维数组/二维数组
        Object[] data = {"{\"principal\":\"waiwai\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}",
                "{\"principal\":\"\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}",
        "{\"principal\":\"waiwai\",\"credentials\":\"\",\"appType\":3,\"loginType\":0}",
                "{\"principal\":\"lemon1111\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}"};
        return data;
    }

    /**
     * 方法二：定义一个二维数组，传入登录接口中
     * @param caseData
     */
    @Test(dataProvider = "getLoginDatas",enabled = false)
    public void test_login_from_array(String caseData){
        //1、准备测试数据
        //String jsonData="{\"principal\":\"waiwai\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}";
        //2、直接调用登录的接口请求
        Response res = ApiCall.login(caseData);
        //3、断言
        /*int statusCode = res.getStatusCode();
        Assert.assertEquals(statusCode,2000);
        String nickName = res.jsonPath().get("nickName");
        Assert.assertEquals(nickName,"waiwai");*/
    }

    /**
     * 方法三：读取Excel中的数据，传入登录接口中
     * @param caseData Excel中的字段封装成对象返回，登录接口直接传入对象中的某一个字段
     */
    @Test(dataProvider = "getLoginDatasFromExcel" )
    public void test_login_from_excel(CaseData caseData){
        //1、准备测试数据
        //String jsonData="{\"principal\":\"waiwai\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}";
        //2、直接调用登录的接口请求
        Response res = ApiCall.login(caseData.getInputParams());
        //3、断言
        String assertDatas = caseData.getAssertResponse();
        assertRespone(res, assertDatas);
        //3-3、断言

    }

    @DataProvider
    public Object[] getLoginDatasFromExcel(){
        List<CaseData> datas = ExcelUtli.readExcel(0);
        //集合转成一维数组
        return datas.toArray();
    }
}
