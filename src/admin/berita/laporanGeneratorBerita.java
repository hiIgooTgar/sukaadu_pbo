package admin.berita;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class laporanGeneratorBerita {

    public static void cetakLaporan(DefaultTableModel model, String outputPath) {
        Document document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            String pathFolderFoto = new File("src/uploads/berita/").getAbsolutePath() + File.separator;

            StringBuilder html = new StringBuilder();
            html.append("<html><head><style>");
            html.append("body { font-family: sans-serif; font-size: 10px; }");
            html.append(".header { text-align: center; border-bottom: 2px solid black; padding-bottom: 10px; }");
            html.append(".title { text-align: center; margin: 20px 0; font-size: 16px; font-weight: bold; text-decoration: underline; display:inline-block; padding-top: 15px; }");
            html.append("table { width: 100%; border-collapse: collapse; margin-top: 30px; margin-bottom: 15px; }");
            html.append("th { background-color: rgb(0,153,255); color: white; padding: 5px; border: 1px solid #000; font-size: 13px; }");
            html.append("td { padding: 5px; border: 1px solid #000; vertical-align: middle; font-size: 12px; }");
            html.append(".footer { margin-top: 35px; text-align: right; }");
            html.append(".img-bukti { width: 80px; height: 60px; }");
            html.append("</style></head><body>");

            html.append("<div class='header'>");
            html.append("<h1 style='margin-bottom:0;'>PEMERINTAH DESA SUKAMAJU</h1>");
            html.append("<h3 style='margin-top:2px; margin-bottom:2px;'>APLIKASI LAYANAN PENGADUAN MASYARAKAT (SukaAdu)</h3>");
            html.append("<p style='margin-top:0;'>Jl. Raya SukaMaju No. 1, Kec. SukaAdu, Jawa Tengah 53211</p>");
            html.append("</div>");
             html.append("<hr />");

            html.append("<div class='title'>REKAPITULASI BERITA</div>");
            
            html.append("<table>");
            html.append("<thead><tr>");
            html.append("<th width='3%'>No</th>");
            html.append("<th width='15%'>Judul Berita</th>");
            html.append("<th width='10%'>Kategori</th>");
            html.append("<th width='25%'>Deskripsi</th>");
            html.append("<th width='15%'>Foto</th>");
            html.append("<th width='10%'>Author</th>");
            html.append("<th width='12%'>Tanggal</th>");
            html.append("<th width='10%'>Status</th>");
            html.append("</tr></thead><tbody>");

            Connection conn = config.connection.getConnection();

            for (int i = 0; i < model.getRowCount(); i++) {
                String idBerita = model.getValueAt(i, 0).toString();
                String judul = (model.getValueAt(i, 2) != null) ? model.getValueAt(i, 2).toString() : "-";
                String kategori = (model.getValueAt(i, 3) != null) ? model.getValueAt(i, 3).toString() : "-";
                String penulis = (model.getValueAt(i, 4) != null) ? model.getValueAt(i, 4).toString() : "-";
                String tanggal = (model.getValueAt(i, 5) != null) ? model.getValueAt(i, 5).toString() : "-";
                String status = (model.getValueAt(i, 6) != null) ? model.getValueAt(i, 6).toString() : "-";

                String deskripsi = "-";
                String tagFoto = "-";
                String sql = "SELECT deskripsi_berita, gambar_berita FROM berita WHERE id_berita = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, idBerita);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    deskripsi = rs.getString("deskripsi_berita");
                    String namaFile = rs.getString("gambar_berita");

                    if (namaFile != null && !namaFile.isEmpty()) {
                        File f = new File(pathFolderFoto + namaFile);
                        if (f.exists()) {
                            tagFoto = "<img src='" + f.toURI().toString() + "' class='img-berita' />";
                        }
                    }
                }

                /* if (deskripsi != null && deskripsi.length() > 200) {
                    deskripsi = deskripsi.substring(0, 197) + "...";
                } */

                html.append("<tr>");
                html.append("<td style='text-align:center;'>").append(i + 1).append("</td>");
                html.append("<td>").append(judul).append("</td>");
                html.append("<td>").append(kategori).append("</td>");
                html.append("<td>").append(deskripsi).append("</td>");
                html.append("<td style='text-align:center;'>").append(tagFoto).append("</td>");
                html.append("<td>").append(penulis).append("</td>");
                html.append("<td>").append(tanggal).append("</td>");
                html.append("<td style='text-align:center;'>").append(status).append("</td>");
                html.append("</tr>");
                
                rs.close();
                ps.close();
            }
            html.append("</tbody></table>");

            html.append("<div class='footer'>");
            html.append("<p>Dicetak pada: ").append(new java.util.Date().toString()).append("</p>");
            html.append("<br /><br /><br /><br /><p><b>Admin Desa Sukamaju</b></p>");
            html.append("</div></body></html>");

            InputStream is = new ByteArrayInputStream(html.toString().getBytes(StandardCharsets.UTF_8));
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, StandardCharsets.UTF_8);
            
            document.close();
            System.out.println("PDF Berita Berhasil Dibuat (Data Lengkap dari DB)!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}