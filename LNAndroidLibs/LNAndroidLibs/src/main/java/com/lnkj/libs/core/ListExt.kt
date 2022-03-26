package com.lnkj.libs.core

/**
 * 从列表中删除符合条件的
 * @receiver ArrayList<T>
 * @param filter Function1<[@kotlin.ParameterName] T, Boolean>
 */
 fun<T> ArrayList<T>.deleteIf(filter: (t: T) -> Boolean){
     val item = filter { filter(it) }
     removeAll(item)
}