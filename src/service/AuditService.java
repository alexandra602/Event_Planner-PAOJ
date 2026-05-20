package service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    // instanța unica a clasei (Singleton)
    private static AuditService instance;

    // fișierul unde se vor salva datele
    private final String FILE_PATH = "audit.csv";

    // formatul pentru data si ora
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private AuditService() {}

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction(String actionName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.println(actionName + "," + timestamp);
        } catch (IOException e) {
            System.out.println("Eroare la scrierea in fisierul de audit: " + e.getMessage());
        }
    }
}