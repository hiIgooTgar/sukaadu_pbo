package admin.pengaduan;

import admin.kategori_pengaduan.*;
import config.connection;

import config.userSession;
import config.sessionValidator;

import admin.*;
import auth.*;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class form_pengaduan_data extends javax.swing.JFrame {

    public form_pengaduan_data() {
        if (!config.sessionValidator.checkSession(this)) {
            return;
        }
        initComponents();
        tampilDataAdmin("");
    }

    public void tampilDataAdmin(String keyword) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 9 || column == 10;
            }
        };

        model.addColumn("No");
        model.addColumn("ID"); 
        model.addColumn("Tanggal");
        model.addColumn("Nama Pengadu");
        model.addColumn("Judul");
        model.addColumn("Kategori"); 
        model.addColumn("Status");
        model.addColumn("Tanggapan");
        model.addColumn("Foto"); 
        model.addColumn("Aksi");
        model.addColumn("Detail");

        try {
            String sql = "SELECT p.*, u.nama, k.nama_kategori, t.isi_tanggapan "
                    + "FROM pengaduan p "
                    + "JOIN users u ON p.id_users = u.id_users "
                    + "JOIN kategori_pengaduan k ON p.id_kategori_pengaduan = k.id_kategori_pengaduan "
                    + "LEFT JOIN tanggapan t ON p.id_pengaduan = t.id_pengaduan "
                    + "WHERE p.judul_pengaduan LIKE ? OR u.nama LIKE ? "
                    + "ORDER BY p.id_pengaduan DESC";

            Connection conn = config.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            int no = 1;
            while (rs.next()) {
                String status = rs.getString("status").toLowerCase();
                String labelAksi = status.equals("belum") ? "Konfirmasi"
                        : status.equals("proses") ? "Tanggapi" : status.toUpperCase();

                model.addRow(new Object[]{
                    no++,
                    rs.getString("id_pengaduan"),
                    rs.getString("tgl_pegaduan"),
                    rs.getString("nama"),
                    rs.getString("judul_pengaduan"),
                    rs.getString("nama_kategori"), 
                    status.toUpperCase(),
                    rs.getString("isi_tanggapan") == null ? "-" : rs.getString("isi_tanggapan"),
                    rs.getString("foto_pengaduan"),
                    labelAksi,
                    "Show"
                });
            }
            tabelDataPengaduan.setModel(model);

            setColumnWidth(1, 0);
            setColumnWidth(8, 0);

            tabelDataPengaduan.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());
            tabelDataPengaduan.getColumnModel().getColumn(9).setCellEditor(new ButtonEditor(new JCheckBox()));
            tabelDataPengaduan.getColumnModel().getColumn(10).setCellRenderer(new ButtonRenderer());
            tabelDataPengaduan.getColumnModel().getColumn(10).setCellEditor(new ButtonEditor(new JCheckBox()));

            tabelDataPengaduan.setRowHeight(35);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setColumnWidth(int index, int width) {
        tabelDataPengaduan.getColumnModel().getColumn(index).setMinWidth(width);
        tabelDataPengaduan.getColumnModel().getColumn(index).setMaxWidth(width);
        tabelDataPengaduan.getColumnModel().getColumn(index).setWidth(width);
    }

    private void showDetailPengaduan(int row) {
        String id = tabelDataPengaduan.getValueAt(row, 1).toString();
        String nama = tabelDataPengaduan.getValueAt(row, 3).toString();
        String judul = tabelDataPengaduan.getValueAt(row, 4).toString();
        String kategori = tabelDataPengaduan.getValueAt(row, 5).toString();
        String status = tabelDataPengaduan.getValueAt(row, 6).toString();
        String fotoName = tabelDataPengaduan.getValueAt(row, 8).toString();

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        String info = "<html><b>ID Pengaduan : </b> " + id + "<br><b>Nama : </b> " + nama + "<br><b>Kategori Pengaduan : </b> " + kategori
                + "<br><b>Judul Pengaduan : </b> " + judul + "<br><b>Status Pengaduan : </b> " + status + "</html>";

        JLabel labelFoto = new JLabel();
        try {
            ImageIcon icon = new ImageIcon("src/uploads/pengaduan/" + fotoName);
            Image img = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            labelFoto.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            labelFoto.setText("Gambar tidak tersedia");
        }

        panel.add(new JLabel(info), BorderLayout.NORTH);
        panel.add(labelFoto, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, panel, "Detail Pengaduan", JOptionPane.PLAIN_MESSAGE);
    }

    private void updateStatus(String id, String status) {
        try {
            String sql = "UPDATE pengaduan SET status = ? WHERE id_pengaduan = ?";
            Connection conn = config.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, id);
            ps.executeUpdate();
            tampilDataAdmin(""); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal update status: " + e.getMessage());
        }
    }

    private void bukaPopupTanggapan(String id) {
        JTextArea textArea = new JTextArea(6, 25);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int result = JOptionPane.showConfirmDialog(null, scrollPane,
                "Input Tanggapan Selesai (ID Pengaduan: " + id + ")",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION && !textArea.getText().trim().isEmpty()) {
            try {
                String sql = "INSERT INTO tanggapan (id_pengaduan, tgl_tanggapan, isi_tanggapan, id_users) VALUES (?, NOW(), ?, ?)";
                Connection conn = config.connection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                ps.setString(2, textArea.getText().trim());
                ps.setInt(3, config.userSession.getInstance().getIdUsers());

                if (ps.executeUpdate() > 0) {
                    updateStatus(id, "selesai");
                    JOptionPane.showMessageDialog(null, "Tanggapan berhasil disimpan!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error simpan tanggapan: " + e.getMessage());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelDataPengaduan = new javax.swing.JTable();
        searchingPengaduan = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        navProfile = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        navDashboard2 = new javax.swing.JLabel();
        navPengaduan2 = new javax.swing.JLabel();
        navBerita2 = new javax.swing.JLabel();
        btn_sign_out2 = new javax.swing.JButton();
        navUsers2 = new javax.swing.JLabel();
        navKategori2 = new javax.swing.JLabel();
        navLaporan2 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel9.setText("Data Pengaduan Masyarakat");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("Aplikasi Layanan Pengaduan Masyakarakat Desa SukaMaju - SukaAdu");

        tabelDataPengaduan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabelDataPengaduan);

        searchingPengaduan.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        searchingPengaduan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchingPengaduanKeyReleased(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 153, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SukaAdu");

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
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1014, Short.MAX_VALUE)
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
                        .addComponent(jLabel2)
                        .addComponent(navProfile)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 153, 255));

        navDashboard2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navDashboard2.setForeground(new java.awt.Color(255, 255, 255));
        navDashboard2.setText("Dashboard");
        navDashboard2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navDashboard2MouseClicked(evt);
            }
        });

        navPengaduan2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navPengaduan2.setForeground(new java.awt.Color(255, 255, 51));
        navPengaduan2.setText("Pengaduan");
        navPengaduan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navPengaduan2MouseClicked(evt);
            }
        });

        navBerita2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navBerita2.setForeground(new java.awt.Color(255, 255, 255));
        navBerita2.setText("Data Berita");
        navBerita2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navBerita2MouseClicked(evt);
            }
        });

        btn_sign_out2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_sign_out2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/mdi--sign-out.png"))); // NOI18N
        btn_sign_out2.setText("Logout");
        btn_sign_out2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sign_out2ActionPerformed(evt);
            }
        });

        navUsers2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navUsers2.setForeground(new java.awt.Color(255, 255, 255));
        navUsers2.setText("Data Users");
        navUsers2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navUsers2MouseClicked(evt);
            }
        });

        navKategori2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navKategori2.setForeground(new java.awt.Color(255, 255, 255));
        navKategori2.setText("Data Kategori");
        navKategori2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navKategori2MouseClicked(evt);
            }
        });

        navLaporan2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navLaporan2.setForeground(new java.awt.Color(255, 255, 255));
        navLaporan2.setText("Rekap Laporan");
        navLaporan2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navLaporan2MouseClicked(evt);
            }
        });

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/clarity--form-line.png"))); // NOI18N

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/ph--users-three.png"))); // NOI18N

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/material-symbols--list-alt-add-outline.png"))); // NOI18N

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/fluent-mdl2--news.png"))); // NOI18N

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/carbon--report.png"))); // NOI18N

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/material-symbols--dashboard-2-gear-outline.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_sign_out2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel24))
                                .addComponent(jLabel25))
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(navDashboard2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navUsers2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navKategori2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navBerita2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navPengaduan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navLaporan2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navDashboard2)
                    .addComponent(jLabel28))
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(navPengaduan2)
                                    .addComponent(jLabel23))
                                .addGap(27, 27, 27)
                                .addComponent(navUsers2))
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(31, 31, 31)
                        .addComponent(navKategori2))
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navBerita2)
                    .addComponent(jLabel26))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navLaporan2)
                    .addComponent(jLabel27))
                .addGap(28, 28, 28)
                .addComponent(btn_sign_out2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel9)
                        .addComponent(jLabel5)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(597, 597, 597)
                            .addComponent(searchingPengaduan, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(81, 81, 81))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5)
                        .addGap(39, 39, 39)
                        .addComponent(searchingPengaduan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchingPengaduanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchingPengaduanKeyReleased
        tampilDataAdmin(searchingPengaduan.getText().trim());
    }//GEN-LAST:event_searchingPengaduanKeyReleased

    private void navProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navProfileMouseClicked
        try {
            admin.profile.form_profile profileForm = new admin.profile.form_profile();
            profileForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form profile : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navProfileMouseClicked

    private void navDashboard2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navDashboard2MouseClicked
        try {
            admin.dashboardAdmin dashboardAdmin = new admin.dashboardAdmin();
            dashboardAdmin.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form kategori pengaduan: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navDashboard2MouseClicked

    private void navPengaduan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navPengaduan2MouseClicked
        try {
            admin.pengaduan.form_pengaduan_data pengaduanForm = new admin.pengaduan.form_pengaduan_data();
            pengaduanForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form data pengaduan : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navPengaduan2MouseClicked

    private void navBerita2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navBerita2MouseClicked
        try {
            admin.berita.form_berita beritaForm = new admin.berita.form_berita();
            beritaForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form berita : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navBerita2MouseClicked

    private void btn_sign_out2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sign_out2ActionPerformed
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
    }//GEN-LAST:event_btn_sign_out2ActionPerformed

    private void navUsers2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navUsers2MouseClicked
        try {
            admin.users.form_users usersForm = new admin.users.form_users();
            usersForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form users: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navUsers2MouseClicked

    private void navKategori2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navKategori2MouseClicked
        try {
            admin.kategori_pengaduan.form_kategori kategoriForm = new admin.kategori_pengaduan.form_kategori();
            kategoriForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form kategori pengaduan: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navKategori2MouseClicked

    private void navLaporan2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navLaporan2MouseClicked
        try {
            admin.rekap_laporan.form_rekap_laporan rekapLaporanForm = new  admin.rekap_laporan.form_rekap_laporan();
            rekapLaporanForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form rekap laporan : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navLaporan2MouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_pengaduan_data().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_sign_out2;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel navBerita2;
    private javax.swing.JLabel navDashboard2;
    private javax.swing.JLabel navKategori2;
    private javax.swing.JLabel navLaporan2;
    private javax.swing.JLabel navPengaduan2;
    private javax.swing.JLabel navProfile;
    private javax.swing.JLabel navUsers2;
    private javax.swing.JTextField searchingPengaduan;
    private javax.swing.JTable tabelDataPengaduan;
    // End of variables declaration//GEN-END:variables

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private String label;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.addActionListener(e -> {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if (row != -1) {
                    String id = table.getValueAt(row, 1).toString();
                    String name = table.getValueAt(row, 3).toString();
                    if (col == 10) {
                        showDetailPengaduan(row);
                    } else {
                        String status = table.getValueAt(row, 6).toString().toLowerCase();
                        if (status.equals("belum")) {
                            Object[] op = {"Terima", "Tolak", "Batal"};
                            int choice = JOptionPane.showOptionDialog(null, "Proses Nama: " + name, "Admin", 0, 3, null, op, op[0]);
                            if (choice == 0) {
                                updateStatus(id, "proses");
                            } else if (choice == 1) {
                                updateStatus(id, "tolak");
                            }
                        } else if (status.equals("proses")) {
                            bukaPopupTanggapan(id);
                        }
                    }
                }
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
            this.table = table;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }

}
