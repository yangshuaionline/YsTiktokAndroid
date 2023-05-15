package com.yangshuai.library.base.router;

import android.text.TextUtils;

import java.lang.reflect.Field;

/**
 * 路由路径统一在此类声明
 * (注意：路由路径一定要斜杠 '/' 开头，否则DataBinding会无法生成)
 *
 * @author hcp
 * @created 2019/3/12
 */
public class RouterPath {

    public interface Features {
        // 图片预览
        String ROUTE_PREVIEW_IMAGES = "/image/preview";
        // 透明页面
        String ROUTE_TRANSPARENT_DIALOG = "/transparent/dialog";
    }
    /**
     * 壳组件
     * */
    public interface App{
        String APP = "/app";
        //选择身份
        String ROUTE_SELECT_IDENTITY_PATH = APP + "/SelectIdentityAct";
    }
    /**
     * 主业务组件
     */
    public interface Main {
        String MAIN = "/main";
        // 入口
        String ROUTE_MAIN_KT_PATH = MAIN + "/mainktAct";
    }
    /**
     * 主业务组件
     */
    public interface HOME {
        String MAIN = "/home";
        // 入口
        String ROUTE_MAIN_PATH = MAIN + "/HomeFragment";

    }
    /**
     * 购物车
     * */
    public interface CART{
        String CART = "/cart";
        String ROUTER_MAIN_SHOPPING_CART = CART + "/ShoppingCartFragment";
    }
    /**
     * 工作台组件
     */
    public interface Work {
        String WORK = "/work";

        // 工作台Fragment
        String ROUTE_WORK_PATH = WORK + "/workFrgm";
    }

    /**
     * 通讯录组件
     */
    public interface Contact {
        String CONTACT = "/contact";
        // 通讯录Fragment
        String ROUTE_CONTACT_PATH = CONTACT + "/contactFrgm";
        //通讯录搜索界面
    }

    /**
     * 个人中心组件(我的)
     */
    public interface Mine {
        String MINE = "/mine";

        // 我的Fragment
        String ROUTE_MINE_PATH = MINE + "/mineFrgm";
        // 员工入职
        String ROUTE_MINE_HIRES = MINE + "/hires";
        // 帐号与安全
        String ROUTE_MINE_ACCOUNT_SECURITY = MINE + "/account/security";
    }



    /**
     * 登录注册
     */
    public interface Signin {
        String SIGNIN = "/signin";
        //验证码登录
        String ROUTE_SIGNIN_PHONE = SIGNIN + "/signin/phone";
        //密码登录
        String ROUTE_SIGNIN_PASSWORD = SIGNIN + "/signin/password";
    }

    public interface IM {
        String IM = "/im";
        String IM_MAIN = IM + "/main";//IM主页，暂时没用
        String IM_GROUP = IM + "/group";//IM群主聊天页
        String IM_CHATTING = IM + "/catting";//IM一对一单聊
        String IM_MESSAGE = IM + "/message";//IM一对一单聊
    }
    /**
     * 判断地址是否是路由
     *
     * @param path 传进来的地址
     */
    public static boolean isRouterPath(String path) {
        int flag = 0;
        Class[] innerClass = RouterPath.class.getDeclaredClasses();
        for (Class classInner : innerClass) {
            Field[] fields = classInner.getDeclaredFields();
            for (Field field : fields) {
                try {
                    Object object = field.get(classInner);
                    if (!TextUtils.isEmpty(object.toString()) && object.toString().equals(path)) {
                        flag = 1;
                        break;
                    }
                } catch (Exception e) {//IllegalAccessException 捕获object为null 情况
                    e.printStackTrace();
                }
            }
            if (flag == 1) {
                break;
            }
        }
        return flag == 1;
    }

}
