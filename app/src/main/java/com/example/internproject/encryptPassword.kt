package com.example.internproject

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers.Default
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class encryptPassword {
    val data = "HellothisaninternshipApp"
    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(password:String) :String{
        val key = generateKey(password)
        val c = Cipher.getInstance("AES")
        c.init(Cipher.ENCRYPT_MODE,key)
        val encval = c.doFinal(data.toByteArray())
        val encvalue = Base64.getEncoder().encodeToString(encval)
        return encvalue
    }

    private fun generateKey(password: String): SecretKeySpec {
        val messagedigest = MessageDigest.getInstance("SHA-256")
        val bytes = password.toByteArray()
        messagedigest.update(bytes,0,bytes.size)
        val key = messagedigest.digest()
        val secretkeyspec = SecretKeySpec(key,"AES")
        return secretkeyspec

    }
}