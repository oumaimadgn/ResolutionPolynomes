package org.example.ferrariresolution.Models;

import java.util.List;

public class FerrariResponse {
    private List<String> roots;
    private String factorizedForm;

    public FerrariResponse(List<String> roots, String factorizedForm) {
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
        return "FerrariResponse{" +
                "roots=" + roots +
                ", factorizedForm='" + factorizedForm + '\'' +
                '}';
    }
}
