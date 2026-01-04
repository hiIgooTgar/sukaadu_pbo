package admin.users;

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

public class laporanGeneratorUsers {

    public static void cetakLaporan(DefaultTableModel model, String outputPath) {
        Document document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            String pathFolderFoto = new File("src/uploads/profile/").getAbsolutePath() + File.separator;

            StringBuilder html = new StringBuilder();
            html.append("<html><head><style>");
            html.append("body { font-family: sans-serif; font-size: 10px; }");
            html.append(".header { text-align: center; border-bottom: 2px solid black; padding-bottom: 10px; }");
            html.append(".title { text-align: center; margin: 20px 0; font-size: 16px; font-weight: bold; text-decoration: underline; display:inline-block; padding-top: 15px; }");
            html.append("table { width: 100%; border-collapse: collapse; margin-top: 30px; margin-bottom: 15px; }");
            html.append("th { background-color: rgb(0,153,255); color: white; padding: 5px; border: 1px solid #000; font-size: 12px; }");
            html.append("td { padding: 5px; border: 1px solid #000; vertical-align: middle; font-size: 11px; }");
            html.append(".img-profile { width: 45px; height: 45px; }");
            html.append(".footer { margin-top: 35px; text-align: right; }");
            html.append("</style></head><body>");

            html.append("<div class='header'>");
            html.append("<h1 style='margin-bottom:0;'>PEMERINTAH DESA SUKAMAJU</h1>");
            html.append("<h3 style='margin-top:2px; margin-bottom:2px;'>APLIKASI LAYANAN PENGADUAN MASYARAKAT (SukaAdu)</h3>");
            html.append("<p style='margin-top:0;'>Jl. Raya SukaMaju No. 1, Kec. SukaAdu, Jawa Tengah 53211</p>");
            html.append("</div>");
            html.append("<hr />");

            html.append("<div class='title'>DATA REKAPITULASI USER</div>");

            html.append("<table>");
            html.append("<thead><tr>");
            html.append("<th width='3%'>No</th><th width='7%'>Foto</th><th width='15%'>Nama Lengkap</th>");
            html.append("<th width='14%'>NIK</th><th width='12%'>Email</th><th width='15%'>TTL</th>");
            html.append("<th width='7%'>Gender</th><th width='5%'>RT/RW</th><th width='9%'>Pekerjaan</th>");
            html.append("<th width='8%'>Role</th><th width='5%'>Status</th>");
            html.append("</tr></thead><tbody>");

            Connection conn = config.connection.getConnection();
            String sql = "SELECT img_profile FROM users WHERE id_users = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 0; i < model.getRowCount(); i++) {
                String idUsers = (model.getValueAt(i, 0) != null) ? model.getValueAt(i, 0).toString() : "";
                String email = (model.getValueAt(i, 2) != null) ? model.getValueAt(i, 2).toString() : "-";
                String nik = (model.getValueAt(i, 4) != null) ? model.getValueAt(i, 4).toString() : "-";
                String nama = (model.getValueAt(i, 5) != null) ? model.getValueAt(i, 5).toString() : "-";
                String tmpLahir = (model.getValueAt(i, 6) != null) ? model.getValueAt(i, 6).toString() : "";
                String tglLahir = (model.getValueAt(i, 7) != null) ? model.getValueAt(i, 7).toString() : "";
                String ttl = tmpLahir + ", " + tglLahir;
                String gender = (model.getValueAt(i, 9) != null) ? model.getValueAt(i, 9).toString() : "-";
                String rt = (model.getValueAt(i, 10) != null) ? model.getValueAt(i, 10).toString() : "0";
                String rw = (model.getValueAt(i, 11) != null) ? model.getValueAt(i, 11).toString() : "0";
                String rtrw = rt + "/" + rw;
                String pekerjaan = (model.getValueAt(i, 12) != null) ? model.getValueAt(i, 12).toString() : "-";
                String role = (model.getValueAt(i, 14) != null) ? model.getValueAt(i, 14).toString() : "-";
                String status = (model.getValueAt(i, 15) != null) ? model.getValueAt(i, 15).toString() : "-";

                String imgTag = "-";
                if (!idUsers.isEmpty()) {
                    ps.setString(1, idUsers);
                    try (ResultSet rs = ps.executeQuery()) {
                        String fileName = "default_profile.jpg";
                        if (rs.next()) {
                            String dbImg = rs.getString("img_profile");
                            if (dbImg != null && !dbImg.isEmpty()) {
                                fileName = dbImg;
                            }
                        }

                        File f = new File(pathFolderFoto + fileName);
                        if (!f.exists()) {
                            f = new File(pathFolderFoto + "default_profile.jpg");
                        }

                        if (f.exists()) {
                            imgTag = "<img src='" + f.toURI().toString() + "' class='img-profile' />";
                        }
                    }
                }

                html.append("<tr>");
                html.append("<td>").append(i + 1).append("</td>");
                html.append("<td>").append(imgTag).append("</td>");
                html.append("<td style='text-align:left;'>").append(nama).append("</td>");
                html.append("<td>").append(nik).append("</td>");
                html.append("<td>").append(email).append("</td>");
                html.append("<td>").append(ttl).append("</td>");
                html.append("<td>").append(gender).append("</td>");
                html.append("<td>").append(rtrw).append("</td>");
                html.append("<td>").append(pekerjaan).append("</td>");
                html.append("<td>").append(role).append("</td>");
                html.append("<td>").append(status).append("</td>");
                html.append("</tr>");
            }

            ps.close();

            html.append("</tbody></table>");
            html.append("<div class='footer'>");
            html.append("<p>Dicetak pada: ").append(new java.util.Date().toString()).append("</p>");
            html.append("<br/><br/><br/><p><b>Kepala Desa Sukamaju</b></p>");
            html.append("</div></body></html>");

            InputStream is = new ByteArrayInputStream(html.toString().getBytes(StandardCharsets.UTF_8));
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, StandardCharsets.UTF_8);
            document.close();

        } catch (Exception e) {
            System.err.println("Error saat cetak PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
