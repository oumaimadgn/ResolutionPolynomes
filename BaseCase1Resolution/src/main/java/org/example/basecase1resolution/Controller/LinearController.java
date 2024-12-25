package org.example.basecase1resolution.Controller;

import org.example.basecase1resolution.Models.LinearResponse;
import org.example.basecase1resolution.Models.PolynomialEntry;
import org.example.basecase1resolution.Services.LinearService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/linear")
public class LinearController {

    private final LinearService linearService;

    public LinearController(LinearService linearService) {
        this.linearService = linearService;
    }

    @PostMapping("/solve")
    public ResponseEntity<LinearResponse> solveLinear(@RequestBody PolynomialEntry polynomialEntry) {
        if (polynomialEntry.getOrder() != 1) {
            return ResponseEntity.badRequest().body(
                    new LinearResponse(
                            List.of("Invalid polynomial order"),
                            "Only linear (order 1) polynomials are supported"
                    )
            );
        }

        double[] coefficients = polynomialEntry.getCoefficients();
        if (coefficients == null || coefficients.length != 2) {
            return ResponseEntity.badRequest().body(
                    new LinearResponse(
                            List.of("Invalid coefficients"),
                            "Linear equations require exactly 2 coefficients (a, b)"
                    )
            );
        }

        double a = coefficients[0];
        double b = coefficients[1];

        LinearResponse response = linearService.solveLinear(a, b);
        return ResponseEntity.ok(response);
    }
}
