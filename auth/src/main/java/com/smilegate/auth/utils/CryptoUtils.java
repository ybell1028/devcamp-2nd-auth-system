package com.smilegate.auth.utils;

import com.smilegate.auth.config.CryptoProperties;
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