import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Data {
    private final Connection conn = Koneksi.getConnection();

    // Method untuk menambahkan data ke tabel yang sesuai (tambah data soal dan pemain)
    public void addData(String table, Object... params) {
        String query = "";

        if (table.equalsIgnoreCase("soal")) {
            query = """
                    INSERT INTO soal (pertanyaan, pilihan_A, pilihan_B, pilihan_C, pilihan_D, jawaban_benar, kategori)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """;
        } else if (table.equalsIgnoreCase("pemain")) {
            query = """
                    INSERT INTO pemain (nama, skor_terakhir, skor_tertinggi, soal_terjawab, soal_benar, soal_salah, waktu_selesai)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """;
        } else {
            System.err.println("Tabel yang dimasukkan tidak valid.");
            return;
        }

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            statement.executeUpdate();
            System.out.println("Data berhasil ditambahkan ke tabel " + table + "!");
        } catch (SQLException e) {
            System.err.println("Gagal menambahkan data ke tabel " + table + ".");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Data data = new Data();

        // Soal-soal kategori Java
        data.addData("soal", "Apa fungsi dari keyword class dalam Java?", "Menghentikan eksekusi program", "Mendeklarasikan sebuah kelas", "Membuat array", 
            "Mendeklarasikan variabel", "Mendeklarasikan sebuah kelas", "Java");
        data.addData("soal", "Apa ekstensi file untuk program Java?", ".java", ".class", ".jar", ".exe", ".java", "Java");
        data.addData("soal", "Manakah tipe data yang digunakan untuk menyimpan angka desimal?", "int", "float", "char", "boolean", "float", "Java");
        data.addData("soal", "Apa hasil dari perintah berikut? System.out.println(10 + 5);", "10", "5", "15", "Error", "15", "Java");
        data.addData("soal", "Apa output dari kode berikut? String str = \"Java\"; System.out.println(str.length());", "3", "4", "5", "Error", "4", "Java");
        data.addData("soal", "Apa yang terjadi jika main() tidak dideklarasikan sebagai static?", "Program akan berjalan normal", "Error pada runtime",
            "Error saat kompilasi", "Tidak ada yang terjadi", "Error saat kompilasi", "Java");
        data.addData("soal", "Bagaimana cara membuat array dengan 10 elemen bertipe int?", "int arr[] = {10};", "int arr[10];", "int arr[] = new int[10];", "int arr = new int[10];", "int arr[] = new int[10];", "Java");
        data.addData("soal", "Apa output dari kode berikut? int a = 5, b = 10; a++; b--; System.out.println(a + b);", "15", "14", "16", "Error", "16", "Java");
        data.addData("soal", "Apa yang dimaksud dengan Overloading dalam Java?", "Dua metode dengan nama yang sama, parameter berbeda", "Dua metode dengan nama berbeda, parameter sama",
            "Variabel yang digunakan dua kali", "Penggunaan memori berlebih", "Dua metode dengan nama yang sama, parameter berbeda", "Java");
        data.addData("soal", "Apa tujuan dari keyword final pada variabel di Java?", "Membuat variabel bersifat konstan", "Membuat variabel dapat diubah", "Menentukan akhir program", "Membuat variabel static", "Membuat variabel bersifat konstan", "Java");
        data.addData("soal", "Apa yang dimaksud dengan Generics di Java?", "Membuat kelas atau metode yang hanya menerima tipe data tertentu",
            "Membuat kelas atau metode yang dapat bekerja dengan berbagai tipe data", "Membuat kelas abstrak", "Membuat metode statis", "Membuat kelas atau metode yang dapat bekerja dengan berbagai tipe data", "Java");
        data.addData("soal", "Apa tujuan utama dari Garbage Collector di Java?", "Menghapus variabel yang tidak digunakan","Meningkatkan performa aplikasi",
            "Mengelola memori dengan menghapus objek yang tidak direferensikan lagi", "Mengoptimalkan pengolahan data dalam array","Mengelola memori dengan menghapus objek yang tidak direferensikan lagi", "Java");
        data.addData("soal", "Apa perbedaan utama antara HashMap dan TreeMap di Java?", "HashMap menggunakan urutan alfanumerik, sedangkan TreeMap tidak.",
            "TreeMap lebih cepat dibandingkan HashMap.", "HashMap tidak menjamin urutan, sedangkan TreeMap mengurutkan berdasarkan kunci.", "Tidak ada perbedaan antara keduanya.",
            "HashMap tidak menjamin urutan, sedangkan TreeMap mengurutkan berdasarkankunci.", "Java");
        data.addData("soal", "Apa yang terjadi jika kita mencoba menambahkan elemen ke ArrayList saat iterasi menggunakan Iterator tanpa Iterator.remove()?",
            "Elemen akan ditambahkan tanpa masalah.", "Terjadi ConcurrentModificationException.", "Elemen ditambahkan, tetapi urutannya tidak terjamin.", "Program berhenti tanpa error.",
            "Terjadi ConcurrentModificationException.", "Java");
        data.addData("soal", "Apa yang terjadi jika dua thread mencoba mengakses metode yang disinkronkan (synchronized) pada objek yang sama secara bersamaan?",
            "Kedua thread akan dieksekusi secara bersamaan tanpa masalah.", "Salah satu thread akan dihentikan sementara waktu sampai thread lainnya selesai.", "Kedua thread akan menghasilkan deadlock.",
            "Kedua thread akan gagal dan throw exception IllegalStateException.", "Salah satu thread akan dihentikan sementara waktu sampai thread lainnyaselesai.", "Java");

        // Soal-soal kategori Python
        data.addData("soal", "Apa fungsi dari keyword def dalam Python?", "Mendeklarasikan fungsi", "Mendeklarasikan variabel", "Menjalankan fungsi",
            "Menentukan tipe data", "Mendeklarasikan fungsi", "Python");
        data.addData("soal", "Bagaimana cara mencetak teks 'Hello World' di Python?", "System.out.println('Hello World')", "echo 'Hello World'", "print('Hello World')",
            "console.log('Hello World')", "print('Hello World')", "Python");
        data.addData("soal", "Apa ekstensi file untuk program Python?", ".py", ".java", ".class", ".pyt",".py", "Python");
        data.addData("soal", "Manakah tipe data yang digunakan untuk menyimpan teks di Python?", "int", "string", "float", "bool", "string", "Python");
        data.addData("soal", "Apa hasil dari kode berikut? print(2 ** 3)", "5", "6", "8", "9", "8", "Python");
        data.addData("soal", "Apa library Python untuk manipulasi data?", "Numpy", "Pandas", "Scikit-learn", "Semua benar", "Semua benar","Python");
        data.addData("soal", "Apa metode untuk menambahkan elemen ke list di Python?", "add()", "insert()", "append()", "push()", "append()","Python");
        data.addData("soal", "Apa perbedaan antara list dan tuple?", "List tidak dapat diubah, tuple dapat", "Tuple tidak dapat diubah, list dapat",
            "Tidak ada perbedaan", "Tuple hanya untuk angka", "Tuple tidak dapat diubah, list dapat", "Python");
        data.addData("soal", "Apa output dari kode berikut?\nx = [1, 2, 3]\nprint(x[3])", "3", "IndexError", "None", "4", "IndexError", "Python");
        data.addData("soal", "Apa arti dari `*args` dalam sebuah fungsi?", "Menyatakan jumlah argumen yang tetap", "Menyatakan argumen variabel", "Menyatakan argumen keyword",
            "Menyatakan argumen yang wajib", "Menyatakan argumen variabel", "Python");
        data.addData("soal", "Apa fungsi dari `len()` dalam Python?", "Menghapus elemen list", "Mengembalikan panjang dari list atau string", "Menambahkan elemen ke list",
            "Memotong string", "Mengembalikan panjang dari list atau string", "Python");
        data.addData("soal", "Apa itu dictionary di Python?", "Tipe data seperti array", "Tipe data key-value pairs", "Tipe data immutable", "Tipe data berbentuk tuple",
            "Tipe data key-value pairs", "Python");
        data.addData("soal", "Apa fungsi dari `lambda` dalam Python?", "Mendeklarasikan class", "Mendeklarasikan fungsi anonymous", "Menginisialisasi variabel",
            "Membuat loop", "Mendeklarasikan fungsi anonymous", "Python");
        data.addData("soal", "Apa output dari kode berikut?\n`print(10 // 3)`", "3.33", "3", "Error", "4", "3","Python");
        data.addData("soal", "Apa itu Python Virtual Environment?", "Lingkungan fisik untuk Python", "Isolasi lingkungan Python untuk proyek", "Lingkungan yang digunakan untuk deployment",
            "Alat untuk debugging Python", "Isolasi lingkungan Python untuk proyek", "Python");

        System.out.println("Semua soal berhasil ditambahkan!");
    }

}
