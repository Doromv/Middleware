package com.doromv.provider.config;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * @author Doromv
 * @Description
 * @create 2022-06-19-16:10
 */
@Configuration
public class MyDubboConfig {
    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("boot-user-service-provider");
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("127.0.0.1:2181");
        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);
        return protocolConfig;
    }

    @Bean
    public ServiceConfig<UserService> serviceConfig( UserService userService){
        ServiceConfig<UserService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(UserService.class);
        serviceConfig.setRef(userService);
        serviceConfig.setVersion("1.0.0");

        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setName("getUserAddressList");
        methodConfig.setTimeout(1000);

        ArrayList<MethodConfig> list = new ArrayList<>();
        list.add(methodConfig);
        serviceConfig.setMethods(list);
        
        return serviceConfig;
    }
}
