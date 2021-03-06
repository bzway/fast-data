package ltd.fdsa.cloud.util;

import lombok.extern.slf4j.Slf4j;
import ltd.fdsa.cloud.constant.SecretConstant;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@Slf4j
public class AESSecretUtil {
    /**
     * 秘钥的大小
     */
    private static final int KEY_SIZE = 128;

    public static byte[] encrypt(String data, String key) {
        if (StringUtils.isNotBlank(data)) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                // 选择一种固定算法，为了避免不同java实现的不同算法，生成不同的密钥，而导致解密失败
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                random.setSeed(key.getBytes());
                keyGenerator.init(KEY_SIZE, random);
                SecretKey secretKey = keyGenerator.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES"); // 创建密码器
                byte[] byteContent = data.getBytes("utf-8");
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec); // 初始化
                byte[] result = cipher.doFinal(byteContent);
                return result; // 加密
            } catch (Exception e) {
                log.error("AESSecretUtil.encrypt", e);
            }
        }
        return null;
    }

    public static String encryptToStr(String data, String key) {

        return StringUtils.isNotBlank(data) ? parseByte2HexStr(encrypt(data, key)) : null;
    }

    public static byte[] decrypt(byte[] data, String key) {
        if (ArrayUtils.isNotEmpty(data)) {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                // 选择一种固定算法，为了避免不同java实现的不同算法，生成不同的密钥，而导致解密失败
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                random.setSeed(key.getBytes());
                keyGenerator.init(KEY_SIZE, random);
                SecretKey secretKey = keyGenerator.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
                Cipher cipher = Cipher.getInstance("AES"); // 创建密码器
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec); // 初始化
                byte[] result = cipher.doFinal(data);
                return result; // 加密
            } catch (Exception e) {
                log.error("AESSecretUtil.encrypt", e);
            }
        }
        return null;
    }

    public static String decryptToStr(String enCryptdata, String key) {
        return StringUtils.isNotBlank(enCryptdata)
                ? new String(decrypt(parseHexStr2Byte(enCryptdata), key))
                : null;
    }

    public static String parseByte2HexStr(byte[] buffer) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buffer.length; i++) {
            String hex = Integer.toHexString(buffer[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
        String ss = encryptToStr("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxMjMiLCJ1c2VyTmFtZSI6Ikp1ZHkiLCJleHAiOjE1MzI3Nzk2MjIsIm5iZiI6MTUzMjc3NzgyMn0.sIw_leDZwG0pJ8ty85Iecd_VXjObYutILNEwPUyeVSo", SecretConstant.DATA_KEY);
        log.info(ss);
        log.info(decryptToStr(ss, SecretConstant.DATA_KEY));
    }
}
