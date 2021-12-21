package com.smilegate.user.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CryptoProperties {
    @Value("${crypto.transformation}")
    private String transformation;
    @Value("${crypto.saltlength}")
    private int saltlength;
    @Value("${crypto.keylength}")
    private int keylength;
    @Value("${crypto.iteration}")
    private int iteration;
    @Value("${crypto.key}")
    private String key;
    @Value("${crypto.algorithm.secretkeyfactory}")
    private String skfAlg;
    @Value("${crypto.algorithm.secretkeyspec}")
    private String sksAlg;
}