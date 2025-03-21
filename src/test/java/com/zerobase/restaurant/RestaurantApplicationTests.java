package com.zerobase.restaurant;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
class RestaurantApplicationTests {

	@Autowired
	private PooledPBEStringEncryptor encryptor;

	@Test
	void contextLoads() {
	}

	@Test
	void encryptor(){
		String plainText = ""; // 암호화할 값
		String encrypted = encryptor.encrypt(plainText); // 암호화
		String decrypted = encryptor.decrypt(encrypted); // 복호화

		System.out.println("🔐 원문     : " + plainText);
		System.out.println("🔐 암호문   : " + encrypted);
		System.out.println("🔐 복호문   : " + decrypted);
	}


}
