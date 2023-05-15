package com.yangshuai.library.base.common;

/**
 * 数据字典
 *
 * @author hcp
 * @email hcp
 */
public interface BaseCommon {

    String PAYMENT_TYPE = "payment_type";// 押付方式

    String DECORATION_TYPE = "decoration_type";//装修类型

    // 客源的装修情况
    String DECORATION = "decoration";//装修类型

    String PROPERTY_SOURCE = "property_source";//来源


    String PROP_CARPORT_TYPE = "prop_carport_type";//车位类型


    String PROP_SHOP_TYPE = "prop_shop_type";//商铺类型


    String PROP_OFFICES_TYPE = "prop_offices_type";//写字楼类型


    String PROPERTY_SITUATIONS = "property_situations";//房屋现状


    String CARPORT_SITUATIONS = "carport_situations";//车位现状


    String OFFICES_SITUATIONS = "offices_situations";//写字楼现状

    String SHOP_SITUATIONS = "shop_situations";//商铺现状


    String PROPERTY_LIFE = "property_life";//产权年限


    String DICTTYPE = "property_right_type";//产权类型

    String PROPERTY_RIGHT = "property_right";//产证年限

    String ORIENTAION_TYPE = "orientation_type";//朝向


    String PROPERTY_DEVICE_TIMES = "property_device_items";//配套设施(住宅)


    String SHOP_DEVICE_TIMES = "shop_device_items";//配套设施(商铺)


    String OFFICE_DEVICE_ITMES = "offices_device_items";//写字楼配套

    String PROP_OFFICE_TYPE = "prop_offices_type";//写字楼类型

    String TAX_TYPE = "tax_type";//税费类型


    String PAY_WAY = "pay_way";//付款方式

    String UB_HOUSELABLE = "ub_houselabel";//房源标签//出售房源


    String UB_RENT_HOUSELABLE = "ub_renthouselabel";//房源标签//租房，房源标签
    String UB_HOUSE_LABLE_RENT = "ub_renthouselabel"; // 房源标签(租)


    String PROPERTY_MODEL = "property_model";//房屋性质
    String OWNER_SHIP = "ownership";// 产权情况
    String PAY_MODEL = "pay_model";//付款方式（合同模块）
    String BANK_NO = "bank_no";//银行名称
    String FINANCE_FUND_NO = "finance_fund_no";//无合同收款款项
    String CONTRACT_ITEM_NO = "contract_item_no";//合同款项
    String CONTRACT_RENT_ITEM_NO = "contract_rent_item_no";// 出租合同款项
    String CONTRACT_SALE_ITEM_NO = "contract_sale_item_no";// 出售合同款项

    String FOLLOW_WAY = "follow_way"; // 新增跟进方式

    String FOLLOW_TPL_MATERIAL = "follow_order_tpl_material"; //
    String ORDER_PROCESS_ADD_ITEM = "order_process_add_item"; //跟单流程

    String MARK_CATEGORY = "ub_markcategory"; // 房源标记(用于列表筛选)


    // 客户来源
    String CUSTOMER_SOURCE = "customer_source"; // 房源标记(用于列表筛选)


    //物业类型purpose_alltype
    String PURPOSE_ALLTYPE = "purpose_alltype"; //楼盘物业

    String PURPOSE_TYPE = "purpose_type";

    // 项目级别
    String PURPOSE_LEVEL = "project_level";
    // 新房楼盘标签
    String PURPOSE_NEWLABEL = "uc_newlabel";

    String PROPERTY_CATEGROY = "property_categroy";  // 新增客户房屋用途

    // 厂房建筑结构（factory_build_structure）
    String FACTORY_BUILD_STRUCTURE = "factory_build_structure";
    // 厂房新旧程度 (factory_situation)
    String FACTORY_SITUATION = "factory_situation";
    // 土地规划用途 (land_planning_purpose)
    String LAND_PLANNING_PURPOSE = "land_planning_purpose";
    // 土地所有权 (land_ownership)
    String LAND_OWNERSHIP = "land_ownership";

    // 公共房源类型(2支队公共 4门店公共 8公司公共)
    String PUB_HOUSE_LEVEL = "property_level";



    // 合同签单界面证件类型
    String ID_TYPE = "id_type";
    // 撤单原因
    String CANCEL_REASON = "cancel_reason";


    // 租房跟进内容分类字典编码
    String FOLLOWUP_REMARK_RENT_CATEGORY = "followup_remark_rent_category";

    // 二手房跟进内容分类字典编码
    String FOLLOWUP_REMARK_SALE_CATEGORY = "followup_remark_sale_category";


    // 房源采集的标记
    @Deprecated
    String GATHER_HOUSE_MARK = "collect_mark";

    // 举报原因(租房)
    String HOUSE_REPORT_REASONS_RENT = "complaint_rent";

    // 举报原因(二手房)
    String HOUSE_REPORT_REASONS_USED = "complaint_sale";

    // 发送通知自动切换公司标识
    int EVENT_SWITCH_COMPANY = 10010001;
    // 发送通知请求定时消息
    int EVENT_ALARM_LIST = 10010002;
    // 发送通知请求是否有必读公告
    int EVENT_MUST_ANNOUNCEMENT = 10010003;
    // 接收虚拟号通知类型
    int EVENT_VIRTUAL_NUMBER = 10010004;

    // 过户方式
    String TRANSFER_WAY = "transfer_way";

    /**
     * 新房跟进方式
     */
    String NEW_HOUSE_FOLLOW_UP_TYPE = "newhouse_followup_type";
    /**
     * 供水
     */
    String WATER_WAY = "water_way";
    /**
     * 供电
     */
    String POWER_WAY = "electricity_way";
    /**
     * 相册分类
     */
    String ALBUM_CLASS = "album_class";
    /**
     * 户型朝向
     */
    String HOUSE_TOWARD = "house_toward";
    /**
     * 新房费用项类型
     */
    String NEW_HOUSE_FEE_TYPE = "newhouse_cost_types";
    // 直播主题类型
    String LIVE_BROADCAST_SUBJECT_TYPE_CODE = "live_broadcast_subject_type_code";
    // 直播类型
    String LIVE_BROADCAST_TYPE_CODE = "live_broadcast_type_code";
    // 主题和直播的映射关系
    String LIVE_BROADCAST_SUBJECT_TO_TYPE_CODE = "live_broadcast_subject_to_type_code";


    // 客户流转途径
    String CUST_DATA_SOURCE = "cust_data_source";

    // 客户来源字典
    String CUST_DATA_OPPORTUNITYSOURCE = "cust_commercial_source";

    // 业主委托-反馈原因
    String OWNER_ENTRUST_FEEDBACK_PROBLEMS = "feedback_problems";
    // -反馈原因 通用
    String PUB_COMMON_FEEDBACK_PROBLEMS = "feedback_problems";

    // 业主委托-状态
    String OWNER_ENTRUST_STATUS = "commission_entry_status";
    // 业主委托-分享海报标题
    String OWNER_ENTRUST_SHATR_TITLE = "commission_share_title";

    // 退付款类型
    String REFUND_PAYOUT_TYPE = "payout_Type";
    // 退付款方式
    String REFUND_PAYOUT_WAY = "trans_channel";

    // 资金划拨账户收支类型
    String FUND_ACCOUNT_FLOW_TYPE = "fund_account_flow_type";

    // 资金划拨账户交易类型
    String FUND_ACCOUNT_TRAN_TYPE = "fund_account_tran_type";


    // 消息注销成功通知
    String UNREGISTER_MESSAGE_SUCCESS_NOTIFY = "UNREGISTER_MESSAGE_SUCCESS_NOTIFY";
    // 消息注销失败通知
    String UNREGISTER_MESSAGE_FAIL_NOTIFY = "UNREGISTER_MESSAGE_FAIL_NOTIFY";

    // finish
    String VIEW_CLOSE = "VIEW_CLOSE";

    /**
     * 海报分类
     */
    String POSTER_TYPE = "poster_classification";

    // 结算审核状态
    String SETTLEMENT_AUDIT_STATUS = "settlement_audit_status";
}
