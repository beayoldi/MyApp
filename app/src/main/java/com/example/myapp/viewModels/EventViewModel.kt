package com.example.myapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class EventViewModel(application: Application):AndroidViewModel(application) {

    fun encryptData(data: String): Pair<ByteArray, ByteArray> {
        val cipher: Cipher = Cipher.getInstance("AES/CBC/NoPadding")

        var temp: String = data
        while (temp.toByteArray().size % 16 != 0)
            temp += "\u0020"

        cipher.init(Cipher.ENCRYPT_MODE, getKey())

        val ivBytes = cipher.iv

        val encryptedBytes = cipher.doFinal(temp.toByteArray())

        return Pair(ivBytes, encryptedBytes)
    }

    fun checkKey(): Boolean {
        val keystore: KeyStore = KeyStore.getInstance("AndroidKeyStore")
        keystore.load(null)
        val secretKeyEntry = keystore.getEntry("MyKeyStore", null) as? KeyStore.SecretKeyEntry
        return secretKeyEntry?.secretKey != null
    }
    fun getKey(): SecretKey {
        val keystore: KeyStore = KeyStore.getInstance("AndroidKeyStore")
        keystore.load(null)
        val secretKeyEntry = keystore.getEntry("MyKeyStore", null) as KeyStore.SecretKeyEntry
        return secretKeyEntry.secretKey
    }

    fun decryptData(ivBytes: ByteArray, data: ByteArray): String{
        val cipher = Cipher.getInstance("AES/CBC/NoPadding")
        val spec = IvParameterSpec(ivBytes)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), spec)
        return cipher.doFinal(data).toString(Charsets.UTF_8).trim()
    }
}