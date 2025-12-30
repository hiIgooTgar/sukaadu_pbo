package admin.rekap_laporan;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.io.*;
import java.awt.Color;
import org.apache.poi.util.IOUtils;

public class excelGeneratorPengaduan {

    public static void exportToExcel(DefaultTableModel model, String outputPath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Data Pengaduan");
            XSSFDrawing drawing = sheet.createDrawingPatriarch();

            XSSFColor customBlue = new XSSFColor(new java.awt.Color(51, 153, 255), null);

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

            String[] headers = {"No", "ID", "Tanggal", "Nama Pengadu", "Judul", "Deskripsi", "Kategori", "Status", "Tanggapan", "Foto"};
            Row headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(30);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setWrapText(true);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);

            for (int i = 0; i < model.getRowCount(); i++) {
                XSSFRow row = sheet.createRow(i + 1);
                row.setHeightInPoints(100); 

                row.createCell(0).setCellValue(i + 1);                  
                row.createCell(1).setCellValue(val(model.getValueAt(i, 1))); 
                row.createCell(2).setCellValue(val(model.getValueAt(i, 2))); 
                row.createCell(3).setCellValue(val(model.getValueAt(i, 3))); 
                row.createCell(4).setCellValue(val(model.getValueAt(i, 4))); 
                row.createCell(5).setCellValue(val(model.getValueAt(i, 5))); 
                row.createCell(6).setCellValue(val(model.getValueAt(i, 6))); 
                row.createCell(7).setCellValue(val(model.getValueAt(i, 7))); 
                row.createCell(8).setCellValue(val(model.getValueAt(i, 8))); 

                String fileName = val(model.getValueAt(i, 9)); 
                if (!fileName.equals("-") && !fileName.isEmpty()) {
                    insertImage(workbook, drawing, fileName, i + 1, 9);
                }

                for (int j = 0; j < headers.length; j++) {
                    if (row.getCell(j) == null) row.createCell(j);
                    row.getCell(j).setCellStyle(dataStyle);
                }
            }

            for (int i = 0; i < headers.length; i++) {
                if (i == 9) {
                    sheet.setColumnWidth(i, 20 * 256); 
                } else {
                    sheet.setColumnWidth(i, 15 * 256);
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal Export: " + e.getMessage());
        }
    }

    private static void insertImage(XSSFWorkbook wb, XSSFDrawing drawing, String fileName, int rowNum, int colNum) {
        try {
            File imgFile = new File("src/uploads/pengaduan/" + fileName);
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
            anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
            drawing.createPicture(anchor, pictureIdx);
        } catch (Exception e) {
            System.out.println("Gagal menyisipkan gambar: " + fileName);
        }
    }

    private static String val(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
}