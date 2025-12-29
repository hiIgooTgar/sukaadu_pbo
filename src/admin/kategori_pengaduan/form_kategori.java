package admin.kategori_pengaduan;

import config.connection;

import config.userSession;
import config.sessionValidator;

import admin.*;
import auth.*;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class form_kategori extends javax.swing.JFrame {

    public form_kategori() {
        if (!config.sessionValidator.checkSession(this)) {
            return;
        }
        initComponents();
        search_table("");
    }

    private void search_table(String keyword) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Nama Kategori");
        model.addColumn("Deskripsi");

        tabelKategoriPengaduan.setModel(model);

        try {
            int no = 1;
            String sql = "SELECT * FROM kategori_pengaduan WHERE nama_kategori LIKE ? OR deskripsi LIKE ? ORDER BY id_kategori_pengaduan ASC";
            try (Connection conn = config.connection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        model.addRow(new Object[]{
                            no++,
                            rs.getString("nama_kategori"),
                            rs.getString("deskripsi")
                        });
                    }
                }
            }
            tabelKategoriPengaduan.setModel(model);
            tabelKategoriPengaduan.setRowHeight(28);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data kategori: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        inputNamaKategori.setText("");
        textAreaDeskripsi.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        inputNamaKategori = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelKategoriPengaduan = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        inputSearching = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textAreaDeskripsi = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        navProfile = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        navDashboard = new javax.swing.JLabel();
        navPengaduan = new javax.swing.JLabel();
        navBerita = new javax.swing.JLabel();
        btn_sign_out = new javax.swing.JButton();
        navUsers = new javax.swing.JLabel();
        navKategori = new javax.swing.JLabel();
        navLaporan = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btnReportKategori = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel1.setText("Nama Kategori");

        inputNamaKategori.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        tabelKategoriPengaduan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "No", "Nama Kategori", "Deskripsi"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tabelKategoriPengaduan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelKategoriPengaduanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelKategoriPengaduan);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Deskripsi Kategori");

        btnDelete.setBackground(new java.awt.Color(0, 153, 255));
        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Hapus");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(0, 153, 255));
        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Tambah");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 153, 255));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Ubah");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 153, 255));
        btnClear.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        inputSearching.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        inputSearching.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputSearchingKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel8.setText("Data Kategori Pengaduan");

        textAreaDeskripsi.setColumns(20);
        textAreaDeskripsi.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        textAreaDeskripsi.setRows(5);
        jScrollPane3.setViewportView(textAreaDeskripsi);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Aplikasi Layanan Pengaduan Masyakarakat Desa SukaMaju - SukaAdu");

        jPanel2.setBackground(new java.awt.Color(0, 153, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("SukaAdu");

        navProfile.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navProfile.setForeground(new java.awt.Color(255, 255, 255));
        navProfile.setText("Profile");
        navProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navProfileMouseClicked(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/uil--user.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navProfile)
                .addGap(46, 46, 46))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
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
        navKategori.setForeground(new java.awt.Color(255, 255, 51));
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

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/clarity--form-line.png"))); // NOI18N

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/ph--users-three.png"))); // NOI18N

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/material-symbols--list-alt-add-outline.png"))); // NOI18N

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/fluent-mdl2--news.png"))); // NOI18N

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/carbon--report.png"))); // NOI18N

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/material-symbols--dashboard-2-gear-outline.png"))); // NOI18N

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
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18))
                                .addComponent(jLabel19))
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING))
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
                    .addComponent(jLabel22))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(navPengaduan)
                                    .addComponent(jLabel17))
                                .addGap(27, 27, 27)
                                .addComponent(navUsers))
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(31, 31, 31)
                        .addComponent(navKategori))
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navBerita)
                    .addComponent(jLabel20))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navLaporan)
                    .addComponent(jLabel21))
                .addGap(28, 28, 28)
                .addComponent(btn_sign_out)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnReportKategori.setBackground(new java.awt.Color(255, 0, 51));
        btnReportKategori.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnReportKategori.setForeground(new java.awt.Color(255, 255, 255));
        btnReportKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/akar-icons--file-white.png"))); // NOI18N
        btnReportKategori.setText("PDF");
        btnReportKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportKategoriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(inputNamaKategori)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(202, 202, 202)
                                .addComponent(btnReportKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inputSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel8)
                    .addComponent(jLabel3))
                .addGap(74, 74, 74))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReportKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputNamaKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 52, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String namaKategori = inputNamaKategori.getText().trim();
        String deskripsiKategori = textAreaDeskripsi.getText().trim();

        if (namaKategori.isEmpty() && deskripsiKategori.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama kategori dan Deskripsi kategori harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (namaKategori.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama kategori harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (namaKategori.isEmpty() && deskripsiKategori.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Deskripsi kategori harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "INSERT INTO kategori_pengaduan (nama_kategori, deskripsi) VALUES (?, ?)";

        try (Connection conn = connection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaKategori);
            ps.setString(2, deskripsiKategori);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data kategori pengaduan berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                search_table("");
                clearForm();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String namaKategori = inputNamaKategori.getText().trim();
        String deskripsiKategori = textAreaDeskripsi.getText().trim();

        int baris = tabelKategoriPengaduan.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data yang ingin diubah terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idKategori = tabelKategoriPengaduan.getModel().getValueAt(baris, 1).toString();

        if (namaKategori.isEmpty() && deskripsiKategori.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama kategori dan Deskripsi kategori harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (namaKategori.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama kategori harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        } else if (deskripsiKategori.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Deskripsi kategori harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE kategori_pengaduan SET nama_kategori = ?, deskripsi = ? WHERE nama_kategori = ?";

        try (Connection conn = connection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaKategori);
            ps.setString(2, deskripsiKategori);
            ps.setString(3, idKategori);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data kategori pengaduan berhasil diubah.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                search_table("");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengubah data. ID kategori pengaduan tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int baris = tabelKategoriPengaduan.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data yang ingin dihapus terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idKategori = tabelKategoriPengaduan.getModel().getValueAt(baris, 1).toString();
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus kategori pengaduan : " + tabelKategoriPengaduan.getModel().getValueAt(baris, 2).toString() + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {

            String sql = "DELETE FROM kategori_pengaduan WHERE nama_kategori = ?";

            try (Connection conn = connection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, idKategori);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Data kategori pengaduan berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    search_table("");
                    clearForm();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tabelKategoriPengaduanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelKategoriPengaduanMouseClicked
        int row = tabelKategoriPengaduan.rowAtPoint(evt.getPoint());

        String namaKategori = tabelKategoriPengaduan.getModel().getValueAt(row, 1).toString();
        String deskripsiKategori = tabelKategoriPengaduan.getModel().getValueAt(row, 2).toString();

        inputNamaKategori.setText(namaKategori);
        textAreaDeskripsi.setText(deskripsiKategori);
    }//GEN-LAST:event_tabelKategoriPengaduanMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        inputNamaKategori.setText("");
        textAreaDeskripsi.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void inputSearchingKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputSearchingKeyReleased
        String keyword = inputSearching.getText().trim();
        if (keyword.isEmpty()) {
            search_table("");
        } else {
            search_table(keyword);
        }
    }//GEN-LAST:event_inputSearchingKeyReleased

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

    private void btnReportKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportKategoriActionPerformed
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setDialogTitle("Save Laporan Data Kategori Pengaduan");
        if (chooser.showSaveDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".pdf")) {
                path += ".pdf";
            }

            laporanGeneratorKategori.cetakLaporan((DefaultTableModel) tabelKategoriPengaduan.getModel(), path);
            javax.swing.JOptionPane.showMessageDialog(this, "Laporan Data Kategori Pengaduan PDF Berhasil Dibuat!");
        }
    }//GEN-LAST:event_btnReportKategoriActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_kategori().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReportKategori;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btn_sign_out;
    private javax.swing.JTextField inputNamaKategori;
    private javax.swing.JTextField inputSearching;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel navBerita;
    private javax.swing.JLabel navDashboard;
    private javax.swing.JLabel navKategori;
    private javax.swing.JLabel navLaporan;
    private javax.swing.JLabel navPengaduan;
    private javax.swing.JLabel navProfile;
    private javax.swing.JLabel navUsers;
    private javax.swing.JTable tabelKategoriPengaduan;
    private javax.swing.JTextArea textAreaDeskripsi;
    // End of variables declaration//GEN-END:variables

}
