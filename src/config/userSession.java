package config;
import java.util.Date;

public class userSession {
    private static userSession instance;

    private int id_users;
    private String email;
    private String password;
    private String nik;
    private String nama;
    private String tempat_lahir;
    private Date tanggal_lahir;
    private String agama;
    private String jenisKelamin;
    private String rt;
    private String rw;
    private String pekerjaan;
    private String pernikahan;
    private String role;
    private int status;

    private userSession() {
        this.id_users = 0;
        this.email = null;
        this.password = null;
        this.nik = null;
        this.nama = null;
        this.tempat_lahir = null;
        this.tanggal_lahir = null;
        this.agama = null;
        this.jenisKelamin = null;
        this.rt = null;
        this.rw = null;
        this.pekerjaan = null;
        this.pernikahan = null;
        this.role = null;
        this.status = 0;
    }

    public static userSession getInstance() {
        if (instance == null) {
            instance = new userSession();
        }
        return instance;
    }

    public void login(int id_users, String email, String password, String nik, String nama, String tempatLahir, Date tanggalLahir,
            String agama, String jenisKelamin, String rt, String rw, String pekerjaan, String pernikahan, String role, int status) {
        this.id_users = id_users;
        this.email = email;
        this.password = password;
        this.nik = nik;
        this.nama = nama;
        this.tempat_lahir = tempatLahir;
        this.tanggal_lahir = tanggalLahir;
        this.agama = agama;
        this.jenisKelamin = jenisKelamin;
        this.rt = rt;
        this.rw = rw;
        this.pekerjaan = pekerjaan;
        this.pernikahan = pernikahan;
        this.role = role;
        this.status = status;
    }

    public void logout() {
        instance = null;
        this.id_users = 0;
        this.email = null;
        this.password = null;
        this.nik = null;
        this.nama = null;
        this.tempat_lahir = null;
        this.tanggal_lahir = null;
        this.agama = null;
        this.jenisKelamin = null;
        this.rt = null;
        this.rw = null;
        this.pekerjaan = null;
        this.pernikahan = null;
        this.role = null;
        this.status = 0;
    }

    public int getIdUsers() {
        return id_users;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNik() {
        return nik;
    }

    public String getNama() {
        return nama;
    }

    public String getTempatLahir() {
        return tempat_lahir;
    }

    public Date getTanggalLahir() {
        return tanggal_lahir;
    }

    public String getAgama() {
        return agama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getRt() {
        return rt;
    }

    public String getRw() {
        return rw;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public String getPernikahan() {
        return pernikahan;
    }

    public String getRole() {
        return role;
    }

    public int getStatus() {
        return status;
    }

    public void login(int idUsers, String namaUsers, String emailUsers, String passwordUsers, String nikUsers, String role, int statusAccount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void login(int idUsers, String emailUsers, String passwordUsers, String nikUsers, String namaUsers, String tempatLahirUsers, String role, int statusAccount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void login(int idUsers, String emailUsers, String passwordUsers, String nikUsers, String namaUsers, String tempatLahirUsers, java.sql.Date tanggalLahirUsers, String agamaUsers, String jenisKelaminUsers, String rtUsers, String rwUsers, String pekerjaanUsers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
