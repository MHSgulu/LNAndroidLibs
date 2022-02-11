package com.lnkj.gallerysaver

class SaveResultModel(
    var isSuccess: Boolean,
    var filePath: String? = null,
    var errorMessage: String? = null
){
    fun toHashMap(): HashMap<String, Any?> {
        val hashMap = HashMap<String, Any?>()
        hashMap["isSuccess"] = isSuccess
        hashMap["filePath"] = filePath
        hashMap["errorMessage"] = errorMessage
        return hashMap
    }
}