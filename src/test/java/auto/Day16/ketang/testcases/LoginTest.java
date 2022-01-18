package auto.Day16.ketang.testcases;


import auto.Day16.ketang.apidefinition.ApiCall;
import auto.Day16.ketang.pojo.CaseData;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * @author 歪歪欧巴
 * @Description TODO
 * @date 2021/12/27 21:37
 * @Copyright 湖南省零檬信息技术有限公司. All rights reserved.
 */
public class LoginTest {
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

    @Test(dataProvider = "getLoginDatas" )
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
        //statuscode:200,nickName:"waiwai"
        //3-1、json字符串怎么转成Java里面的Map？？？
        //3-2、遍历Map
        //3-3、断言



        /*String[] datas01 = assertDatas.split(",");
        for(int i=0; i< datas01.length;i++){
            String[] datas02 = datas01[i].split(":");
            String name = datas02[0];
            Object value = datas02[1];
            if(name.equals("statuscode")){
                //获取实际响应体字段对应的值(nickName)
                System.out.println("value的类型"+value.getClass());
                //响应状态码
                Assert.assertEquals(res.getStatusCode(),value);

            }else {
                Assert.assertEquals(res.jsonPath().get(name),value);
            }
        }*/
    }

    @DataProvider
    public Object[] getLoginDatasFromExcel(){
        //1、读取Excel？？Easypoi
        ImportParams importParams = new ImportParams();
        importParams.setStartSheetIndex(0);
        //读取的文件src\test\resources\caseData.xlsx
        List<CaseData> datas = ExcelImportUtil.importExcel(new File("src\\test\\resources\\caseData.xlsx"),
                CaseData.class,importParams);
        //集合转成一维数组
        return datas.toArray();
    }
}
