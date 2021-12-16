package com.smilegate.userserver.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
//@ConfigurationProperties(prefix = "crypto")
@Component
public class CryptoProperties {
    @Value("${crypto.transformation}")
    private String transformation;
    @Value("${crypto.length}")
    private int length;
    @Value("${crypto.key}")
    private String key;
    @Value("${crypto.algorithm.secretkeyfactory}")
    private String skfAlg;
    @Value("${crypto.algorithm.secretkeyspec}")
    private String sksAlg;
}
