package com.yangshuai.library.base;

import com.yangshuai.library.base.appinfo.AppInfoManager;
import com.yangshuai.library.base.oss.OSSMediaType;

import org.junit.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author zrh
 * @Created 4/28/19
 * @Editor zrh
 * @Edited 4/28/19
 * @Type
 * @Layout
 * @Api
 */
public class OSSClientTest {
    @Test
    public void upload() {
        long progerss = 1903825;
        long total = 9579275;
        float percent = progerss * 100.f / total;
        int p = (int) (percent);
        System.out.println("percent:" + p + "%");
    }

    @Test
    public void getFileTypeTest() {
        String suffix = "mp4";
        String type = OSSMediaType.getTypeBySuffix(suffix);
        System.out.println(type);
        System.out.println(OSSMediaType.isSupportType(type));
    }

    @Test
    public void testPassword() {
        // 必须包含一个数字一个字母，其他字符可以随便输入
        String isPassword = "^(?=.*?[a-zA-Z])(?=.*?[0-9]).{8,16}$";


        boolean result1 = Pattern.compile(isPassword).matcher("123234345").matches();
        boolean result2 = Pattern.compile(isPassword).matcher("adfasdfasdf").matches();
        boolean result3 = Pattern.compile(isPassword).matcher("_aas&**(").matches();
        boolean result4 = Pattern.compile(isPassword).matcher("aas&**(1").matches();

        System.out.println("匹配结果：" + result1);
        System.out.println("匹配结果：" + result2);
        System.out.println("匹配结果：" + result3);
        System.out.println("匹配结果：" + result4);
    }

    @Test
    public void test_array_sorted() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(Math.round((float) Math.random() * 100));
        }

        System.out.println("未排序");
        for (Integer integer : list) {
            System.out.println(integer);
        }

        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        System.out.println("已排序");
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    @Test
    public void test_get_date_int() {
        System.out.println("now:" + AppInfoManager.getNowDate());
    }

    @Test
    public void test_decimalformat(){
        double value = Double.valueOf("0");
        DecimalFormat decimalFormat = new DecimalFormat(",###.##");
        String str = decimalFormat.format(value);
        System.out.println(str);
    }
}
