package com.yangshuai.library.base.config;

/**
 * 组件生命周期反射类名管理
 * 在这里注册需要初始化的组件
 * 通过反射动态调用各个组件的初始化方法
 * PS: 以下模块中初始化的Module类不能被混淆，必须填入对应完整包名 + 类名
 *
 * @author hcp
 * @created 2019/3/13
 */
public class ModuleLifecycleReflexs {
    // 目前原型图的有：首页、消息、工作、通讯录、我的

    // 基础模块
    private static final String BaseInit = "com.ujuz.library.base.BaseModuleInit";

    // 主业务模块
    private static final String MainInit = "com.ujuz.module.main.MainModuleInit";
    // 首页模块
    private static final String HomeInit = "com.ujuz.module.home.HomeModuleInit";
    // 消息模块
    private static final String MsgInit = "com.ujuz.module.message.MessageModuleInit";
    // 工作模块
    private static final String WorkInit = "com.ujuz.module.work.WorkModuleInit";
    // 通讯录模块
    private static final String ContactsInit = "com.ujuz.module.contacts.ContactsModuleInit";
    // 我的模块
    private static final String MineInit = "com.ujuz.module.mine.MineModuleInit";

    // 合同模块
    private static final String ContractInit = "com.ujuz.module.contract.ContractModulelnit";
    // 二手房模块
    private static final String UsedHouseInit = "com.ujuz.module.used.house.UsedHouseModuleInit";
    // 租房模块
    private static final String RentHouseInit = "com.ujuz.module.rent.house.RentHouseModuleInit";
    // 公共房源模块
    private static final String PubHouseInit = "com.yjyz.module.pub.house.PubHouseModuleInit";

    // 新增房源模块
    private static final String CreateHouseInit = "com.ujuz.module.create.house.CreateHouseModuleInit";

    // 消息模块(IM)
    private static final String MessageInit = "com.ujuz.module.message.MessageModuleInit";

    // 客源模块
    private static final String CustomerInit = "com.ujuz.module.customer.CustomerModuleInit";

    // 小区模块
    private static final String PropertiesSaleInit = "com.ujuz.module.properties.sale.PropertiesSaleModuleInit";

    // 登录注册模块
    private static final String SigninInit = "com.ujuz.module.signin.SigninModuleInit";

    // 地图模块
    private static final String HouseMapInit = "com.ujuz.module.house.map.HouseMapModuleInit";

    // 数据分析模块
    private static final String DATA_ANALYSIS_INIT = "com.yjyz.module_data_analysis.DataAnalysisModuleInit";

    // 房源标记模块
    private static final String HouseMarkInit = "com.ujuz.module_house.mark.HouseMarkModuleInit";

    // 我的房源收藏
    private static final String MyCollectHouseInit = "com.yjyz.module.my.collect.house.MyCollectHouseModuleInit";

    // 门店房源
    private static final String MyStoreHouseInit = "com.yjyz.module.store.house.MyStoreHouseModuleInit";

    // 电子合同
    private static final String ECONTRACT_INIT = "com.ujuz.module.econtract.EContractModulelnit";

    // 放入这里来确定执行顺序
    public static String[] initModuleNames = {
            BaseInit,
            MainInit,
            MessageInit,
            WorkInit,
            ContactsInit,
            MineInit,
            SigninInit,
            CreateHouseInit,
            ContractInit,
            UsedHouseInit,
            RentHouseInit,
            PubHouseInit,
            CustomerInit,
            PropertiesSaleInit,
            HouseMapInit,
            SigninInit,
            DATA_ANALYSIS_INIT,
            HouseMarkInit,
            MyCollectHouseInit,
            MyStoreHouseInit,
            ECONTRACT_INIT
    };

}
