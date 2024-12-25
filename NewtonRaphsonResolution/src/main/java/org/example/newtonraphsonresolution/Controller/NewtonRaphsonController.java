package org.example.newtonraphsonresolution.Controller;

import org.example.newtonraphsonresolution.Models.PolynomialEntry;
import org.example.newtonraphsonresolution.Models.PolynomialResponse;
import org.example.newtonraphsonresolution.Services.NewtonRaphsonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/newtonraphson")
public class NewtonRaphsonController {
    private final NewtonRaphsonService newtonRaphsonService;

    public NewtonRaphsonController(NewtonRaphsonService newtonRaphsonService) {
        this.newtonRaphsonService = newtonRaphsonService;
    }

    @PostMapping("/solve")
    public ResponseEntity<PolynomialResponse> solvePolynomial(@RequestBody PolynomialEntry polynomialEntry) {
        PolynomialResponse response = newtonRaphsonService.solvePolynomial(polynomialEntry);
        return ResponseEntity.ok(response);
    }
}
