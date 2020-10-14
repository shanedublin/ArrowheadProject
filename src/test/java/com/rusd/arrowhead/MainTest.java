package com.rusd.arrowhead;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.rusd.arrowhead.AutoReviewTest.createApplicant;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testPrint(){
        List<ApplicantResult> list = new ArrayList<>();
        Applicant a = new Applicant();
        a.setFirstName("Shane");
        a.setLastName("Dublin");
        ApplicantResult ar = new ApplicantResult();
        ar.setApplicant(a);
        ar.setResult(AutoReview.INSTANT_ACCEPT);
        list.add(ar);

        Main.printResults(list);
    }

    @Test
    void testRejection(){
        List<ApplicantResult> list = new ArrayList<>();
        Applicant a = new Applicant();
        a.setFirstName("Shane");
        a.setLastName("dOOBLIN");

        ApplicantResult ar = new ApplicantResult();
        ar.setApplicant(a);
        ar.setResult(AutoReview.INSTANT_REJECT);
        ar.getRejectionReasons().add("Name Bad");
        list.add(ar);

        Main.printResults(list);
    }

    @Test
    void FullTest(){
        Applicant a1 = createApplicant("Shane", "Dublin", "California", false, 19, 3.9f, 4.0f, 2000, 29);
        Applicant a2 = createApplicant("Shane", "Dublin","Arizona", false, 19, 3.9f, 4.0f, 2000, 29);
        Applicant a3 = createApplicant("SHANE", "Dublin", "Arizona", true, -19, 0.9f, 4.0f, 2000, 29);
        List<Applicant> list = new ArrayList<>();
        list.add(a1);
        list.add(a2);
        list.add(a3);
        List<ApplicantResult> results = Main.processApplicants(list);
        assertEquals(3, results.size());
        Main.printResults(results);

    }

    @Test
    void testMain(){
        Main.main(new String[]{"src/test/resources/TestFile.csv"});
    }


}