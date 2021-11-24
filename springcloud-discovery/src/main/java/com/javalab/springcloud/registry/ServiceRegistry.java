package com.javalab.springcloud.registry;

public interface ServiceRegistry extends AutoCloseable{

    ServiceInstanceMapper getInstanceByName(String name);
}
