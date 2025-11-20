//package org.xmis.bunny.crypto
//
//import kotlinx.cinterop.ExperimentalForeignApi
//import kotlinx.cinterop.addressOf
//import kotlinx.cinterop.convert
//import kotlinx.cinterop.memScoped
//import kotlinx.cinterop.reinterpret
//import kotlinx.cinterop.usePinned
//import platform.CoreFoundation.CFDataCreate
//import platform.CoreFoundation.CFDataGetBytePtr
//import platform.CoreFoundation.CFDataGetLength
//import platform.CoreFoundation.CFDataRef
//import platform.CoreFoundation.kCFAllocatorDefault
//import platform.Foundation.NSData
//import platform.Foundation.dataWithBytes
//import platform.posix.memcpy
//
//@OptIn(ExperimentalForeignApi::class)
//fun ByteArray.toNSData(): NSData {
//    return this.usePinned { pinned ->
//        NSData.dataWithBytes(
//            bytes = pinned.addressOf(0),
//            length = this.size.toULong()
//        )
//    }
//}
//
//@OptIn(ExperimentalForeignApi::class)
//fun NSData.toByteArray(): ByteArray {
//    val size = length.toInt()
//    if (size == 0) return ByteArray(0)
//
//    val result = ByteArray(size)
//    result.usePinned { pinned ->
//        memcpy(pinned.addressOf(0), bytes, size.convert())
//    }
//    return result
//}
//
//
//@OptIn(ExperimentalForeignApi::class)
//fun ByteArray.toCFData(): CFDataRef? = memScoped {
//    if (isEmpty()) {
//        CFDataCreate(kCFAllocatorDefault, null, 0)
//    } else {
//        this@toCFData.usePinned { pinned ->
//            CFDataCreate(
//                kCFAllocatorDefault,
//                pinned.addressOf(0).reinterpret(),
//                this@toCFData.size.convert()
//            )
//        }
//    }
//}
//
//@OptIn(ExperimentalForeignApi::class)
//fun CFDataRef.toByteArray(): ByteArray {
//    val len = CFDataGetLength(this).toInt()
//    if (len == 0) return ByteArray(0)
//
//    val result = ByteArray(len)
//    result.usePinned { pinned ->
//        val src = CFDataGetBytePtr(this)
//        memcpy(pinned.addressOf(0), src, len.convert())
//    }
//    return result
//}