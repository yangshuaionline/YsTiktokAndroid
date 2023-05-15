package com.yangshuai.module.main.entity

data class CompanyInfo(
    val code: String,
    val `data`: Data,
    val msg: String,
    val requestId: Any,
    val succeed: Boolean
)

data class Data(
    val condition: Any,
    val list: List<ListBean>,
    val order: Any,
    val pageNo: Int,
    val rowCntPerPage: Int,
    val totalPage: Int,
    val totalRowCount: Int
)

data class ListBean(
    val commercialId: String,
    val createdTm: Long,
    val name: String,
    val openId: String,
    val phone: String,
    val requireType: List<String>,
    val sex: String,
    val sourceName: String,
    val status: Int,
    val statusName: String,
    val tag: List<String>
)