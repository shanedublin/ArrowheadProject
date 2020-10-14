package com.rusd.arrowhead;

public class AutoReview {

    public static final String INSTANT_REJECT = "instant reject";
    public static final String INSTANT_ACCEPT = "instant accept";
    public static final String FURTHER_REVIEW = "further review";

    public static final String REJECTION_FELONIES = "\"Has at least one felony in the past 5 years\"";
    public static final String REJECTION_GPA = "\"GPA is below required the required scale of 70%\"";
    public static final String REJECTION_NAME = "\"Name is in improper format, First letter should be uppercase, other should be lower\"";
    public static final String REJECTION_AGE = "\"Must enter positive age\"";


    public boolean instantReject(ApplicantResult applicantResult) {
        Applicant applicant = applicantResult.getApplicant();
        boolean instantReject = false;

        if (invalidApplicantName(applicant)) {
            instantReject = true;
            applicantResult.getRejectionReasons().add(REJECTION_NAME + " value provided: " + applicant.getFirstName() + "  " + applicant.getLastName() );
        }

        if (applicant.hasFelonies()) {
            instantReject = true;
            applicantResult.getRejectionReasons().add(REJECTION_FELONIES + " value provided: " + applicant.hasFelonies());
        }

        if (applicant.getGpa().getPercent() < 0.70f) {
            instantReject = true;
            applicantResult.getRejectionReasons().add(REJECTION_GPA + " value provided: " + applicant.getGpa().getPercent());
        }

        if (applicant.getAge() < 0) {
            instantReject = true;
            applicantResult.getRejectionReasons().add(REJECTION_AGE + " value provided: " + applicant.getAge());
        }

        return instantReject;
    }

    public boolean instantAccept(Applicant applicant) {
        return instantAcceptAgeState(applicant)
                && instantAcceptGPA(applicant)
                && instantAcceptTestScore(applicant);
    }


    public boolean invalidApplicantName(Applicant applicant) {
        return invalidName(applicant.getFirstName()) || invalidName(applicant.getLastName());
    }

    public boolean invalidName(String name) {
        if (Character.isLowerCase(name.charAt(0))) {
            return true;
        }

        for (int i = 1; i < name.length(); i++) {
            if (Character.isUpperCase(name.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public boolean instantAcceptAgeState(Applicant a) {

        if (a.getAge() > 80) {
            return true;
        }
        String state = a.getState();
        if ("California".equalsIgnoreCase(state)) {
            if (17f <= a.getAge() && 26 > a.getAge()) {
                return true;
            }
        }
        return false;
    }

    public boolean instantAcceptGPA(Applicant a) {
        return 0.90f <= a.getGpa().getPercent();
    }

    public boolean instantAcceptTestScore(Applicant a) {

        return (a.getSatScore() >= 1920 || a.getActScore() >= 27);

    }

    public ApplicantResult process(Applicant a) {

        ApplicantResult result = new ApplicantResult();
        result.setApplicant(a);

        if (instantReject(result)) {
            result.setResult(INSTANT_REJECT);
            return result;
        }

        if (instantAccept(a)) {
            result.setResult(INSTANT_ACCEPT);
            return result;
        }

        result.setResult(FURTHER_REVIEW);
        return result;
    }
}
