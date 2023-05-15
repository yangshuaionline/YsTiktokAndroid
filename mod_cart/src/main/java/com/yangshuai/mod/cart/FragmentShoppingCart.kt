package com.yangshuai.mod.cart

import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.yangshuai.library.base.BaseFragment
import com.yangshuai.library.base.router.RouterPath
import com.yangshuai.mod.cart.bean.GoodsBean
import com.yangshuai.mod.cart.bean.NormalBean
import com.yangshuai.mod.cart.bean.ShopBean
import com.yangshuai.mod.cart.databinding.FragmentShoppingCartBinding
import com.ocnyang.cartlayout.bean.CartItemBean
import com.ocnyang.cartlayout.bean.ICartItem
import com.ocnyang.cartlayout.listener.CartOnCheckChangeListener
import io.github.wongxd.skulibray.specSelect.SpecSelectFragment
import io.github.wongxd.skulibray.specSelect.bean.SpecBean
import io.github.wongxd.skulibray.specSelect.bean.SpecBean.AttrsBean
import io.github.wongxd.skulibray.specSelect.sku.ProductModel

/**
 * 购物车
 */
@Route(path = RouterPath.CART.ROUTER_MAIN_SHOPPING_CART)
class FragmentShoppingCart : BaseFragment<FragmentShoppingCartBinding, ShoppingCartViewModel>(),View.OnClickListener{
    private var mAdapter: CartMainAdapter? = null
    //是否处于编辑状态
    private var isEditing = false
    //购物车商品ChildItem的总数量，店铺条目不计算在内
    private var totalCount = 0
    //勾选的商品总数量，店铺条目不计算在内
    private var totalCheckedCount = 0
    //勾选的商品总价格
    private var totalPrice = 0.0

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int = R.layout.fragment_shopping_cart

    override fun initVariableId(): Int = BR.viewModel

    override fun initData() {
        super.initData()
        mBinding?.tvEdit?.setOnClickListener(this)
        mBinding?.checkboxAll?.setOnClickListener(this)
        mBinding?.btnGoToPay?.setOnClickListener(this)
        mBinding?.tvTitle?.text = getString(R.string.cart, 0)
        mBinding?.btnGoToPay?.text = getString(R.string.go_settle_X, 0)
        mBinding?.tvTotalPrice?.text = getString(R.string.rmb_X, 0.00)
        mBinding?.recycler?.layoutManager = LinearLayoutManager(activity)
        mAdapter = CartMainAdapter(activity, data)
        mAdapter?.let {adapter->
            adapter.isCanCollapsing = true
            mBinding.recycler.let {
                adapter.setOnCheckChangeListener(object :
                    CartOnCheckChangeListener(it, adapter) {
                    override fun onCalculateChanged(cartItemBean: ICartItem?) {
                        calculate()
                    }
                })
            }
        }

        mBinding?.recycler?.adapter = mAdapter

        // 给列表注册 ContextMenu 事件。
        // 同时如果想让ItemView响应长按弹出菜单，需要在item xml布局中设置 android:longClickable="true"
        mBinding?.recycler?.let { registerForContextMenu(it) }
    }

    /**
     * 添加选项菜单
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //        getMenuInflater().inflate(R.menu.main_contextmenu, menu);
    }

    /**
     * 选项菜单点击事件
     *
     * @param item
     * @return
     */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        //获取到的是 listView 里的条目信息
//        RecyclerViewWithContextMenu.RecyclerViewContextInfo info = (RecyclerViewWithContextMenu.RecyclerViewContextInfo) item.getMenuInfo();
//        Log.d("ContentMenu", "onCreateContextMenu position = " + (info != null ? info.getPosition() : "-1"));
//        if (info != null && info.getPosition() != -1) {
//            switch (item.getItemId()) {
//                case R.id.action_remove:
//                    mAdapter.removeChild(info.getPosition());
//                    Toast.makeText(getActivity(), "成功移入收藏", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.action_findmore:
//                    Toast.makeText(getActivity(), "查找与" + ((GoodsBean) mAdapter.getData().get(info.getPosition())).getGoods_name() + "相似的产品", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.action_delete:
//                    mAdapter.removeChild(info.getPosition());
//                    break;
//                default:
//                    break;
//            }
//        }
        return super.onContextItemSelected(item)
    }

    /**
     * 统计操作<br></br>
     * 1.先清空全局计数器<br></br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br></br>
     * 3.给相关的 textView 进行数据填充
     */
    private fun calculate() {
        totalCheckedCount = 0
        totalCount = 0
        totalPrice = 0.00
        var notChildTotalCount = 0
        if (mAdapter!!.data != null) {
            for (iCartItem in mAdapter!!.data) {
                if (iCartItem.itemType == ICartItem.TYPE_CHILD) {
                    totalCount++
                    if (iCartItem.isChecked) {
                        totalCheckedCount++
                        totalPrice += (iCartItem as GoodsBean).goods_price * iCartItem.goods_amount
                    }
                } else {
                    notChildTotalCount++
                }
            }
        }
        mBinding!!.tvTitle.text = getString(R.string.cart, totalCount)
        mBinding!!.btnGoToPay.text =
            getString(if (isEditing) R.string.delete_X else R.string.go_settle_X, totalCheckedCount)
        mBinding!!.tvTotalPrice.text = getString(R.string.rmb_X, totalPrice)
        if (mBinding!!.checkboxAll.isChecked && (totalCheckedCount == 0 || totalCheckedCount + notChildTotalCount != mAdapter!!.data.size)) {
            mBinding!!.checkboxAll.isChecked = false
        }
        if (totalCheckedCount != 0 && !mBinding!!.checkboxAll.isChecked && totalCheckedCount + notChildTotalCount == mAdapter!!.data.size) {
            mBinding!!.checkboxAll.isChecked = true
        }
    }

    override fun onClick(v: View) {
        val id = v.id //编辑按钮事件
        if (id == R.id.tv_edit) {
            isEditing = !isEditing
            mBinding!!.tvTitle.text =
                getString(if (isEditing) R.string.edit_done else R.string.edit)
            mBinding!!.btnGoToPay.text =
                getString(
                    if (isEditing) R.string.delete_X else R.string.go_settle_X,
                    totalCheckedCount
                )
            //提交订单 & 删除选中（编辑状态）
        } else if (id == R.id.btn_go_to_pay) {
            submitEvent()
            val list = mutableListOf<ProductModel.AttributesEntity.AttributeMembersEntity>()
//            for(i = )
            //          {"data" : {
//       "attrs":[
//                {"value":[{"id":3,"name":"红色"}], "key" : "颜色"}
//               ],
//
//      "combs":[
//                {"id":10,"productId":5,"stock":2,"comb":"4,6,23,20","desc":"蓝色-20KG-绵阳-30cm","price":1.0,specImg:""}
//              ]
//
//      }}
            val bean = SpecBean()
             //          {"data" : {
            //       "attrs":[
            //                {"value":[{"id":3,"name":"红色"}], "key" : "颜色"}
            //               ],
            //
            //      "combs":[
            //                {"id":10,"productId":5,"stock":2,"comb":"4,6,23,20","desc":"蓝色-20KG-绵阳-30cm","price":1.0,specImg:""}
            //              ]
            //
            //      }}
            val attrs  = mutableListOf<AttrsBean>()
            val attrsBean = AttrsBean()
            for(i in 0..3){
                attrsBean.key = "颜色${i+1}"
                val valueList = mutableListOf <AttrsBean.ValueBean>()
                for(a in 0..3){
                    val value = AttrsBean.ValueBean()
                    value.id = a
                    value.name = "属性${a}"
                    valueList.add(value)
                }
                attrsBean.value = valueList
            }
            attrs.add(attrsBean)
            bean.attrs = attrs
            bean.combs = mutableListOf()
            activity?.let {act->
                //显示商品属性  https://github.com/Wongxd/skuLib
                SpecSelectFragment.showDialog(act as AppCompatActivity?, null, mutableListOf(), bean)
                    .setShowGoodImgListener { iv, imgUrl ->  }
                    .setSubmitSpecCombListener { combBean, num, statusRestoreList ->  }
            }

        } else if (id == R.id.checkbox_all) {
            mAdapter!!.checkedAll((v as CheckBox).isChecked)
        }
    }

    private fun submitEvent() {
        if (isEditing) {
            if (totalCheckedCount == 0) {
                Toast.makeText(activity, "请勾选你要删除的商品", Toast.LENGTH_SHORT).show()
            } else {
                mAdapter!!.removeChecked()
            }
        } else {
            if (totalCheckedCount == 0) {
                Toast.makeText(activity, "你还没有选择任何商品", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    activity,
                    StringBuilder().append("你选择了").append(totalCheckedCount).append("件商品")
                        .append("共计 ").append(totalPrice).append("元"),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * 数据初始化尤其重要
     * 1. childItem 数据全部在 GroupItem 数据的下方，数据顺序严格按照对应关系；
     * 2. GroupItem 下的 ChildItem 数据不能为空；
     * 3. 初始化时如果不需要，所有类型的条目都可以不设置ID，GroupItem也不用设置setChilds()；
     *
     *
     * 列表操作时数据动态的变化设置：
     * 1. 通过 CartAdapter 的 addData、setNewData；
     * 2. 单个添加各个条目可以通过对应的 add 方法；
     * 3. 单独添加一个 GroupItem ,可以把它的 ChildItem 数据放到 setChilds 中。
     *
     * @return
     */
    private val data: List<CartItemBean>
        private get() {
            val cartItemBeans = ArrayList<CartItemBean>()
            val normalBean = NormalBean()
            normalBean.markdownNumber = 6
            cartItemBeans.add(normalBean)
            for (i in 0..9) {
                val shopBean = ShopBean()
                shopBean.shop_name = "解忧杂货铺 第" + (i + 1) + "分店"
                shopBean.itemType = CartItemBean.TYPE_GROUP
                cartItemBeans.add(shopBean)
                for (j in 0 until i + 5) {
                    val goodsBean = GoodsBean()
                    goodsBean.goods_name = "忘忧水 " + (j + 1) + " 代"
                    goodsBean.itemType = CartItemBean.TYPE_CHILD
                    goodsBean.itemId = ((j + 1) * 10 + j).toLong()
                    goodsBean.goods_price = (j + 1).toDouble()
                    goodsBean.groupId = i
                    cartItemBeans.add(goodsBean)
                }
            }
            return cartItemBeans
        }
}