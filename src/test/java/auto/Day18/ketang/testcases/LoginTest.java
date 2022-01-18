package auto.Day18.ketang.testcases;

import auto.Day18.ketang.apidefinition.ApiCall;
import auto.Day18.ketang.common.BaseTest;
import auto.Day18.ketang.pojo.CaseData;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;

import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static io.restassured.RestAssured.given;

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
        Assert.assertEquals(statusCode,2000);
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
        assertResponse(assertDatas,res);
    }


    @DataProvider
    public Object[] getLoginDatasFromExcel(){
        //1、读取Excel？？Easypoi
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(0);
        //读取的文件src\test\resources\caseData.xlsx
        List<CaseData> datas = ExcelImportUtil.importExcel(new File("src/test/resources/caseData.xlsx"),
                CaseData.class,importParams);
        //集合转成一维数组

        return datas.toArray();
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("Content-Type","application/json");
        map.put("Authorization","XXXX");
        map.put("XXX111","XXXX");
    }
}
