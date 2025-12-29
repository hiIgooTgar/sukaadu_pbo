package admin.profile;

import admin.*;
import config.connection;
import masyarakat.pengaduan.*;

import config.userSession;
import config.sessionValidator;

import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

public class form_profile extends javax.swing.JFrame {

    private File fileFoto;
    private String namaFileFoto;

    public form_profile() {
        if (!config.sessionValidator.checkSession(this)) {
            return;
        }
        initComponents();
        initProfile();
    }

    private void initProfile() {
        config.userSession session = config.userSession.getInstance();

        inputEmail.setText(session.getEmail());
        inputPassword.setText(session.getPassword());
        inputNIK.setText(session.getNik());
        inputNama.setText(session.getNama());
        inputTempatLahir.setText(session.getTempatLahir());
        inputTanggalLahir.setDate(session.getTanggalLahir());
        inputRT.setText(session.getRt());
        inputRW.setText(session.getRw());
        inputPekerjaan.setText(session.getPekerjaan());
        inputAgama.setSelectedItem(session.getAgama());
        inputJenisKelamin.setSelectedItem(session.getJenisKelamin());
        inputPernikahan.setSelectedItem(session.getPernikahan());

        String fotoName = session.getImgProfile();
        String path;

        if (fotoName == null || fotoName.isEmpty() || fotoName.equals("default_profile.jpg")) {
            path = "src/uploads/profile/default_profile.jpg";
        } else {
            path = "src/uploads/profile/" + fotoName;
        }

        try {
            ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(profileImage.getWidth(), profileImage.getHeight(), Image.SCALE_SMOOTH));
            profileImage.setIcon(icon);
        } catch (Exception e) {
            profileImage.setIcon(new ImageIcon(new ImageIcon("src/uploads/profile/default_profile.jpg").getImage().getScaledInstance(profileImage.getWidth(), profileImage.getHeight(), Image.SCALE_SMOOTH)));
        }
    }

    private boolean cekEmailDuplikat(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = connection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal cek duplikat email: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        inputTempatLahir = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        inputNIK = new javax.swing.JTextField();
        inputTanggalLahir = new com.toedter.calendar.JDateChooser();
        inputJenisKelamin = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        inputRT = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        inputAgama = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        inputRW = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        profileImage = new javax.swing.JLabel();
        inputPernikahan = new javax.swing.JComboBox<>();
        btnUpdateProfile = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        chooseProfileImage = new javax.swing.JButton();
        inputPassword = new javax.swing.JTextField();
        inputEmail = new javax.swing.JTextField();
        inputPekerjaan = new javax.swing.JTextField();
        inputNama = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        navProfile = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        navDashboard = new javax.swing.JLabel();
        navPengaduan = new javax.swing.JLabel();
        navBerita = new javax.swing.JLabel();
        btn_sign_out = new javax.swing.JButton();
        navUsers = new javax.swing.JLabel();
        navKategori = new javax.swing.JLabel();
        navLaporan = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel8.setText("Tempat Lahir");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel10.setText("Tanggal Lahir");

        inputTempatLahir.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel11.setText("NIK");

        inputNIK.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        inputJenisKelamin.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        inputJenisKelamin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Jenis Kelamin", "Laki-laki", "Perempuan" }));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel12.setText("RT");

        inputRT.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel13.setText("Jenis Kelamin");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setText("Aplikasi Layanan Pengaduan Masyakarakat Desa SukaMaju - SukaAdu");

        inputAgama.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        inputAgama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Agama", "Islam", "Kristen", "Katholik", "Hindu", "Buddha", "Konghucu" }));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel14.setText("Agama");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel15.setText("Status Pernikahan");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel17.setText("RW");

        inputRW.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel18.setText("Pekerjaan");

        profileImage.setBackground(new java.awt.Color(0, 153, 255));
        profileImage.setOpaque(true);

        inputPernikahan.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        inputPernikahan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Status Pernikahaan", "Belum Menikah", "Sudah Menikah" }));

        btnUpdateProfile.setBackground(new java.awt.Color(0, 153, 255));
        btnUpdateProfile.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnUpdateProfile.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateProfile.setText("Ubah Profile");
        btnUpdateProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateProfileActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Email");

        chooseProfileImage.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        chooseProfileImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/akar-icons--file.png"))); // NOI18N
        chooseProfileImage.setText("Pilih Gambar");
        chooseProfileImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseProfileImageActionPerformed(evt);
            }
        });

        inputPassword.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        inputEmail.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        inputPekerjaan.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        inputNama.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel16.setText("Password");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setText("Nama Lengkap");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 23)); // NOI18N
        jLabel1.setText("Profile Admin");

        jPanel2.setBackground(new java.awt.Color(0, 153, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SukaAdu");

        navProfile.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navProfile.setForeground(new java.awt.Color(255, 255, 102));
        navProfile.setText("Profile");
        navProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navProfileMouseClicked(evt);
            }
        });

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/uil--user.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navProfile)
                .addGap(46, 46, 46))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(navProfile)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        navDashboard.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navDashboard.setForeground(new java.awt.Color(255, 255, 255));
        navDashboard.setText("Dashboard");
        navDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navDashboardMouseClicked(evt);
            }
        });

        navPengaduan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navPengaduan.setForeground(new java.awt.Color(255, 255, 255));
        navPengaduan.setText("Pengaduan");
        navPengaduan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navPengaduanMouseClicked(evt);
            }
        });

        navBerita.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navBerita.setForeground(new java.awt.Color(255, 255, 255));
        navBerita.setText("Data Berita");
        navBerita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navBeritaMouseClicked(evt);
            }
        });

        btn_sign_out.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_sign_out.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/mdi--sign-out.png"))); // NOI18N
        btn_sign_out.setText("Logout");
        btn_sign_out.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sign_outActionPerformed(evt);
            }
        });

        navUsers.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navUsers.setForeground(new java.awt.Color(255, 255, 255));
        navUsers.setText("Data Users");
        navUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navUsersMouseClicked(evt);
            }
        });

        navKategori.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navKategori.setForeground(new java.awt.Color(255, 255, 255));
        navKategori.setText("Data Kategori");
        navKategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navKategoriMouseClicked(evt);
            }
        });

        navLaporan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navLaporan.setForeground(new java.awt.Color(255, 255, 255));
        navLaporan.setText("Rekap Laporan");
        navLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navLaporanMouseClicked(evt);
            }
        });

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/clarity--form-line.png"))); // NOI18N

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/ph--users-three.png"))); // NOI18N

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/material-symbols--list-alt-add-outline.png"))); // NOI18N

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/fluent-mdl2--news.png"))); // NOI18N

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/carbon--report.png"))); // NOI18N

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/material-symbols--dashboard-2-gear-outline.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_sign_out, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21))
                                .addComponent(jLabel22))
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(navDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navKategori, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navBerita, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navPengaduan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navDashboard)
                    .addComponent(jLabel25))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(navPengaduan)
                                    .addComponent(jLabel20))
                                .addGap(27, 27, 27)
                                .addComponent(navUsers))
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(31, 31, 31)
                        .addComponent(navKategori))
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navBerita)
                    .addComponent(jLabel23))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navLaporan)
                    .addComponent(jLabel24))
                .addGap(28, 28, 28)
                .addComponent(btn_sign_out)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(profileImage, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chooseProfileImage, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputRT, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputRW, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(inputPekerjaan)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(inputPernikahan, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdateProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(inputEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputNIK)
                                    .addComponent(inputPassword)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel16))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(inputTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(108, 108, 108)
                                        .addComponent(jLabel14)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(inputTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(inputAgama, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(87, 87, 87))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(inputEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(inputNIK, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(2, 101, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(profileImage, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 25, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel14))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(inputTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(inputAgama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(inputTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(12, 12, 12)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel17))
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(inputJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputRT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputRW, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel12)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputPekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(chooseProfileImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUpdateProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputPernikahan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateProfileActionPerformed
        try {
            String email = inputEmail.getText().trim();
            String password = inputPassword.getText().trim();
            String nik = inputNIK.getText().trim();
            String nama = inputNama.getText().trim();
            String tempatLahir = inputTempatLahir.getText().trim();
            java.util.Date tanggalLahir = inputTanggalLahir.getDate();
            String agama = inputAgama.getSelectedItem().toString();
            String jenisKelamin = inputJenisKelamin.getSelectedItem().toString();
            String rt = inputRT.getText().trim();
            String rw = inputRW.getText().trim();
            String pekerjaan = inputPekerjaan.getText().trim();
            String pernikahan = inputPernikahan.getSelectedItem().toString();

            if (email.isEmpty() || nik.isEmpty() || nama.isEmpty()
                    || tanggalLahir == null || rt.isEmpty() || rw.isEmpty() || pekerjaan.isEmpty() || pernikahan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua data wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (nik.length() != 16 || !nik.matches("[0-9]+")) {
                JOptionPane.showMessageDialog(this, "NIK harus tepat 16 digit angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            config.userSession session = config.userSession.getInstance();

            int confirm = JOptionPane.showConfirmDialog(this, "Simpan perubahan profile?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            Connection conn = config.connection.getConnection();
            String sql;
            PreparedStatement ps;
            String finalFileName = (fileFoto != null) ? namaFileFoto : session.getImgProfile();

            if (!password.isEmpty() && !password.equals(session.getPassword())) {
                if (password.length() < 8) {
                    JOptionPane.showMessageDialog(this, "Password baru minimal 8 karakter!");
                    return;
                }

                String passwordHashed = config.hashUtils.hashMD5(password);

                sql = "UPDATE users SET email=?, password=?, nik=?, nama=?, tempat_lahir=?, tanggal_lahir=?, "
                        + "agama=?, jenis_kelamin=?, rt=?, rw=?, pekerjaan=?, pernikahan=?, img_profile=? "
                        + "WHERE id_users=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, passwordHashed);
                ps.setString(3, nik);
                ps.setString(4, nama);
                ps.setString(5, tempatLahir);
                ps.setString(6, new java.text.SimpleDateFormat("yyyy-MM-dd").format(tanggalLahir));
                ps.setString(7, agama);
                ps.setString(8, jenisKelamin);
                ps.setString(9, rt);
                ps.setString(10, rw);
                ps.setString(11, pekerjaan);
                ps.setString(12, pernikahan);
                ps.setString(13, finalFileName);
                ps.setInt(14, session.getIdUsers());
            } else {
                sql = "UPDATE users SET email=?, nik=?, nama=?, tempat_lahir=?, tanggal_lahir=?, "
                        + "agama=?, jenis_kelamin=?, rt=?, rw=?, pekerjaan=?, pernikahan=?, img_profile=? "
                        + "WHERE id_users=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, nik);
                ps.setString(3, nama);
                ps.setString(4, tempatLahir);
                ps.setString(5, new java.text.SimpleDateFormat("yyyy-MM-dd").format(tanggalLahir));
                ps.setString(6, agama);
                ps.setString(7, jenisKelamin);
                ps.setString(8, rt);
                ps.setString(9, rw);
                ps.setString(10, pekerjaan);
                ps.setString(11, pernikahan);
                ps.setString(12, finalFileName);
                ps.setInt(13, session.getIdUsers());
            }

            if (ps.executeUpdate() > 0) {
                if (fileFoto != null) {
                    File dir = new File("src/uploads/profile");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    if (session.getImgProfile() != null && !session.getImgProfile().equals("default_profile.jpg")) {
                        File oldFile = new File(dir + "/" + session.getImgProfile());
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    }
                    java.nio.file.Files.copy(fileFoto.toPath(), new File(dir + "/" + finalFileName).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }

                String sessionPass = (!password.isEmpty()) ? config.hashUtils.hashMD5(password) : session.getPassword();
                session.login(session.getIdUsers(), email, null, nik, nama, tempatLahir, tanggalLahir,
                        agama, jenisKelamin, rt, rw, pekerjaan, pernikahan, session.getRole(), session.getStatus(), finalFileName);
                JOptionPane.showMessageDialog(this, "Profile Berhasil Diperbarui!");
                fileFoto = null;
                initProfile();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateProfileActionPerformed

    private void chooseProfileImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseProfileImageActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();

        if (f != null) {
            fileFoto = f;
            namaFileFoto = "profile_" + System.currentTimeMillis() + "_" + f.getName();
            ImageIcon icon = new ImageIcon(new ImageIcon(f.toString()).getImage().getScaledInstance(profileImage.getWidth(), profileImage.getHeight(), Image.SCALE_SMOOTH));
            profileImage.setIcon(icon);
        }
    }//GEN-LAST:event_chooseProfileImageActionPerformed

    private void navProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navProfileMouseClicked
        try {
            admin.profile.form_profile profileForm = new admin.profile.form_profile();
            profileForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form profile : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navProfileMouseClicked

    private void navDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navDashboardMouseClicked
        try {
            admin.dashboardAdmin dashboardAdmin = new admin.dashboardAdmin();
            dashboardAdmin.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form kategori pengaduan: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navDashboardMouseClicked

    private void navPengaduanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navPengaduanMouseClicked
        try {
            admin.pengaduan.form_pengaduan_data pengaduanForm = new admin.pengaduan.form_pengaduan_data();
            pengaduanForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form data pengaduan : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navPengaduanMouseClicked

    private void navBeritaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navBeritaMouseClicked
        try {
            admin.berita.form_berita beritaForm = new admin.berita.form_berita();
            beritaForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form berita : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navBeritaMouseClicked

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

    private void navUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navUsersMouseClicked
        try {
            admin.users.form_users usersForm = new admin.users.form_users();
            usersForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form users: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navUsersMouseClicked

    private void navKategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navKategoriMouseClicked
        try {
            admin.kategori_pengaduan.form_kategori kategoriForm = new admin.kategori_pengaduan.form_kategori();
            kategoriForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form kategori pengaduan: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navKategoriMouseClicked

    private void navLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navLaporanMouseClicked
        try {
            admin.rekap_laporan.form_rekap_laporan rekapLaporanForm = new admin.rekap_laporan.form_rekap_laporan();
            rekapLaporanForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form rekap laporan : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navLaporanMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_profile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUpdateProfile;
    private javax.swing.JButton btn_sign_out;
    private javax.swing.JButton chooseProfileImage;
    private javax.swing.JComboBox<String> inputAgama;
    private javax.swing.JTextField inputEmail;
    private javax.swing.JComboBox<String> inputJenisKelamin;
    private javax.swing.JTextField inputNIK;
    private javax.swing.JTextField inputNama;
    private javax.swing.JTextField inputPassword;
    private javax.swing.JTextField inputPekerjaan;
    private javax.swing.JComboBox<String> inputPernikahan;
    private javax.swing.JTextField inputRT;
    private javax.swing.JTextField inputRW;
    private com.toedter.calendar.JDateChooser inputTanggalLahir;
    private javax.swing.JTextField inputTempatLahir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel navBerita;
    private javax.swing.JLabel navDashboard;
    private javax.swing.JLabel navKategori;
    private javax.swing.JLabel navLaporan;
    private javax.swing.JLabel navPengaduan;
    private javax.swing.JLabel navProfile;
    private javax.swing.JLabel navUsers;
    private javax.swing.JLabel profileImage;
    // End of variables declaration//GEN-END:variables
}
