package org.woodpecker.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.woodpecker.service.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AppConfig {

    @Autowired
    void configureAndStoreObjectMapper(ObjectMapper objectMapper) {
//        objectMapper.registerModule(new Hibernate5Module());
        JsonUtil.setMapper(objectMapper);
    }

}
