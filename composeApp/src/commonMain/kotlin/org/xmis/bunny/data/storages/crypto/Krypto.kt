package xmis.bunny.krypto

const val AES_KEY_ALIAS = "xmis.bunny.krypto"

expect object Krypto {

    fun encrypt(bytes: ByteArray): ByteArray
    fun decrypt(bytes: ByteArray): ByteArray
}