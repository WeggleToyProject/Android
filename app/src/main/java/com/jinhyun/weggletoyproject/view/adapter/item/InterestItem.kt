package com.jinhyun.weggletoyproject.view.adapter.item

data class InterestItem(
    val itemCode: Int,
    val drawableId: Int,
    val name: String?,
    var selected: Boolean = false
)