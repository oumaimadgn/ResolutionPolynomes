package org.example.fixedpointresolution.Models;

import java.util.List;

public class FixedPointResponse {
    private List<String> roots;
    private String message;

    public FixedPointResponse(List<String> roots, String message) {
        this.roots = roots;
        this.message = message;
    }

    public List<String> getRoots() {
        return roots;
    }

    public void setRoots(List<String> roots) {
        this.roots = roots;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FixedPointResponse{" +
                "roots=" + roots +
                ", message='" + message + '\'' +
                '}';
    }
}
