package admin.kategori_pengaduan;

import config.connection;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class form_kategori extends javax.swing.JFrame {

    public form_kategori() {
        initComponents();
        load_table();
    }

    private void load_table() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Nama Kategori");
        model.addColumn("Deskripsi");

        tabelKategoriPengaduan.setModel(model);

        try {
            int no = 1;
            Connection conn = config.connection.getConnection();
            String sql = "SELECT * FROM kategori_pengaduan ORDER BY id_kategori_pengaduan ASC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            boolean hasData = rs.next();
            if (!hasData) {
                model.addRow(new Object[]{"", "Data kategori tidak ditemukan", ""});
            }

            if (hasData) {
                do {
                    model.addRow(new Object[]{
                        no++,
                        rs.getString("nama_kategori"),
                        rs.getString("deskripsi")
                    });
                } while (rs.next());
            }
            tabelKategoriPengaduan.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data kategori: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search_table(String keyword) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Nama Kategori");
        model.addColumn("Deskripsi");

        tabelKategoriPengaduan.setModel(model);

        try {
            int no = 1;
            Connection conn = config.connection.getConnection();

            String sql = "SELECT * FROM kategori_pengaduan WHERE nama_kategori LIKE ? OR deskripsi LIKE ? ORDER BY id_kategori_pengaduan ASC";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
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

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        inputNamaKategori = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelKategoriPengaduan = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaDeskripsi = new javax.swing.JTextArea();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        inputSearching = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Data Kategori - SukaAdu");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel3)
                .addContainerGap(678, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel3)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jLabel1.setText("Nama Kategori");

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
        if (tabelKategoriPengaduan.getColumnModel().getColumnCount() > 0) {
            tabelKategoriPengaduan.getColumnModel().getColumn(0).setResizable(false);
            tabelKategoriPengaduan.getColumnModel().getColumn(1).setResizable(false);
            tabelKategoriPengaduan.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel2.setText("Deskripsi Kategori");

        textAreaDeskripsi.setColumns(20);
        textAreaDeskripsi.setRows(5);
        jScrollPane2.setViewportView(textAreaDeskripsi);

        btnDelete.setText("Hapus");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setText("Tambah");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Ubah");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnBack.setText("Kembali");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        inputSearching.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputSearchingKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(inputNamaKategori)
                            .addComponent(jScrollPane2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(inputSearching, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputNamaKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnUpdate))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnClear)
                            .addComponent(btnDelete)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 131, Short.MAX_VALUE))
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

        Connection conn = connection.getConnection();
        String sql = "INSERT INTO kategori_pengaduan (nama_kategori, deskripsi) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaKategori);
            ps.setString(2, deskripsiKategori);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data kategori pengaduan berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                load_table();
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

        Connection conn = connection.getConnection();
        String sql = "UPDATE kategori_pengaduan SET nama_kategori = ?, deskripsi = ? WHERE nama_kategori = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaKategori);
            ps.setString(2, deskripsiKategori);
            ps.setString(3, idKategori);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data kategori pengaduan berhasil diubah.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                load_table();
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
            Connection conn = connection.getConnection();
            String sql = "DELETE FROM kategori_pengaduan WHERE nama_kategori = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, idKategori);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Data kategori pengaduan berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    load_table();
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

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        try {
            admin.dashboardAdmin dashboardAdmin = new admin.dashboardAdmin();
            dashboardAdmin.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka dashboard admin: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void inputSearchingKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputSearchingKeyReleased
        String keyword = inputSearching.getText().trim();
        if (keyword.isEmpty()) {
            load_table();
        } else {
            search_table(keyword);
        }
    }//GEN-LAST:event_inputSearchingKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_kategori().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JTextField inputNamaKategori;
    private javax.swing.JTextField inputSearching;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelKategoriPengaduan;
    private javax.swing.JTextArea textAreaDeskripsi;
    // End of variables declaration//GEN-END:variables

}
