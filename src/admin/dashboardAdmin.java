package admin;

import config.sessionValidator;
import config.userSession;

import admin.*;
import auth.signInPage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class dashboardAdmin extends javax.swing.JFrame {

    public dashboardAdmin() {
        if (!config.sessionValidator.checkSession(this)) {
            return;
        }
        initComponents();
        tampilkanNamaPengguna();
        countData();
    }

    private void tampilkanNamaPengguna() {
        String nama = userSession.getInstance().getNama();
        label_name.setText("Hallo, Selamat Datang " + nama);
    }

    private void countData() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = config.connection.getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM users");
            if (rs.next()) {
                int totalUsers = rs.getInt("total");
                countDataUsers.setText(String.valueOf(totalUsers));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM pengaduan");
            if (rs.next()) {
                int totalPengaduan = rs.getInt("total");
                countDataPengaduan.setText(String.valueOf(totalPengaduan));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM kategori_pengaduan");
            if (rs.next()) {
                int totalKategori = rs.getInt("total");
                countDataKategoriPengaduan.setText(String.valueOf(totalKategori));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM berita");
            if (rs.next()) {
                int totalBerita = rs.getInt("total");
                countDataBerita.setText(String.valueOf(totalBerita));
            }
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menghitung data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_name = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        navDashboard = new javax.swing.JLabel();
        navPengaduan = new javax.swing.JLabel();
        navBerita = new javax.swing.JLabel();
        btn_sign_out = new javax.swing.JButton();
        navUsers = new javax.swing.JLabel();
        navKategori = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        countDataUsers = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        countDataPengaduan = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        countDataKategoriPengaduan = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        countDataBerita = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        label_name.setFont(new java.awt.Font("Tahoma", 0, 21)); // NOI18N
        label_name.setText("Hallo, Selamat Datang");

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SukaAdu");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Profile");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 866, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(46, 46, 46))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        jLabel7.setText("Sistem Pengaduan Masyarakat - SukaAdu");

        jLabel8.setText("SukaAdu adalah aplikasi pelayanan pengaduan masyarakat yang memudahkan");

        jLabel10.setText("warga melaporkan masalah lingkungan, administrasi, dan layanan publik secara cepat,");

        jLabel11.setText("terpantau, serta transparan melalui platform digital yang mudah digunakan");

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        navDashboard.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navDashboard.setForeground(new java.awt.Color(255, 255, 51));
        navDashboard.setText("Dashboard");
        navDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navDashboardMouseClicked(evt);
            }
        });

        navPengaduan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navPengaduan.setForeground(new java.awt.Color(255, 255, 255));
        navPengaduan.setText("Pengaduan");

        navBerita.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navBerita.setForeground(new java.awt.Color(255, 255, 255));
        navBerita.setText("Berita");

        btn_sign_out.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_sign_out.setText("Logout");
        btn_sign_out.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sign_outActionPerformed(evt);
            }
        });

        navUsers.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navUsers.setForeground(new java.awt.Color(255, 255, 255));
        navUsers.setText("Data Users");

        navKategori.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navKategori.setForeground(new java.awt.Color(255, 255, 255));
        navKategori.setText("Data Kategori");
        navKategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navKategoriMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_sign_out, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(navDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(navPengaduan)
                                    .addComponent(navBerita)
                                    .addComponent(navUsers, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                    .addComponent(navKategori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 2, Short.MAX_VALUE)))))
                .addGap(37, 37, 37))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(navDashboard)
                .addGap(32, 32, 32)
                .addComponent(navPengaduan)
                .addGap(33, 33, 33)
                .addComponent(navUsers)
                .addGap(37, 37, 37)
                .addComponent(navKategori)
                .addGap(31, 31, 31)
                .addComponent(navBerita)
                .addGap(39, 39, 39)
                .addComponent(btn_sign_out)
                .addContainerGap(445, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(51, 153, 255));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Data Users");

        countDataUsers.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        countDataUsers.setForeground(new java.awt.Color(255, 255, 255));
        countDataUsers.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(countDataUsers))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(countDataUsers)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(51, 153, 255));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Data Pengaduan");

        countDataPengaduan.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        countDataPengaduan.setForeground(new java.awt.Color(255, 255, 255));
        countDataPengaduan.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(countDataPengaduan))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(countDataPengaduan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(51, 153, 255));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Data Kategori Pengaduan");

        countDataKategoriPengaduan.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        countDataKategoriPengaduan.setForeground(new java.awt.Color(255, 255, 255));
        countDataKategoriPengaduan.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(countDataKategoriPengaduan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(countDataKategoriPengaduan)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(51, 153, 255));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Data Berita");

        countDataBerita.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        countDataBerita.setForeground(new java.awt.Color(255, 255, 255));
        countDataBerita.setText("0");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(countDataBerita))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(countDataBerita)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_name)
                    .addComponent(jLabel11)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addComponent(label_name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel10)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_sign_outActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sign_outActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin Logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            config.userSession.getInstance().logout();
            try {
                auth.signInPage loginPage = new auth.signInPage();
                loginPage.setVisible(true);
                this.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Gagal redirect ke halaman login: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_sign_outActionPerformed

    private void navDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navDashboardMouseClicked
        try {
            admin.dashboardAdmin dashboardAdmin = new admin.dashboardAdmin();
            dashboardAdmin.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form kategori pengaduan: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navDashboardMouseClicked

    private void navKategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navKategoriMouseClicked
        try {
            admin.kategori_pengaduan.form_kategori kategoriForm = new admin.kategori_pengaduan.form_kategori();
            kategoriForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form kategori pengaduan: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navKategoriMouseClicked
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboardAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_sign_out;
    private javax.swing.JLabel countDataBerita;
    private javax.swing.JLabel countDataKategoriPengaduan;
    private javax.swing.JLabel countDataPengaduan;
    private javax.swing.JLabel countDataUsers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel label_name;
    private javax.swing.JLabel navBerita;
    private javax.swing.JLabel navDashboard;
    private javax.swing.JLabel navKategori;
    private javax.swing.JLabel navPengaduan;
    private javax.swing.JLabel navUsers;
    // End of variables declaration//GEN-END:variables
}
