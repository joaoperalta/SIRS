package Bank.Server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AES {
	
	private SecretKey _key;
	private byte[] _iv;
	//private byte[] _iv = {0 ,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
	
	public AES(String key){
		setKey(key);		
	}
	
	public byte[] getIV(){ return _iv;}
	
	private void setKey(String key){
		
		_key = new SecretKeySpec(key.getBytes(), 0, key.getBytes().length, "AES"); 
	}
	
	public void setIV(byte[] iv){
		_iv = iv;
	}
	
	public byte[] generateIV(){

		byte[] iv = new byte[16];	// Save the IV bytes or send it in plaintext with the encrypted data so you can decrypt the data later
		SecureRandom prng = new SecureRandom();
		prng.nextBytes(iv);
		_iv = iv;
		return iv;
	}
	
	public byte[] encrypt(String strDataToEncrypt) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException{
		 
		Cipher aesCipherForEncryption = Cipher.getInstance("AES/CBC/PKCS5PADDING");
				
		aesCipherForEncryption.init(Cipher.ENCRYPT_MODE, _key, new IvParameterSpec(_iv));
			
		byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
		byte[] byteCipherText = aesCipherForEncryption.doFinal(byteDataToEncrypt);
		
		return byteCipherText;
	}
	
	public byte[] decrypt(byte[] byteCipherText) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, IOException{
		
		Cipher aesCipherForDecryption = Cipher.getInstance("AES/CBC/PKCS5PADDING"); // Must specify the mode explicitly as most JCE providers default to ECB mode!!				

		aesCipherForDecryption.init(Cipher.DECRYPT_MODE, _key,new IvParameterSpec(_iv));
		System.out.println("in: "+byteCipherText.length);
		byte[] byteDecryptedText = aesCipherForDecryption.doFinal(byteCipherText);
		
		return byteDecryptedText;
	}
}
