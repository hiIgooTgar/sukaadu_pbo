package admin.berita;

public class kategoriBerita {

    private int id;
    private String nama;

    public kategoriBerita(int id, String nama) {
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
