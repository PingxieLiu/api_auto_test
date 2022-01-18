package auto.Day19.ketang.testcases;

import auto.Day19.ketang.apidefinition.ApiCall;
import auto.Day19.ketang.common.BaseTest;
import auto.Day19.ketang.util.Environment;
import auto.Day19.ketang.util.JDBCUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * @author 歪歪欧巴
 * @Description TODO
 * @date 2022/1/7 21:15
 * @Copyright 湖南省零檬信息技术有限公司. All rights reserved.
 */
public class RegisterTest extends BaseTest {
    @Test
    public void test_register_success(){
        //1、发起验证码接口
        String data01 = "{\"mobile\":\"13323234542\"}";
        ApiCall.sendRegisterSms(data01);

        //2、校验验证码接口
        //关键问题：验证码该怎么获取？？？查询数据库表tz_sms_log-->Java代码查询数据库
        String sql = "SELECT mobile_code from tz_sms_log where id = (SELECT MAX(id) FROM tz_sms_log);";
        String code = (String) JDBCUtils.querySingleData(sql);
        //将验证码保存到环境变量中
        Environment.saveToEnvironment("code",code);
        String data02 = "{\"mobile\":\"13323234542\",\"validCode\":\"#code#\"}";
        Response checkRes = ApiCall.checkRegisterSms(data02);
        //拿到接口响应纯文本类型的数据
        String checkSms = checkRes.body().asString();
        //将验证码校验字符串保存到环境变量中
        Environment.saveToEnvironment("checkSms",checkSms);

        //3、注册接口请求
        String data03 = "{\"appType\":3,\"checkRegisterSmsFlag\":\"#checkSms#\"," +
                "\"mobile\":\"13323234542\",\"userName\":\"lemontester03\"," +
                "\"password\":\"123456\",\"registerOrBind\":1,\"validateType\":1}";
        Response registerRes = ApiCall.register(data03);
    }
}
