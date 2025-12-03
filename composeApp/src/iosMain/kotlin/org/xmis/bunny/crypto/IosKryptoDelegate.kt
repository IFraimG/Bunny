package org.xmis.bunny.crypto

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.alloc
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.value
import platform.CoreFoundation.CFDataRef
import platform.CoreFoundation.CFDictionaryAddValue
import platform.CoreFoundation.CFDictionaryCreateMutable
import platform.CoreFoundation.CFTypeRefVar
import platform.CoreFoundation.kCFAllocatorDefault
import platform.CoreFoundation.kCFBooleanTrue
import platform.Foundation.NSData
import platform.Security.SecItemAdd
import platform.Security.SecItemCopyMatching
import platform.Security.SecRandomCopyBytes
import platform.Security.errSecDuplicateItem
import platform.Security.errSecSuccess
import platform.Security.kSecAttrAccessible
import platform.Security.kSecAttrAccessibleWhenUnlocked
import platform.Security.kSecAttrApplicationTag
import platform.Security.kSecClass
import platform.Security.kSecClassKey
import platform.Security.kSecMatchLimit
import platform.Security.kSecMatchLimitOne
import platform.Security.kSecRandomDefault
import platform.Security.kSecReturnData
import platform.Security.kSecValueData
import xmis.bunny.krypto.AES_KEY_ALIAS

/**
 * this object does the logic with the native ios keychain
 * Generate 32-byte key (AES -256)
 * If we already have the key we return it
 * else we create a new random one and save it then return it
 *Stores data safely using iOS Security framework (SecItemAdd, SecItemCopyMatching)
 */
@OptIn(ExperimentalForeignApi::class)

object IosKryptoDelegate {
    private const val AES_KEY_SIZE_BYTES = 32

    val defaultKey by lazy { getOrCreateKey(); }

    /**
     * 	If key already exists in Keychain → return it.
     * 	If not → generate and save a new one.
     * So this gives you a stable AES key on this device.
     */
    private fun getOrCreateKey(): ByteArray {
        loadKey()?.let { return it }
        val newKey = generateRandomKey()
        saveKey(newKey)
        return newKey
    }

    /**
     *[kSecClass] type of item we are saving [kSecClassKey] means CRYPTO KEY
     *[kSecAttrApplicationTag] the tag of the data [AES_KEY_ALIAS] or Key Alias
     *[kSecValueData] the actual key that maps to the key we will use to save
     *[kSecAttrAccessible] the key that informs ios when to access the key
     *[kSecAttrAccessibleWhenUnlocked]Key is only available when the device is unlocked.
     */
    private fun saveKey(key: ByteArray) = memScoped {
        val keyData = key.toCFData() ?: return
        val tagData = AES_KEY_ALIAS.encodeToByteArray().toCFData() ?: return
        val dict = CFDictionaryCreateMutable(
            kCFAllocatorDefault,
            0,
            null,
            null
        ) ?: return
        CFDictionaryAddValue(dict, kSecClass, kSecClassKey)
        CFDictionaryAddValue(dict, kSecAttrApplicationTag, tagData)
        CFDictionaryAddValue(dict, kSecAttrAccessible, kSecAttrAccessibleWhenUnlocked)
        CFDictionaryAddValue(dict, kSecValueData, keyData)
        val status = SecItemAdd(dict, null)
        check(status == errSecSuccess || status == errSecDuplicateItem) {
            "SecItemAdd failed with status: $status"
        }
    }

    /**
     *[kSecClass] type of item we are saving [kSecClassKey] means CRYPTO KEY
     *[kSecAttrApplicationTag] the tag of the data [com.mohaberabi.krypto.krypto.AES_KEY_ALIAS] or Key Alias
     *[kSecValueData] the actual key that maps to the key we will use to save
     *[platform.Security.kSecReturnData] we are telling the Query to return the key
     *[platform.Security.kSecMatchLimitOne] Limit the result to exaclty one key only
     *Keychain reads output into a pointer.
     *Kotlin/Native requires a scoped memory region for this.
     * memScoped allocates temporary C memory for the duration of the block Automatically freed afterward.
     */
    private fun loadKey(): ByteArray? = memScoped {
        val tagData = AES_KEY_ALIAS.encodeToByteArray().toCFData()
        val query = CFDictionaryCreateMutable(
            allocator = kCFAllocatorDefault,
            capacity = 0,
            keyCallBacks = null,
            valueCallBacks = null
        ) ?: return@memScoped null
        CFDictionaryAddValue(query, kSecClass, kSecClassKey)
        CFDictionaryAddValue(query, kSecAttrApplicationTag, tagData)
        CFDictionaryAddValue(query, kSecReturnData, kCFBooleanTrue)
        CFDictionaryAddValue(query, kSecMatchLimit, kSecMatchLimitOne)
        val resultVar = alloc<CFTypeRefVar>()
        val status = SecItemCopyMatching(query, resultVar.ptr)
        if (status != errSecSuccess) return@memScoped null
        val cfData = resultVar.value
        if (cfData == null) return@memScoped null
        (cfData as CFDataRef).toByteArray()
    }

    /**
     * 	Creates a 32-byte random array (AES-256).
     * 	Each byte is random from 0–255.
     * 	[usePinned]  --> read below to understand it
     * 	kotlin native normally moves the arrays in memory but IOS needs
     * 	a stable Pointer ( memory must not move)
     *  so the [usePinned] Pins the byteArray :
     *  it makes sure that memory address stays fixed during block is executed
     *  so after we are done we pass the pointer to IOS
     *  [addressOf] gives pointer to the index of the byte
     *  [NSData.create] creates an ios NSData object from the bytes
     *  [NSData] is exactly same as [ByteArray] in JVM
     *  at the end this is the key we will be using to store the data in IOS Chain
     *
     */

    fun generateRandomKey(): ByteArray = randomBytes(AES_KEY_SIZE_BYTES)

    fun randomBytes(length: Int): ByteArray {
        val array = ByteArray(length)
        array.usePinned { pinned ->
            val status = SecRandomCopyBytes(
                kSecRandomDefault,
                length.convert(),
                pinned.addressOf(0)
            )
            check(status == errSecSuccess) { "SecRandomCopyBytes failed: $status" }
        }
        return array
    }
}