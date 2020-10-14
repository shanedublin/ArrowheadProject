package com.rusd.arrowhead;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    /**
     * Needs a file path to run
     * @param args
     */
    public static void main(String[] args) {

        String path = args[0];
        try(Stream<String> stream = Files.lines(Paths.get(path))) {
            String[] objects = stream.map(line -> line.toString()).toArray(String[]::new );
            List<Applicant> list = convertArgs(objects);
            List<ApplicantResult> results = processApplicants(list);
            printResults(results);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static List<Applicant> convertArgs(String[] args) {
        List<Applicant> list = new ArrayList<>();
        for (String line: args) {
            String[] split = line.split(",");
            try {
                Applicant a = new Applicant();
                a.setFirstName(split[0].trim());
                a.setLastName(split[1].trim());
                a.setState(split[2].trim());
                a.setFelonies(Boolean.valueOf(split[3].trim()));
                a.setAge(Float.parseFloat(split[4].trim()));
                a.getGpa().setGrade(Float.parseFloat(split[5].trim()));
                a.getGpa().setScale(Float.parseFloat(split[6].trim()));
                a.setSatScore(Float.parseFloat(split[7].trim()));
                a.setActScore(Float.parseFloat(split[8].trim()));
                list.add(a);

            } catch (Exception e) {
                System.err.println("Invalid format");
                e.printStackTrace();

            }


        }

        return list;
    }

    public static List<ApplicantResult>  processApplicants(List<Applicant> list) {
        AutoReview ar = new AutoReview();
        List<ApplicantResult> resultList = new ArrayList<>();
        list.stream().forEach(applicant -> {
            ApplicantResult res = ar.process(applicant);
            resultList.add(res);
        });
        return resultList;

    }


    public static void printResults(List<ApplicantResult> list){
        list.stream().forEach(res -> System.out.println(res.toString()));
    }
}
