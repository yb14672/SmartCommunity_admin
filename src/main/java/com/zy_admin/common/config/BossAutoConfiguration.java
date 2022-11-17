package com.zy_admin.common.config;

import com.zy_admin.util.SnowflakeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author admin
 */
@Configuration
@EnableConfigurationProperties(TestProperties.class)
public class BossAutoConfiguration {
    @Autowired
    private TestProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public SnowflakeManager snowflakeManager() {
        return new SnowflakeManager(this.properties.getSnowflake().getMachineId(), this.properties.getSnowflake().getDataCenterId());
    }
}
