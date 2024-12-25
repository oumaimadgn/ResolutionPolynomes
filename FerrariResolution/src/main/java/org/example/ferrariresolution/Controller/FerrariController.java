package org.example.ferrariresolution.Controller;

import org.example.ferrariresolution.Models.PolynomialEntry;
import org.example.ferrariresolution.Models.FerrariResponse;
import org.example.ferrariresolution.Services.FerrariService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ferrari")
public class FerrariController {

    private final FerrariService ferrariService;

    public FerrariController(FerrariService ferrariService) {
        this.ferrariService = ferrariService;
    }

    @PostMapping("/solve")
    public ResponseEntity<FerrariResponse> solveFerrari(@RequestBody PolynomialEntry polynomialEntry) {
        if (polynomialEntry.getOrder() != 4) {
            return ResponseEntity.badRequest().body(
                    new FerrariResponse(
                            List.of("Invalid polynomial order"),
                            "Only quartic (order 4) polynomials are supported"
                    )
            );
        }

        double[] coefficients = polynomialEntry.getCoefficients();
        FerrariResponse response = ferrariService.solveFerrari(
                coefficients[0],
                coefficients[1],
                coefficients[2],
                coefficients[3],
                coefficients[4],
                polynomialEntry.getDomain()
        );

        return ResponseEntity.ok(response);
    }
}
