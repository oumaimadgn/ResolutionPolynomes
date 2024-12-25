package org.example.Controller;

import org.example.Models.PolynomialEntry;
import org.example.Models.QuadraticResponse;
import org.example.Services.QuadraticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quadratic")
public class QuadraticController {

    private final QuadraticService quadraticService;

    public QuadraticController(QuadraticService quadraticService) {
        this.quadraticService = quadraticService;
    }

    @PostMapping("/solve")
    public ResponseEntity<QuadraticResponse> solveQuadratic(@RequestBody PolynomialEntry polynomialEntry) {
        if (polynomialEntry.getOrder() != 2) {
            return ResponseEntity.badRequest().body(
                    new QuadraticResponse(
                            List.of("Invalid polynomial order"),
                            "Only order 2 is supported"
                    )
            );
        }

        double[] coefficients = polynomialEntry.getCoefficients();
        double a = coefficients[0];
        double b = coefficients[1];
        double c = coefficients[2];

        QuadraticResponse response = quadraticService.solveQuadratic(a, b, c, polynomialEntry.getDomain());

        return ResponseEntity.ok(response);
    }
}
