package com.rusd.arrowhead;

import com.rusd.arrowhead.Applicant;
import com.rusd.arrowhead.ApplicantResult;
import com.rusd.arrowhead.AutoReview;
import com.rusd.arrowhead.GPA;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AutoReviewTest {

    AutoReview review;

    @BeforeEach
    public void init(){
        review = new AutoReview();
    }


    @ParameterizedTest
    @CsvSource({"true,  4.0, 4.0, 18, Shane, Dublin",
                "false, 2.796, 4.0, 18, Shane, Dublin",
                "false, 4.0, 4.0, 18, sHANE, Dublin",
                "false, 4.0, 4.0, 18, SHANE, Dublin",
                "false, 4.0, 4.0, 18, Shane, dublin",
                "false, 4.0, 4.0, 18, Shane, DublIn",
                "false, 4.0, 4.0, -1, Shane, Dublin"})
    void instantReject(boolean felonies, float gpaGrade, float gpaScale, int age, String firstName, String lastName){
        Applicant a = createApplicant(firstName,lastName,"", felonies,age,gpaGrade,gpaScale,5000f,100f);

        ApplicantResult ar = new ApplicantResult();
        ar.setApplicant(a);
        boolean result = review.instantReject(ar);
        Assert.assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({
            "shane, Dublin",
            "Shane, dublin",
            "SHane, Dublin",
            "Shane, DublIn",
    })
    void rejectName(String first, String last){
        Applicant a = new Applicant();
        a.setLastName(last);
        a.setFirstName(first);
        boolean invalid = review.invalidApplicantName(a);
        Assert.assertTrue("first :" + first + " last: " + last,invalid);
    }

    @ParameterizedTest
    @CsvSource({
            "California, 16.99",
            "California, 26.00",
            "Arizona, 24",
            "Arizona, 80.0",
    })
    void rejectStateAge(String state, float age){
        Applicant a = new Applicant();
        a.setState(state);
        a.setAge(age);

        boolean accept = review.instantAcceptAgeState(a);
        Assert.assertFalse("State: " + state + " age: " + age, accept);
    }

    @ParameterizedTest
    @CsvSource({
            "California, 17",
            "California, 24",
            "California, 25.99",
            "Arizona, 80.001",
    })
    void acceptStateAge(String state, float age){
        Applicant a = new Applicant();
        a.setState(state);
        a.setAge(age);

        boolean accept = review.instantAcceptAgeState(a);
        Assert.assertTrue("State: " + state + " age: " + age, accept);
    }

    @ParameterizedTest
    @CsvSource({
            "4.0,4.0",
            "90.00,100",
    })
    void instantAcceptGPA(float grade, float scale){
        Applicant a = new Applicant();
        GPA gpa = new GPA();
        a.setGpa(gpa);
        gpa.setScale(scale);
        gpa.setGrade(grade);

        boolean accept = review.instantAcceptGPA(a);
        Assert.assertTrue("Scale: " + scale + " Grade: " + grade, accept);
    }

    @ParameterizedTest
    @CsvSource({
            "3.5,4.0",
            "89.99,100",
    })
    void instantAcceptGPAFalse(float grade, float scale){
        Applicant a = new Applicant();
        GPA gpa = new GPA();
        a.setGpa(gpa);
        gpa.setScale(scale);
        gpa.setGrade(grade);

        boolean accept = review.instantAcceptGPA(a);
        Assert.assertFalse("Scale: " + scale + " Grade: " + grade, accept);
    }

    @ParameterizedTest
    @CsvSource({
            "1920,27",
            "0,898",
            "2000,0",
    })
    void instantAcceptTest(float sat, float act){
        Applicant a = new Applicant();
        a.setSatScore(sat);
        a.setActScore(act);

        boolean accept = review.instantAcceptTestScore(a);
        Assert.assertTrue("Sat: " + sat + " Act: " + act, accept);
    }

    @ParameterizedTest
    @CsvSource({
            "1919.99,26.99",
            "100, 5",
    })
    void instantAcceptTestFalse(float sat, float act){
        Applicant a = new Applicant();
        a.setSatScore(sat);
        a.setActScore(act);

        boolean accept = review.instantAcceptTestScore(a);
        Assert.assertFalse("Sat: " + sat + " Act: " + act, accept);
    }

    @ParameterizedTest
    @CsvSource({
            "Shane, Dublin, California, true, 19, 3.9, 4.0, 2000, 29, 1," + AutoReview.REJECTION_FELONIES,
            "Shane, Dublin, California, false, 19, 2.0, 4.0, 2000, 29, 1," + AutoReview.REJECTION_GPA,
            "Shane, Dublin, California, false, -19, 3.9, 4.0, 2000, 29, 1," + AutoReview.REJECTION_AGE,
            "Shane, DUblin, California, false, 19, 3.9, 4.0, 2000, 29, 1," + AutoReview.REJECTION_NAME,
    })
    void rejectionReasons(String firstName, String lastName, String state, boolean felonies,
                          float age, float gpaGrade, float gpaScale, float sat, float act, int numberReasons, String reason){
        Applicant a = createApplicant(firstName, lastName, state, felonies, age, gpaGrade, gpaScale, sat, act);
        ApplicantResult result = review.process(a);
        Assert.assertEquals(numberReasons, result.getRejectionReasons().size());
        String s = result.getRejectionReasons().get(0);
        boolean contains = s.contains(reason);
        Assert.assertEquals(true, contains);
    }

    @ParameterizedTest
    @CsvSource({
            "Shane, Dublin, Arizona, false, 29, 3.0, 4.0, 1900, 24, further review",
            "Shane, Dublin, California, false, 24, 3.9, 4.0, 2000, 29, instant accept",
            "Shane, Dublin, Arizona, true, 29, 3.0, 4.0, 1900, 24, instant reject",
    })
    void integrationTest(String firstName, String lastName, String state, boolean felonies,
                           float age, float gpaGrade, float gpaScale, float sat, float act, String expected){

        Applicant a = createApplicant(firstName, lastName, state, felonies, age, gpaGrade, gpaScale, sat, act);

        ApplicantResult result = review.process(a);
        Assert.assertEquals(expected, result.getResult());

    }


    @ParameterizedTest
    @CsvSource({
            "Shane, Dublin, California, false, 24, 3.9, 4.0, 2000, 29, 0",
    })
    void noRejectionReasons(String firstName, String lastName, String state, boolean felonies,
                          float age, float gpaGrade, float gpaScale, float sat, float act, int numberReasons){
        Applicant a = createApplicant(firstName, lastName, state, felonies, age, gpaGrade, gpaScale, sat, act);
        ApplicantResult result = review.process(a);
        Assert.assertEquals(numberReasons, result.getRejectionReasons().size());
    }

    public  static Applicant createApplicant(String firstName, String lastName, String state, boolean felonies,
                             float age, float gpaGrade, float gpaScale, float sat, float act){
        Applicant a = new Applicant();
        a.setFelonies(felonies);
        a.setAge(age);
        a.setState(state);
        GPA gpa = new GPA();
        gpa.setGrade(gpaGrade);
        gpa.setScale(gpaScale);
        a.setGpa(gpa);
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setSatScore(sat);
        a.setActScore(act);
        return a;
    }




}

