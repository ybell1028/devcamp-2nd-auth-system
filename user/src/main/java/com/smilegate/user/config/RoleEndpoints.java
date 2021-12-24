package com.smilegate.user.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties("endpoints")
public class RoleEndpoints {
    private List<String> associate = new ArrayList<>();
    private List<String> regular = new ArrayList<>();
    private List<String> admin = new ArrayList<>();
}
