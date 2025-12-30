package admin.berita;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.io.*;
import java.awt.Color;

public class excelGeneratorBerita {

    public static void exportToExcel(DefaultTableModel model, String outputPath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Data Berita");
            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            XSSFColor customBlue = new XSSFColor(new Color(51, 153, 255), null);

            XSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(customBlue);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);

            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(font);

            String[] headers = {"No", "ID", "Judul Berita", "Kategori", "Penulis", "Tanggal", "Status", "Gambar"};
            Row headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(30);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setAlignment(HorizontalAlignment.LEFT);
            dataStyle.setWrapText(true);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);

            for (int i = 0; i < model.getRowCount(); i++) {
                XSSFRow row = sheet.createRow(i + 1);
                row.setHeightInPoints(80); 

                row.createCell(0).setCellValue(i + 1);                       
                row.createCell(1).setCellValue(val(model.getValueAt(i, 0))); 
                row.createCell(2).setCellValue(val(model.getValueAt(i, 2))); 
                row.createCell(3).setCellValue(val(model.getValueAt(i, 3))); 
                row.createCell(4).setCellValue(val(model.getValueAt(i, 4))); 
                row.createCell(5).setCellValue(val(model.getValueAt(i, 5))); 
                row.createCell(6).setCellValue(val(model.getValueAt(i, 6))); 

                String idBerita = val(model.getValueAt(i, 0));
                String namaGambar = getNamaGambar(idBerita);
                
                if (namaGambar != null && !namaGambar.isEmpty()) {
                    insertImage(workbook, drawing, namaGambar, i + 1, 7);
                }

                for (int j = 0; j < headers.length; j++) {
                    if(row.getCell(j) == null) row.createCell(j);
                    row.getCell(j).setCellStyle(dataStyle);
                }
            }

            for (int i = 0; i < headers.length; i++) {
                if (i == 7) sheet.setColumnWidth(i, 20 * 256); 
                else sheet.setColumnWidth(i, 18 * 256);
            }

            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal Export: " + e.getMessage());
        }
    }

    private static String getNamaGambar(String id) {
        String nama = "";
        try {
            java.sql.Connection conn = config.connection.getConnection();
            String sql = "SELECT gambar_berita FROM berita WHERE id_berita = ?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) nama = rs.getString("gambar_berita");
        } catch (Exception e) {
            System.out.println("Gagal ambil nama gambar: " + e.getMessage());
        }
        return nama;
    }

    private static void insertImage(XSSFWorkbook wb, XSSFDrawing drawing, String fileName, int rowNum, int colNum) {
        try {
            File imgFile = new File("src/uploads/berita/" + fileName);
            if (!imgFile.exists()) return;

            InputStream is = new FileInputStream(imgFile);
            byte[] bytes = IOUtils.toByteArray(is);
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            is.close();

            XSSFCreationHelper helper = wb.getCreationHelper();
            XSSFClientAnchor anchor = helper.createClientAnchor();

            anchor.setCol1(colNum);
            anchor.setRow1(rowNum);
            anchor.setCol2(colNum + 1);
            anchor.setRow2(rowNum + 1);

            drawing.createPicture(anchor, pictureIdx);
        } catch (Exception e) {
            System.out.println("Error insert image: " + e.getMessage());
        }
    }

    private static String val(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
}