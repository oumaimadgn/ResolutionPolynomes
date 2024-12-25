package com.example.Model;

public class QuadraticResolutionResponse {
    private String root1;
    private String root2;
    private String factorizedForm;

    public QuadraticResolutionResponse(String root1, String root2, String factorizedForm) {
        this.root1 = root1;
        this.root2 = root2;
        this.factorizedForm = factorizedForm;
    }

    public String getRoot1() {
        return root1;
    }

    public String getRoot2() {
        return root2;
    }

    public String getFactorizedForm() {
        return factorizedForm;
    }

    public void setRoot1(String root1) {
        this.root1 = root1;
    }

    public void setRoot2(String root2) {
        this.root2 = root2;
    }

    public void setFactorizedForm(String factorizedForm) {
        this.factorizedForm = factorizedForm;
    }

    @Override
    public String toString() {
        return "QuadraticResponse{" +
                "root1='" + root1 + '\'' +
                ", root2='" + root2 + '\'' +
                ", factorizedForm='" + factorizedForm + '\'' +
                '}';
    }
}

