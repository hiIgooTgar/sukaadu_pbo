package admin.users;

import admin.kategori_pengaduan.*;
import config.connection;

import config.userSession;
import config.sessionValidator;

import admin.*;
import auth.*;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class form_users extends javax.swing.JFrame {

    public form_users() {
        if (!config.sessionValidator.checkSession(this)) {
            return;
        }

        initComponents();
        load_table();
    }

    private void load_table() {
        DefaultTableModel model = new DefaultTableModel();
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

        tabelUsers.setModel(model);

        try {
            int no = 1;
            boolean foundData = false;
            String sql = "SELECT * FROM users ORDER BY id_users ASC";
            try (Connection conn = config.connection.getConnection();
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(sql)) {

                while (rs.next()) {
                    foundData = true;
                    model.addRow(new Object[]{
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
                        rs.getString("pernikahan")
                    });
                }
            }

            if (!foundData) {
                model.addRow(new Object[]{"", "Data pengguna tidak ditemukan", ""});
            }

            tabelUsers.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data pengguna: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search_table(String keyword) {
        DefaultTableModel model = new DefaultTableModel();
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

        tabelUsers.setModel(model);

        try {
            int no = 1;
            String sql = "SELECT * FROM users WHERE email LIKE ? OR nik LIKE ? OR nama LIKE ? OR agama LIKE ? OR jenis_kelamin LIKE ? ORDER BY id_users ASC";
            try (Connection conn = config.connection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");
                ps.setString(3, "%" + keyword + "%");
                ps.setString(4, "%" + keyword + "%");
                ps.setString(5, "%" + keyword + "%");

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        model.addRow(new Object[]{
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
                            rs.getString("pernikahan")
                        });
                    }
                }
            }
            tabelUsers.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data pengguna: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
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

    private void clearForm() {
        inputEmail.setText("");
        inputPassword.setText("");
        inputNIK.setText("");
        inputNama.setText("");
        inputTempatLahir.setText("");
        inputTanggalLahir.setDate(null);
        inputAgama.setSelectedItem(0);
        inputJenisKelamin.setSelectedItem(0);
        inputRT.setText("");
        inputRW.setText("");
        inputPekerjaan.setText("");
        inputPernikahan.setSelectedItem(0);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        navProfile = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        navDashboard = new javax.swing.JLabel();
        navPengaduan = new javax.swing.JLabel();
        navBerita = new javax.swing.JLabel();
        btn_sign_out = new javax.swing.JButton();
        navUsers = new javax.swing.JLabel();
        navKategori = new javax.swing.JLabel();
        navLaporan = new javax.swing.JLabel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel1.setText("Email");

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
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Hapus");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(51, 153, 255));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Tambah");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(51, 153, 255));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Ubah");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(51, 153, 255));
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        inputSearching.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputSearchingKeyReleased(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("SukaAdu");

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
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(navProfile)
                .addGap(46, 46, 46))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(navProfile))
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(navDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(navUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navKategori, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(navBerita, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navPengaduan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_sign_out, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(navDashboard)
                .addGap(28, 28, 28)
                .addComponent(navPengaduan)
                .addGap(30, 30, 30)
                .addComponent(navUsers)
                .addGap(31, 31, 31)
                .addComponent(navKategori)
                .addGap(29, 29, 29)
                .addComponent(navBerita)
                .addGap(33, 33, 33)
                .addComponent(navLaporan)
                .addGap(31, 31, 31)
                .addComponent(btn_sign_out)
                .addContainerGap(418, Short.MAX_VALUE))
        );

        jLabel5.setText("Aplikasi Layanan Pengaduan Masyakarakat Desa SukaMaju - SukaAdu");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel9.setText("Data Pengguna");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Password");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setText("Nama Lengkap");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setText("Tempat Lahir");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel10.setText("Tanggal Lahir");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel11.setText("NIK");

        inputJenisKelamin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Jenis Kelamin", "Laki-laki", "Perempuan" }));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel12.setText("RT");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel13.setText("Jenis Kelamin");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel14.setText("Agama");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel15.setText("Status Pernikahan");

        inputAgama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Agama", "Islam", "Kristen", "Katholik", "Hindu", "Buddha", "Konghucu" }));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel17.setText("RW");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel18.setText("Pekerjaan");

        inputPernikahan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Status Pernikahaan", "Belum Menikah", "Sudah Menikah" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(inputJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(inputPekerjaan))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(88, 88, 88))
                                    .addComponent(inputPernikahan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(inputNIK, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(inputTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(inputTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(inputAgama, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(inputEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(inputSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(68, 68, 68))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel9)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5)
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(inputEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(inputNIK, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(inputTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(inputTanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(inputAgama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel18)
                                        .addComponent(jLabel15))
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(inputJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputRT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputRW, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputPernikahan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputPekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addComponent(inputSearching, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
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

        if (emailInput.isEmpty() || passwordInput.isEmpty() || nikInput.isEmpty() || namaInput.isEmpty()
                || tempatLahirInput.isEmpty() || tanggalLahirInput == null
                || rtInput.isEmpty() || rwInput.isEmpty() || pekerjaanInput.isEmpty()) {
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

        if (cekEmailDuplikat(emailInput)) {
            JOptionPane.showMessageDialog(this, "Email ini sudah terdaftar. Silakan gunakan email lain.", "Registrasi Gagal", JOptionPane.ERROR_MESSAGE);
            inputEmail.setText("");
            inputPassword.setText("");
            return;
        }

        if (passwordInput.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password harus memiliki minimal 8 karakter!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            inputPassword.setText("");
            return;
        }

        if (nikInput.length() == 16) {
            JOptionPane.showMessageDialog(this, "NIK harus memiliki minimal 16 karakter!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            inputPassword.setText("");
            return;
        }

        String hashedPassword = config.hashUtils.hashMD5(passwordInput);
        String sql = "INSERT INTO users "
                + "(email, password, nik, nama, tempat_lahir, tanggal_lahir, agama, jenis_kelamin, rt, rw, pekerjaan, pernikahan, role, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

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
            ps.setString(13, "masyarakat");
            ps.setInt(14, 1);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data user berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                load_table();
                clearForm();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
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

        int baris = tabelUsers.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih baris data yang ingin diubah terlebih dahulu.",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idUsers = tabelUsers.getValueAt(baris, 0).toString();

        if (emailInput.isEmpty() || passwordInput.isEmpty() || nikInput.isEmpty()
                || namaInput.isEmpty() || tempatLahirInput.isEmpty()
                || tanggalLahirInput == null || rtInput.isEmpty()
                || rwInput.isEmpty() || pekerjaanInput.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE users SET email=?, password=?, nik=?, nama=?, tempat_lahir=?, tanggal_lahir=?, agama=?, "
                + "jenis_kelamin=?, rt=?, rw=?, pekerjaan=?, pernikahan=? WHERE id_users=?";

        try (Connection conn = connection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emailInput);
            ps.setString(2, passwordInput);
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
            ps.setString(13, idUsers);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data pengguna berhasil diubah.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                load_table();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengubah data pengguna.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int baris = tabelUsers.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data yang ingin dihapus terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idUsers = tabelUsers.getModel().getValueAt(baris, 1).toString();
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data pengguna : " + tabelUsers.getModel().getValueAt(baris, 4).toString() + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {

            String sql = "DELETE FROM users WHERE email = ?";

            try (Connection conn = connection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, idUsers);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Data pengguna berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    load_table();
                    clearForm();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tabelUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelUsersMouseClicked
        int row = tabelUsers.rowAtPoint(evt.getPoint());
        if (row < 0) {
            return;
        }
        DefaultTableModel model = (DefaultTableModel) tabelUsers.getModel();

        String email = model.getValueAt(row, 1) != null ? model.getValueAt(row, 1).toString() : "";
        String password = model.getValueAt(row, 2) != null ? model.getValueAt(row, 2).toString() : "";
        String nik = model.getValueAt(row, 3) != null ? model.getValueAt(row, 3).toString() : "";
        String nama = model.getValueAt(row, 4) != null ? model.getValueAt(row, 4).toString() : "";
        String tempatLahir = model.getValueAt(row, 5) != null ? model.getValueAt(row, 5).toString() : "";
        String tanggalLahir = model.getValueAt(row, 6) != null ? model.getValueAt(row, 6).toString() : "";
        String agama = model.getValueAt(row, 7) != null ? model.getValueAt(row, 7).toString() : "";
        String jenisKelamin = model.getValueAt(row, 8) != null ? model.getValueAt(row, 8).toString() : "";
        String rt = model.getValueAt(row, 9) != null ? model.getValueAt(row, 9).toString() : "";
        String rw = model.getValueAt(row, 10) != null ? model.getValueAt(row, 10).toString() : "";
        String pekerjaan = model.getValueAt(row, 11) != null ? model.getValueAt(row, 11).toString() : "";
        String pernikahan = model.getValueAt(row, 12) != null ? model.getValueAt(row, 12).toString() : "";

        inputEmail.setText(email);
        inputPassword.setText(password);
        inputNIK.setText(nik);
        inputNama.setText(nama);
        inputTempatLahir.setText(tempatLahir);
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
        inputAgama.setSelectedItem(agama);
        inputJenisKelamin.setSelectedItem(jenisKelamin);
        inputRT.setText(rt);
        inputRW.setText(rw);
        inputPekerjaan.setText(pekerjaan);
        inputPernikahan.setSelectedItem(pernikahan);
    }//GEN-LAST:event_tabelUsersMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void inputSearchingKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputSearchingKeyReleased
        String keyword = inputSearching.getText().trim();
        if (keyword.isEmpty()) {
            load_table();
        } else {
            search_table(keyword);
        }
    }//GEN-LAST:event_inputSearchingKeyReleased

    private void navDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navDashboardMouseClicked
        try {
            admin.dashboardAdmin dashboardAdmin = new admin.dashboardAdmin();
            dashboardAdmin.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form kategori pengaduan: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navDashboardMouseClicked

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

    private void navKategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navKategoriMouseClicked
        try {
            admin.kategori_pengaduan.form_kategori kategoriForm = new admin.kategori_pengaduan.form_kategori();
            kategoriForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form kategori pengaduan: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navKategoriMouseClicked

    private void navUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navUsersMouseClicked
        try {
            admin.users.form_users usersForm = new admin.users.form_users();
            usersForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form users: " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navUsersMouseClicked

    private void navProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navProfileMouseClicked
        try {
            admin.profile.form_profile profileForm = new admin.profile.form_profile();
            profileForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form profile : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navProfileMouseClicked

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

    private void navLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navLaporanMouseClicked
        try {
            admin.rekap_laporan.form_rekap_laporan rekapLaporanForm = new  admin.rekap_laporan.form_rekap_laporan();
            rekapLaporanForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form rekap laporan : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navLaporanMouseClicked

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
    private javax.swing.JTextField inputSearching;
    private com.toedter.calendar.JDateChooser inputTanggalLahir;
    private javax.swing.JTextField inputTempatLahir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel navBerita;
    private javax.swing.JLabel navDashboard;
    private javax.swing.JLabel navKategori;
    private javax.swing.JLabel navLaporan;
    private javax.swing.JLabel navPengaduan;
    private javax.swing.JLabel navProfile;
    private javax.swing.JLabel navUsers;
    private javax.swing.JTable tabelUsers;
    // End of variables declaration//GEN-END:variables

}
