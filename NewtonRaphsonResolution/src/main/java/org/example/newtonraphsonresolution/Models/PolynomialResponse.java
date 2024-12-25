package org.example.newtonraphsonresolution.Models;


import java.util.List;

public class PolynomialResponse {
    private List<String> roots;
    private String factorizedForm;

    public PolynomialResponse(List<String> roots, String factorizedForm) {
        this.roots = roots;
        this.factorizedForm = factorizedForm;
    }

    public List<String> getRoots() {
        return roots;
    }

    public String getFactorizedForm() {
        return factorizedForm;
    }

    public void setRoots(List<String> roots) {
        this.roots = roots;
    }

    public void setFactorizedForm(String factorizedForm) {
        this.factorizedForm = factorizedForm;
    }
}
