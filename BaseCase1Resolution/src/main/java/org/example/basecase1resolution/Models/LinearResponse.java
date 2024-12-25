package org.example.basecase1resolution.Models;

import java.util.List;

public class LinearResponse {
    private List<String> roots;
    private String factorizedForm;

    public LinearResponse(List<String> roots, String factorizedForm) {
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
        return "LinearResponse{" +
                "roots=" + roots +
                ", factorizedForm='" + factorizedForm + '\'' +
                '}';
    }
}
