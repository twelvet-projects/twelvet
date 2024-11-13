package com.twelvet.server.ai.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeCloudStore;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeStoreOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    /*@Bean
    public DashScopeCloudStore store(DashScopeApi dashscopeApi) {
        return new DashScopeCloudStore(
                dashscopeApi, new DashScopeStoreOptions("twelvet"));
    }*/

}
