package xmis.bunny.krypto


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

@OptIn(ExperimentalEncodingApi::class)
fun encrypt(text: String): String {
    if (text.isBlank()) return ""
    val plainBytes = text.encodeToByteArray()
    val cipherBytes = Krypto.encrypt(plainBytes)
    return Base64.encode(cipherBytes)
}

@OptIn(ExperimentalEncodingApi::class)
fun decrypt(encryptedText: String): String {
    if (encryptedText.isBlank()) return ""
    val cipherBytes = Base64.decode(encryptedText)
    val plainBytes = Krypto.decrypt(cipherBytes)
    return plainBytes.decodeToString()
}