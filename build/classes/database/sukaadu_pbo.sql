-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 15, 2026 at 01:35 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

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
(1, 'Lubang Jalan di Dekat Balai Desa Ancam Keselamatan Pengendara', 'Sebuah lubang besar ditemukan di jalan utama dekat balai desa dan \ndikeluhkan oleh warga. Lubang tersebut kerap tergenang air saat hujan \nsehingga sulit terlihat, dan berpotensi menyebabkan kecelakaan serta \nkerusakan kendaraan yang melintas. Lubang tersebut kini telah ditutup \nsementara oleh pihak desa. Penanganan ini dilakukan sebagai langkah awal \nuntuk mencegah kecelakaan sambil menunggu perbaikan permanen.', '2025-11-12', 'Publish', 'berita_1768469104338_lubang_besar.jpg', 1, 1),
(2, 'Fogging Dilaksanakan di Wilayah RW 05', 'Menindaklanjuti laporan meningkatnya kasus DBD, pemerintah desa telah \nberkoordinasi dengan Puskesmas setempat untuk melaksanakan kegiatan \nfogging di wilayah RW 05. Kegiatan ini diharapkan mampu menekan \npenyebaran penyakit dan melindungi kesehatan warga.', '2025-11-26', 'Publish', 'berita_1768469480529_kasus_dbd.jpg', 1, 5),
(3, 'Sampah di Sungai Kecil Berhasil Dibersihkan', 'Penumpukan sampah plastik di aliran sungai kecil dekat perbatasan desa \ntelah dibersihkan melalui kegiatan kerja bakti bersama warga dan petugas \nkebersihan. Upaya ini dilakukan untuk mencegah banjir serta menjaga \nkebersihan dan kelestarian lingkungan.', '2025-11-20', 'Publish', 'berita_1768469592662_sampah_sungai.jpg', 1, 3),
(4, 'Balap Liar Berhasil Dicegah Melalui Patroli Malam', 'Aksi balap liar yang kerap terjadi setiap malam Minggu di jalan desa telah \nditangani oleh aparat desa bersama pihak keamanan. Patroli rutin kini dilakukan \nuntuk menjaga ketertiban dan memberikan rasa aman kepada warga sekitar.', '2025-11-22', 'Draft', 'berita_1768469663935_balap_liar.jpeg', 1, 4),
(5, 'Pengurusan Kartu Keluarga Warga Sudah Diproses', 'Keluhan warga terkait keterlambatan pengurusan Kartu Keluarga (KK) \ntelah ditindaklanjuti oleh pemerintah desa. Saat ini berkas pengajuan sudah \ndiproses dan warga telah mendapatkan informasi yang jelas mengenai status \nadministrasi yang diajukan.', '2025-11-14', 'Draft', 'berita_1768469755809_kk_keterlambatan.jpg', 1, 2),
(6, 'Keramaian Malam Hari di Pemukiman Telah Ditertibkan', 'Gangguan keramaian akibat suara musik keras di area pemukiman telah \nditindaklanjuti oleh pihak RT dan aparat desa. Warga yang bersangkutan \ntelah diberikan teguran sehingga kondisi lingkungan kembali kondusif.', '2025-11-28', 'Arsip', 'berita_1768469886882_gangguan_keramaian.jpeg', 1, 4);

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
(1, '2025-11-10', 'Penutupan Lubang Besar di Jalan Utama Desa', 'Terdapat lubang yang cukup dalam di jalan utama dekat balai desa. \nBeberapa pengendara hampir terjatuh saat menghindari lubang tersebut, \nterutama saat tergenang air hujan.', 'pengaduan_1768399259413_lubang_besar.jpg', 'selesai', 1, 3),
(2, '2025-11-13', 'Keterlambatan Pengurusan Kartu Keluarga (KK)', 'Saya sudah mengajukan pembaruan KK sejak dua minggu lalu, namun sampai \nsekarang belum ada kabar. Petugas di loket sulit dihubungi untuk \nkonfirmasi status berkas.', 'pengaduan_1768399388601_kk_keterlambatan.jpg', 'selesai', 2, 3),
(3, '2025-11-15', 'Jam Operasional Loket Desa Tidak Tepat Waktu', 'Pagi ini loket pelayanan baru dibuka pukul 09.00 WIB, padahal jadwal \nseharusnya jam 08.00 WIB. Banyak warga yang sudah mengantre lama di luar.', 'pengaduan_1768399508219_operasional_loket_desa.JPG', 'tolak', 2, 4),
(4, '2025-11-19', 'Penumpukan Sampah di Area Sungai Kecil', 'Terjadi penumpukan sampah plastik di aliran sungai kecil dekat perbatasan \ndesa. Jika tidak segera dibersihkan, dikhawatirkan akan menyebabkan \nbanjir saat hujan lebat.', 'pengaduan_1768399581593_sampah_sungai.jpg', 'selesai', 3, 4),
(5, '2025-11-21', 'Maraknya Aksi Balap Liar di Malam Minggu', 'Setiap malam Minggu sekitar pukul 23.00, sekelompok remaja melakukan balap \nliar di jalan desa. Suara knalpot sangat bising dan mengganggu jam istirahat \nwarga.', 'pengaduan_1768400216548_balap_liar.jpeg', 'selesai', 4, 2),
(6, '2025-11-25', 'Permintaan Fogging karena Kasus DBD Meningkat', 'Dikarenakan sudah ada 2 warga yang terjangkit DBD di wilayah RW 05, \nkami memohon agar pihak desa berkoordinasi dengan Puskesmas untuk \nmelakukan fogging massal.', 'pengaduan_1768400283028_kasus_dbd.jpg', 'selesai', 5, 2),
(7, '2025-11-27', 'Gangguan Keramaian di Area Pemukiman', 'Ada kegiatan warga yang memutar musik dengan volume sangat keras \nhingga larut malam tanpa izin RT setempat, sehingga mengganggu warga \nyang sakit dan balita.', 'pengaduan_1768400393920_gangguan_keramaian.jpeg', 'selesai', 1, 5),
(8, '2025-12-02', 'Perbaikan Lampu Jalan Mati di Dusun RT 03', 'Sudah seminggu lampu jalan di sepanjang gang RT 03 mati total. \nKondisi jalan menjadi sangat gelap saat malam hari dan rawan kecelakaan \nbagi pengendara motor. Mohon segera diperbaiki.', 'pengaduan_1768400509735_lampu_padam.jpg', 'tolak', 1, 5),
(9, '2025-12-05', 'Kurangnya Fasilitas Kebersihan di Posyandu', 'Tempat cuci tangan dan tempat sampah di gedung Posyandu saat ini dalam \nkondisi rusak. Mohon disediakan fasilitas yang baru demi menjaga higienitas \nsaat jadwal pemeriksaan bayi.', 'pengaduan_1768400858978_posyandu_cuci_tangan.jpg', 'proses', 5, 5),
(11, '2025-12-07', 'Bau Tidak Sedap dari Saluran Air yang Tersumbat', 'Saluran air di depan pasar desa tersumbat limbah domestik sehingga \nmenimbulkan bau busuk yang menyengat dan mengganggu kenyamanan \npedagang serta pembeli.', 'pengaduan_1768401473915_air_terseumbat.jpeg', 'belum', 3, 5);

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
(1, 1, '2026-01-14', 'Laporan diterima. Kami telah menandai lokasi tersebut untuk penanganan darurat (penutupan sementara) sore ini. Untuk perbaikan permanen, akan kami ajukan dalam anggaran pemeliharaan jalan minggu depan.', 1),
(2, 2, '2026-01-14', 'Mohon maaf atas keterlambatan tersebut. Setelah kami cek, ada kendala pada sistem kependudukan pusat. Petugas kami akan segera menghubungi Bapak/Ibu melalui nomor telepon terdaftar untuk pengambilan berkas hari ini.', 1),
(3, 3, '2026-01-14', 'Mohon maaf, laporan Anda kami tolak. Sebagai informasi, pada hari yang Anda maksud, seluruh staf desa sedang melaksanakan rapat koordinasi wajib di kantor kecamatan, dan pemberitahuan telah ditempel di gerbang sejak sehari sebelumnya.', 1),
(4, 4, '2026-01-14', 'Laporan telah kami terima. Dinas Kebersihan Desa akan melakukan kerja bakti pembersihan sungai pada hari Sabtu ini. Kami mengimbau warga untuk tetap menjaga kebersihan sungai.', 1),
(5, 5, '2026-01-14', 'Laporan segera ditindaklanjuti. Kami akan berkoordinasi dengan Bhabinkamtibmas dan Linmas untuk melakukan patroli rutin di area tersebut pada jam-jam rawan.', 1),
(6, 6, '2026-01-14', 'Kami turut prihatin. Laporan sudah kami teruskan ke Puskesmas SukaMaju. Jadwal fogging di wilayah RW 05 sedang disusun dan akan dilaksanakan paling lambat lusa pagi.', 1),
(7, 7, '2026-01-14', 'Terima kasih atas aduannya. Petugas Linmas akan segera mendatangi lokasi untuk memberikan teguran secara persuasif kepada warga yang bersangkutan agar menghormati waktu istirahat tetangga.', 1),
(8, 8, '2026-01-14', 'Mohon maaf, laporan Anda kami tolak. Berdasarkan pengecekan lapangan, titik lampu yang dilaporkan berada di dalam area perumahan privat (cluster), sehingga pemeliharaan merupakan tanggung jawab pengelola perumahan, bukan desa.', 1);

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
(2, 'prasetyoadi@gmail.com', 'c511436155a3738b583e17b5649332a7', '3306451233120003', 'Prasetyo Adi Purnomo', 'Brebes', 'Kristen', '2001-01-20', 'Laki-laki', '007', '001', 'Mahasiswa', 'Sudah Menikah', 'Masyarakat', 'Aktif', 'default_profile.jpg'),
(3, 'andhikadwi@gmail.com', 'c511436155a3738b583e17b5649332a7', '3303143212310001', 'Andhika Dwi Cahya Purnama', 'Purwokerto', 'Islam', '2009-04-09', 'Laki-laki', '04', '005', 'Mahasiswa', 'Sudah Menikah', 'Masyarakat', 'Aktif', 'profile_1768397290196_baldwind.jpg'),
(4, 'andiniputri@gmail.com', 'c511436155a3738b583e17b5649332a7', '3304343423530002', 'Andini Setya Putri', 'Purwokerto', 'Islam', '2007-05-04', 'Perempuan', '005', '003', 'Mahasiswa', 'Belum Menikah', 'Masyarakat', 'Aktif', 'default_profile.jpg'),
(5, 'igotegarprambudhy@gmail.com', 'c511436155a3738b583e17b5649332a7', '3306451233120003', 'Igo Tegar Prambudhy', 'Purbalingga', 'Islam', '2005-08-17', 'Laki-laki', '005', '003', 'Mahasiswa', 'Belum Menikah', 'Masyarakat', 'Aktif', 'profile_1768398739707_people-man.jpg');

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
  MODIFY `id_berita` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `kategori_pengaduan`
--
ALTER TABLE `kategori_pengaduan`
  MODIFY `id_kategori_pengaduan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `pengaduan`
--
ALTER TABLE `pengaduan`
  MODIFY `id_pengaduan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `tanggapan`
--
ALTER TABLE `tanggapan`
  MODIFY `id_tanggapan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_users` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

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
