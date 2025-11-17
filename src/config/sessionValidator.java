package config;

import auth.signInPage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class sessionValidator {
    public static boolean checkSession(JFrame currentFrame) {
        userSession session = userSession.getInstance();
        if (session.getIdUsers() == 0) {
            JOptionPane.showMessageDialog(currentFrame, 
                    "Anda harus login terlebih dahulu untuk mengakses halaman ini.", 
                    "Akses Ditolak", 
                    JOptionPane.WARNING_MESSAGE);
            try {
                signInPage loginPage = new signInPage();
                loginPage.setVisible(true);
                currentFrame.dispose(); 
            } catch (Exception e) {
                System.err.println("Gagal redirect ke login: " + e.getMessage());
            }
            return false;
        }
        return true; 
    }
}