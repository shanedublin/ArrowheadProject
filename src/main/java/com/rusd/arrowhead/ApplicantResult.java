package com.rusd.arrowhead;

import java.util.ArrayList;
import java.util.List;

public class ApplicantResult {
    private Applicant applicant;
    private List<String> rejectionReasons = new ArrayList<>();
    private String result;

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public List<String> getRejectionReasons() {
        return rejectionReasons;
    }

    public void setRejectionReasons(List<String> rejectionReasons) {
        this.rejectionReasons = rejectionReasons;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {

        String s = applicant + " Status  : \"" + result + "\"";
        if(!rejectionReasons.isEmpty()) {
            s += "\n\tRejection Reasons\n";
            for (String r : rejectionReasons) {
                s += "\t\t" + r + "\n";
            }
        }
        return s;
    }
}
