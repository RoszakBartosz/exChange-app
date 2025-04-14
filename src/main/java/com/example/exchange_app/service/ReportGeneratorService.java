package com.example.exchange_app.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportGeneratorService {

        public static void generateCsvReport(List<String[]> data, String filePath) {
            File file = new File(filePath);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Zapisz nagłówki (opcjonalnie)
                writer.write("currency,code,mid,amount");  // Zmień na własne kolumny
                writer.newLine();

                // Zapisz dane
                for (String[] row : data) {
                    writer.write(String.join(",", row));  // Łączenie danych w jedno wiersze CSV
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

