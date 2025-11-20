//package org.xmis.bunny.crypto
//
//import org.xmis.bunny.crypto.IosKrypto
//
//import kotlinx.cinterop.ExperimentalForeignApi
//
//object SwiftInteropKrypto {
//
//    @OptIn(ExperimentalForeignApi::class)
//    fun encrypt(bytes: ByteArray): ByteArray {
//        if (bytes.isEmpty()) return byteArrayOf()
//        val data = bytes.toNSData()
//        return requireNotNull(IosKrypto.encrypt(data)?.toByteArray())
//    }
//
//
//    @OptIn(ExperimentalForeignApi::class)
//    fun decrypt(bytes: ByteArray): ByteArray {
//        if (bytes.isEmpty()) return byteArrayOf()
//        val data = bytes.toNSData()
//        return requireNotNull(IosKrypto.decrypt(data)?.toByteArray())
//    }
//}