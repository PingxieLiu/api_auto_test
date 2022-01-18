package auto.Day19.lianxi.util;

import com.github.javafaker.Faker;
import org.testng.annotations.Test;

import java.util.Locale;

/**
 * @Classname RandomDataUtil
 * @Description TODO
 * @Date 1/16/22 7:57 PM
 * @Created by lhk
 */
public class RandomDataUtil {

    public static String phoneNumber() {
        Faker faker = new Faker(Locale.CHINA);
        String s = faker.phoneNumber().cellPhone();
        return s;
    }


    public static String getUserPhone() {
        //生成随机的电话号码
        Faker faker = new Faker(Locale.CHINA);
        String phone = faker.phoneNumber().cellPhone();
        //查询数据库
        String sql = "select count(*) from tz_user where user_mobile='" + phone + "';";
        //循环遍历
        while (true) {
            long count = (long) JDBCUtils.querySingData(sql);

            if (count == 0) {
                //没有被注册的手机
                break;
            } else if (count == 1) {
                //手机号被注册了，从新生成一个手机号；
                phone = faker.phoneNumber().cellPhone();
                //查询数据库
                sql = "select count(*) from tz_user where user_mobile='" + phone + "';";

            }
        }
        return phone;
    }


    public static String getUserName() {
        //生成随机用户姓名
        Faker faker = new Faker();
        String randomname = faker.name().lastName();
        //查询数据库
        String sql = "select count(*) from tz_user where user_name='" + randomname+ "';";
        //循环遍历
        while (true) {
            long count = (long) JDBCUtils.querySingData(sql);

            if (count == 0) {
                //没有被注册的手机
                break;
            } else if (count == 1) {
                //手机号被注册了，从新生成一个手机号；
                randomname = faker.name().lastName();
                //查询数据库
                sql = "select count(*) from tz_user where user_mobile='" + randomname + "';";

            }
        }
        return randomname;
    }
}
