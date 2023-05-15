package com.yangshuai.library.base.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单验证类
 *
 * @Author hcp
 * @Created 2019/3/30
 * @Editor hcp
 * @Edited 2019/3/30
 * @Type
 * @Layout
 * @Api
 */
public class UVerify {
    //无长度
    private static final int LENGTH_NONE = 0;
    //
    public static final String ERROR_MSG_IGNORE = null;

    public enum Type {
        //手机
        PHONE("[0-1][03456789]\\d{9}"),
        //特别行政区13位手机
        PHONE13("[0-1][03456789]\\d{11}"),
        //字符串是否为空
        EMPTY(null),
        //字符串最大长度
        MAX(null),
        //字符串最小长度
        MIN(null),
        //邮箱
        MAIL("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$"),
        //数字
        NUMBER("^(-?\\d+)(\\.\\d+)?$"),
        //数字不能大于
        NUMBER_MAX(null),
        //数字不能小于
        NUMBER_MIN(null),
        //零
        ZERO(null),
        //空列表
        EMPTY_LIST(null),
        //列表最小长度
        MIN_LIST(null),
        //列表最大长度
        MAX_LIST(null),
        //自定义
        CUSTOM(null),
        //等于
        PHONES(null),
        ID_CARD("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
        String regex;

        Type(String regex) {
            this.regex = regex;
        }
    }

    private List<VerifyBean> verifys;


    public UVerify() {
        verifys = new ArrayList<>();
    }

    private UVerify self() {
        return this;
    }


    public static UVerify get() {
        return new UVerify();
    }

    /**
     * 判断字符串的长度是否等于某个值
     * */


    /**
     * 验证字符串是否为空
     *
     * @param text
     * @param errMsg
     * @return
     */
    public UVerify empty(String text, String errMsg) {
        return asText(Type.EMPTY, text, LENGTH_NONE, errMsg);
    }

    /**
     * 验证字符串是否为手机
     *
     * @param phone
     * @param errMsg
     * @return
     */
    public UVerify phone(String phone, String errMsg) {
        if (TextUtils.isEmpty(phone)) return empty(phone, errMsg);
        if (phone.length() == 13) {
            return asText(Type.PHONE13, phone, LENGTH_NONE, errMsg);
        } else {
            return asText(Type.PHONE, phone, LENGTH_NONE, errMsg);
        }

    }

    /**
     *
     * 验证是否为手机号，不考虑首位1，只需要13或者15位
     *
     * */
    public UVerify phoneNoLimit(String phone, String errMsg) {
        if (TextUtils.isEmpty(phone)) return empty(phone, errMsg);
        return asText(Type.PHONES, phone, LENGTH_NONE, errMsg);
    }

    /**
     * 验证字符串是否为身份证
     *
     * */
    public UVerify idCard(String idCard, String errMsg) {
        if (TextUtils.isEmpty(idCard)) return empty(idCard, errMsg);
        return asText(Type.ID_CARD, idCard, LENGTH_NONE, errMsg);
    }


    /**
     * 验证字符串是否为邮箱
     *
     * @param mail
     * @param errMsg
     * @return
     */
    public UVerify mail(String mail, String errMsg) {
        return asText(Type.MAIL, mail, LENGTH_NONE, errMsg);
    }

    /**
     * 验证字符串长度不能超过maxLength
     *
     * @param text
     * @param maxLength
     * @param errMsg
     * @return
     */
    public UVerify maxLength(String text, int maxLength, String errMsg) {
        return asText(Type.MAX, text, maxLength, errMsg);
    }

    /**
     * 验证字符串长度不能小于minLength
     *
     * @param text
     * @param minLength
     * @param errMsg
     * @return
     */
    public UVerify minLength(String text, int minLength, String errMsg) {
        return asText(Type.MIN, text, minLength, errMsg);
    }

    /**
     * 验证字符串是否为数字
     *
     * @param text
     * @param errMsg
     * @return
     */
    public UVerify number(String text, String errMsg) {
        return asText(Type.NUMBER, text, LENGTH_NONE, errMsg);
    }

    /**
     * 数字不能小于
     *
     * @param text
     * @param min
     * @param errMsg
     * @return
     */
    public UVerify numberMin(String text, int min, String errMsg) {
        return asText(Type.NUMBER_MIN, text, min, errMsg);
    }

    /**
     * 数字不能大于
     *
     * @param text
     * @param max
     * @param errMsg
     * @return
     */
    public UVerify numberMax(String text, int max, String errMsg) {
        return asText(Type.NUMBER_MAX, text, max, errMsg);
    }


    /**
     * 验证字符串是否为0
     *
     * @param text
     * @param errMsg
     * @return
     */
    public UVerify zero(String text, String errMsg) {
        return asText(Type.ZERO, text, LENGTH_NONE, errMsg);
    }

    /**
     * 验证列表是否为空
     *
     * @param list
     * @param errMsg
     * @return
     */
    public UVerify emptyList(List<?> list, String errMsg) {
        return asList(Type.EMPTY_LIST, list, LENGTH_NONE, errMsg);
    }

    /**
     * 验证列表长度不能小于 minLength
     *
     * @param list
     * @param minLength
     * @param errMsg
     * @return
     */
    public UVerify minList(List<?> list, int minLength, String errMsg) {
        return asList(Type.MIN_LIST, list, minLength, errMsg);
    }

    /**
     * 验证列表长度不能大于 maxLength
     *
     * @param list
     * @param maxLength
     * @param errMsg
     * @return
     */
    public UVerify maxList(List<?> list, int maxLength, String errMsg) {
        return asList(Type.MAX_LIST, list, maxLength, errMsg);
    }


    public UVerify custom(OnCustomVerifyListener customVerifyListener) {
        VerifyBean bean = new VerifyBean();
        bean.setType(Type.CUSTOM);
        bean.setErrMsg("");
        bean.setCustom(customVerifyListener);
        verifys.add(bean);
        return self();
    }


    private UVerify asText(Type type, String text, int length, String errMsg) {

        VerifyBean bean = new VerifyBean(type, text, null, length, errMsg);

        verifys.add(bean);

        return self();
    }


    private UVerify asList(Type type, List<?> list, int length, String errMsg) {
        VerifyBean bean = new VerifyBean(type, null, list, length, errMsg);
        verifys.add(bean);
        return self();
    }


    public String verify() {
        for (VerifyBean verify : verifys) {
            String errMsg = verify(verify);
            if (errMsg != null) {
                return errMsg;
            }
        }
        return null;
    }

    private String verify(VerifyBean bean) {
        String errMsg = bean.getErrMsg();
        //忽略本次验证
        if (errMsg == ERROR_MSG_IGNORE) {
            return ERROR_MSG_IGNORE;
        }
        Type type = bean.getType();
        String text = bean.getText();
        int len = bean.getLength();
        List<?> list = bean.getList();
        //避免null空指针异常
        if (type != Type.EMPTY && (text == null || text.equals("null"))) {
            text = "";
        }

        String finalErrMsg = null;
        if (type == Type.EMPTY) {//验证字符串是否为空
            if (text == null || "".equals(text) || "null".equals(text)) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.MIN) {//字符串最小长度
            if (text.length() < len) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.MAX) {//字符串最大长度
            if (text.length() > len) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.NUMBER) {//是否为数字
            if (!text.matches(type.regex)) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.NUMBER_MIN) {//数字不能小于
            if (text.matches(Type.NUMBER.regex) && Double.parseDouble(text) < len) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.NUMBER_MAX) {//数字不能大于
            if (text.matches(Type.NUMBER.regex) && Double.parseDouble(text) > len) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.ZERO) {//是否为0
            if (text.matches(Type.NUMBER.regex) && Double.parseDouble(text) == 0) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.PHONE) {//是否为手机号码
            if (!text.matches(type.regex)) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.PHONE13) {
            if (!text.matches(type.regex)) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.MAIL) {//是否为邮箱
            if (!text.matches(type.regex)) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.EMPTY_LIST) {//列表是否为空
            if (list == null || list.size() == 0) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.MIN_LIST) {//列表长度不能小于...
            if (list.size() < len) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.MAX_LIST) {//列表长度不能大于...
            if (list.size() > len) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.CUSTOM && bean.getCustom() != null) {
            String err = bean.getCustom().call(self());
            if (!TextUtils.isEmpty(err)) {
                finalErrMsg = err;
            }
        } else if (type == Type.ID_CARD) {
            if (!text.matches(type.regex)) {
                finalErrMsg = errMsg;
            }
        } else if (type == Type.PHONES) {//手机号只做长度判断
            if (text.length() < 11 || text.length() > 13 || text.length() == 12) {
                finalErrMsg = errMsg;
            }
        }

        return finalErrMsg;

    }


    private final class VerifyBean {
        private Type type;
        private String text;
        private List<?> list;
        private int length;
        private String errMsg;
        private OnCustomVerifyListener custom;


        public VerifyBean() {

        }

        public VerifyBean(Type type, String text, List<?> list, int length, String errMsg) {
            this.type = type;
            this.text = text;
            this.list = list;
            this.length = length;
            this.errMsg = errMsg;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public List<?> getList() {
            return list;
        }

        public void setList(List<?> list) {
            this.list = list;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public void setCustom(OnCustomVerifyListener custom) {
            this.custom = custom;
        }

        public OnCustomVerifyListener getCustom() {
            return custom;
        }
    }

    public interface OnCustomVerifyListener {
        String call(UVerify verify);
    }
}
