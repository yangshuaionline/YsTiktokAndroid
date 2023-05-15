package com.yangshuai.module.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED
import com.yangshuai.library.base.account.AccountManager
import com.yangshuai.library.base.router.RouterManager
import com.yangshuai.library.base.router.RouterPath
import com.yangshuai.library.base.utils.StatusBarUtils
import com.yangshuai.module.main.R
import com.yangshuai.module.main.activity.adapter.FragmentAdapterKt
import com.yangshuai.module.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_act.*

@Route(path = RouterPath.Main.ROUTE_MAIN_KT_PATH)
class MainActivitykt : AppCompatActivity() {
    lateinit var mViewModel: MainViewModel
    var list: List<Fragment>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
        initParam()
        //避免过度绘制
        window.setBackgroundDrawable(null)
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        StatusBarUtils.setStatusBarTransparent(this)
        StatusBarUtils.setStatusBarTextColorDark(this)
        initFragment()
        initViewPager()
        initBottomNavigation()

    }

    /**
     * 初始化底部菜单栏
     */
    private fun initBottomNavigation() {
        with(nav_view) {
            enableAnimation(false)
            labelVisibilityMode = LABEL_VISIBILITY_LABELED
            itemIconTintList = null
            setIconSize(21f, 21f)
            currentItem = 0
            setupWithViewPager(fragment_viewpager)
            setOnNavigationItemSelectedListener() { it ->
                val itemId = it.itemId

                resetToDefaultIcon()
                when (itemId) {
                    R.id.nav_message -> it.setIcon(R.mipmap.icon_nav_msg_selected)
                    R.id.nav_work -> it.setIcon(R.mipmap.icon_nav_work_selected)
                    R.id.nav_contacts -> it.setIcon(R.mipmap.icon_nav_contacts_selected)
                    R.id.nav_mine -> it.setIcon(R.mipmap.icon_nav_mine_selected)
                }
                true

            }
        }
    }

    private fun resetToDefaultIcon() {
        with(nav_view) {
            menu.findItem(R.id.nav_message).setIcon(R.mipmap.icon_nav_msg_unselected)
            menu.findItem(R.id.nav_work).setIcon(R.mipmap.icon_nav_work_unselected)
            menu.findItem(R.id.nav_contacts).setIcon(R.mipmap.icon_nav_contacts_unselected)
            menu.findItem(R.id.nav_mine).setIcon(R.mipmap.icon_nav_mine_unselected)
        }
    }

    /**
     * 初始化viewpage
     */
    private fun initViewPager() {
        with(fragment_viewpager) {
            offscreenPageLimit = 0
            adapter = FragmentAdapterKt(supportFragmentManager, list!!)
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {

                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })
        }
    }

    /**
     * 添加fragment  根据不用的角色类型 加载不同的 fragmen 组
     * usedType 1 投资人   2  资源商   3 服务商  4 企业
     */
    private fun initFragment() {
        var usedType = AccountManager.getUsedType()
        var Newsfragmen:Fragment? = null
        var Valuefragmen:Fragment? = null
        var Marketfragmen:Fragment? = null
        var Minefragmen:Fragment? = null
        usedType?.let {
            when(usedType){
                "1"->{
                    //新闻
                    Newsfragmen = ARouter.getInstance().build(RouterPath.Contact.ROUTE_CONTACT_PATH)
                            .navigation() as Fragment
                    //价值
                    Valuefragmen =
                            ARouter.getInstance().build(RouterPath.Work.ROUTE_WORK_PATH).navigation() as Fragment
                    //市场
                    Marketfragmen =
                            ARouter.getInstance().build(RouterPath.CART.ROUTER_MAIN_SHOPPING_CART).navigation() as Fragment
//                        ARouter.getInstance().build(RouterPath.HOME.ROUTE_MAIN_PATH).navigation() as Fragment

                    //我的
                    Minefragmen =
                            ARouter.getInstance().build(RouterPath.Mine.ROUTE_MINE_PATH).navigation() as Fragment
                }
                "2"->{

                }
                "3"->{

                }
                "4"->{

                }
                else->{

                }
            }
            list = listOf(Newsfragmen!!, Valuefragmen!!, Marketfragmen!!, Minefragmen!!)
        }
    }
    private fun initParam() {
        ARouter.getInstance().inject(this)
        RouterManager.addPage(RouterPath.IM.IM_GROUP,this)
    }
    override fun onDestroy() {
        RouterManager.remove(this)
        super.onDestroy()
    }
}