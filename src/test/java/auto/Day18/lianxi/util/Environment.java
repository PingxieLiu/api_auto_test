package auto.Day18.lianxi.util;

import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname Environment
 * @Description TODO
 * @Date 1/3/22 5:15 PM
 * @Created by lhk
 *
 * 环境变量
 */
public class Environment {

    public  static Map<String,Object> emmap = new HashMap<String, Object>();


    //存值
    public  static void saveEmmap(String varName, Object varValue){
        Environment.emmap.put(varName,varValue);
    }

    //取值
    public static Object getEmmap(String varName){
        return Environment.emmap.get(varName);
    }

    /**
     * String参数化替换
     * @param Params
     * @return
     */
    public static String replaceParams(String Params) {
        //识别正则表达式
        String regex = "#(.+?)#";
        //编译得到pattern模式对象
        Pattern pattern = Pattern.compile(regex);
        //通过pattern的matcher匹配得到 匹配器 -->搜索
        Matcher matcher = pattern.matcher(Params);
        //循环在原始的字符串中，
        while (matcher.find()){
            //matcher.group(0) 表示整个匹配到的字符串
            String whoole = matcher.group(0);
            //matcher.group(1) 分组的第一个结果 #xxx# 可以匹配到里面的xx
            String subString = matcher.group(1);
            Params = Params.replace(whoole, Environment.getEmmap(subString) + "");

        }
        return Params;
    }

    /**
     * String参数化替换
     * @param map
     * @return
     */
    public static Map replaceParams(Map map) {
        String jsonObject = JSONObject.toJSONString(map);
        String s = replaceParams(jsonObject);
        Map map1 = JSONObject.parseObject(s);
        return map1;
    }


    @Test
    void regexTest(){
        //String jsonData = "{\"basketId\":0,\"count\":1,\"prodId\":\"83\",\"shopId\":1,\"skuId\":415}";
        String jsonData = "{\"basketId\":0,\"count\":1,\"prodId\":\"#prodId#\",\"shopId\":1,\"skuId\":#skuId#}";

        Environment.saveEmmap("prodId",83);
        Environment.saveEmmap("skuId",415);
        String s = replaceParams(jsonData);
        System.out.println(s);

    }



}
