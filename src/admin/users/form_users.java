package admin.users;

import java.awt.Image;
import java.io.File;

import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class form_users extends javax.swing.JFrame {

    private File fileFoto;
    private String namaFileFoto;

    public form_users() {
        if (!config.sessionValidator.checkSession(this)) {
            return;
        }

        initComponents();
        search_table("");
    }

    private void search_table(String keyword) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("No");
        model.addColumn("Email");
        model.addColumn("Password");
        model.addColumn("NIK");
        model.addColumn("Nama");
        model.addColumn("Tempat Lahir");
        model.addColumn("Tanggal Lahir");
        model.addColumn("Agama");
        model.addColumn("Jenis Kelamin");
        model.addColumn("RT");
        model.addColumn("RW");
        model.addColumn("Pekerjaan");
        model.addColumn("Status Pernikahan");
        model.addColumn("Role");
        model.addColumn("Status Akun");

        try {
            int no = 1;
            String sql = "SELECT * FROM users WHERE (email LIKE ? OR nik LIKE ? OR nama LIKE ? "
                    + "OR agama LIKE ? OR jenis_kelamin LIKE ?) ORDER BY id_users DESC";

            try (Connection conn = config.connection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {

                String searchKey = "%" + keyword + "%";
                ps.setString(1, searchKey);
                ps.setString(2, searchKey);
                ps.setString(3, searchKey);
                ps.setString(4, searchKey);
                ps.setString(5, searchKey);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        model.addRow(new Object[]{
                            rs.getString("id_users"),
                            no++,
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("nik"),
                            rs.getString("nama"),
                            rs.getString("tempat_lahir"),
                            rs.getString("tanggal_lahir"),
                            rs.getString("agama"),
                            rs.getString("jenis_kelamin"),
                            rs.getString("rt"),
                            rs.getString("rw"),
                            rs.getString("pekerjaan"),
                            rs.getString("pernikahan"),
                            rs.getString("role"),
                            rs.getString("status")
                        });
                    }
                }
            }

            tabelUsers.setModel(model);
            tabelUsers.getColumnModel().getColumn(0).setMinWidth(0);
            tabelUsers.getColumnModel().getColumn(0).setMaxWidth(0);
            tabelUsers.getColumnModel().getColumn(0).setWidth(0);
            tabelUsers.getColumnModel().getColumn(0).setPreferredWidth(0);
            tabelUsers.getColumnModel().getColumn(1).setMaxWidth(50);
            tabelUsers.setRowHeight(28);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private boolean cekEmailDuplikat(String email, String idUsers) {
        String sql = (idUsers == null || idUsers.isEmpty())
                ? "SELECT COUNT(*) FROM users WHERE email = ?"
                : "SELECT COUNT(*) FROM users WHERE email = ? AND id_users != ?";

        try (Connection conn = config.connection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            if (idUsers != null && !idUsers.isEmpty()) {
                ps.setString(2, idUsers);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal validasi email: " + e.getMessage());
        }
        return false;
    }

    private String getOldFoto(String id) {
        try {
            String sql = "SELECT img_profile FROM users WHERE id_users = ?";
            PreparedStatement ps = config.connection.getConnection().prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String img = rs.getString("img_profile");
                return (img == null || img.isEmpty()) ? "default_profile.jpg" : img;
            }
        } catch (Exception e) {
            System.err.println("Error getOldFoto: " + e.getMessage());
        }
        return "default_profile.jpg";
    }

    private void loadGambarProfil(String id) {
        try {
            String sql = "SELECT img_profile FROM users WHERE id_users = ?";
            Connection conn = config.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            String imgName = "default_profile.jpg";

            if (rs.next()) {
                String dbImg = rs.getString("img_profile");
                if (dbImg != null && !dbImg.isEmpty()) {
                    imgName = dbImg;
                }
            }

            File file = new File("src/uploads/profile/" + imgName);
            if (!file.exists()) {
                file = new File("src/uploads/profile/default_profile.jpg");
            }

            if (file.exists()) {
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(profileImg.getWidth(), profileImg.getHeight(), Image.SCALE_SMOOTH);
                profileImg.setIcon(new ImageIcon(img));
                profileImg.setText("");
            } else {
                profileImg.setIcon(null);
                profileImg.setText("Image Missing");
            }

        } catch (SQLException e) {
            System.out.println("Error Load Gambar: " + e.getMessage());
        }
    }

    private void clearForm() {
        inputEmail.setText("");
        inputPassword.setText("");
        inputNIK.setText("");
        inputNama.setText("");
        inputTempatLahir.setText("");
        inputTanggalLahir.setDate(null);
        inputAgama.setSelectedIndex(0);
        inputJenisKelamin.setSelectedIndex(0);
        inputPernikahan.setSelectedIndex(0);
        inputRole.setSelectedIndex(0);
        inputStatusAkun.setSelectedIndex(0);
        inputRT.setText("");
        inputRW.setText("");
        inputPekerjaan.setText("");

        profileImg.setIcon(null);
        profileImg.setText("");
        fileFoto = null;
        namaFileFoto = "";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        inputEmail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelUsers = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        inputSearching = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        inputPassword = new javax.swing.JTextField();
        inputNama = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        inputTempatLahir = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        inputNIK = new javax.swing.JTextField();
        inputTanggalLahir = new com.toedter.calendar.JDateChooser();
        inputJenisKelamin = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        inputRT = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        inputRW = new javax.swing.JTextField();
        inputAgama = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        inputPernikahan = new javax.swing.JComboBox<>();
        inputPekerjaan = new javax.swing.JTextField();
        inputRole = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        inputStatusAkun = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        profileImg = new javax.swing.JLabel();
        btnProfileChoose = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        navProfile = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        navDashboard = new javax.swing.JLabel();
        navPengaduan = new javax.swing.JLabel();
        navBerita = new javax.swing.JLabel();
        btn_sign_out = new javax.swing.JButton();
        navUsers = new javax.swing.JLabel();
        navKategori = new javax.swing.JLabel();
        navLaporan = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        btnReportUsers = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel1.setText("Email");

        inputEmail.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jScrollPane1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        tabelUsers.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelUsersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelUsers);

        btnDelete.setBackground(new java.awt.Color(51, 153, 255));
        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Hapus");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(51, 153, 255));
        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Tambah");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(51, 153, 255));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Ubah");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(51, 153, 255));
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

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("Aplikasi Layanan Pengaduan Masyakarakat Desa SukaMaju - SukaAdu");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel9.setText("Data Users");

        inputPassword.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        inputNama.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Password");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setText("Nama Lengkap");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setText("Tempat Lahir");

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

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel14.setText("Agama");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel15.setText("Status Pernikahan");

        inputRW.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        inputAgama.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        inputAgama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Agama", "Islam", "Kristen", "Katholik", "Hindu", "Buddha", "Konghucu" }));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel17.setText("RW");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel18.setText("Pekerjaan");

        inputPernikahan.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        inputPernikahan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Status Pernikahaan", "Belum Menikah", "Sudah Menikah" }));

        inputPekerjaan.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        inputRole.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        inputRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Role", "Admin", "Masyarakat" }));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel16.setText("Role");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel19.setText("Status Akun");

        inputStatusAkun.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        inputStatusAkun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Status Akun", "Aktif", "Tidak Aktif" }));

        profileImg.setBackground(new java.awt.Color(0, 153, 255));
        profileImg.setOpaque(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profileImg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(profileImg, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );

        btnProfileChoose.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnProfileChoose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/akar-icons--file.png"))); // NOI18N
        btnProfileChoose.setText("Pilih Gambar");
        btnProfileChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileChooseActionPerformed(evt);
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

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/uil--user.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navProfile)
                .addGap(46, 46, 46))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
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
        navUsers.setForeground(new java.awt.Color(255, 255, 51));
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

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/clarity--form-line.png"))); // NOI18N

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/ph--users-three.png"))); // NOI18N

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/material-symbols--list-alt-add-outline.png"))); // NOI18N

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/fluent-mdl2--news.png"))); // NOI18N

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/carbon--report.png"))); // NOI18N

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/material-symbols--dashboard-2-gear-outline.png"))); // NOI18N

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
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22))
                                .addComponent(jLabel23))
                            .addComponent(jLabel24)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING))
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
                    .addComponent(jLabel26))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(navPengaduan)
                                    .addComponent(jLabel21))
                                .addGap(27, 27, 27)
                                .addComponent(navUsers))
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(31, 31, 31)
                        .addComponent(navKategori))
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navBerita)
                    .addComponent(jLabel24))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navLaporan)
                    .addComponent(jLabel25))
                .addGap(28, 28, 28)
                .addComponent(btn_sign_out)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnReportUsers.setBackground(new java.awt.Color(255, 0, 51));
        btnReportUsers.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnReportUsers.setForeground(new java.awt.Color(255, 255, 255));
        btnReportUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/akar-icons--file-white.png"))); // NOI18N
        btnReportUsers.setText("PDF");
        btnReportUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportUsersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnProfileChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(47, 47, 47)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(inputAgama, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel13)
                                            .addGap(92, 92, 92)
                                            .addComponent(jLabel12))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(inputJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(inputRT, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel17)
                                        .addComponent(inputRW, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(15, 15, 15)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel19)
                                        .addComponent(jLabel18)
                                        .addComponent(inputPekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(inputStatusAkun, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(inputEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(inputNama)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addGap(284, 284, 284))
                                            .addComponent(inputNIK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(inputTempatLahir))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(inputTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10)))
                                    .addComponent(jLabel14)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(inputPernikahan, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel15))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(inputRole, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel16)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1010, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnReportUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(inputSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5))))
                .addGap(66, 84, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel9)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5)
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel1)
                                                    .addComponent(jLabel3))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(inputEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(inputNIK, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(inputTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnProfileChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel13)
                                                .addComponent(jLabel12))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(inputJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(inputRT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel14)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(inputAgama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel18)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(inputPekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(inputRW, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(inputRole, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel15))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(inputStatusAkun, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(inputPernikahan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReportUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String emailInput = inputEmail.getText().trim();
        String passwordInput = inputPassword.getText().trim();
        String nikInput = inputNIK.getText().trim();
        String namaInput = inputNama.getText().trim();
        String tempatLahirInput = inputTempatLahir.getText().trim();
        java.util.Date tanggalLahirInput = inputTanggalLahir.getDate();
        String agamaInput = inputAgama.getSelectedItem().toString();
        String jenisKelaminInput = inputJenisKelamin.getSelectedItem().toString();
        String rtInput = inputRT.getText().trim();
        String rwInput = inputRW.getText().trim();
        String pekerjaanInput = inputPekerjaan.getText().trim();
        String pernikahanInput = inputPernikahan.getSelectedItem().toString();
        String roleInput = inputRole.getSelectedItem().toString();
        String statusInput = inputStatusAkun.getSelectedItem().toString();

        if (emailInput.isEmpty() || passwordInput.isEmpty() || nikInput.isEmpty() || namaInput.isEmpty()
                || tempatLahirInput.isEmpty() || tanggalLahirInput == null
                || rtInput.isEmpty() || rwInput.isEmpty() || pekerjaanInput.isEmpty() || roleInput.isEmpty() || statusInput.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Semua field wajib diisi!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;

        }

        if (!config.validasiEmail.isValidEmail(emailInput)) {
            JOptionPane.showMessageDialog(this, "Format email tidak valid (contoh: user@domain.com).", "Peringatan", JOptionPane.WARNING_MESSAGE);
            inputEmail.setText("");
            return;
        }

        if (cekEmailDuplikat(emailInput, "")) {
            JOptionPane.showMessageDialog(this, "Email ini sudah terdaftar. Silakan gunakan email lain.", "Registrasi Gagal", JOptionPane.ERROR_MESSAGE);
            inputEmail.requestFocus();
            return;
        }

        if (passwordInput.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password harus memiliki minimal 8 karakter!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            inputPassword.setText("");
            return;

        }

        if (nikInput.length() != 16) {
            JOptionPane.showMessageDialog(this,
                    "NIK harus tepat 16 digit! (Input saat ini: " + nikInput.length() + " digit)",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            inputNIK.requestFocus();
            return;

        }

        if (!nikInput.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "NIK hanya boleh berisi angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            inputNIK.setText("");
            return;
        }

        String fotoSimpan = (fileFoto != null) ? namaFileFoto : "default_profile.jpg";

        try {
            String hashedPassword = config.hashUtils.hashMD5(passwordInput);

            String sql = "INSERT INTO users (email, password, nik, nama, tempat_lahir, tanggal_lahir, agama, "
                    + "jenis_kelamin, rt, rw, pekerjaan, pernikahan, role, status, img_profile) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Connection conn = config.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, emailInput);
            ps.setString(2, hashedPassword);
            ps.setString(3, nikInput);
            ps.setString(4, namaInput);
            ps.setString(5, tempatLahirInput);
            ps.setDate(6, new java.sql.Date(tanggalLahirInput.getTime()));
            ps.setString(7, agamaInput);
            ps.setString(8, jenisKelaminInput);
            ps.setString(9, rtInput);
            ps.setString(10, rwInput);
            ps.setString(11, pekerjaanInput);
            ps.setString(12, pernikahanInput);
            ps.setString(13, roleInput);
            ps.setString(14, statusInput);
            ps.setString(15, fotoSimpan);

            if (ps.executeUpdate() > 0) {
                if (fileFoto != null) {
                    File dir = new File("src/uploads/profile");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    java.nio.file.Files.copy(fileFoto.toPath(),
                            new File(dir + "/" + fotoSimpan).toPath(),
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }

                JOptionPane.showMessageDialog(this, "Data '" + namaInput + "' berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                search_table("");
                clearForm();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan ke Database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (java.io.IOException ex) {
            JOptionPane.showMessageDialog(this, "Gagal mengunggah foto: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int baris = tabelUsers.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data dari tabel yang ingin diubah!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idUsers = tabelUsers.getValueAt(baris, 0).toString();
        String emailLama = tabelUsers.getValueAt(baris, 2).toString();

        String emailInput = inputEmail.getText().trim();
        String passwordInput = inputPassword.getText().trim();
        String nikInput = inputNIK.getText().trim();
        String namaInput = inputNama.getText().trim();
        String tempatLahirInput = inputTempatLahir.getText().trim();
        java.util.Date tanggalLahirInput = inputTanggalLahir.getDate();
        String agamaInput = inputAgama.getSelectedItem().toString();
        String jenisKelaminInput = inputJenisKelamin.getSelectedItem().toString();
        String rtInput = inputRT.getText().trim();
        String rwInput = inputRW.getText().trim();
        String pekerjaanInput = inputPekerjaan.getText().trim();
        String pernikahanInput = inputPernikahan.getSelectedItem().toString();
        String roleInput = inputRole.getSelectedItem().toString();
        String statusInput = inputStatusAkun.getSelectedItem().toString();

        if (emailInput.isEmpty() || nikInput.isEmpty() || namaInput.isEmpty()
                || tempatLahirInput.isEmpty() || tanggalLahirInput == null
                || rtInput.isEmpty() || rwInput.isEmpty() || pekerjaanInput.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Semua field wajib diisi!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!emailInput.equals(emailLama)) {
            if (cekEmailDuplikat(emailInput, idUsers)) {
                JOptionPane.showMessageDialog(this, "Email '" + emailInput + "' sudah terdaftar pada akun lain!", "Gagal Update", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (nikInput.length() != 16 || !nikInput.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "NIK harus berisi 16 digit angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = config.connection.getConnection();
            String sql;
            PreparedStatement ps;
            String oldFoto = getOldFoto(idUsers);
            String fotoUpdate = (fileFoto != null) ? namaFileFoto : oldFoto;

            if (!passwordInput.isEmpty()) {
                if (passwordInput.length() < 8) {
                    JOptionPane.showMessageDialog(this, "Password baru minimal 8 karakter!");
                    return;
                }
                sql = "UPDATE users SET email=?, password=?, nik=?, nama=?, tempat_lahir=?, tanggal_lahir=?, "
                        + "agama=?, jenis_kelamin=?, rt=?, rw=?, pekerjaan=?, pernikahan=?, role=?, status=?, img_profile=? "
                        + "WHERE id_users=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, emailInput);
                ps.setString(2, config.hashUtils.hashMD5(passwordInput));
                ps.setString(3, nikInput);
                ps.setString(4, namaInput);
                ps.setString(5, tempatLahirInput);
                ps.setString(6, new java.text.SimpleDateFormat("yyyy-MM-dd").format(tanggalLahirInput));
                ps.setString(7, agamaInput);
                ps.setString(8, jenisKelaminInput);
                ps.setString(9, rtInput);
                ps.setString(10, rwInput);
                ps.setString(11, pekerjaanInput);
                ps.setString(12, pernikahanInput);
                ps.setString(13, roleInput);
                ps.setString(14, statusInput);
                ps.setString(15, fotoUpdate);
                ps.setString(16, idUsers);
            } else {
                sql = "UPDATE users SET email=?, nik=?, nama=?, tempat_lahir=?, tanggal_lahir=?, "
                        + "agama=?, jenis_kelamin=?, rt=?, rw=?, pekerjaan=?, pernikahan=?, role=?, status=?, img_profile=? "
                        + "WHERE id_users=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, emailInput);
                ps.setString(2, nikInput);
                ps.setString(3, namaInput);
                ps.setString(4, tempatLahirInput);
                ps.setString(5, new java.text.SimpleDateFormat("yyyy-MM-dd").format(tanggalLahirInput));
                ps.setString(6, agamaInput);
                ps.setString(7, jenisKelaminInput);
                ps.setString(8, rtInput);
                ps.setString(9, rwInput);
                ps.setString(10, pekerjaanInput);
                ps.setString(11, pernikahanInput);
                ps.setString(12, roleInput);
                ps.setString(13, statusInput);
                ps.setString(14, fotoUpdate);
                ps.setString(15, idUsers);
            }

            if (ps.executeUpdate() > 0) {
                if (fileFoto != null) {
                    File dir = new File("src/uploads/profile");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    if (oldFoto != null && !oldFoto.equals("default_profile.jpg")) {
                        File fOld = new File(dir + "/" + oldFoto);
                        if (fOld.exists()) {
                            fOld.delete();
                        }
                    }
                    java.nio.file.Files.copy(fileFoto.toPath(),
                            new File(dir + "/" + namaFileFoto).toPath(),
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }

                JOptionPane.showMessageDialog(this, "Data '" + namaInput + "' berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                search_table("");
                clearForm();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat update: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int baris = tabelUsers.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih baris data yang ingin dihapus terlebih dahulu!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idUsers = tabelUsers.getValueAt(baris, 0).toString();
        String namaUser = tabelUsers.getValueAt(baris, 5).toString();
        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus data pengguna: " + namaUser + "?\n"
                + "Data yang dihapus tidak dapat dikembalikan.",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                String namaFotoLama = getOldFoto(idUsers);
                String sql = "DELETE FROM users WHERE id_users = ?";
                Connection conn = config.connection.getConnection();
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, idUsers);

                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected > 0) {
                        if (namaFotoLama != null && !namaFotoLama.equals("default_profile.jpg")) {
                            File fileFoto = new File("src/uploads/profile/" + namaFotoLama);
                            if (fileFoto.exists()) {
                                fileFoto.delete();
                            }
                        }

                        JOptionPane.showMessageDialog(this,
                                "Data pengguna '" + namaUser + "' berhasil dihapus.",
                                "Sukses",
                                JOptionPane.INFORMATION_MESSAGE);

                        search_table("");
                        clearForm();
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Gagal menghapus data dari database: " + e.getMessage(),
                        "Error Database",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tabelUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelUsersMouseClicked
        int row = tabelUsers.getSelectedRow();
        if (row == -1) {
            return;
        }
        DefaultTableModel model = (DefaultTableModel) tabelUsers.getModel();

        String idUsers = model.getValueAt(row, 0) == null ? "" : model.getValueAt(row, 0).toString();
        String email = model.getValueAt(row, 2) == null ? "" : model.getValueAt(row, 2).toString();
        String nik = model.getValueAt(row, 4) == null ? "" : model.getValueAt(row, 4).toString();
        String nama = model.getValueAt(row, 5) == null ? "" : model.getValueAt(row, 5).toString();
        String tempatLahir = model.getValueAt(row, 6) == null ? "" : model.getValueAt(row, 6).toString();
        String tanggalLahir = model.getValueAt(row, 7) == null ? "" : model.getValueAt(row, 7).toString();
        String agama = model.getValueAt(row, 8) == null ? "" : model.getValueAt(row, 8).toString();
        String jenisKelamin = model.getValueAt(row, 9) == null ? "" : model.getValueAt(row, 9).toString();
        String rt = model.getValueAt(row, 10) == null ? "" : model.getValueAt(row, 10).toString();
        String rw = model.getValueAt(row, 11) == null ? "" : model.getValueAt(row, 11).toString();
        String pekerjaan = model.getValueAt(row, 12) == null ? "" : model.getValueAt(row, 12).toString();
        String pernikahan = model.getValueAt(row, 13) == null ? "" : model.getValueAt(row, 13).toString();
        String role = model.getValueAt(row, 14) == null ? "" : model.getValueAt(row, 14).toString();
        String statusAkun = model.getValueAt(row, 15) == null ? "" : model.getValueAt(row, 15).toString();

        inputEmail.setText(email);
        inputPassword.setText("");
        inputNIK.setText(nik);
        inputNama.setText(nama);
        inputTempatLahir.setText(tempatLahir);
        inputRT.setText(rt);
        inputRW.setText(rw);
        inputPekerjaan.setText(pekerjaan);

        try {
            if (!tanggalLahir.isEmpty()) {
                java.util.Date date = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(tanggalLahir);
                inputTanggalLahir.setDate(date);
            } else {
                inputTanggalLahir.setDate(null);
            }
        } catch (Exception e) {
            inputTanggalLahir.setDate(null);
        }

        if (!agama.isEmpty()) {
            inputAgama.setSelectedItem(agama);
        }
        if (!jenisKelamin.isEmpty()) {
            inputJenisKelamin.setSelectedItem(jenisKelamin);
        }
        if (!pernikahan.isEmpty()) {
            inputPernikahan.setSelectedItem(pernikahan);
        }
        if (!role.isEmpty()) {
            inputRole.setSelectedItem(role);
        }
        if (!statusAkun.isEmpty()) {
            inputStatusAkun.setSelectedItem(statusAkun);
        }
        if (!idUsers.isEmpty()) {
            loadGambarProfil(idUsers);
        }
    }//GEN-LAST:event_tabelUsersMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void inputSearchingKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputSearchingKeyReleased
        String keyword = inputSearching.getText().trim();
        if (keyword.isEmpty()) {
            search_table("");
        } else {
            search_table(keyword);
        }
    }//GEN-LAST:event_inputSearchingKeyReleased

    private void btnProfileChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileChooseActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();

        if (f != null) {
            fileFoto = f;
            namaFileFoto = "profile_" + System.currentTimeMillis() + "_" + f.getName();

            try {
                ImageIcon icon = new ImageIcon(f.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(profileImg.getWidth(), profileImg.getHeight(), Image.SCALE_SMOOTH);
                profileImg.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal memuat gambar: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnProfileChooseActionPerformed

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

    private void btnReportUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportUsersActionPerformed
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setDialogTitle("Save Laporan Data Users");
        if (chooser.showSaveDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".pdf")) {
                path += ".pdf";
            }

            laporanGeneratorUsers.cetakLaporan((DefaultTableModel) tabelUsers.getModel(), path);
            javax.swing.JOptionPane.showMessageDialog(this, "Laporan Data Users PDF Berhasil Dibuat!");
        }
    }//GEN-LAST:event_btnReportUsersActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_users().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnProfileChoose;
    private javax.swing.JButton btnReportUsers;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btn_sign_out;
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
    private javax.swing.JComboBox<String> inputRole;
    private javax.swing.JTextField inputSearching;
    private javax.swing.JComboBox<String> inputStatusAkun;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel navBerita;
    private javax.swing.JLabel navDashboard;
    private javax.swing.JLabel navKategori;
    private javax.swing.JLabel navLaporan;
    private javax.swing.JLabel navPengaduan;
    private javax.swing.JLabel navProfile;
    private javax.swing.JLabel navUsers;
    private javax.swing.JLabel profileImg;
    private javax.swing.JTable tabelUsers;
    // End of variables declaration//GEN-END:variables

}
