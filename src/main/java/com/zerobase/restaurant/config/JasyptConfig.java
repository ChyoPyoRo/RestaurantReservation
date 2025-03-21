package com.zerobase.restaurant.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.salt.RandomSaltGenerator;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {
    //application.properties값 암호화
    //vm option으로 입력
    @Value("${jasypt.encryptor.password}")
    private String password;
    @Bean("jasyptEncryptor")
    public PooledPBEStringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(password); // 암호화 키 설정
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256"); //암호화에 사용할 알고리즘
        config.setKeyObtentionIterations("1000"); //암호화키 생성을 위한 반복 횟수
        config.setPoolSize("1"); // 암호화키 풀의 크기
        config.setSaltGenerator(new RandomSaltGenerator());//salt 생성 클래스 > 동일한 단어 다른 결과값
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");//무작위 초기화 벡터 설정
        config.setStringOutputType("base64");//결과값 (Base64로 출력)
        encryptor.setConfig(config);
        return encryptor;
    }
}
