package xmis.bunny.krypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

actual object Krypto {
    private const val PROVIDER = "AndroidKeyStore"
    private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
    private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
    private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
    private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"

    private val keyGenerator by lazy { KeyGenerator.getInstance(ALGORITHM, PROVIDER); }
    private val cipher by lazy { Cipher.getInstance(TRANSFORMATION); }
    private val keyStore by lazy { KeyStore.getInstance(PROVIDER).apply { load(null); }; }

    /**
     * 	1.	cipher.init(Cipher.ENCRYPT_MODE, getKey())
     * 	•	Puts cipher in ENCRYPT_MODE using the secret key from getKey().
     * 	•	For CBC mode, when you init in ENCRYPT_MODE, it will randomly generate an IV
     *   	(because we set setRandomizedEncryptionRequired(true) later).
     * 	2.	val iv = cipher.iv
     * 	•	After initialization, cipher.iv contains the random IV it just generated.
     * 	•	IV is required for decrypting later.
     * 	3.	val encrypted = cipher.doFinal(bytes)
     * 	•	This encrypts the input bytes.
     * 	•	doFinal = process all data and finish encryption.
     * 	4.	return iv + encrypted
     * 	•	We prepend the IV to the ciphertext.
     * 	•	Why? Because to decrypt, we must know the IV that was used.
     * 	•	IV is not secret, only random. So we store it together with the ciphertext.
     * 	    So encrypt() does:
     *      Generate random IV → encrypt → return [IV][CIPHERTEXT] in one ByteArray.
     */
    actual fun encrypt(bytes: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(bytes)
        return iv + encrypted
    }

    /**
     * We expect the input bytes to be: [IV][CIPHERTEXT].
     * 	1.	val iv = bytes.copyOfRange(0, cipher.blockSize)
     * 	•	AES block size = 16 bytes.
     * 	•	Takes the first 16 bytes as the IV.
     * 	2.	val data = bytes.copyOfRange(cipher.blockSize, bytes.size)
     * 	•	Takes the rest of the bytes as the actual encrypted data.
     * 	3.	cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
     * 	•	Now init cipher in DECRYPT_MODE.
     * 	•	Use the same key (getKey()) and the extracted IV.
     * 	•	IvParameterSpec(iv) tells the cipher which IV was originally used.
     * 	4.	return cipher.doFinal(data)
     * 	•	Decrypts the data and returns the original plaintext.
     *      So decrypt() does:
     *      Split IV + ciphertext → initialize cipher with IV → decrypt → return plaintext.
     */
    actual fun decrypt(bytes: ByteArray): ByteArray {
        val iv = bytes.copyOfRange(0, cipher.blockSize)
        val data = bytes.copyOfRange(cipher.blockSize, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        return cipher.doFinal(data)
    }

    /**
     * 	•keyStore.getEntry(AES_KEY_ALIAS, null)
     * → Check if a key with alias AES_KEY_ALIAS already exists in keystore.
     * → Returns a KeyStore.Entry, which might be a SecretKeyEntry.
     * 	•	as? KeyStore.SecretKeyEntry
     * → Safely cast it to a SecretKeyEntry if it’s that type.
     * 	•	existing?.secretKey ?: createKey()
     * 	•	If we found an existing key → return it.
     * 	•	If not → create a new one using createKey().
     *
     * This ensures:
     * 	•	First call: generates the key and stores it in Keystore.
     * 	•	Next calls: just re-use the same key.
     */
    private fun getKey(): SecretKey {
        val existing = keyStore.getEntry(AES_KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        return existing?.secretKey ?: createKey()
    }


    /**
     * 	•AES_KEY_ALIAS
     * → The name (alias) for this key inside Keystore.
     * → You’ll use this alias to retrieve it later.
     * 	•PURPOSE_ENCRYPT or PURPOSE_DECRYPT
     * → This key is allowed to encrypt and decrypt.
     */
    private fun createKey(): SecretKey {
        val builder = KeyGenParameterSpec.Builder(
            AES_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or
                    KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(BLOCK_MODE)
            .setEncryptionPaddings(PADDING)
            .setRandomizedEncryptionRequired(true)
            .setUserAuthenticationRequired(false)
            .setKeySize(256)
        return keyGenerator.apply { init(builder.build()); }.generateKey()
    }
}