package codec;

import com.mongodb.internal.HexUtils;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESTest {

//  public static void main(String[] args) throws Exception {
//    var ins = new AESTest();
//    var key = ins.genKey();
////    System.out.println(ins.getBaseEncodeString(key.getEncoded()));
////    var content = "xiong!";
////    var encryptContent = ins.encrypt(content.getBytes(), key);
////    var decryptContent = ins.decrypt(encryptContent, key);
////    System.out.println(decryptContent);
//
//    ins.testCFB(key);
////    ins.getEncodeHex();
////    ins.decodeHex();
//  }
//
//  public SecretKey genKey() throws NoSuchAlgorithmException {
//    var generator = KeyGenerator.getInstance("AES");
//    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//    random.setSeed("haha".getBytes());
//    generator.init(128, random);
//    return generator.generateKey();
//  }
//
//  public byte[] encrypt(byte[] content, Key key)
//      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
//      IllegalBlockSizeException, InvalidAlgorithmParameterException {
//    Cipher cipher = Cipher.getInstance("AES/OFB/NoPadding"); //不同的模式，init的参数是不一样的
//    //如果是使用默认的，则不需要额外的参数
//    SecretKey aesKey = new SecretKeySpec(key.getEncoded(), "AES");
//    IvParameterSpec ivSpec = new IvParameterSpec(key.getEncoded());
//
//    cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
//    byte[] result = cipher.doFinal(content);
//    System.out.println(getBaseEncodeString(result));
//    return result;
//  }
//
//  public String decrypt(byte[] content, Key key)
//      throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
//      IllegalBlockSizeException, InvalidAlgorithmParameterException {
//    Cipher cipher = Cipher.getInstance("AES/OFB/NoPadding");
//    SecretKey aesKey = new SecretKeySpec(key.getEncoded(), "AES");
//    IvParameterSpec ivSpec = new IvParameterSpec(key.getEncoded());
//
//    cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
//    byte[] result = cipher.doFinal(content);
//    return new String(result);
//  }
//
//  public String getBaseEncodeString(byte[] content) {
//    return Base64.getEncoder().encodeToString(content);
//  }
//
//  public void testCFB(Key key) throws Exception{
//    String text = "xiong!";
//    var textByte = text.getBytes();
//
//    Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
//    SecretKey aesKey = new SecretKeySpec(key.getEncoded(), "AES");
//    IvParameterSpec ivSpec = new IvParameterSpec(key.getEncoded());
//
//    cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
//
//    Cipher cipher1 = Cipher.getInstance("AES/OFB/NoPadding");
//    SecretKey aesKey1 = new SecretKeySpec(key.getEncoded(), "AES");
//    IvParameterSpec ivSpec1 = new IvParameterSpec(key.getEncoded());
//
//    cipher1.init(Cipher.DECRYPT_MODE, aesKey1, ivSpec1);
//
//    var data1 = cipher.doFinal(text.getBytes());
//    var data2 = cipher.doFinal(text.getBytes());
//    var data3 = cipher.doFinal(text.getBytes());
//    System.out.println(new String(cipher1.doFinal(data2)));
//
//  }
//
//
//  //生成加密后的字符串
//  public void getEncodeHex() throws Exception {
//    byte[] key = new byte[16];
//    for (int i = 0; i < 16; i++) {
//      key[i] = 0x00;
//    }
//    Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
//    SecretKey aesKey = new SecretKeySpec(key, "AES");
//    IvParameterSpec ivSpec = new IvParameterSpec(key);
//
//    cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
//    var text = "xiong!";
//    byte[] result = cipher.doFinal(text.getBytes());
//
//    System.out.println(HexUtils.toHex(result));
//  }
//
//  public void decodeHex() throws Exception {
//    byte[] encode = {0x1E, (byte) 0x80, 0x24, (byte) 0xBA, (byte) 0x88, (byte) 0xAB};
//    byte[] key = new byte[16];
//    for (int i = 0; i < 16; i++) {
//      key[i] = 0x00;
//    }
//    Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
//    SecretKey aesKey = new SecretKeySpec(key, "AES");
//    IvParameterSpec ivSpec = new IvParameterSpec(key);
//    cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
//    System.out.println(new String(cipher.doFinal(encode)));
//  }

}
