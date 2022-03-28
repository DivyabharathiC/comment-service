package com.example.commentservice.Feign;


import com.example.commentservice.model.User;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service")
public interface UserFeignClient {

    @LoadBalanced
    @GetMapping("/api/v1/users/{userId}")
    User getUser(@PathVariable("userId") String userId);
}
