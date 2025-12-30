package masyarakat.laporan;

import admin.*;
import masyarakat.pengaduan.*;

import config.userSession;
import config.sessionValidator;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class form_laporan extends javax.swing.JFrame {

    public form_laporan() {
        if (!config.sessionValidator.checkSession(this)) {
            return;
        }
        initComponents();
        cariLaporan("");
    }

    private void cariLaporan(String keyword) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("ID Pengaduan");
        model.addColumn("Tanggal");
        model.addColumn("Judul");
        model.addColumn("Deskripsi");
        model.addColumn("Kategori");
        model.addColumn("Foto");
        model.addColumn("Status");
        model.addColumn("Aksi");

        try {
            int no = 1;
            int idUsers = config.userSession.getInstance().getIdUsers();
            Connection conn = config.connection.getConnection();
            String sql = "SELECT p.id_pengaduan, p.tgl_pegaduan, p.judul_pengaduan, p.deskripsi_pengaduan, "
                    + "k.nama_kategori, p.foto_pengaduan, p.status "
                    + "FROM pengaduan p "
                    + "JOIN kategori_pengaduan k ON p.id_kategori_pengaduan = k.id_kategori_pengaduan "
                    + "WHERE p.id_users = ? AND p.judul_pengaduan LIKE ? "
                    + "ORDER BY p.id_pengaduan DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsers);
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    no++,
                    rs.getString("id_pengaduan"),
                    rs.getString("tgl_pegaduan"),
                    rs.getString("judul_pengaduan"),
                    rs.getString("deskripsi_pengaduan"),
                    rs.getString("nama_kategori"),
                    rs.getString("foto_pengaduan"),
                    rs.getString("status"),
                    "Lihat Tanggapan"
                });
            }

            tabelLaporanMasyarakat.setModel(model);
            tabelLaporanMasyarakat.setRowHeight(80);
            tabelLaporanMasyarakat.getColumnModel().getColumn(1).setMinWidth(0);
            tabelLaporanMasyarakat.getColumnModel().getColumn(1).setMaxWidth(0);
            tabelLaporanMasyarakat.getColumnModel().getColumn(1).setWidth(0);
            setTableRenderers();
            setTableButtonListener();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Pencarian gagal: " + e.getMessage());
        }
    }

    private void setTableRenderers() {
        tabelLaporanMasyarakat.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                if (value != null) {
                    String path = "src/uploads/pengaduan/" + value.toString();
                    File file = new File(path);
                    if (file.exists()) {
                        ImageIcon imgIcon = new ImageIcon(path);
                        Image img = imgIcon.getImage().getScaledInstance(100, 70, Image.SCALE_SMOOTH);
                        label.setIcon(new ImageIcon(img));
                    }
                }
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });

        tabelLaporanMasyarakat.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                if (value != null) {
                    String status = value.toString();
                    setText(status.toUpperCase());
                    if ("belum".equalsIgnoreCase(status)) {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.WHITE);
                    } else if ("proses".equalsIgnoreCase(status)) {
                        c.setBackground(Color.YELLOW);
                        c.setForeground(Color.BLACK);
                    } else if ("selesai".equalsIgnoreCase(status)) {
                        c.setBackground(Color.GREEN);
                        c.setForeground(Color.BLACK);
                    } else if ("belum".equalsIgnoreCase(status)) {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.WHITE);
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }
                return c;
            }
        });

        tabelLaporanMasyarakat.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel panel = new JPanel(new java.awt.GridBagLayout());
                panel.setOpaque(true);
                panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15));

                JLabel btn = new JLabel();
                btn.setOpaque(true);
                btn.setHorizontalAlignment(JLabel.CENTER);
                btn.setFont(new Font("Tahoma", Font.PLAIN, 12));

                btn.setPreferredSize(new Dimension(130, 30));
                btn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

                String status = table.getValueAt(row, 7).toString().toLowerCase();

                if (status.equals("belum")) {
                    btn.setText("Hapus Pengaduan");
                    btn.setBackground(new Color(255, 51, 51));
                    btn.setForeground(Color.WHITE);
                } else if (status.equals("proses")) {
                    btn.setText("Belum Ada Tanggapan");
                    btn.setBackground(new Color(255, 204, 0));
                    btn.setForeground(Color.BLACK);
                } else if (status.equals("selesai")) {
                    btn.setText("Lihat Tanggapan");
                    btn.setBackground(new Color(40, 167, 69));
                    btn.setForeground(Color.WHITE);
                } else if (status.equals("tolak")) {
                    btn.setText("Hapus Pengaduan");
                    btn.setBackground(new Color(255, 51, 51));
                    btn.setForeground(Color.WHITE);
                }

                if (isSelected) {
                    panel.setBackground(table.getSelectionBackground());
                } else {
                    panel.setBackground(Color.WHITE);
                }

                panel.add(btn);
                return panel;
            }
        });
    }

    private void setTableButtonListener() {
        tabelLaporanMasyarakat.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tabelLaporanMasyarakat.getSelectedRow();
                int col = tabelLaporanMasyarakat.getSelectedColumn();

                if (col == 8) {
                    String status = tabelLaporanMasyarakat.getValueAt(row, 7).toString().toLowerCase();
                    String idPengaduan = tabelLaporanMasyarakat.getValueAt(row, 1).toString();

                    if (status.equals("belum") || status.equals("tolak")) {
                        int confirm = JOptionPane.showConfirmDialog(null,
                                "Apakah Anda yakin ingin menghapus pengaduan ini?",
                                "Konfirmasi Hapus",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);

                        if (confirm == JOptionPane.YES_OPTION) {
                            prosesHapus(idPengaduan);
                        }

                    } else if (status.equals("proses")) {
                        JOptionPane.showMessageDialog(null, "Laporan Anda sedang diproses oleh petugas. Mohon ditunggu.");
                    } else if (status.equals("selesai")) {
                        tampilkanPopupTanggapan(idPengaduan);
                    }
                }
            }
        });
    }

    private void prosesHapus(String id) {
        try {
            Connection conn = config.connection.getConnection();
            String sql = "DELETE FROM pengaduan WHERE id_pengaduan = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            int execution = ps.executeUpdate();
            if (execution > 0) {
                JOptionPane.showMessageDialog(this, "Pengaduan berhasil dihapus.");
                cariLaporan("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus: " + e.getMessage());
        }
    }

    private void tampilkanPopupTanggapan(String id) {
        try {
            Connection conn = config.connection.getConnection();
            String sql = "SELECT tgl_tanggapan, isi_tanggapan FROM tanggapan WHERE id_pengaduan = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String pesan = "Tanggal Tanggapan : " + rs.getString("tgl_tanggapan") + "\n\n"
                        + "Isi Tanggapan :\n" + rs.getString("isi_tanggapan");
                JOptionPane.showMessageDialog(this, pesan, "Tanggapan Petugas", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Belum ada tanggapan untuk pengaduan ini.", "Info", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        navPengaduan = new javax.swing.JLabel();
        navBeranda = new javax.swing.JLabel();
        navBerita = new javax.swing.JLabel();
        navProfile = new javax.swing.JLabel();
        btn_sign_out = new javax.swing.JButton();
        navLaporan = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelLaporanMasyarakat = new javax.swing.JTable();
        searchingLaporan = new javax.swing.JTextField();
        btnExportPengaduan = new javax.swing.JButton();
        btnExportPengaduanExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SukaAdu");

        navPengaduan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navPengaduan.setForeground(new java.awt.Color(255, 255, 255));
        navPengaduan.setText("Pengaduan");
        navPengaduan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navPengaduanMouseClicked(evt);
            }
        });

        navBeranda.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navBeranda.setForeground(new java.awt.Color(255, 255, 255));
        navBeranda.setText("Beranda");
        navBeranda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navBerandaMouseClicked(evt);
            }
        });

        navBerita.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navBerita.setForeground(new java.awt.Color(255, 255, 255));
        navBerita.setText("Berita");
        navBerita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navBeritaMouseClicked(evt);
            }
        });

        navProfile.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navProfile.setForeground(new java.awt.Color(255, 255, 255));
        navProfile.setText("Profile");
        navProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navProfileMouseClicked(evt);
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

        navLaporan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navLaporan.setForeground(new java.awt.Color(255, 255, 51));
        navLaporan.setText("Laporan");
        navLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navLaporanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 396, Short.MAX_VALUE)
                .addComponent(navBeranda)
                .addGap(45, 45, 45)
                .addComponent(navPengaduan)
                .addGap(50, 50, 50)
                .addComponent(navLaporan)
                .addGap(49, 49, 49)
                .addComponent(navBerita)
                .addGap(39, 39, 39)
                .addComponent(navProfile)
                .addGap(31, 31, 31)
                .addComponent(btn_sign_out)
                .addGap(49, 49, 49))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(navPengaduan)
                    .addComponent(navBeranda)
                    .addComponent(navBerita)
                    .addComponent(navProfile)
                    .addComponent(btn_sign_out)
                    .addComponent(navLaporan))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setText("Aplikasi Layanan Pengaduan Masyakarakat Desa SukaMaju - SukaAdu");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 23)); // NOI18N
        jLabel1.setText("Laporan Pengaduan Masyarakat");

        tabelLaporanMasyarakat.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tabelLaporanMasyarakat);

        searchingLaporan.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        searchingLaporan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchingLaporanKeyReleased(evt);
            }
        });

        btnExportPengaduan.setBackground(new java.awt.Color(255, 0, 51));
        btnExportPengaduan.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnExportPengaduan.setForeground(new java.awt.Color(255, 255, 255));
        btnExportPengaduan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/akar-icons--file-white.png"))); // NOI18N
        btnExportPengaduan.setText("PDF");
        btnExportPengaduan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportPengaduanActionPerformed(evt);
            }
        });

        btnExportPengaduanExcel.setBackground(new java.awt.Color(0, 153, 0));
        btnExportPengaduanExcel.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnExportPengaduanExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExportPengaduanExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/akar-icons--file-white.png"))); // NOI18N
        btnExportPengaduanExcel.setText("Excel");
        btnExportPengaduanExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportPengaduanExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnExportPengaduanExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnExportPengaduan, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(searchingLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1046, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchingLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportPengaduan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportPengaduanExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void navPengaduanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navPengaduanMouseClicked
        try {
            masyarakat.pengaduan.form_pengaduan formMasyarakat = new masyarakat.pengaduan.form_pengaduan();
            formMasyarakat.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form pengaduan masyarakat: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navPengaduanMouseClicked

    private void navBeritaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navBeritaMouseClicked
        try {
            masyarakat.berita.form_berita formBeritaMasyarakat = new masyarakat.berita.form_berita();
            formBeritaMasyarakat.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form berita masyarakat: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navBeritaMouseClicked

    private void navProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navProfileMouseClicked
        try {
            masyarakat.profile.form_profile formProfileMasyarakat = new masyarakat.profile.form_profile();
            formProfileMasyarakat.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form pengaduan masyarakat: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navProfileMouseClicked

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

    private void navLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navLaporanMouseClicked
        try {
            masyarakat.laporan.form_laporan formLaporanMasyarakat = new masyarakat.laporan.form_laporan();
            formLaporanMasyarakat.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form laporan masyarakat: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navLaporanMouseClicked

    private void searchingLaporanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchingLaporanKeyReleased
        String keyword = searchingLaporan.getText().trim();

        if (keyword.isEmpty()) {
            cariLaporan("");
        } else {
            cariLaporan(keyword);
        }
    }//GEN-LAST:event_searchingLaporanKeyReleased

    private void navBerandaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navBerandaMouseClicked
        try {
            masyarakat.dashboardMasyarakat dashboardMasyarakat = new masyarakat.dashboardMasyarakat();
            dashboardMasyarakat.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka dashboard masyarakat: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navBerandaMouseClicked

    private void btnExportPengaduanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportPengaduanActionPerformed
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setDialogTitle("Save Laporan Pengaduan");
        if (chooser.showSaveDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".pdf")) {
                path += ".pdf";
            }
            laporanGeneratorPengaduan.cetakLaporan((DefaultTableModel) tabelLaporanMasyarakat.getModel(), path);
            javax.swing.JOptionPane.showMessageDialog(this, "Laporan Pengaduan PDF Berhasil Dibuat!");
        }
    }//GEN-LAST:event_btnExportPengaduanActionPerformed

    private void btnExportPengaduanExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportPengaduanExcelActionPerformed
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setDialogTitle("Simpan Laporan Pengaduan Excel");
        if (chooser.showSaveDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            if (!path.toLowerCase().endsWith(".xlsx")) {
                path += ".xlsx";
            }
            try {
                DefaultTableModel model = (DefaultTableModel) tabelLaporanMasyarakat.getModel();
                excelGeneratorPengaduan.exportToExcel(model, path);
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Data pengaduan berhasil di-export ke Excel",
                        "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Terjadi kesalahan: " + e.getMessage(),
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnExportPengaduanExcelActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_laporan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportPengaduan;
    private javax.swing.JButton btnExportPengaduanExcel;
    private javax.swing.JButton btn_sign_out;
    private javax.swing.JButton exportData;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel navBeranda;
    private javax.swing.JLabel navBerita;
    private javax.swing.JLabel navLaporan;
    private javax.swing.JLabel navPengaduan;
    private javax.swing.JLabel navProfile;
    private javax.swing.JTextField searchingLaporan;
    private javax.swing.JTable tabelLaporanMasyarakat;
    // End of variables declaration//GEN-END:variables
}
