#### 介绍
自由封装

#### 软件架构
模块化开发
Kotlin + MVVM + Rxjava + Retrofit2 + DataBinding + Jetpack + Glide
适配 TargetSdkVersion 30 存储强制分区

#### 使用说明

1. app : 宿主app 集成功能模块 依赖于需要用到得所有业务模块
2. lib_button:按钮功能组件
3. lib_dialog: 弹窗功能组件
4. library_base : 公共基础 各个子模块依赖
5. library_resource ：公共基础 资源文件
6. mod_cart：购物车模块
7. mod_im ：im业务模块
8. mod_login ：登录业务模块
9. mod_pay ：支付业务模块
10. mod_video : 视频业务模块
11. module_find : 发现模块
12. module_first : 首页模块
13. module_main ：开屏页、主页
14. module_message ：消息模块
15. module_mine ：我的模块
16. module_publish ： 发布模块

#### 层级关系

- library_resource 顶级资源存放
  - lib_button 按钮组件
    - library_base 顶级父类、公共类
        - lib_dialog 弹窗
            - mod_cart 购物车
            - mod_im 即时通讯
            - mod_login 登录
            - mod_pay 支付
            - mode_video 视频
                - module_find 发现
                - module_first 首页
                - module_message 消息
                - module_mine 我的
                - module_publish 发布
                    - module_main 主页
                      - app 壳



