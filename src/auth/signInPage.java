package auth;

import config.connection;
import config.hashUtils;

import java.sql.*;
import javax.swing.JOptionPane;

import admin.dashboardAdmin;
import masyarakat.dashboardMasyarakat;
import auth.signUpPage;

public class signInPage extends javax.swing.JFrame {

    public signInPage() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        inputEmail = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        inputPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        btnSignIn = new javax.swing.JButton();
        hrefSignUp = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Sign In - SukaAdu");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(328, 328, 328))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(47, 47, 47))
        );

        jLabel3.setText("Email");

        jLabel4.setText("Password");

        btnSignIn.setBackground(new java.awt.Color(255, 255, 255));
        btnSignIn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSignIn.setText("Sign In");
        btnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignInActionPerformed(evt);
            }
        });

        hrefSignUp.setText("Belum punya akun? Sign Up sekarang");
        hrefSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hrefSignUpMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(inputEmail)
                            .addComponent(inputPassword)
                            .addComponent(btnSignIn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(263, 263, 263))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(hrefSignUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(361, 361, 361))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(btnSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(hrefSignUp)
                .addGap(0, 103, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignInActionPerformed
        String emailInput = inputEmail.getText();
        String passwordInput = new String(inputPassword.getPassword());

        if (emailInput.isEmpty() && passwordInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email dan Password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (emailInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (passwordInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String hashedInputPassword = config.hashUtils.hashMD5(passwordInput);
        Connection conn = connection.getConnection();
        String sql = "SELECT * FROM users WHERE email = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emailInput);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPasswordDB = rs.getString("password");
                    if (hashedInputPassword.equals(hashedPasswordDB)) {
                        String role = rs.getString("role");
                        String namaUsers = rs.getString("nama");
                        
                        if (role.equals("admin")) {
                            JOptionPane.showMessageDialog(this, "Selamat datang, " + namaUsers, "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);
                            admin.dashboardAdmin dashboardAdmin = new admin.dashboardAdmin();
                            dashboardAdmin.setVisible(true);
                            this.dispose();
                        } else if (role.equals("masyarakat")) {
                            JOptionPane.showMessageDialog(this, "Selamat datang, " + namaUsers, "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);
                            masyarakat.dashboardMasyarakat dashboardMasyarakat = new masyarakat.dashboardMasyarakat();
                            dashboardMasyarakat.setVisible(true);
                            this.dispose();

                        } else {
                            JOptionPane.showMessageDialog(this, "Role pengguna tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Email atau Password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
                        inputEmail.setText("");
                        inputPassword.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Email atau Password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
                    inputEmail.setText("");
                    inputPassword.setText("");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSignInActionPerformed

    private void hrefSignUpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hrefSignUpMouseClicked
        try {
            auth.signUpPage signUpPage = new auth.signUpPage();
            signUpPage.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form pendaftaran: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_hrefSignUpMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new signInPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSignIn;
    private javax.swing.JLabel hrefSignUp;
    private javax.swing.JTextField inputEmail;
    private javax.swing.JPasswordField inputPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
