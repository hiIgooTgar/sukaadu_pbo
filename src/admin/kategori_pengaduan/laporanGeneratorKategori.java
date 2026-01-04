package admin.kategori_pengaduan;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.swing.table.DefaultTableModel;

public class laporanGeneratorKategori {

    public static void cetakLaporan(DefaultTableModel model, String outputPath) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            StringBuilder html = new StringBuilder();
            html.append("<html><head><style>");
            html.append("body { font-family: sans-serif; font-size: 10px; }");
            html.append(".header { text-align: center; border-bottom: 2px solid black; padding-bottom: 10px; }");
            html.append(".title { text-align: center; margin: 20px 0; font-size: 16px; font-weight: bold; text-decoration: underline; display:inline-block; padding-top: 15px; }");
            html.append("table { width: 100%; border-collapse: collapse; margin-top: 30px; margin-bottom: 15px; }");
            html.append("th { background-color: rgb(0,153,255); color: white; padding: 5px; border: 1px solid #000; font-size: 13px; }");
            html.append("td { padding: 5px; border: 1px solid #000; vertical-align: middle; font-size: 12px; }");
            html.append(".footer { margin-top: 35px; text-align: right; }");
            html.append("</style></head><body>");

            html.append("<div class='header'>");
            html.append("<h1 style='margin-bottom:0;'>PEMERINTAH DESA SUKAMAJU</h1>");
            html.append("<h3 style='margin-top:2px; margin-bottom:2px;'>APLIKASI LAYANAN PENGADUAN MASYARAKAT (SukaAdu)</h3>");
            html.append("<p style='margin-top:0;'>Jl. Raya SukaMaju No. 1, Kec. SukaAdu, Jawa Tengah 53211</p>");
            html.append("</div>");
            html.append("<hr />");

            html.append("<div class='title'>DATA KATEGORI PENGADUAN</div>");

            html.append("<table>");
            html.append("<thead><tr>");
            html.append("<th width='5%'>No</th>");
            html.append("<th width='35%'>Nama Kategori</th>");
            html.append("<th width='60%'>Deskripsi</th>");
            html.append("</tr></thead><tbody>");

            for (int i = 0; i < model.getRowCount(); i++) {
                String nama = (model.getValueAt(i, 1) != null) ? model.getValueAt(i, 1).toString() : "-";
                String deskripsi = (model.getValueAt(i, 2) != null) ? model.getValueAt(i, 2).toString() : "-";

                html.append("<tr>");
                html.append("<td>").append(i + 1).append("</td>");
                html.append("<td><b>").append(nama).append("</b></td>");
                html.append("<td>").append(deskripsi).append("</td>");
                html.append("</tr>");
            }

            html.append("</tbody></table>");
            html.append("<div class='footer'>");
            html.append("<p>Dicetak pada: ").append(new java.util.Date().toString()).append("</p>");
            html.append("<br /><br /><br /><br />");
            html.append("<p><b>Admin Desa Sukamaju</b></p>");
            html.append("</div>");
            html.append("</body></html>");

            InputStream is = new ByteArrayInputStream(html.toString().getBytes(StandardCharsets.UTF_8));
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, StandardCharsets.UTF_8);
            document.close();
            System.out.println("PDF Kategori Berhasil Dibuat!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}