package admin.berita;

import config.connection;
import config.userSession;
import config.sessionValidator;

import admin.*;
import auth.*;
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

    private File fileGambar = null;
    private String namaFileGambar = "";

    public form_berita() {
        if (!config.sessionValidator.checkSession(this)) {
            return;
        }
        initComponents();
        searchingBerita("");
        tampilKategoriBerita();
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
                    + "WHERE judul_berita LIKE '%" + key + "%' ORDER BY id_berita DESC";

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
            tabelBerita.setRowHeight(29);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void tampilKategoriBerita() {
        try {
            selectKategori.removeAllItems();

            Connection conn = config.connection.getConnection();
            String sql = "SELECT id_kategori_pengaduan, nama_kategori FROM kategori_pengaduan";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id_kategori_pengaduan");
                String nama = rs.getString("nama_kategori");
                selectKategori.addItem(new kategoriBerita(id, nama));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat kategori: " + e.getMessage());
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

    private void clearForm() {
        inputJudul.setText("");
        inputDeskripsi.setText("");
        inputTanggal.setDate(null);
        if (selectStatus.getItemCount() > 0) {
            selectStatus.setSelectedIndex(0);
        }
        if (selectKategori.getItemCount() > 0) {
            selectKategori.setSelectedIndex(0);
        }
        fileGambar = null;
        namaFileGambar = "";
        imageElement.setIcon(null);
        tabelBerita.clearSelection();
        if (tabelBerita.isEditing()) {
            tabelBerita.getCellEditor().stopCellEditing();
        }
    }

    private void setDefaultImage() {
        imageElement.setIcon(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        navProfile = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        navDashboard = new javax.swing.JLabel();
        navPengaduan = new javax.swing.JLabel();
        navBerita = new javax.swing.JLabel();
        btn_sign_out = new javax.swing.JButton();
        navUsers = new javax.swing.JLabel();
        navKategori = new javax.swing.JLabel();
        navLaporan = new javax.swing.JLabel();
        imageElement = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        inputDeskripsi = new javax.swing.JTextArea();
        chooseGambar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        resetForm = new javax.swing.JButton();
        inputJudul = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        selectKategori = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        inputTanggal = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        selectStatus = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelBerita = new javax.swing.JTable();
        searchingBerita = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1080, Short.MAX_VALUE)
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

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel8.setText("Data Berita Pengaduan");

        jLabel3.setText("Aplikasi Layanan Pengaduan Masyakarakat Desa SukaMaju - SukaAdu");

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
        navBerita.setForeground(new java.awt.Color(255, 255, 51));
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        imageElement.setBackground(new java.awt.Color(51, 153, 255));
        imageElement.setOpaque(true);

        inputDeskripsi.setColumns(20);
        inputDeskripsi.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        inputDeskripsi.setRows(5);
        jScrollPane2.setViewportView(inputDeskripsi);

        chooseGambar.setText("Pilih gambar");
        chooseGambar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseGambarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("Judul Berita");

        resetForm.setBackground(new java.awt.Color(0, 153, 255));
        resetForm.setForeground(new java.awt.Color(255, 255, 255));
        resetForm.setText("Clear");
        resetForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetFormActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setText("Kategori Berita");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setText("Tanggal Berita");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel9.setText("Deskripsi Berita");

        btnAdd.setBackground(new java.awt.Color(51, 153, 255));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Tambah Berita");
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });

        jLabel1.setText("Status Berita");

        selectStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Status Berita", "Publish", "Draft", "Arsip" }));

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
        tabelBerita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBeritaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelBerita);

        searchingBerita.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchingBeritaKeyReleased(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 153, 255));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Ubah Berita");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(0, 153, 255));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Hapus Berita");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel8)
                        .addComponent(jLabel3)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(chooseGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                                .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                                .addComponent(resetForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(imageElement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(55, 55, 55)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5)
                                .addComponent(inputJudul)
                                .addComponent(jLabel9)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addComponent(selectKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(inputTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addContainerGap())
                                        .addComponent(selectStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addComponent(jScrollPane1))
                    .addComponent(searchingBerita, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addGap(44, 44, 44)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(imageElement, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(8, 8, 8)
                                .addComponent(inputJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(10, 10, 10)
                                .addComponent(selectKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel1))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(inputTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(selectStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                                .addComponent(searchingBerita, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chooseGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(resetForm, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))))
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

    private void chooseGambarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseGambarActionPerformed
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Gambar (JPG, PNG, JPEG)", "jpg", "png", "jpeg");
        fc.setFileFilter(filter);

        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            fileGambar = fc.getSelectedFile();
            namaFileGambar = System.currentTimeMillis() + "_" + fileGambar.getName();

            try {
                ImageIcon icon = new ImageIcon(fileGambar.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(imageElement.getWidth(),
                        imageElement.getHeight(),
                        Image.SCALE_SMOOTH);
                imageElement.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal memuat gambar: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_chooseGambarActionPerformed

    private void resetFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetFormActionPerformed
        clearForm();
    }//GEN-LAST:event_resetFormActionPerformed

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        kategoriBerita kat = (kategoriBerita) selectKategori.getSelectedItem();
        String status = selectStatus.getSelectedItem().toString();
        java.util.Date tgl = inputTanggal.getDate();

        if (inputJudul.getText().isEmpty() || fileGambar == null || tgl == null) {
            JOptionPane.showMessageDialog(this, "Judul, Tanggal, dan Gambar wajib diisi!");
            return;
        }

        try {
            config.userSession session = config.userSession.getInstance();
            String sql = "INSERT INTO berita (judul_berita, deskripsi_berita, tgl_terbit, status_publikasi, gambar_berita, id_kategori_pengaduan, id_users) VALUES (?,?,?,?,?,?,?)";
            Connection conn = config.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, inputJudul.getText());
            ps.setString(2, inputDeskripsi.getText());
            ps.setTimestamp(3, new java.sql.Timestamp(tgl.getTime()));
            ps.setString(4, status);
            ps.setString(5, namaFileGambar);
            ps.setInt(6, kat.getId());
            ps.setInt(7, session.getIdUsers());

            if (ps.executeUpdate() > 0) {
                File dest = new File("src/uploads/berita/" + namaFileGambar);
                java.nio.file.Files.copy(fileGambar.toPath(), dest.toPath());

                JOptionPane.showMessageDialog(this, "Berita berhasil ditambahkan oleh " + session.getNama());
                searchingBerita("");
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Tambah: " + e.getMessage());
        }
    }//GEN-LAST:event_btnAddMouseClicked

    private void tabelBeritaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBeritaMouseClicked
        int row = tabelBerita.getSelectedRow();
        if (row == -1) {
            return;
        }
        String idBerita = tabelBerita.getValueAt(row, 0).toString();

        try {
            String sql = "SELECT b.*, k.nama_kategori FROM berita b "
                    + "JOIN kategori_pengaduan k ON b.id_kategori_pengaduan = k.id_kategori_pengaduan "
                    + "WHERE b.id_berita = ?";

            Connection conn = config.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idBerita);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                inputJudul.setText(rs.getString("judul_berita"));
                inputDeskripsi.setText(rs.getString("deskripsi_berita"));
                if (rs.getTimestamp("tgl_terbit") != null) {
                    inputTanggal.setDate(new java.util.Date(rs.getTimestamp("tgl_terbit").getTime()));
                } else {
                    inputTanggal.setDate(null);
                }

                int idKatDB = rs.getInt("id_kategori_pengaduan");
                for (int i = 0; i < selectKategori.getItemCount(); i++) {
                    kategoriBerita item = (kategoriBerita) selectKategori.getItemAt(i);
                    if (item.getId() == idKatDB) {
                        selectKategori.setSelectedIndex(i);
                        break;
                    }
                }

                String status = rs.getString("status_publikasi");
                selectStatus.setSelectedItem(status);
                String namaFoto = rs.getString("gambar_berita");
                if (namaFoto != null && !namaFoto.isEmpty()) {
                    File file = new File("src/uploads/berita/" + namaFoto);
                    if (file.exists()) {
                        ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                        Image img = icon.getImage().getScaledInstance(imageElement.getWidth(),
                                imageElement.getHeight(), Image.SCALE_SMOOTH);
                        imageElement.setIcon(new ImageIcon(img));
                        imageElement.setText("");
                    } else {
                        setDefaultImage();
                    }
                } else {
                    setDefaultImage();
                }
            }
        } catch (Exception e) {
            System.out.println("Error Mouse Click: " + e.getMessage());
        }
    }//GEN-LAST:event_tabelBeritaMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int row = tabelBerita.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data berita di tabel dahulu!");
            return;
        }

        String id = tabelBerita.getValueAt(row, 0).toString();
        kategoriBerita kat = (kategoriBerita) selectKategori.getSelectedItem();

        try {
            String fotoLama = "";
            Connection conn = config.connection.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT gambar_berita FROM berita WHERE id_berita='" + id + "'");
            if (rs.next()) {
                fotoLama = rs.getString("gambar_berita");
            }

            String fotoUpdate = (fileGambar != null) ? namaFileGambar : fotoLama;

            String sql = "UPDATE berita SET judul_berita=?, deskripsi_berita=?, tgl_terbit=?, status_publikasi=?, gambar_berita=?, id_kategori_pengaduan=? WHERE id_berita=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, inputJudul.getText());
            ps.setString(2, inputDeskripsi.getText());
            ps.setTimestamp(3, new java.sql.Timestamp(inputTanggal.getDate().getTime()));
            ps.setString(4, selectStatus.getSelectedItem().toString());
            ps.setString(5, fotoUpdate);
            ps.setInt(6, kat.getId());
            ps.setString(7, id);

            if (ps.executeUpdate() > 0) {
                if (fileGambar != null) {
                    File fOld = new File("src/uploads/berita/" + fotoLama);
                    if (fOld.exists()) {
                        fOld.delete();
                    }
                    java.nio.file.Files.copy(fileGambar.toPath(), new File("src/uploads/berita/" + namaFileGambar).toPath());
                }
                JOptionPane.showMessageDialog(this, "Data berita berhasil diperbarui");
                searchingBerita("");
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Update: " + e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int row = tabelBerita.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data berita di tabel dahulu!");
            return;
        }

        String id = tabelBerita.getValueAt(row, 0).toString();
        int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus berita ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                Connection conn = config.connection.getConnection();
                ResultSet rs = conn.createStatement().executeQuery("SELECT gambar_berita FROM berita WHERE id_berita='" + id + "'");
                if (rs.next()) {
                    File f = new File("src/uploads/berita/" + rs.getString("gambar_berita"));
                    if (f.exists()) {
                        f.delete();
                    }
                }

                String sql = "DELETE FROM berita WHERE id_berita = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Berita Terhapus");
                searchingBerita("");
                clearForm();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btn_sign_out;
    private javax.swing.JButton chooseGambar;
    private javax.swing.JLabel imageElement;
    private javax.swing.JTextArea inputDeskripsi;
    private javax.swing.JTextField inputJudul;
    private com.toedter.calendar.JDateChooser inputTanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel navBerita;
    private javax.swing.JLabel navDashboard;
    private javax.swing.JLabel navKategori;
    private javax.swing.JLabel navLaporan;
    private javax.swing.JLabel navPengaduan;
    private javax.swing.JLabel navProfile;
    private javax.swing.JLabel navUsers;
    private javax.swing.JButton resetForm;
    private javax.swing.JTextField searchingBerita;
    private javax.swing.JComboBox<kategoriBerita> selectKategori;
    private javax.swing.JComboBox<String> selectStatus;
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
            } else if ("Draft".equalsIgnoreCase(status)) {
                c.setBackground(new Color(255, 193, 7));
                c.setForeground(Color.BLACK);
                c.setFont(c.getFont().deriveFont(Font.BOLD));
            } else if ("Arsip".equalsIgnoreCase(status)) {
                c.setBackground(Color.RED);
                c.setForeground(Color.WHITE);
                c.setFont(c.getFont().deriveFont(Font.BOLD));
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
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
