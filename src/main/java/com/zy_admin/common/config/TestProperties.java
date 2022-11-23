package com.zy_admin.common.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "com.admin.util")
public class TestProperties {

    private SnowflakeProperties snowflake;

}
