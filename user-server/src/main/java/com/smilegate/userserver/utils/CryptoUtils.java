package com.smilegate.userserver.utils;

import com.smilegate.userserver.config.CryptoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class CryptoUtils {
    public final CryptoProperties cryptoProperties;

    public String encrypt(String str) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[cryptoProperties.getSaltlength()];
        random.nextBytes(salt);

        SecretKeyFactory factory = SecretKeyFactory.getInstance(cryptoProperties.getSkfAlg());
        // key에 랜덤 salt를 더하고 20000번 해시하여 256bit 길이의 PBEKeySpec 생성
        PBEKeySpec pbeKeySpec = new PBEKeySpec(cryptoProperties.getKey().toCharArray(), salt, cryptoProperties.getIteration(), cryptoProperties.getKeylength());
        SecretKey secretKey = factory.generateSecret(pbeKeySpec); // 윗 라인에서 생성한 키로 secretKey 생성
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), cryptoProperties.getSksAlg());

        Cipher cipher = Cipher.getInstance(cryptoProperties.getTransformation());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec); // 위에서 생성한 secret으로 CBC
        AlgorithmParameters params = cipher.getParameters();

        // Initialization Vector
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedText = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        byte[] buffer = new byte[salt.length + iv.length + encryptedText.length];

        System.arraycopy(salt, 0, buffer, 0, salt.length);
        System.arraycopy(iv, 0, buffer, salt.length, iv.length);
        System.arraycopy(encryptedText, 0, buffer, salt.length + iv.length, encryptedText.length);

        return Base64.getEncoder().encodeToString(buffer);
    }

    public String decrypt(String str) throws Exception {
        Cipher cipher = Cipher.getInstance(cryptoProperties.getTransformation());
        ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(str));

        byte[] salt = new byte[cryptoProperties.getSaltlength()];
        buffer.get(salt, 0, salt.length);
        byte[] iv = new byte[cipher.getBlockSize()];
        buffer.get(iv, 0, iv.length);
        byte[] encryptedText = new byte[buffer.capacity() - salt.length - iv.length];
        buffer.get(encryptedText);

        SecretKeyFactory factory = SecretKeyFactory.getInstance(cryptoProperties.getSkfAlg());
        PBEKeySpec pbeKeySpec = new PBEKeySpec(cryptoProperties.getKey().toCharArray(), salt, cryptoProperties.getIteration(), cryptoProperties.getKeylength());
        SecretKey secretKey = factory.generateSecret(pbeKeySpec);
        SecretKeySpec secretKetSpec = new SecretKeySpec(secretKey.getEncoded(), cryptoProperties.getSksAlg());

        cipher.init(Cipher.DECRYPT_MODE, secretKetSpec, new IvParameterSpec(iv));

        byte[] decryptedTextBytes = cipher.doFinal(encryptedText);
        return new String(decryptedTextBytes);
    }
}