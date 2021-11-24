package com.web.controller;

import com.javalab.springcloud.registry.ServiceInstanceMapper;
import com.javalab.springcloud.registry.ServiceRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ServicesController.PREFIX)
public class ServicesController {
    public static final String PREFIX = "/instances";

    ServiceRegistry serviceRegistry;

    public ServicesController(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceInstanceMapper getInstanceByName(@PathVariable String name){
        return serviceRegistry.getInstanceByName(name);
    }
}
