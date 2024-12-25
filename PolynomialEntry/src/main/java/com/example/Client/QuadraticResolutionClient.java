package com.example.Client;

import com.example.Model.PolynomialResponse;
import com.example.Model.QuadraticResolutionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "QuadraticResolution")
public interface QuadraticResolutionClient {
    @PostMapping("/api/v1/quadratic/solve")
    QuadraticResolutionResponse resolveQuadratic(PolynomialResponse request);
}
