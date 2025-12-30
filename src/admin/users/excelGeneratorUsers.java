package admin.users;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.io.*;
import java.awt.Color;

public class excelGeneratorUsers {

    public static void exportToExcel(DefaultTableModel model, String outputPath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Data Pengguna");
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

            String[] headers = {
                "No", "Email", "NIK", "Nama", "Tempat Lahir", "Tanggal Lahir", 
                "Agama", "Gender", "RT/RW", "Pekerjaan", "Status Pernikahan", "Role", "Status Akun", "Foto Profil"
            };

            Row headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(30);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);

            for (int i = 0; i < model.getRowCount(); i++) {
                XSSFRow row = sheet.createRow(i + 1);
                row.setHeightInPoints(70);

                row.createCell(0).setCellValue(i + 1);                         
                row.createCell(1).setCellValue(val(model.getValueAt(i, 2)));    
                row.createCell(2).setCellValue(val(model.getValueAt(i, 4)));   
                row.createCell(3).setCellValue(val(model.getValueAt(i, 5)));   
                row.createCell(4).setCellValue(val(model.getValueAt(i, 6)));  
                row.createCell(5).setCellValue(val(model.getValueAt(i, 7)));    
                row.createCell(6).setCellValue(val(model.getValueAt(i, 8)));    
                row.createCell(7).setCellValue(val(model.getValueAt(i, 9)));  
                row.createCell(8).setCellValue(val(model.getValueAt(i, 10)) + "/" + val(model.getValueAt(i, 11))); 
                row.createCell(9).setCellValue(val(model.getValueAt(i, 12)));   
                row.createCell(10).setCellValue(val(model.getValueAt(i, 13)));  
                row.createCell(11).setCellValue(val(model.getValueAt(i, 14)));  
                row.createCell(12).setCellValue(val(model.getValueAt(i, 15)));  

                String idUser = val(model.getValueAt(i, 0));
                String fileName = getNamaGambarUser(idUser);
                insertProfileImage(workbook, drawing, fileName, i + 1, 13);

                for (int j = 0; j < headers.length; j++) {
                    if (row.getCell(j) == null) row.createCell(j);
                    row.getCell(j).setCellStyle(dataStyle);
                }
            }

            for (int i = 0; i < headers.length - 1; i++) {
                sheet.autoSizeColumn(i);
            }
            sheet.setColumnWidth(13, 15 * 256); 

            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal Export: " + e.getMessage());
        }
    }

    private static String getNamaGambarUser(String id) {
        String img = "default_profile.jpg";
        try {
            java.sql.Connection conn = config.connection.getConnection();
            String sql = "SELECT img_profile FROM users WHERE id_users = ?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String dbImg = rs.getString("img_profile");
                if (dbImg != null && !dbImg.isEmpty()) img = dbImg;
            }
        } catch (Exception e) {
            System.err.println("Gagal ambil foto: " + e.getMessage());
        }
        return img;
    }

    private static void insertProfileImage(XSSFWorkbook wb, XSSFDrawing drawing, String fileName, int rowNum, int colNum) {
        try {
            File imgFile = new File("src/uploads/profile/" + fileName);
            if (!imgFile.exists()) {
                imgFile = new File("src/uploads/profile/default_profile.jpg");
            }
            
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
            
            anchor.setDx1(10 * 10000); 
            anchor.setDy1(10 * 10000);

            drawing.createPicture(anchor, pictureIdx);
        } catch (Exception e) {
            System.err.println("Error insert image: " + e.getMessage());
        }
    }

    private static String val(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
}