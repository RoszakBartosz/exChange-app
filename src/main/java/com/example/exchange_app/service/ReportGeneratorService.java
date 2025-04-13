package com.example.exchange_app.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportGeneratorService {

    public static void generateCsvReport(List<String[]> data, String filePath) {
//        File file = new File(filePath);
//        File parentDir = file.getParentFile();
//
//        if (parentDir != null && !parentDir.exists()) {
//            parentDir.mkdirs(); // tworzy foldery jeśli nie istnieją
//        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("currency,mid,amount");
            writer.newLine();

            for (String[] row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }

            System.out.println("Raport zapisany: " + filePath); // podpowiedź gdzie
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

