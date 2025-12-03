// package org.xmis.bunny.crypto.IosKrypto
//
// import Foundation
// import Security
// import CommonCrypto
//
// @objcMembers
// public class IosKrypto: NSObject {
//
//     private static let keyAlias = "xmis.bunny.krypto"
//     private static let keyLength = 32 // 32 bytes => AES-256
//
//     // MARK: - Public API
//
//     private static func getOrCreateKey() -> Data? {
//         if let existing = loadKey() {
//             return existing
//         }
//         guard let key = generateRandomKey() else { return nil }
//         saveKey(key)
//         return key
//     }
//
//     /// Encrypts data using AES/CBC/PKCS7.
//     /// Returns [IV (16 bytes)] + [ciphertext]
//     public static func encrypt(_ plain: Data) -> Data? {
//         guard let key = getOrCreateKey() else { return nil }
//
//         // 1) Random IV
//         var iv = Data(count: kCCBlockSizeAES128)
//         let ivStatus = iv.withUnsafeMutableBytes { ivBytes in
//             guard let base = ivBytes.baseAddress else { return errSecParam }
//             return SecRandomCopyBytes(
//                 kSecRandomDefault,
//                 kCCBlockSizeAES128,
//                 base
//             )
//         }
//         if ivStatus != errSecSuccess { return nil }
//
//         // 2) Encrypt
//         let cryptLength = plain.count + kCCBlockSizeAES128
//         var cryptData = Data(count: cryptLength)
//         var numBytesEncrypted: size_t = 0
//
//         let status = cryptData.withUnsafeMutableBytes { cryptBytes in
//             iv.withUnsafeBytes { ivBytes in
//                 plain.withUnsafeBytes { plainBytes in
//                     key.withUnsafeBytes { keyBytes in
//                         CCCrypt(
//                             CCOperation(kCCEncrypt),
//                             CCAlgorithm(kCCAlgorithmAES),
//                             CCOptions(kCCOptionPKCS7Padding),
//                             keyBytes.baseAddress, key.count,
//                             ivBytes.baseAddress,
//                             plainBytes.baseAddress, plain.count,
//                             cryptBytes.baseAddress, cryptLength,
//                             &numBytesEncrypted
//                         )
//                     }
//                 }
//             }
//         }
//
//         guard status == kCCSuccess else { return nil }
//
//         // Trim output to actual size
//         cryptData.removeSubrange(numBytesEncrypted..<cryptData.count)
//
//         // 3) Return IV + ciphertext
//         var result = Data()
//         result.append(iv)
//         result.append(cryptData)
//         return result
//     }
//
//     /// Decrypts [IV (16 bytes)] + [ciphertext]
//     public static func decrypt(_ cipher: Data) -> Data? {
//         guard cipher.count > kCCBlockSizeAES128 else { return nil }
//         guard let key = getOrCreateKey() else { return nil }
//
//         let iv = cipher.subdata(in: 0..<kCCBlockSizeAES128)
//         let encrypted = cipher.subdata(in: kCCBlockSizeAES128..<cipher.count)
//
//         var decryptedData = Data(count: encrypted.count + kCCBlockSizeAES128)
//         let decryptedLength = decryptedData.count      // 👈 capture length BEFORE
//         var numBytesDecrypted: size_t = 0
//
//         let status = decryptedData.withUnsafeMutableBytes { decryptedBytes in
//             iv.withUnsafeBytes { ivBytes in
//                 encrypted.withUnsafeBytes { encBytes in
//                     key.withUnsafeBytes { keyBytes in
//                         CCCrypt(
//                             CCOperation(kCCDecrypt),
//                             CCAlgorithm(kCCAlgorithmAES),
//                             CCOptions(kCCOptionPKCS7Padding),
//                             keyBytes.baseAddress, key.count,
//                             ivBytes.baseAddress,
//                             encBytes.baseAddress, encrypted.count,
//                             decryptedBytes.baseAddress, decryptedLength, // 👈 use local
//                             &numBytesDecrypted
//                         )
//                     }
//                 }
//             }
//         }
//
//         guard status == kCCSuccess else { return nil }
//
//         decryptedData.removeSubrange(numBytesDecrypted..<decryptedData.count)
//         return decryptedData
//     }
//
//
//     // MARK: - Key / Keychain internals
//
//     private static func generateRandomKey() -> Data? {
//         var data = Data(count: keyLength)
//         let status = data.withUnsafeMutableBytes { bytes in
//             guard let base = bytes.baseAddress else { return errSecParam }
//             return SecRandomCopyBytes(kSecRandomDefault, keyLength, base)
//         }
//         return (status == errSecSuccess) ? data : nil
//     }
//
//     private static func saveKey(_ key: Data) {
//         guard let tag = keyAlias.data(using: .utf8) else { return }
//
//         let query: [String: Any] = [
//             kSecClass as String: kSecClassKey,
//             kSecAttrApplicationTag as String: tag,
//             kSecAttrAccessible as String: kSecAttrAccessibleWhenUnlocked,
//             kSecValueData as String: key
//         ]
//
//         SecItemDelete(query as CFDictionary)
//         SecItemAdd(query as CFDictionary, nil)
//     }
//
//     private static func loadKey() -> Data? {
//         guard let tag = keyAlias.data(using: .utf8) else { return nil }
//
//         let query: [String: Any] = [
//             kSecClass as String: kSecClassKey,
//             kSecAttrApplicationTag as String: tag,
//             kSecReturnData as String: true,
//             kSecMatchLimit as String: kSecMatchLimitOne,
//         ]
//
//         var item: CFTypeRef?
//         let status = SecItemCopyMatching(query as CFDictionary, &item)
//         guard status == errSecSuccess else { return nil }
//         return item as? Data
//     }
// }