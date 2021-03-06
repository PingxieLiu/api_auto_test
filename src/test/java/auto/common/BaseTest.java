package auto.common;

import auto.util.JDBCUtils;
import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;
import java.util.Set;

/**
 * @Classname BaseTest
 * @Description TODO
 * @Date 1/3/22 4:38 PM
 * @Created by lhk
 */
public class BaseTest {
    /**
     * 通用的响应断言的方法
     * @param res  接口返回数据
     * @param assertDatas  Excel中的断言的数据 json数据
     */
    public static void assertRespone(Response res, String assertDatas) {
        //statuscode:200,nickName:"waiwai"
        //3-1、json字符串怎么转成Java里面的Map？？？
        if (assertDatas != null) {
            Map<String, Object> map = JSONObject.parseObject(assertDatas);
            //3-2、遍历Map
            Set<Map.Entry<String, Object>> datas = map.entrySet();
            for (Map.Entry<String, Object> data : datas) {
                String key = data.getKey();
                Object value = data.getValue();
                if ("statuscode".equals(key)) {
                    System.out.println("响应状态码，期望值：" + value + ",实际值：" + res.getStatusCode());
                    //获取接口的响应状态码
                    Assert.assertEquals(value, res.getStatusCode());
                } else {
                    //nickName  --> 相当于Gpath表达式
                    //响应体数据断言
                    Assert.assertEquals(value, res.jsonPath().get(key));
                    System.out.println("响应体数据，期望值：" + value + ",实际值：" + res.jsonPath().get(key));
                }
            }
        }
    }

    /**
     * 通用的数据库断言方法
     * @param asserDB
     */
    public static void asserDB(String asserDB){
        Map<String, Object> map = JSONObject.parseObject(asserDB);
        //3-2、遍历Map
        Set<Map.Entry<String, Object>> datas = map.entrySet();
        for (Map.Entry<String, Object> keyValue : datas) {
            String key = keyValue.getKey();//sql语句；
            //查询数据库
            Object actualValue =JDBCUtils.querySingData(key);
            System.out.println("实际值："+actualValue.toString());
            System.out.println("期望值："+keyValue.getValue().toString());
            Assert.assertEquals(actualValue.toString(),keyValue.getValue().toString());

        }
    }
}


