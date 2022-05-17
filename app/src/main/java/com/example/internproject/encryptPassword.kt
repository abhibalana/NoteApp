package com.example.internproject

import android.os.Build
import android.util.Base64.DEFAULT
import android.util.Base64.decode

import androidx.annotation.RequiresApi
import java.lang.Byte.decode
import java.security.MessageDigest
import java.security.spec.PSSParameterSpec.DEFAULT
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class encryptPassword {
    val data = "HellothisaninternshipApp"
    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(value:String) :String{
        val key = generateKey(data)
        val c = Cipher.getInstance("AES")
        c.init(Cipher.ENCRYPT_MODE,key)
        val encval = c.doFinal(value.toByteArray())
        val encvalue = Base64.getEncoder().encodeToString(encval)
        return encvalue
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(value:String):String{
        val key = generateKey(data)
      val c = Cipher.getInstance("AES")
        c.init(Cipher.DECRYPT_MODE,key)
        val byteArray = decode(value,android.util.Base64.DEFAULT)
        return String(c.doFinal(byteArray))

    }

    private fun generateKey(password:String ): SecretKeySpec {
        val disgest = MessageDigest.getInstance("SHA-256")
        val bytes = password.toByteArray()
        disgest.update(bytes,0,bytes.size)
        val key = disgest.digest()
        val secretKey = SecretKeySpec(key,"AES")
        return secretKey



    }
}