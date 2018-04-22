/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multitoolapp.common;

/**
 *
 * @author Chandan
 */
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

//import sun.misc.*;

public class EncryptionDecryption {

    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't',
        'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    public static String encryptString(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
       // byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = null;//new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    public static String decryptString(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = null;//new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }

}
