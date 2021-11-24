package com.javalab.springcloud.registry;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;

import java.util.Collection;
import java.util.Optional;

@Slf4j
public class ServiceRegistryImpl implements ServiceRegistry{
    
    ServiceDiscovery<ZookeeperInstance> serviceDiscovery;
    CuratorFramework curatorFramework;
    ZookeeperDiscoveryProperties zookeeperDiscoveryProperties;

    public ServiceRegistryImpl(CuratorFramework curatorFramework, ZookeeperDiscoveryProperties zookeeperDiscoveryProperties) {
        this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ZookeeperInstance.class)
                .client(curatorFramework)
                .basePath(zookeeperDiscoveryProperties.getRoot()).build();
        this.curatorFramework = curatorFramework;
        this.zookeeperDiscoveryProperties = zookeeperDiscoveryProperties;
    }

    @Override
    public ServiceInstanceMapper getInstanceByName(String name) {
        try {
            Collection<ServiceInstance<ZookeeperInstance>> serviceInstances = serviceDiscovery.queryForInstances(name);
            Optional<ServiceInstance<ZookeeperInstance>> optionalServiceInstance =
                    serviceInstances.stream()
                    .filter(instance -> StringUtils.equals(instance.getName(), name)).findFirst();
            if (optionalServiceInstance.isPresent()){
                ServiceInstance<ZookeeperInstance> serviceInstance = optionalServiceInstance.get();
                ServiceInstanceMapper serviceInstanceMapper = new ServiceInstanceMapper(serviceInstance);
                return  serviceInstanceMapper;
            }
        } catch (Exception e) {
            log.error("Failed to fetch instance {} from zookeeper", name);
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        if(curatorFramework != null){
            curatorFramework.close();
            curatorFramework = null;
        }
        if (this.serviceDiscovery != null){
            serviceDiscovery.close();
            curatorFramework = null;
        }
    }
}
