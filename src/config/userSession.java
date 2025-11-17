package config;

public class userSession {

    private static userSession instance;

    private int id_users;
    private String nama;
    private String email;
    private String password;
    private String gender;
    private String alamat;
    private String role;
    private int status;

    private userSession() {
        this.id_users = 0;
        this.nama = null;
        this.email = null;
        this.password = null;
        this.gender = null;
        this.alamat = null;
        this.role = null;
        this.status = 0;
    }

    public static userSession getInstance() {
        if (instance == null) {
            instance = new userSession();
        }
        return instance;
    }

    public void login(int id_users, String nama, String email, String password, String gender, String alamat, String role, int status) {
        this.id_users = id_users;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.alamat = alamat;
        this.role = role;
        this.status = status;
    }

    public void logout() {
        instance = null;
        this.id_users = 0;
        this.nama = null;
        this.email = null;
        this.password = null;
        this.gender = null;
        this.alamat = null;
        this.role = null;
        this.status = 0;
    }

    public int getIdUsers() {
        return id_users;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getRole() {
        return role;
    }

    public int getStatus() {
        return status;
    }
}
