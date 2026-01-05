package admin.rekap_laporan;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class pdfGeneratorPengaduanId {

    public static void exportToPDF(String idPengaduan, String outputPath) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            String pathProfil = new File("src/uploads/profile/").getAbsolutePath() + File.separator;
            String pathBukti = new File("src/uploads/pengaduan/").getAbsolutePath() + File.separator;

            String sql = "SELECT p.*, u.nama, u.img_profile, k.nama_kategori, t.isi_tanggapan "
                    + "FROM pengaduan p "
                    + "JOIN users u ON p.id_users = u.id_users "
                    + "JOIN kategori_pengaduan k ON p.id_kategori_pengaduan = k.id_kategori_pengaduan "
                    + "LEFT JOIN tanggapan t ON p.id_pengaduan = t.id_pengaduan "
                    + "WHERE p.id_pengaduan = ?";

            Connection conn = config.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idPengaduan);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                StringBuilder html = new StringBuilder();
                html.append("<html><head><style>");
                html.append("body { font-family: sans-serif; font-size: 12px; line-height: 1.6; }");
                html.append(".header { text-align: center; border-bottom: 2px solid black; padding-bottom: 10px; margin-bottom: 20px; }");
                html.append(".title { text-align: center; font-size: 16px; font-weight: bold; text-decoration: underline; margin-bottom: 30px; display:inline-block; padding-top: 15px; }");
                html.append(".label { font-weight: bold; width: 150px; display: inline-block; }");
                html.append(".value { display: inline-block; }");
                html.append(".foto-box { text-align: center; margin-top: 20px; }");
                html.append(".img-bukti { width: 300px; border: 1px solid #ccc; padding: 5px; }");
                html.append(".row-detail-pengadu { width: 100%; margin-top: 20px; padding-bottom: 20px; }");
                html.append(".img-p { float: left; width: 15%; }");
                html.append(".img-profil { width: 80px; height: 80px; border: 1px solid #ccc; }");
                html.append(".row-pengadu { float: left; width: 85%; }");
                html.append(".content-box { margin-bottom: 8px; font-size: 12px; }");
                html.append(".label { font-weight: bold; width: 120px; display: inline-block; }");
                html.append(".clear { clear: both; }"); 
                html.append(".footer { margin-top: 50px; text-align: right; }");
                html.append(".tanggapan-section { zbackground-color: #f2f2f2; padding: 15px; border-left: 4px solid #0099ff; margin-top: 20px; }");
                html.append("</style></head><body>");

                html.append("<div class='header'>");
                html.append("<h1 style='margin-bottom:0;'>PEMERINTAH DESA SUKAMAJU</h1>");
                html.append("<h3 style='margin-top:2px; margin-bottom:2px;'>APLIKASI LAYANAN PENGADUAN MASYARAKAT (SukaAdu)</h3>");
                html.append("<p style='margin-top:0;'>Jl. Raya SukaMaju No. 1, Kec. SukaAdu, Jawa Tengah 53211</p>");
                html.append("</div>");
                html.append("<hr />");

                html.append("<div class='title'>DETAIL LAPORAN PENGADUAN ID ").append(idPengaduan).append("</div>");
                html.append("<br />");

                html.append("<div class='row-detail-pengadu'>");
                html.append("<div class='img-p' style='padding-top: 10px;'>");
                String imgProfilName = rs.getString("img_profile");
                if (imgProfilName == null || imgProfilName.isEmpty()) {
                    imgProfilName = "default_profile.jpg";
                }
                File fProfil = new File(pathProfil + imgProfilName);
                if (fProfil.exists()) {
                    html.append("<img src='").append(fProfil.toURI().toString()).append("' class='img-profil' />");
                } else {
                    html.append("<div style='width:80px; height:80px; text-align:center; padding-top:30px;'>No Foto</div>");
                }
                html.append("</div>");

                html.append("<div class='row-pengadu'>");
                html.append("<div class='content-box'><span class='label'>Nama Pengadu</span>: ").append(rs.getString("nama")).append("</div>");
                html.append("<div class='content-box'><span class='label'>Tanggal Laporan</span>: ").append(rs.getString("tgl_pegaduan")).append("</div>");
                html.append("<div class='content-box'><span class='label'>Kategori</span>: ").append(rs.getString("nama_kategori")).append("</div>");
                html.append("<div class='content-box'><span class='label'>Status Laporan</span>: <span style='font-weight:bold; color:#blue;'>").append(rs.getString("status").toUpperCase()).append("</span></div>");
                html.append("</div>");

                html.append("<div class='clear'></div>");

                html.append("</div>");

                html.append("<div class='content-box'><strong>Judul Pengaduan : </strong><br/>").append(rs.getString("judul_pengaduan")).append("</div>");
                html.append("<div class='content-box'><strong>Isi Deskripsi : </strong><br/>").append(rs.getString("deskripsi_pengaduan")).append("</div>");
                html.append("<br />");
                String imgBuktiName = rs.getString("foto_pengaduan");
                File fBukti = new File(pathBukti + imgBuktiName);
                if (fBukti.exists()) {
                    html.append("<div class='foto-box'>");
                    html.append("<p><strong>Bukti Foto Kejadian</strong></p>");
                    html.append("<img src='").append(fBukti.toURI().toString()).append("' class='img-bukti' />");
                    html.append("</div>");
                }

                html.append("<br />");
                String isiTanggapan = rs.getString("isi_tanggapan");
                html.append("<div class='tanggapan-section'>");
                html.append("<strong>Tanggapan Admin:</strong><br/>");
                html.append(isiTanggapan == null ? "<i>Belum ada tanggapan resmi dari pihak admin.</i>" : isiTanggapan);
                html.append("</div>");

                html.append("<br />");
                html.append("<div class='footer'>");
                html.append("<p>Dicetak pada: ").append(new java.util.Date().toString()).append("</p>");
                html.append("<br /><br /><br />");
                html.append("<p><b>Admin Desa Sukamaju</b></p>");
                html.append("</div>");

                html.append("</body></html>");

                InputStream is = new ByteArrayInputStream(html.toString().getBytes(StandardCharsets.UTF_8));
                XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, StandardCharsets.UTF_8);
            }

            document.close();
            javax.swing.JOptionPane.showMessageDialog(null, "PDF Pengaduan Berhasil di-export");
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error PDF: " + e.getMessage());
        }
    }
}
