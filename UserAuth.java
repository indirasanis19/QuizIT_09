import java.sql.*;
import javax.swing.*;

public class UserAuth {

    private static Connection connectToDB() {
        return Koneksi.getConnection();
    }

    public static boolean register(String username, String password, String password2) {
        if (!password.equals(password2)) {
            JOptionPane.showMessageDialog(null, "Password tidak sama!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try (Connection conn = connectToDB()) {
            if (conn == null) return false;

            String checkUserQuery = "SELECT username FROM users WHERE username = ?";
            PreparedStatement checkUserStmt = conn.prepareStatement(checkUserQuery);
            checkUserStmt.setString(1, username);
            ResultSet rs = checkUserStmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Username is already registered!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            String insertUserQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement insertUserStmt = conn.prepareStatement(insertUserQuery);
            insertUserStmt.setString(1, username);
            insertUserStmt.setString(2, password);
            insertUserStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registration successfull!", "Success", JOptionPane.INFORMATION_MESSAGE);

            String insertPlayerQuery = "INSERT INTO pemain (nama, skor_terakhir, skor_tertinggi) VALUES (?, 0, 0)";
            PreparedStatement insertPlayerStmt = conn.prepareStatement(insertPlayerQuery);
            insertPlayerStmt.setString(1, username);
            insertPlayerStmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean login(String username, String password) {
        try (Connection conn = connectToDB()) {
            if (conn == null) return false;

            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (password.equals(storedPassword)) {
                    return true;
                } 
            }

            JOptionPane.showMessageDialog(null, "Incorrect username/password", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object[] getPlayerProfile(String username) {
        try (Connection conn = connectToDB()) {
            if (conn == null) return null;
            String query = """
                    SELECT p.nama, p.skor_terakhir, p.skor_tertinggi
                    FROM pemain p
                    WHERE p.nama = ?
                    """;
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                String name = rs.getString("nama");
                int lastScore = rs.getInt("skor_terakhir");
                int highScore = rs.getInt("skor_tertinggi");
                return new Object[]{name, lastScore, highScore};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }    

    public static void updatePlayerStats(String username, int skorTerakhir, int soalTerjawab, int soalBenar) {
        try (Connection conn = connectToDB()) {
            if (conn == null) return;

            String query = "SELECT skor_tertinggi FROM pemain WHERE nama = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int skorTertinggi = rs.getInt("skor_tertinggi");
                int skorBaru = Math.max(skorTertinggi, skorTerakhir);

                String updateQuery = "UPDATE pemain SET skor_terakhir = ?, skor_tertinggi = ?, soal_terjawab = soal_terjawab + ?, soal_benar = soal_benar + ? WHERE nama = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, skorTerakhir);
                updateStmt.setInt(2, skorBaru);
                updateStmt.setInt(3, soalTerjawab);
                updateStmt.setInt(4, soalBenar);
                updateStmt.setString(5, username);

                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

