package com.wrbug.datafinder.util

import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object MD5Utils {
    // 全局数组
    private val strDigits =
        arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")

    @JvmStatic
    fun encode(data: String) = encode(data.toByteArray())

    @JvmStatic
    fun encode(data: ByteArray): String {
        try {
            val md = MessageDigest.getInstance("MD5")
            return byteToString(md.digest(data))
        } catch (ex: NoSuchAlgorithmException) {
            ex.printStackTrace()
        }

        return ""
    }

    @JvmStatic
    fun encode(file: File): String {
        if (!file.isFile) {
            return ""
        }
        val buffer = ByteArray(1024)
        try {
            val digest = MessageDigest.getInstance("MD5")
            val fis = FileInputStream(file)
            fis.use {
                var len: Int = it.read(buffer, 0, 1024)
                while (len != -1) {
                    digest.update(buffer, 0, len)
                    len = it.read(buffer, 0, 1024)
                }
                return byteToString(digest!!.digest())
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun byteToString(bByte: ByteArray): String {
        val sBuffer = StringBuilder()
        for (aBByte in bByte) {
            sBuffer.append(byteToArrayString(aBByte))
        }
        return sBuffer.toString()
    }

    private fun byteToArrayString(bByte: Byte): String {
        var iRet = bByte.toInt()
        if (iRet < 0) {
            iRet += 256
        }
        val iD1 = iRet / 16
        val iD2 = iRet % 16
        return strDigits[iD1] + strDigits[iD2]
    }

}

fun String?.getMd5(): String = if (this.isNullOrEmpty()) "" else MD5Utils.encode(this)
fun File.getMd5(): String = MD5Utils.encode(this)
