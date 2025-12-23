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

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        navProfile = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        navDashboard1 = new javax.swing.JLabel();
        navPengaduan1 = new javax.swing.JLabel();
        navBerita1 = new javax.swing.JLabel();
        btn_sign_out1 = new javax.swing.JButton();
        navUsers1 = new javax.swing.JLabel();
        navKategori1 = new javax.swing.JLabel();
        navLaporan1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelDataPengaduan = new javax.swing.JTable();
        searchingPengaduan = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1012, Short.MAX_VALUE)
                .addComponent(navProfile)
                .addGap(46, 46, 46))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(navProfile))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));

        navDashboard1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navDashboard1.setForeground(new java.awt.Color(255, 255, 255));
        navDashboard1.setText("Dashboard");
        navDashboard1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navDashboard1MouseClicked(evt);
            }
        });

        navPengaduan1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navPengaduan1.setForeground(new java.awt.Color(255, 255, 51));
        navPengaduan1.setText("Pengaduan");
        navPengaduan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navPengaduan1MouseClicked(evt);
            }
        });

        navBerita1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navBerita1.setForeground(new java.awt.Color(255, 255, 255));
        navBerita1.setText("Data Berita");
        navBerita1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navBerita1MouseClicked(evt);
            }
        });

        btn_sign_out1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_sign_out1.setText("Logout");
        btn_sign_out1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sign_out1ActionPerformed(evt);
            }
        });

        navUsers1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navUsers1.setForeground(new java.awt.Color(255, 255, 255));
        navUsers1.setText("Data Users");
        navUsers1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navUsers1MouseClicked(evt);
            }
        });

        navKategori1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navKategori1.setForeground(new java.awt.Color(255, 255, 255));
        navKategori1.setText("Data Kategori");
        navKategori1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navKategori1MouseClicked(evt);
            }
        });

        navLaporan1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navLaporan1.setForeground(new java.awt.Color(255, 255, 255));
        navLaporan1.setText("Rekap Laporan");
        navLaporan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navLaporan1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(navDashboard1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(navUsers1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navKategori1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(navBerita1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navPengaduan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_sign_out1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navLaporan1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(navDashboard1)
                .addGap(28, 28, 28)
                .addComponent(navPengaduan1)
                .addGap(30, 30, 30)
                .addComponent(navUsers1)
                .addGap(31, 31, 31)
                .addComponent(navKategori1)
                .addGap(29, 29, 29)
                .addComponent(navBerita1)
                .addGap(33, 33, 33)
                .addComponent(navLaporan1)
                .addGap(31, 31, 31)
                .addComponent(btn_sign_out1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel9.setText("Data Pengaduan Masyarakat");

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

        searchingPengaduan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchingPengaduanKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(597, 597, 597)
                                .addComponent(searchingPengaduan, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel9)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5)
                        .addGap(39, 39, 39)
                        .addComponent(searchingPengaduan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 43, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void navProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navProfileMouseClicked
        try {
            admin.profile.form_profile profileForm = new admin.profile.form_profile();
            profileForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form profile : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navProfileMouseClicked

    private void navDashboard1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navDashboard1MouseClicked
        try {
            admin.dashboardAdmin dashboardAdmin = new admin.dashboardAdmin();
            dashboardAdmin.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form kategori pengaduan: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navDashboard1MouseClicked

    private void navPengaduan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navPengaduan1MouseClicked
        try {
            admin.pengaduan.form_pengaduan_data pengaduanForm = new admin.pengaduan.form_pengaduan_data();
            pengaduanForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form data pengaduan : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navPengaduan1MouseClicked

    private void navBerita1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navBerita1MouseClicked
        try {
            admin.berita.form_berita beritaForm = new admin.berita.form_berita();
            beritaForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form berita : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navBerita1MouseClicked

    private void btn_sign_out1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sign_out1ActionPerformed
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
    }//GEN-LAST:event_btn_sign_out1ActionPerformed

    private void navUsers1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navUsers1MouseClicked
        try {
            admin.users.form_users usersForm = new admin.users.form_users();
            usersForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form users: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navUsers1MouseClicked

    private void navKategori1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navKategori1MouseClicked
        try {
            admin.kategori_pengaduan.form_kategori kategoriForm = new admin.kategori_pengaduan.form_kategori();
            kategoriForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form kategori pengaduan: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navKategori1MouseClicked

    private void navLaporan1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navLaporan1MouseClicked
        try {
            admin.rekap_laporan.form_rekap_laporan rekapLaporanForm = new admin.rekap_laporan.form_rekap_laporan();
            rekapLaporanForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form rekap laporan : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navLaporan1MouseClicked

    private void searchingPengaduanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchingPengaduanKeyReleased
        tampilDataAdmin(searchingPengaduan.getText().trim());
    }//GEN-LAST:event_searchingPengaduanKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_pengaduan_data().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_sign_out1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel navBerita1;
    private javax.swing.JLabel navDashboard1;
    private javax.swing.JLabel navKategori1;
    private javax.swing.JLabel navLaporan1;
    private javax.swing.JLabel navPengaduan1;
    private javax.swing.JLabel navProfile;
    private javax.swing.JLabel navUsers1;
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
                    if (col == 10) {
                        showDetailPengaduan(row);
                    } else {
                        String status = table.getValueAt(row, 6).toString().toLowerCase();
                        if (status.equals("belum")) {
                            Object[] op = {"Terima", "Tolak", "Batal"};
                            int choice = JOptionPane.showOptionDialog(null, "Proses ID: " + id, "Admin", 0, 3, null, op, op[0]);
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
