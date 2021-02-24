package br.com.zup.proposta.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AESUtil {
	
	@Value("${aes.crypt.salt}")
	private String salt;
	
	@Value("${aes.crypt.password}")
	private String password;
	
	@Value("${aes.crypt.key}")
	private String key;
	
	public SecretKey generateKey(int n) throws NoSuchAlgorithmException {
	    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	    keyGenerator.init(n);
	    SecretKey key = keyGenerator.generateKey();
	    return key;
	}
	
	public SecretKey getKeyFromPassword()
	    throws NoSuchAlgorithmException, InvalidKeySpecException {
	    
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
	    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
	        .getEncoded(), "AES");
	    return secret;
	}
	
	public IvParameterSpec generateIv() {
	    byte[] iv = this.key.getBytes();
	    return new IvParameterSpec(iv);
	}
	
	public String encrypt(String input) {
		
		IvParameterSpec iv = generateIv();
		String algorithm = "AES/CBC/PKCS5Padding";
		
		try {
			SecretKey key = getKeyFromPassword();
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			
			byte[] cipherText = cipher.doFinal(input.getBytes());
			
			return Base64.getEncoder()
					.encodeToString(cipherText);
			
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return null;
	}
	
	public String decrypt(String cipherText) {
	
		IvParameterSpec iv = generateIv();
		String algorithm = "AES/CBC/PKCS5Padding";
		
		try {
			SecretKey key = getKeyFromPassword();
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			
			byte[] plainText = cipher.doFinal(Base64.getDecoder()
					.decode(cipherText));
			
			return new String(plainText);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return null;
	}
	
	public boolean isCrypt(String text) {
		try {
			this.decrypt(text);
			return true;
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
		}
		return false;
	}
}
