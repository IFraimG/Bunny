package org.xmis.bunny.data.storages.crypto

import xmis.bunny.krypto.Krypto
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun Krypto.encrypt(text: String): String {
    if (text.isBlank()) return ""
    val plainBytes = text.encodeToByteArray()
    val cipherBytes = Krypto.encrypt(plainBytes)
    return Base64.encode(cipherBytes)
}

@OptIn(ExperimentalEncodingApi::class)
fun Krypto.decrypt(encryptedText: String): String {
    if (encryptedText.isBlank()) return ""
    val cipherBytes = Base64.decode(encryptedText)
    val plainBytes = Krypto.decrypt(cipherBytes)
    return plainBytes.decodeToString()
}