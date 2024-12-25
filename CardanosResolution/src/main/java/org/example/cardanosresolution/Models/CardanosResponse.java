package org.example.cardanosresolution.Models;

import java.util.List;

public class CardanosResponse {
    private List<String> roots; // List of roots
    private String factorizedForm;

    public CardanosResponse(List<String> roots, String factorizedForm) {
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
        return "CardanosResponse{" +
                "roots=" + roots +
                ", factorizedForm='" + factorizedForm + '\'' +
                '}';
    }
}
