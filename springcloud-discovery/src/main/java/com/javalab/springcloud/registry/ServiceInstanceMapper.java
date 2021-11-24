package com.javalab.springcloud.registry;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;

@Getter
@NoArgsConstructor
public class ServiceInstanceMapper {
    String serviceInstanceId;
    String serviceInstanceName;
    ZookeeperInstance payload;

    public ServiceInstanceMapper(ServiceInstance<ZookeeperInstance> zookeeperInstance) {
        this.serviceInstanceId = zookeeperInstance.getId();
        this.serviceInstanceName = zookeeperInstance.getName();
        this.payload = zookeeperInstance.getPayload();
    }
}
