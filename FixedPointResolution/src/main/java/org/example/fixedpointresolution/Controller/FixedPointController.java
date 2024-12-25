package org.example.fixedpointresolution.Controller;

import org.example.fixedpointresolution.Models.FixedPointResponse;
import org.example.fixedpointresolution.Services.FixedPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fixedpoint")
public class FixedPointController {

    private final FixedPointService fixedPointService;

    @Autowired
    public FixedPointController(FixedPointService fixedPointService) {
        this.fixedPointService = fixedPointService;
    }

    @PostMapping("/solve")
    public ResponseEntity<FixedPointResponse> solveFixedPoint(@RequestParam double initialGuess,
                                                              @RequestParam String domain) {
        FixedPointResponse response = fixedPointService.solveFixedPoint(initialGuess, domain);
        return ResponseEntity.ok(response);
    }
}
