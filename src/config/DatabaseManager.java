package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    // datele de conectare la oracle
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER = "event_planner";
    private static final String PASSWORD = "planner123";

    // instanta unica a conexiunii (Singleton)
    private static Connection connection;

    private DatabaseManager() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // incarcam driverul
                Class.forName("oracle.jdbc.driver.OracleDriver");

                // realizam conexiunea la baza de date
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                // System.out.println("   [!] Sistem: Conexiunea la Oracle a fost stabilita cu succes!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("   [!] EROARE: Driverul JDBC nu a fost gasit!");
        } catch (SQLException e) {
            System.out.println("   [!] EROARE la conectarea bazei de date: " + e.getMessage());
        }
        return connection;
    }

    // metoda pentru a inchide conexiunea la final
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("   [!] Sistem: Conexiunea la Oracle a fost inchisa.");
            }
        } catch (SQLException e) {
            System.out.println("   [!] EROARE la inchiderea conexiunii: " + e.getMessage());
        }
    }
}