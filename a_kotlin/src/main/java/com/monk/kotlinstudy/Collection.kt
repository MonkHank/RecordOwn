package com.monk.kotlinstudy

import java.util.Collections
import java.util.TreeMap

/**
 * author: Jit

 * date: 2024/9/3 14:23 周二

 * desc: file desc
 */



class Collection {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {


            val linkedHashMap = TreeMap<String,String>()
            linkedHashMap["1A"] = "A"
            linkedHashMap["C"] = "C"
            linkedHashMap["3B"] = "B"
            linkedHashMap["D"] = "D"
            println(linkedHashMap)

            val treeMap = TreeMap<String,String>()
            treeMap["filename"] = "UKSS_OTHERPACE_SENDPHOTOMSG_REQ_SENDER_yyyyMMddHHmmssSSS"
            treeMap["encryptType"] = "AES"
            treeMap["timestamp"] = "202409031026554"
            treeMap["version"] = "1.0"
            treeMap["signType"] = "RSA256"
            println(treeMap)

            val hashMap = hashMapOf<String,String>()
            hashMap["filename"] = "UKSS_OTHERPACE_SENDPHOTOMSG_REQ_SENDER_yyyyMMddHHmmssSSS"
            hashMap["encryptType"] = "AES"
            hashMap["timestamp"] = "202409031026554"
            hashMap["version"] = "1.0"
            hashMap["signType"] = "RSA256"
            println(hashMap)


            val newMap: MutableMap<String, String> = HashMap()
            for (key in hashMap.keys) {
                val value: String? = hashMap[key]
                if (value == null || value == "" || key.equals("sign", ignoreCase = true)) {
                    continue
                }
                newMap[key] = value
            }


            //2.排序
            val keys: List<String> = ArrayList(newMap.keys)

            // 排序前
            keys.forEach {
                println(it)
            }
            println(newMap)

            // 排序后
            Collections.sort(keys)
            keys.forEach {
                println(it)
            }

            println(newMap)// map 没有影响，上述仅仅对键进行排序
        }
    }
}