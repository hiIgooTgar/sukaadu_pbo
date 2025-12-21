package masyarakat.pengaduan;

public class kategoriPengaduan {

    private int id;
    private String nama;

    public kategoriPengaduan(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return nama; 
    }
}
