package masyarakat.laporan;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.io.FileOutputStream;
import java.io.File;

public class excelGeneratorPengaduan {

    public static void exportToExcel(DefaultTableModel model, String outputPath) {
        // Inisialisasi menggunakan XSSFWorkbook langsung
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data Pengaduan");

            // --- STYLE HEADER ---
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            Font font = workbook.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(font);

            // --- HEADER ---
            String[] headers = {"No", "ID", "Tanggal", "Judul", "Deskripsi", "Kategori", "Status"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // --- ISI DATA ---
            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(val(model.getValueAt(i, 1))); 
                row.createCell(2).setCellValue(val(model.getValueAt(i, 2))); 
                row.createCell(3).setCellValue(val(model.getValueAt(i, 3))); 
                row.createCell(4).setCellValue(val(model.getValueAt(i, 4))); 
                row.createCell(5).setCellValue(val(model.getValueAt(i, 5))); 
                row.createCell(6).setCellValue(val(model.getValueAt(i, 7))); 
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // --- PROSES SIMPAN ---
            try (FileOutputStream fileOut = new FileOutputStream(new File(outputPath))) {
                workbook.write(fileOut);
                fileOut.flush(); // Memaksa data keluar ke file
            }
            
            JOptionPane.showMessageDialog(null, "Export Excel Berhasil!");

        } catch (NoSuchMethodError e) {
            JOptionPane.showMessageDialog(null, "Kritikal Error: Library Commons-Compress 1.21 tidak terbaca.\n"
                    + "Tolong lakukan 'Clean and Build' sekarang.", "Library Conflict", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String val(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
}