package com.example.Controller;

import com.example.Model.PolynomialRequest;
import com.example.Model.PolynomialResponse;
import com.example.Service.PolynomialService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/polynomial")
public class PolynomialController {

    private final PolynomialService polynomialService;

    public PolynomialController(PolynomialService polynomialService) {
        this.polynomialService = polynomialService;
    }

    @Operation(summary = "Analyze a polynomial", description = "Analyze a polynomial equation with specified domain and interval")
    @PostMapping("/evaluate")
    public ResponseEntity<PolynomialResponse> analyzePolynomial(@RequestBody PolynomialRequest request) {

        // Extract interval from the request

        // Process polynomial and return the response
        PolynomialResponse response = polynomialService.processPolynomial(
                request.getPolynomial(),
                request.getDomain()
        );
        return ResponseEntity.ok(response);
    }
}