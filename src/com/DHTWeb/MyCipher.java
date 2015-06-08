package com.DHTWeb;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import net.tomp2p.peers.PeerAddress;
import net.tomp2p.storage.DataBuffer;
import net.tomp2p.utils.Utils;



/**
 * DES��ȫ�������
 * 
 * <pre>
 * ֧�� DES��DESede(TripleDES,����3DES)��AES��Blowfish��RC2��RC4(ARCFOUR)
 * DES          		key size must be equal to 56
 * DESede(TripleDES) 	key size must be equal to 112 or 168
 * AES          		key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
 * Blowfish     		key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
 * RC2          		key size must be between 40 and 1024 bits
 * RC4(ARCFOUR) 		key size must be between 40 and 1024 bits
 * �������� ��Ҫ��ע JDK Document http://.../docs/technotes/guides/security/SunProviders.html
 * </pre>
 * 
 * @author ����
 * @version 1.0
 * @since 1.0
 */
public abstract class MyCipher  {
	/**
	 * ALGORITHM �㷨 <br>
	 * ���滻Ϊ��������һ���㷨��ͬʱkeyֵ��size��Ӧ�ı䡣
	 * 
	 * <pre>
	 * DES          		key size must be equal to 56
	 * DESede(TripleDES) 	key size must be equal to 112 or 168
	 * AES          		key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
	 * Blowfish     		key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
	 * RC2          		key size must be between 40 and 1024 bits
	 * RC4(ARCFOUR) 		key size must be between 40 and 1024 bits
	 * </pre>
	 * 
	 * ��Key toKey(byte[] key)������ʹ����������
	 * <code>SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);</code> �滻
	 * <code>
	 * DESKeySpec dks = new DESKeySpec(key);
	 * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
	 * SecretKey secretKey = keyFactory.generateSecret(dks);
	 * </code>
	 */
	public static final String ALGORITHM = "DES";
	
	


	/**
	 * ת����Կ<br>
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Key toKey(String key) throws Exception {
		byte[] buf=encryptSHA(key);
		return toKey(buf);
	}
	/**
	 * ת����Կ<br>
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Key toKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);

		// ��ʹ�������ԳƼ����㷨ʱ����AES��Blowfish���㷨ʱ�������������滻�������д���
		// SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);

		return secretKey;
	}

	/**
	 * SHA����
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(String key) throws Exception {

		MessageDigest sha = MessageDigest.getInstance("SHA");
		sha.update(key.getBytes());
		return sha.digest();

	}

	/**
	 * ����
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(data);
	}

	/**
	 * ����
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);

		return cipher.doFinal(data);
	}
	
	public static byte[] encrypt(Object data, Key key) throws Exception {
		
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);

		return cipher.doFinal(Utils.encodeJavaObject(data));
	}

	
	
}




