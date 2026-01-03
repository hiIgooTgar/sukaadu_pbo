package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class connection {
    public static Connection getConnection() throws SQLException { 
        String url = "jdbc:mysql://localhost:3306/sukaadu_pbo"; 
        String user = "root"; 
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver MySQL tidak ditemukan. Pastikan MySQL Connector/J sudah ditambahkan ke Libraries.", "Error Driver", JOptionPane.ERROR_MESSAGE);
            System.exit(0); 
            return null; 
        } 
    }
    
    public static void main(String args[]) {
        System.out.println("Memulai uji koneksi...");
        Connection testConn = null;
        try {
            testConn = getConnection();
            if (testConn != null) {
                System.out.println("Koneksi berhasil!");
                testConn.close(); 
                System.out.println("Koneksi ditutup.");
            } else {
                System.out.println("Koneksi gagal. Lihat jendela pesan error untuk detail.");
            }
        } catch (SQLException e) {
             System.out.println("Koneksi gagal: " + e.getMessage());
        }
    }
}