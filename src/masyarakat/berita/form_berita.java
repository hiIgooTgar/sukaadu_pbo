package masyarakat.berita;

import admin.*;
import config.connection;
import masyarakat.pengaduan.*;

import config.userSession;
import config.sessionValidator;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

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
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class form_berita extends javax.swing.JFrame {

    public form_berita() {
        if (!config.sessionValidator.checkSession(this)) {
            return;
        }
        initComponents();
        searchingBerita("");
    }

    private void searchingBerita(String key) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        };

        model.addColumn("ID");
        model.addColumn("No");
        model.addColumn("Judul Berita");
        model.addColumn("Kategori");
        model.addColumn("Penulis");
        model.addColumn("Tanggal");
        model.addColumn("Status");
        model.addColumn("Aksi");

        try {
            int no = 1;
            String sql = "SELECT berita.*, kategori_pengaduan.nama_kategori, users.nama AS penulis "
                    + "FROM berita "
                    + "JOIN kategori_pengaduan ON berita.id_kategori_pengaduan = kategori_pengaduan.id_kategori_pengaduan "
                    + "LEFT JOIN users ON berita.id_users = users.id_users "
                    + "WHERE (judul_berita LIKE '%" + key + "%') AND status_publikasi = 'Publish' "
                    + "ORDER BY id_berita DESC";

            Connection conn = config.connection.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_berita"),
                    no++,
                    rs.getString("judul_berita"),
                    rs.getString("nama_kategori"),
                    rs.getString("penulis"),
                    rs.getString("tgl_terbit"),
                    rs.getString("status_publikasi"),
                    "Detail"
                });
            }
            tabelBerita.setModel(model);
            tabelBerita.getColumnModel().getColumn(6).setCellRenderer(new StatusColorRenderer());
            tabelBerita.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
            tabelBerita.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox()));
            tabelBerita.getColumnModel().getColumn(0).setMinWidth(0);
            tabelBerita.getColumnModel().getColumn(0).setMaxWidth(0);
            tabelBerita.setRowHeight(35);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showDetailDialog(String id) {
        try {
            String sql = "SELECT b.*, k.nama_kategori, u.nama FROM berita b "
                    + "JOIN kategori_pengaduan k ON b.id_kategori_pengaduan = k.id_kategori_pengaduan "
                    + "LEFT JOIN users u ON b.id_users = u.id_users WHERE b.id_berita = ?";
            PreparedStatement ps = config.connection.getConnection().prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String msg = "Judul : " + rs.getString("judul_berita") + "\n"
                        + "KAtegori Berita : " + rs.getString("nama_kategori") + "\n"
                        + "Author : " + rs.getString("nama") + "\n"
                        + "Tanggal : " + rs.getString("tgl_terbit") + "\n"
                        + "Status Berita : " + rs.getString("status_publikasi") + "\n"
                        + "Deskripsi : \n" + rs.getString("deskripsi_berita");

                ImageIcon icon = null;
                File f = new File("src/uploads/berita/" + rs.getString("gambar_berita"));
                if (f.exists()) {
                    icon = new ImageIcon(new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH));
                }

                JOptionPane.showMessageDialog(this, msg, "Detail Berita", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        navPengaduan = new javax.swing.JLabel();
        navBeranda = new javax.swing.JLabel();
        navBerita = new javax.swing.JLabel();
        navProfile = new javax.swing.JLabel();
        btn_sign_out = new javax.swing.JButton();
        navLaporan = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        searchingBerita = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelBerita = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 23)); // NOI18N
        jLabel1.setText("Berita Masyarakat");

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
        navBerita.setForeground(new java.awt.Color(255, 255, 51));
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
        navLaporan.setForeground(new java.awt.Color(255, 255, 255));
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 418, Short.MAX_VALUE)
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

        searchingBerita.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        searchingBerita.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchingBeritaKeyReleased(evt);
            }
        });

        tabelBerita.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tabelBerita);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(97, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(searchingBerita, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1))
                        .addGap(544, 544, 544))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1059, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(92, 92, 92))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(38, 38, 38)
                .addComponent(searchingBerita, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
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

    private void navBerandaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navBerandaMouseClicked
        try {
            masyarakat.dashboardMasyarakat dashboardMasyarakat = new masyarakat.dashboardMasyarakat();
            dashboardMasyarakat.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka dashboard masyarakat: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navBerandaMouseClicked

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

    private void searchingBeritaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchingBeritaKeyReleased
        searchingBerita(searchingBerita.getText());
    }//GEN-LAST:event_searchingBeritaKeyReleased

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_berita().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_sign_out;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel navBeranda;
    private javax.swing.JLabel navBerita;
    private javax.swing.JLabel navLaporan;
    private javax.swing.JLabel navPengaduan;
    private javax.swing.JLabel navProfile;
    private javax.swing.JTextField searchingBerita;
    private javax.swing.JTable tabelBerita;
    // End of variables declaration//GEN-END:variables

    class StatusColorRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            ((JLabel) c).setOpaque(true);

            String status = (value != null) ? value.toString() : "";

            if ("Publish".equalsIgnoreCase(status)) {
                c.setBackground(new Color(0, 153, 0));
                c.setForeground(Color.WHITE);
                c.setFont(c.getFont().deriveFont(Font.BOLD));
            }

            if (isSelected) {
                c.setBackground(c.getBackground().darker());
            }
            setHorizontalAlignment(SwingConstants.CENTER);
            return c;
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            int row = table.getSelectedRow();
            String id = table.getValueAt(row, 0).toString();
            showDetailDialog(id);
            return label;
        }
    }

}
