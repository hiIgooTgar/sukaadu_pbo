package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class connection {
    private static Connection conn;
    
    public static Connection getConnection() {
        if (conn == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/sukaadu_pbo"; 
                String user = "root"; 
                String password = "";
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Koneksi Database Gagal: Cek XAMPP/MySQL dan konfigurasi: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                conn = null; // Pastikan conn tetap null
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Driver MySQL tidak ditemukan. Pastikan MySQL Connector/J sudah ditambahkan ke Libraries.", "Error Driver", JOptionPane.ERROR_MESSAGE);
                conn = null;
                System.exit(0); 
            }
        }
        return conn;
    }
    
    public static void main(String args[]) {
        System.out.println("Memulai uji koneksi...");
        if (getConnection() != null) {
            System.out.println("Koneksi berhasil!");
            // Secara opsional, tutup koneksi setelah pengujian:
            try {
                conn.close();
                System.out.println("Koneksi ditutup.");
            } catch (SQLException e) {
                System.out.println("Gagal menutup koneksi: " + e.getMessage());
            }
        } else {
            System.out.println("Koneksi gagal. Lihat jendela pesan error untuk detail.");
        }
    }
}