package org.example.mullerresolution.Models;

import java.util.List;

public class MullerResponse {
    private List<String> roots;
    private String factorizedForm;

    public MullerResponse(List<String> roots, String factorizedForm) {
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
