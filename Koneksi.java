import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Koneksi {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            createDatabaseAndTable(connection);
            connection = DriverManager.getConnection(URL + "pbo_quizit", USER, PASSWORD);
            System.out.println("Koneksi ke database       : Berhasil");
            return connection;
        } catch (SQLException e) {
            System.err.println("Koneksi ke database       : Gagal (" + e.getMessage() + ")");
            return null;
        }
    }
    private static void createDatabaseAndTable(Connection connection) {
        String createDatabase = "CREATE DATABASE IF NOT EXISTS pbo_quizit";
        String createTableSoal = """
                CREATE TABLE IF NOT EXISTS soal (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    pertanyaan VARCHAR(255) NOT NULL,
                    pilihan_A VARCHAR(255) NOT NULL,
                    pilihan_B VARCHAR(255) NOT NULL,
                    pilihan_C VARCHAR(255) NOT NULL,
                    pilihan_D VARCHAR(255) NOT NULL,
                    jawaban_benar VARCHAR(255) NOT NULL,
                    kategori VARCHAR(20) NOT NULL
                );
                """;
        String createTablePemain = """
                CREATE TABLE IF NOT EXISTS pemain (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nama VARCHAR(100) NOT NULL,
                    skor_terakhir INT DEFAULT 0,
                    skor_tertinggi INT DEFAULT 0,
                    soal_terjawab INT DEFAULT 0,
                    soal_benar INT DEFAULT 0,
                    soal_salah INT DEFAULT 0,
                    waktu_selesai TIME DEFAULT NULL
                );
                """;
        try (Statement statement = connection.createStatement()) {
            statement.execute(createDatabase);
            statement.execute("USE pbo_quizit");
            statement.execute(createTableSoal);
            statement.execute(createTablePemain);
            System.out.println("Membuat Database/Tabel    : Berhasil");
        } catch (SQLException e) {
            System.err.println("Membuat Database/Tabel    : Gagal/Sudah ada (" + e.getMessage() + ")");
        }
    }
}