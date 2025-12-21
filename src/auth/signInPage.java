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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        inputEmail = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        inputPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        btnSignIn = new javax.swing.JButton();
        hrefSignUp = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Selamat Datang di ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Masuk ke SukaAdu untuk mengirim pengaduan masyarakat");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("secara cepat, mudah, aman, dan transparan yang tepat.");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("SukaAdu - Sukamaju");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(1, 1, 1)
                .addComponent(jLabel9)
                .addGap(26, 26, 26)
                .addComponent(jLabel5)
                .addGap(1, 1, 1)
                .addComponent(jLabel6)
                .addGap(24, 24, 24))
        );

        inputEmail.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Email");

        inputPassword.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel4.setText("Password");

        btnSignIn.setBackground(new java.awt.Color(51, 153, 255));
        btnSignIn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnSignIn.setForeground(new java.awt.Color(255, 255, 255));
        btnSignIn.setText("Sign In");
        btnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignInActionPerformed(evt);
            }
        });

        hrefSignUp.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        hrefSignUp.setText("Belum punya akun? Sign Up sekarang");
        hrefSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hrefSignUpMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("*");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("*");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(256, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(hrefSignUp)
                        .addGap(352, 352, 352))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(1, 1, 1)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(1, 1, 1)
                                .addComponent(jLabel8))
                            .addComponent(inputPassword)
                            .addComponent(inputEmail)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(248, 248, 248))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(inputEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(btnSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(hrefSignUp)
                .addGap(104, 104, 104))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignInActionPerformed
        String emailInput = inputEmail.getText();
        String passwordInput = new String(inputPassword.getPassword());

        if (emailInput.isEmpty() && passwordInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email dan Password harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (emailInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (passwordInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!config.validasiEmail.isValidEmail(emailInput)) {
            JOptionPane.showMessageDialog(this, "Format email tidak valid (contoh: user@domain.com).", "Peringatan", JOptionPane.WARNING_MESSAGE);
            inputEmail.setText("");
            return;
        }

        String hashedInputPassword = config.hashUtils.hashMD5(passwordInput);
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = connection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emailInput);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPasswordDB = rs.getString("password");
                    
                    if (hashedInputPassword.equals(hashedPasswordDB)) {
                        int idUsers = rs.getInt("id_users");
                        String emailUsers = rs.getString("email");
                        String passwordUsers = rs.getString("password");
                        String nikUsers = rs.getString("nik");
                        String namaUsers = rs.getString("nama");
                        String tempatLahirUsers = rs.getString("tempat_lahir");
                        Date tanggalLahirUsers = rs.getDate("tanggal_lahir");
                        String agamaUsers = rs.getString("agama");
                        String jenisKelaminUsers = rs.getString("jenis_kelamin");
                        String rtUsers = rs.getString("rt");
                        String rwUsers = rs.getString("rw");
                        String pekerjaanUsers = rs.getString("pekerjaan");
                        String pernikahaanUsers = rs.getString("pernikahan");
                        String role = rs.getString("role");
                        int statusAccount = rs.getInt("status");

                        config.userSession.getInstance().login(idUsers, emailUsers, passwordUsers, nikUsers, namaUsers, tempatLahirUsers, 
                                tanggalLahirUsers, agamaUsers, jenisKelaminUsers, rtUsers, rwUsers, pekerjaanUsers, pernikahaanUsers, role, statusAccount);
                        
                        if (statusAccount == 1) {
                            if (role.equalsIgnoreCase("admin")) { 
                                JOptionPane.showMessageDialog(this, "Selamat datang, " + namaUsers, "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);
                                admin.dashboardAdmin dashboardAdmin = new admin.dashboardAdmin();
                                dashboardAdmin.setVisible(true);
                                this.dispose();
                            } else if (role.equalsIgnoreCase("masyarakat")) {
                                JOptionPane.showMessageDialog(this, "Selamat datang, " + namaUsers, "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);
                                masyarakat.dashboardMasyarakat dashboardMasyarakat = new masyarakat.dashboardMasyarakat();
                                dashboardMasyarakat.setVisible(true);
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(this, "Role pengguna tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
                                inputPassword.setText("");
                                config.userSession.getInstance().logout();
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Akun Anda saat ini dinonaktifkan. Silakan hubungi Administrator.", "Login Gagal: Akun Non-aktif", JOptionPane.WARNING_MESSAGE);
                            inputPassword.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Email atau Password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
                        inputPassword.setText(""); 
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Email atau Password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
                    inputPassword.setText(""); 
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan database: Cek XAMPP dan koneksi.java. Detail: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Terjadi kesalahan sistem saat login: " + e.getMessage(), "Error Sistem", JOptionPane.ERROR_MESSAGE);
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
