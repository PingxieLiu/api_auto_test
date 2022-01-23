package auto.testcases;

import auto.common.ApiCall;
import auto.common.BaseTest;
import auto.util.Environment;
import auto.util.JDBCUtils;
import auto.util.RandomDataUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * @Classname RegisterTest
 * @Description TODO
 * @Date 1/15/22 4:10 PM
 * @Created by lhk
 */
public class RegisterTest extends BaseTest {


    @Test
    @BeforeTest
    @BeforeMethod
    @AfterMethod
    @BeforeSuite
    @AfterSuite
    @AfterTest
    @BeforeClass
    @AfterClass
    public void sendSms_success_Test() {
        //获取手机号 -->查询数据库
        String userPhone = RandomDataUtil.getUserPhone();
        Environment.saveEmmap("userPhone", userPhone);

        //获取用户名；
        String randomName = RandomDataUtil.getUserName();
        Environment.saveEmmap("randomName",randomName);
        //1、发送验证码
        String data1 = "{\"mobile\": \"#userPhone#\"}";
        ApiCall.sendRegisterSms(data1);
        //查询数据库，获取验证码
        String sql = "SELECT mobile_code from tz_sms_log where id = (SELECT MAX(id) FROM tz_sms_log);";
        String code = (String) JDBCUtils.querySingData(sql);
        Environment.saveEmmap("code",code);
        //校验验证码接口
        String data2 = "{\"mobile\": \"#userPhone#\",\"validCode\": \"#code#\"}";
        Response res = ApiCall.checkRegisterSms(data2);
        //拿到接口的文本类型的数据
        String checkSms = res.body().asString();
        //将验证码保存到环境变量中
        Environment.saveEmmap("checkSms", checkSms);
        //注册接口
        String data03 = "{\"appType\":3,\"checkRegisterSmsFlag\": \"#checkSms#\"," +
                "\"mobile\": \"#userPhone#\",\"userName\": \"#randomName#\"," +
                "\"password\": \"123456\",\"registerOrBind\": 1,\"validateType\": 1}";
        Response response = ApiCall.registerOrBindUser(data03);

        //响应断言
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.jsonPath().get("nickName"),randomName);

        //数据库断言
        String ressql="select count(*) from tz_user where user_mobile='#userPhone#';";
        Assert.assertEquals((long)JDBCUtils.querySingData(ressql),1);


    }

}
