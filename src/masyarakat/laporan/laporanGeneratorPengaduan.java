package masyarakat.laporan;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.swing.table.DefaultTableModel;

public class laporanGeneratorPengaduan {

    public static void cetakLaporan(DefaultTableModel model, String outputPath) {
        Document document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            String pathFolderFoto = new File("src/uploads/pengaduan/").getAbsolutePath() + File.separator;

            StringBuilder html = new StringBuilder();
             html.append("<html><head><style>");
            html.append("body { font-family: sans-serif; font-size: 10px; }");
            html.append(".header { text-align: center; border-bottom: 2px solid black; padding-bottom: 10px; }");
            html.append(".title { text-align: center; margin: 20px 0; font-size: 16px; font-weight: bold; text-decoration: underline; }");
            html.append("table { width: 100%; border-collapse: collapse; margin-top: 30px; margin-bottom: 15px; }");
            html.append("th { background-color: rgb(0,153,255); color: white; padding: 5px; border: 1px solid #000; font-size: 13px; }");
            html.append("td { padding: 5px; border: 1px solid #000; vertical-align: middle; font-size: 12px; }");
            html.append(".img-box { width: 80px; height: 60px; text-align: center; }");
            html.append(".footer { margin-top: 35px; text-align: right; }");
            html.append("</style></head><body>");

             html.append("<div class='header'>");
            html.append("<h1 style='margin-bottom:0;'>PEMERINTAH DESA SUKAMAJU</h1>");
            html.append("<h3 style='margin-top:2px; margin-bottom:2px;'>APLIKASI LAYANAN PENGADUAN MASYARAKAT (SukaAdu)</h3>");
            html.append("<p style='margin-top:0;'>Jl. Raya SukaMaju No. 1, Kec. SukaAdu, Jawa Tengah 53211</p>");
            html.append("</div>");

            html.append("<div class='title'>LAPORAN PENGADUAN</div>");
            
            html.append("<table>");
            html.append("<thead><tr>");
            html.append("<th width='4%'>No</th>");
            html.append("<th width='12%'>Tanggal</th>");
            html.append("<th width='15%'>Judul</th>");
            html.append("<th width='30%'>Deskripsi</th>");
            html.append("<th width='12%'>Kategori</th>");
            html.append("<th width='15%'>Foto</th>");
            html.append("<th width='12%'>Status</th>");
            html.append("</tr></thead><tbody>");

            for (int i = 0; i < model.getRowCount(); i++) {
                String tgl       = (model.getValueAt(i, 2) != null) ? model.getValueAt(i, 2).toString() : "-";
                String judul     = (model.getValueAt(i, 3) != null) ? model.getValueAt(i, 3).toString() : "-";
                String deskripsi = (model.getValueAt(i, 4) != null) ? model.getValueAt(i, 4).toString() : "-";
                String kategori  = (model.getValueAt(i, 5) != null) ? model.getValueAt(i, 5).toString() : "-";
                String namaFoto  = (model.getValueAt(i, 6) != null) ? model.getValueAt(i, 6).toString() : "";
                String status    = (model.getValueAt(i, 7) != null) ? model.getValueAt(i, 7).toString() : "-";

                String tagFoto = "<i>Tidak ada foto</i>";
                if (!namaFoto.isEmpty() && !namaFoto.equals("-")) {
                    File f = new File(pathFolderFoto + namaFoto);
                    if (f.exists()) {
                        tagFoto = "<img src='" + f.toURI().toString() + "' class='img-box' />";
                    }
                }

                html.append("<tr>");
                html.append("<td>").append(i + 1).append("</td>");
                html.append("<td>").append(tgl).append("</td>");
                html.append("<td>").append(judul).append("</td>");
                html.append("<td>").append(deskripsi).append("</td>");
                html.append("<td>").append(kategori).append("</td>");
                html.append("<td style='text-align:center;'>").append(tagFoto).append("</td>");
                html.append("<td style='text-align:center;'><b>").append(status.toUpperCase()).append("</b></td>");
                html.append("</tr>");
            }

            html.append("</tbody></table>");
            html.append("<div class='footer'>");
            html.append("<p>Dicetak pada: ").append(new java.util.Date().toString()).append("</p>");
            html.append("<br/><br/><br/><p><b>Petugas Layanan Aduan</b></p>");
            html.append("</div></body></html>");

            InputStream is = new ByteArrayInputStream(html.toString().getBytes(StandardCharsets.UTF_8));
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, StandardCharsets.UTF_8);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}