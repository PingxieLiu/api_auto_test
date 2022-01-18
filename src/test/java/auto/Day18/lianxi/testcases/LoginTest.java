package auto.Day18.lianxi.testcases;


import auto.Day18.lianxi.apidefinition.ApiCall;
import auto.Day18.lianxi.common.BaseTest;
import auto.Day18.lianxi.pojo.CaseData;
import auto.Day18.lianxi.util.ExcelUtli;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

/**
 * @author 歪歪欧巴
 * @Description TODO
 * @date 2021/12/27 21:37
 * @Copyright 湖南省零檬信息技术有限公司. All rights reserved.
 */
public class LoginTest extends BaseTest {
    @Test
    public void test_login_success(){
        //1、准备测试数据
        String jsonData="{\"principal\":\"waiwai\",\"credentials\":\"lemon123456\",\"appType\":3,\"loginType\":0}";
        //2、直接调用登录的接口请求
        Response res = ApiCall.login(jsonData);
        //3、断言
        int statusCode = res.getStatusCode();
        Assert.assertEquals(statusCode,200);
        String nickName = res.jsonPath().get("nickName");
        Assert.assertEquals(nickName,"waiwai");
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
