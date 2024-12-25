package org.example.bairstowresolution.Controller;

import org.example.bairstowresolution.Models.PolynomialEntry;
import org.example.bairstowresolution.Models.PolynomialResponse;
import org.example.bairstowresolution.Services.BairstowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bairstow")
public class BairstowController {
    private final BairstowService bairstowService;

    public BairstowController(BairstowService bairstowService) {
        this.bairstowService = bairstowService;
    }

    @PostMapping("/solve")
    public ResponseEntity<PolynomialResponse> solvePolynomial(@RequestBody PolynomialEntry polynomialEntry) {
        PolynomialResponse response = bairstowService.solvePolynomial(polynomialEntry);
        return ResponseEntity.ok(response);
    }
}
