//package org.xmis.bunny.crypto
//
//import kotlinx.cinterop.ExperimentalForeignApi
//import kotlinx.cinterop.memScoped
//import kotlinx.cinterop.usePinned
//import platform.CoreCrypto.CCCrypt
//import platform.CoreCrypto.kCCBlockSizeAES128
//import platform.CoreCrypto.kCCSuccess
//import platform.posix.size_tVar
//
//@OptIn(ExperimentalForeignApi::class)
//object KotlinNativeKrypto {
//    private val AES_BLOCK_SIZE = kCCBlockSizeAES128.toInt()
//
//    /**
//     * UByteArray(BLOCK_SIZE.toInt()) { ... } → create an array of unsigned bytes of length 16.
//     *	For each element: (0..255).random().toUByte() → generate random number 0–255, convert to UByte.
//     * 	toByteArray() → convert to normal ByteArray.
//     * 	Why IV?
//     * 	•	IV makes sure that encrypting the same data twice gives different ciphertext.
//     * 	•	Very important for security.
//     * 	•	IV is not secret, just random & unique.
//     *  val encryptedBytes = ByteArray(bytes.size + BLOCK_SIZE.toInt())
//     *  	•	You allocate an array to hold the encrypted data.
//     * 	•	Its size is:
//     * 	•	original size bytes.size
//     * 	•	one extra block (16 bytes) to handle PKCS7 padding.
//     * 	•	AES with padding can produce up to input length + block size.
//     * 	op = [kCCEncrypt]Operation type. , kCCEncrypt means encrypt mode.
//     *  alg = [kCCAlgorithmAES] the algorithm to encrypt with
//     *  options = [kCCOptionPKCS7Padding] Use PKCS#7 padding, a standard padding method so data length can be a multiple of block size.
//     *  key = [key.size]Pointer to the raw key bytes.
//     * 	•	key is NSData.
//     * 	•	key.bytes is like void* pointer to its bytes in memory.
//     *   iv = [iv.refTo(0)] -Pointer to IV data - iv is a ByteArray. - iv.refTo(0) gives a C pointer to the first element.
//     *   CommonCrypto uses IV to initialize the block cipher state.
//     * 	 dataIn = bytes.refTo(0), bytes.size.convert()
//     * 	 dataIn pointer → your plaintext data, first byte.
//     * 	 bytes.size.convert() → number of bytes of input data.
//     * 	dataOut = encryptedBytes.refTo(0), encryptedBytes.size.convert()
//     * 	dataOut pointer → buffer where ciphertext will be written.
//     * 	encryptedBytes.size.convert() → maximum number of bytes it can write.
//     * 	dataOutMoved = outLength.ptr
//     * 	•	Pointer to your size_tVar variable.
//     * 	•	After the call, outLength.value will contain how many bytes of ciphertext were actually written.
//     */
//    fun encrypt(bytes: ByteArray): ByteArray {
//        val key = IosKryptoDelegate.defaultKey
//        val iv = IosKryptoDelegate.randomBytes(AES_BLOCK_SIZE)
//        val outBuffer = ByteArray(bytes.size + AES_BLOCK_SIZE)
//        var outLength = 0
//        memScoped {
//            val outLengthVar = alloc<size_tVar>()
//            val status = key.usePinned { keyPinned ->
//                iv.usePinned { ivPinned ->
//                    bytes.usePinned { inPinned ->
//                        outBuffer.usePinned { outPinned ->
//                            CCCrypt(
//                                op = kCCEncrypt,
//                                alg = kCCAlgorithmAES,
//                                options = kCCOptionPKCS7Padding,
//                                key = keyPinned.addressOf(0),
//                                keyLength = key.size.convert(),
//                                iv = ivPinned.addressOf(0),
//                                dataIn = inPinned.addressOf(0),
//                                dataInLength = bytes.size.convert(),
//                                dataOut = outPinned.addressOf(0),
//                                dataOutAvailable = outBuffer.size.convert(),
//                                dataOutMoved = outLengthVar.ptr
//                            )
//                        }
//                    }
//                }
//            }
//
//            if (status != kCCSuccess) {
//                return bytes
//            }
//            outLength = outLengthVar.value.toInt()
//        }
//
//        val cipher = outBuffer.copyOf(outLength)
//        return iv + cipher
//    }
//
//    fun decrypt(bytes: ByteArray): ByteArray {
//        if (bytes.size <= AES_BLOCK_SIZE) return bytes
//        val key = IosKryptoDelegate.defaultKey
//        val iv = bytes.copyOfRange(0, AES_BLOCK_SIZE)
//        val cipher = bytes.copyOfRange(AES_BLOCK_SIZE, bytes.size)
//        val outBuffer = ByteArray(cipher.size + AES_BLOCK_SIZE)
//        var outLength: Int = 0
//
//        memScoped {
//            val outLengthVar = alloc<size_tVar>()
//            val status = key.usePinned { keyPinned ->
//                iv.usePinned { ivPinned ->
//                    cipher.usePinned { inPinned ->
//                        outBuffer.usePinned { outPinned ->
//                            CCCrypt(
//                                op = kCCDecrypt,
//                                alg = kCCAlgorithmAES,
//                                options = kCCOptionPKCS7Padding,
//                                key = keyPinned.addressOf(0),
//                                keyLength = key.size.convert(),
//                                iv = ivPinned.addressOf(0),
//                                dataIn = inPinned.addressOf(0),
//                                dataInLength = cipher.size.convert(),
//                                dataOut = outPinned.addressOf(0),
//                                dataOutAvailable = outBuffer.size.convert(),
//                                dataOutMoved = outLengthVar.ptr
//                            )
//                        }
//                    }
//                }
//            }
//            if (status != kCCSuccess) {
//                return bytes
//            }
//            outLength = outLengthVar.value.toInt()
//        }
//        return outBuffer.copyOf(outLength)
//    }
//}