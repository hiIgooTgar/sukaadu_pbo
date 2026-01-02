-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 02, 2026 at 01:44 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sukaadu_pbo`
--

-- --------------------------------------------------------

--
-- Table structure for table `berita`
--

CREATE TABLE `berita` (
  `id_berita` int(11) NOT NULL,
  `judul_berita` varchar(255) NOT NULL,
  `deskripsi_berita` text NOT NULL,
  `tgl_terbit` date NOT NULL,
  `status_publikasi` enum('Draft','Publish','Arsip') NOT NULL DEFAULT 'Draft',
  `gambar_berita` varchar(255) NOT NULL,
  `id_users` int(11) DEFAULT NULL,
  `id_kategori_pengaduan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `berita`
--

INSERT INTO `berita` (`id_berita`, `judul_berita`, `deskripsi_berita`, `tgl_terbit`, `status_publikasi`, `gambar_berita`, `id_users`, `id_kategori_pengaduan`) VALUES
(1, 'sdsds', 'sdsdsdsd', '2025-12-12', 'Draft', '1766815341547_Screenshot 2025-03-06 173222.png', 1, 5),
(2, ' testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest', ' testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest', '2025-12-18', 'Publish', '1766816955921_Screenshot 2025-04-29 163837.png', 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `kategori_pengaduan`
--

CREATE TABLE `kategori_pengaduan` (
  `id_kategori_pengaduan` int(11) NOT NULL,
  `nama_kategori` varchar(255) DEFAULT NULL,
  `deskripsi` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kategori_pengaduan`
--

INSERT INTO `kategori_pengaduan` (`id_kategori_pengaduan`, `nama_kategori`, `deskripsi`) VALUES
(1, 'Infrastruktur dan Utilitas', 'Aduan terkait kerusakan atau masalah pada fasilitas fisik dan layanan dasar publik.'),
(2, 'Pelayanan Publik', 'Aduan mengenai kualitas, prosedur, atau perilaku petugas dalam memberikan layanan publik.'),
(3, 'Lingkungan Hidup', 'Aduan yang berkaitan dengan kebersihan, polusi, dan pengelolaan sampah di lingkungan masyarakat.'),
(4, 'Ketertiban dan Keamanan', 'Aduan mengenai gangguan keamanan, ketertiban umum, dan masalah sosial yang terjadi di wilayah.'),
(5, 'Kesehatan Publik', 'Aduan terkait fasilitas dan layanan kesehatan yang disediakan oleh pemerintah (puskesmas, rumah sakit).');

-- --------------------------------------------------------

--
-- Table structure for table `pengaduan`
--

CREATE TABLE `pengaduan` (
  `id_pengaduan` int(11) NOT NULL,
  `tgl_pegaduan` date DEFAULT NULL,
  `judul_pengaduan` varchar(255) DEFAULT NULL,
  `deskripsi_pengaduan` text DEFAULT NULL,
  `foto_pengaduan` varchar(255) DEFAULT NULL,
  `status` enum('belum','proses','selesai','tolak') DEFAULT NULL,
  `id_kategori_pengaduan` int(11) DEFAULT NULL,
  `id_users` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pengaduan`
--

INSERT INTO `pengaduan` (`id_pengaduan`, `tgl_pegaduan`, `judul_pengaduan`, `deskripsi_pengaduan`, `foto_pengaduan`, `status`, `id_kategori_pengaduan`, `id_users`) VALUES
(1, '2025-12-28', 'Jalan Rusak', 'Jalanan Rusak Bang', '1766908033682_Screenshot 2025-07-03 055706.png', 'selesai', 1, 3),
(3, '2025-12-25', 'sdsdsd', 'sdsds', 'pengaduan_1766924564931_Screenshot 2025-03-20 154755.png', 'selesai', 1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `tanggapan`
--

CREATE TABLE `tanggapan` (
  `id_tanggapan` int(11) NOT NULL,
  `id_pengaduan` int(11) DEFAULT NULL,
  `tgl_tanggapan` date DEFAULT current_timestamp(),
  `isi_tanggapan` text DEFAULT NULL,
  `id_users` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tanggapan`
--

INSERT INTO `tanggapan` (`id_tanggapan`, `id_pengaduan`, `tgl_tanggapan`, `isi_tanggapan`, `id_users`) VALUES
(1, 1, '2025-12-28', 'Aman bro, laksanakan', 1),
(3, 3, '2025-12-28', 'yabai', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_users` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nik` char(16) DEFAULT NULL,
  `nama` varchar(128) DEFAULT NULL,
  `tempat_lahir` varchar(64) DEFAULT NULL,
  `agama` enum('Islam','Kristen','Katholik','Hindu','Buddha','Konghucu') DEFAULT NULL,
  `tanggal_lahir` date DEFAULT NULL,
  `jenis_kelamin` enum('Laki-laki','Perempuan') DEFAULT NULL,
  `rt` char(4) DEFAULT NULL,
  `rw` char(4) DEFAULT NULL,
  `pekerjaan` varchar(128) DEFAULT NULL,
  `pernikahan` enum('Belum Menikah','Sudah Menikah') DEFAULT NULL,
  `role` enum('Admin','Masyarakat') DEFAULT NULL,
  `status` enum('Aktif','Tidak Aktif') DEFAULT NULL,
  `img_profile` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_users`, `email`, `password`, `nik`, `nama`, `tempat_lahir`, `agama`, `tanggal_lahir`, `jenis_kelamin`, `rt`, `rw`, `pekerjaan`, `pernikahan`, `role`, `status`, `img_profile`) VALUES
(1, 'admin@gmail.com', 'c511436155a3738b583e17b5649332a7', NULL, 'Admin Sukamaju', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Admin', 'Aktif', 'default_profile.jpg'),
(2, 'igotegar@gmail.com', 'c511436155a3738b583e17b5649332a7', '1234123412341234', 'Igo Tegar Prambudhy', 'Purbalingga', 'Kristen', '2006-01-14', 'Laki-laki', '02', '04', 'Mahasiswa', 'Belum Menikah', 'Masyarakat', 'Aktif', 'default_profile.jpg'),
(3, 'tegar@gmail.com', 'c511436155a3738b583e17b5649332a7', '4341434143414341', 'Tegar', 'Purbalingga', 'Katholik', '2005-12-09', 'Laki-laki', '04', '005', 'Mahasiswa', 'Belum Menikah', 'Masyarakat', 'Aktif', 'default_profile.jpg'),
(4, 'nando@gmail.com', 'c511436155a3738b583e17b5649332a7', NULL, 'Nando Yayesa', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Masyarakat', 'Aktif', 'default_profile.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `berita`
--
ALTER TABLE `berita`
  ADD PRIMARY KEY (`id_berita`),
  ADD KEY `id_users` (`id_users`),
  ADD KEY `berita_ibfk_2` (`id_kategori_pengaduan`);

--
-- Indexes for table `kategori_pengaduan`
--
ALTER TABLE `kategori_pengaduan`
  ADD PRIMARY KEY (`id_kategori_pengaduan`);

--
-- Indexes for table `pengaduan`
--
ALTER TABLE `pengaduan`
  ADD PRIMARY KEY (`id_pengaduan`),
  ADD KEY `id_kategori_pengaduan` (`id_kategori_pengaduan`),
  ADD KEY `id_users` (`id_users`);

--
-- Indexes for table `tanggapan`
--
ALTER TABLE `tanggapan`
  ADD PRIMARY KEY (`id_tanggapan`),
  ADD KEY `id_pengaduan` (`id_pengaduan`),
  ADD KEY `id_users` (`id_users`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_users`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `berita`
--
ALTER TABLE `berita`
  MODIFY `id_berita` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `kategori_pengaduan`
--
ALTER TABLE `kategori_pengaduan`
  MODIFY `id_kategori_pengaduan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `pengaduan`
--
ALTER TABLE `pengaduan`
  MODIFY `id_pengaduan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tanggapan`
--
ALTER TABLE `tanggapan`
  MODIFY `id_tanggapan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_users` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `berita`
--
ALTER TABLE `berita`
  ADD CONSTRAINT `berita_ibfk_1` FOREIGN KEY (`id_users`) REFERENCES `users` (`id_users`),
  ADD CONSTRAINT `berita_ibfk_2` FOREIGN KEY (`id_kategori_pengaduan`) REFERENCES `kategori_pengaduan` (`id_kategori_pengaduan`);

--
-- Constraints for table `pengaduan`
--
ALTER TABLE `pengaduan`
  ADD CONSTRAINT `pengaduan_ibfk_1` FOREIGN KEY (`id_kategori_pengaduan`) REFERENCES `kategori_pengaduan` (`id_kategori_pengaduan`),
  ADD CONSTRAINT `pengaduan_ibfk_2` FOREIGN KEY (`id_users`) REFERENCES `users` (`id_users`);

--
-- Constraints for table `tanggapan`
--
ALTER TABLE `tanggapan`
  ADD CONSTRAINT `tanggapan_ibfk_1` FOREIGN KEY (`id_pengaduan`) REFERENCES `pengaduan` (`id_pengaduan`),
  ADD CONSTRAINT `tanggapan_ibfk_2` FOREIGN KEY (`id_users`) REFERENCES `users` (`id_users`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
