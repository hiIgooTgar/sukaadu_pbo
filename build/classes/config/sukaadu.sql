CREATE DATABASE sukaadu_pbo
USE sukaadu_pbo

CREATE TABLE users (
 id_users INT AUTO_INCREMENT PRIMARY KEY,
 email VARCHAR(255) UNIQUE,
 username VARCHAR(255),
 nama VARCHAR(128),
 gender ENUM('L', 'P'),
 alamat TEXT,
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
 judul_pengaduan varchar(255),
 deskripsi_pengaduan text,
 foto_pengaduan varchar(255),
 status enum('belum', 'proses', 'selesai'),
 id_kategori_pengaduan int,
 
 foreign key (id_kategori_pengaduan) references kategori_pengaduan(id_kategori_pengaduan)
) 