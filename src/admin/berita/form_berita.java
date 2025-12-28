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

        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        navProfile = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        navDashboard1 = new javax.swing.JLabel();
        navPengaduan1 = new javax.swing.JLabel();
        navBerita1 = new javax.swing.JLabel();
        btn_sign_out1 = new javax.swing.JButton();
        navUsers1 = new javax.swing.JLabel();
        navKategori1 = new javax.swing.JLabel();
        navLaporan1 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        jLabel8.setText("Data Berita Pengaduan");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Aplikasi Layanan Pengaduan Masyakarakat Desa SukaMaju - SukaAdu");

        imageElement.setBackground(new java.awt.Color(51, 153, 255));
        imageElement.setOpaque(true);

        inputDeskripsi.setColumns(20);
        inputDeskripsi.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        inputDeskripsi.setRows(5);
        jScrollPane2.setViewportView(inputDeskripsi);

        chooseGambar.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        chooseGambar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/akar-icons--file.png"))); // NOI18N
        chooseGambar.setText("Pilih Gambar");
        chooseGambar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseGambarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("Judul Berita");

        resetForm.setBackground(new java.awt.Color(0, 153, 255));
        resetForm.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        resetForm.setForeground(new java.awt.Color(255, 255, 255));
        resetForm.setText("Clear");
        resetForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetFormActionPerformed(evt);
            }
        });

        inputJudul.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setText("Kategori Berita");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setText("Tanggal Berita");

        selectKategori.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel9.setText("Deskripsi Berita");

        btnAdd.setBackground(new java.awt.Color(51, 153, 255));
        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Tambah");
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel1.setText("Status Berita");

        selectStatus.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
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

        searchingBerita.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        searchingBerita.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchingBeritaKeyReleased(evt);
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

        btnDelete.setBackground(new java.awt.Color(0, 153, 255));
        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Hapus");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
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
                        .addComponent(jLabel2)
                        .addComponent(navProfile)))
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
        navPengaduan1.setForeground(new java.awt.Color(255, 255, 255));
        navPengaduan1.setText("Pengaduan");
        navPengaduan1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navPengaduan1MouseClicked(evt);
            }
        });

        navBerita1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        navBerita1.setForeground(new java.awt.Color(255, 255, 51));
        navBerita1.setText("Data Berita");
        navBerita1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navBerita1MouseClicked(evt);
            }
        });

        btn_sign_out1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_sign_out1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/mdi--sign-out.png"))); // NOI18N
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

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/clarity--form-line.png"))); // NOI18N

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/ph--users-three.png"))); // NOI18N

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/material-symbols--list-alt-add-outline.png"))); // NOI18N

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/fluent-mdl2--news.png"))); // NOI18N

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/carbon--report.png"))); // NOI18N

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/icon/material-symbols--dashboard-2-gear-outline.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btn_sign_out1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel24))
                                .addComponent(jLabel25))
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(navDashboard1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navUsers1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navKategori1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navBerita1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navPengaduan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(navLaporan1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navDashboard1)
                    .addComponent(jLabel28))
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(navPengaduan1)
                                    .addComponent(jLabel23))
                                .addGap(27, 27, 27)
                                .addComponent(navUsers1))
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(31, 31, 31)
                        .addComponent(navKategori1))
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navBerita1)
                    .addComponent(jLabel26))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(navLaporan1)
                    .addComponent(jLabel27))
                .addGap(28, 28, 28)
                .addComponent(btn_sign_out1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchingBerita, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addComponent(jLabel3)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(chooseGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(resetForm, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(imageElement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(55, 55, 55)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5)
                                .addComponent(inputJudul)
                                .addComponent(jLabel9)
                                .addComponent(jScrollPane2)
                                .addComponent(jLabel6)
                                .addComponent(selectKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(inputTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(selectStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1018, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(imageElement, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(chooseGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(resetForm, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(8, 8, 8)
                                .addComponent(inputJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(10, 10, 10)
                                .addComponent(selectKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel1))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(inputTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(selectStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(42, 42, 42)
                        .addComponent(searchingBerita, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            admin.rekap_laporan.form_rekap_laporan rekapLaporanForm = new  admin.rekap_laporan.form_rekap_laporan();
            rekapLaporanForm.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka form rekap laporan : " + e.getMessage(), "Error Redirect", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_navLaporan1MouseClicked

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
    private javax.swing.JButton btn_sign_out1;
    private javax.swing.JButton chooseGambar;
    private javax.swing.JLabel imageElement;
    private javax.swing.JTextArea inputDeskripsi;
    private javax.swing.JTextField inputJudul;
    private com.toedter.calendar.JDateChooser inputTanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel navBerita1;
    private javax.swing.JLabel navDashboard1;
    private javax.swing.JLabel navKategori1;
    private javax.swing.JLabel navLaporan1;
    private javax.swing.JLabel navPengaduan1;
    private javax.swing.JLabel navProfile;
    private javax.swing.JLabel navUsers1;
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
