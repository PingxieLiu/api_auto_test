package auto.Day19.ketang.common;

import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;
import java.util.Set;

/**
 * @author 歪歪欧巴
 * @Description TODO
 * @date 2021/12/31 20:31
 * @Copyright 湖南省零檬信息技术有限公司. All rights reserved.
 */
public class BaseTest {
    /**
     * 通用的响应断言方法
     * @param assertDatas Excel中的断言数据（Json格式设计）
     * @param res 接口响应结果
     */
    public void assertResponse(String assertDatas, Response res){
        //判空处理，当从Excel读取的响应数据为空的，表示不需要断言
        if(null != assertDatas) {
            //{"statuscode":200,"nickName":"waiwai"}
            //3-1、json字符串怎么转成Java里面的Map？？？ 1:FastJson 2:Jackson
            Map<String, Object> map = JSONObject.parseObject(assertDatas);
            //3-2、遍历Map
            Set<Map.Entry<String, Object>> datas = map.entrySet();
            for (Map.Entry<String, Object> keyValue : datas) {
                String key = keyValue.getKey();
                Object value = keyValue.getValue();
                //3-3、断言
                if ("statuscode".equals(key)) {
                    //获取接口的响应状态码
                    int statuscode = res.getStatusCode();
                    System.out.println("断言响应状态码，期望值：" + value + " 实际值：" + statuscode);
                    Assert.assertEquals(statuscode, value);
                } else {
                    //响应体数据断言
                    //nickName -->相当于Gpath表达式
                    Object actualValue = res.jsonPath().get(key);
                    System.out.println("断言响应体字段"+key+"，期望值：" + value + " 实际值：" + actualValue);
                    Assert.assertEquals(actualValue, value);
                }
            }
        }
    }

}
