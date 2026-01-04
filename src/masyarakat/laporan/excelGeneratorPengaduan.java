package masyarakat.laporan;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;   
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.io.FileOutputStream;
import java.io.File;
import java.awt.Color; 

public class excelGeneratorPengaduan {

    public static void exportToExcel(DefaultTableModel model, String outputPath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data Pengaduan");

            Color myColor = new Color(51, 153, 255);
            XSSFColor customBlue = new XSSFColor(myColor, null);

            XSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(customBlue);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);

            Font font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(font);

            String[] headers = {"No", "ID Pengaduan", "Tanggal", "Judul", "Deskripsi", "Kategori", "Status"};
            Row headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(25); 

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);

            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                
                createStyledCell(row, 0, String.valueOf(i + 1), dataStyle);
                createStyledCell(row, 1, val(model.getValueAt(i, 1)), dataStyle);
                createStyledCell(row, 2, val(model.getValueAt(i, 2)), dataStyle);
                createStyledCell(row, 3, val(model.getValueAt(i, 3)), dataStyle);
                createStyledCell(row, 4, val(model.getValueAt(i, 4)), dataStyle);
                createStyledCell(row, 5, val(model.getValueAt(i, 5)), dataStyle);
                createStyledCell(row, 6, val(model.getValueAt(i, 7)), dataStyle);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            File file = new File(outputPath);
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                fileOut.flush();
            }

        } catch (NoSuchMethodError | NoClassDefFoundError e) {
            JOptionPane.showMessageDialog(null, "Kesalahan Library (Konflik JAR):\n" + e.getMessage() + 
                "\n\nPastikan Anda sudah menghapus commons-compress-1.0.jar dan melakukan Clean and Build.", "Library Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Export: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private static void createStyledCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private static String val(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
}