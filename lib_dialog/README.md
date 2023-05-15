# dialog

#### 层级

- DialogConst   全局常量，用于配置一些默认属性
- DialogManager     负责对dialog进行统一管理 
- BaseDialogTitle   dialog顶部小组件，根据传入参数处理页面逻辑
- BaseDialogBottom  dialog底部小组件，根据传入参数处理页面逻辑
    - BaseBean  生成dialog的bean父类，空的
    - BaseDialogBuilder 负责处理title、content、bottom的组合，以及参数传递
        - AlertBean 生成AlertDialog的属性集
        - AlertDialogBuilder    根据AlertBean生成对应的Dialog样式
            - DialogUser    统一的调用入口，传递参数必须是BaseBean的子类


