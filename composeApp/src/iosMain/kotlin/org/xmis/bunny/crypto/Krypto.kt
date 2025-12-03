//package xmis.bunny.krypto
//
//actual object Krypto {
//    actual fun encrypt(bytes: ByteArray): ByteArray {
//        if (bytes.isEmpty()) return byteArrayOf()
//        return ByteArray(32)
////        return KotlinNativeKrypto.encrypt(bytes)
//    }
//
//    actual fun decrypt(bytes: ByteArray): ByteArray {
//        if (bytes.isEmpty()) return byteArrayOf()
//        return ByteArray(32)
////        return KotlinNativeKrypto.decrypt(bytes)
//    }
//}