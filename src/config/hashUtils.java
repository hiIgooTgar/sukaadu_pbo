package config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

public class hashUtils {

    // Metode untuk menghitung MD5 dari string input
    public static String hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            
            // Konversi byte array menjadi signum
            BigInteger no = new BigInteger(1, messageDigest);
            
            // Konversi BigInteger menjadi representasi String Hex (32 karakter)
            String hashText = no.toString(16);
            
            // Pastikan hash selalu 32 karakter dengan menambahkan nol di depan jika perlu
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            
            return hashText;

        } catch (NoSuchAlgorithmException e) {
            // Seharusnya tidak terjadi, karena MD5 standar di Java
            throw new RuntimeException("MD5 Algorithm not found", e);
        }
    }
}