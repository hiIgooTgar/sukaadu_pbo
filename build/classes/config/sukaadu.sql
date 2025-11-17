CREATE DATABASE sukaadu_pbo;
USE sukaadu_pbo;

DROP DATABASE sukaadu_pbo

CREATE TABLE users (
 id_users INT AUTO_INCREMENT PRIMARY KEY,
 email VARCHAR(255) UNIQUE,
 PASSWORD VARCHAR(255),
 nama VARCHAR(128),
 gender ENUM('L', 'P'),
 alamat TEXT,
 ROLE ENUM('admin', 'masyarakat'),
 STATUS BOOLEAN
)

CREATE TABLE kategori_pengaduan (
 id_kategori_pengaduan INT AUTO_INCREMENT PRIMARY KEY,
 nama_kategori VARCHAR(255),
 deskripsi TEXT
)

CREATE TABLE pengaduan (
 id_pengaduan INT AUTO_INCREMENT PRIMARY KEY,
 tgl_pegaduan DATETIME,
 judul_pengaduan VARCHAR(255),
 deskripsi_pengaduan TEXT,
 foto_pengaduan VARCHAR(255),
 STATUS ENUM('belum', 'proses', 'selesai'),
 id_kategori_pengaduan INT,
 id_users INT,
 
 FOREIGN KEY (id_kategori_pengaduan) REFERENCES kategori_pengaduan(id_kategori_pengaduan),
 FOREIGN KEY (id_users) REFERENCES users(id_users)
) 


CREATE TABLE tanggapan (
  id_tanggapan INT AUTO_INCREMENT PRIMARY KEY,
  id_pengaduan INT,
  tgl_tanggapan DATE DEFAULT CURRENT_TIMESTAMP,
  isi_tanggapan TEXT,
  id_users INT,

  FOREIGN KEY (id_pengaduan) REFERENCES pengaduan(id_pengaduan),
  FOREIGN KEY (id_users) REFERENCES users(id_users)
);


CREATE TABLE berita (
 id_berita INT AUTO_INCREMENT PRIMARY KEY,
 judul_berita VARCHAR(255) NOT NULL,
 isi_berita TEXT NOT NULL,
 tgl_terbit DATETIME NOT NULL,
 status_publikasi ENUM('draft', 'published') NOT NULL DEFAULT 'draft',
 id_users INT,
    
 FOREIGN KEY (id_users) REFERENCES users(id_users)
);