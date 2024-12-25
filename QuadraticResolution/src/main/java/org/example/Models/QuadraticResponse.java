package org.example.Models;

import java.util.List;

public class QuadraticResponse {
    private List<String> roots; // List of roots
    private String factorizedForm;

    public QuadraticResponse(List<String> roots, String factorizedForm) {
        this.roots = roots;
        this.factorizedForm = factorizedForm;
    }

    public List<String> getRoots() {
        return roots;
    }

    public void setRoots(List<String> roots) {
        this.roots = roots;
    }

    public String getFactorizedForm() {
        return factorizedForm;
    }

    public void setFactorizedForm(String factorizedForm) {
        this.factorizedForm = factorizedForm;
    }

    @Override
    public String toString() {
        return "QuadraticResponse{" +
                "roots=" + roots +
                ", factorizedForm='" + factorizedForm + '\'' +
                '}';
    }
}
