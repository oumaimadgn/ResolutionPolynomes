package com.example.Api_Gateway.Model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PolynomialRequest {

    private String polynomial;
    private String domain;
    private String method; // Added method field

    // Optional Constructor
    public PolynomialRequest(String polynomial, String domain, String method) {
        this.polynomial = polynomial;
        this.domain = domain;
        this.method = method;
    }

    @Override
    public String toString() {
        return "PolynomialRequest{" +
                "polynomial='" + polynomial + '\'' +
                ", domain='" + domain + '\'' +
                ", method='" + method + '\'' +
                '}';
    }


}
