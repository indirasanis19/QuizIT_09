import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Data {
    private final Connection conn = Koneksi.getConnection();

    // Method untuk menambahkan data ke tabel yang sesuai (tambah data soal dan pemain)
    public void addData(String table, Object... params) {
        String queryInsert = "";
        String queryCheck = "";

        if (table.equalsIgnoreCase("soal")) {
            queryInsert = """
                    INSERT INTO soal (pertanyaan, pilihan_A, pilihan_B, pilihan_C, pilihan_D, jawaban_benar, kategori)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """;
            queryCheck = """
                    SELECT COUNT(*) FROM soal WHERE pertanyaan = ?
                    """;
        } else if (table.equalsIgnoreCase("pemain")) {
            queryInsert = """
                    INSERT INTO pemain (nama, skor_terakhir, skor_tertinggi, soal_terjawab, soal_benar, soal_salah, waktu_selesai)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """;
        } else {
            System.err.println("Tabel yang dimasukkan tidak valid.");
            return;
        }

        try (PreparedStatement checkStatement = conn.prepareStatement(queryCheck)) {
            checkStatement.setObject(1, params[0]);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Data sudah ada di tabel");
                return;
            }
        } catch (SQLException e) {
            System.err.println("Gagal memeriksa data di tabel " + table + ".");
            e.printStackTrace();
            return;
    }

        try (PreparedStatement insertStatement = conn.prepareStatement(queryInsert)) {
            for (int i = 0; i < params.length; i++) {
                insertStatement.setObject(i + 1, params[i]);
            }
            insertStatement.executeUpdate();
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
        data.addData("soal", "Apa output dari kode berikut?\nString str = \"Java\";\nSystem.out.println(str.length());", "3", "4", "5", "Error", "4", "Java");
        data.addData("soal", "Apa yang terjadi jika main() tidak dideklarasikan sebagai static?", "Program akan berjalan normal", "Error pada runtime",
            "Error saat kompilasi", "Tidak ada yang terjadi", "Error saat kompilasi", "Java");
        data.addData("soal", "Bagaimana cara membuat array dengan 10 elemen bertipe int?", "int arr[] = {10};", "int arr[10];", "int arr[] = new int[10];", "int arr = new int[10];", "int arr[] = new int[10];", "Java");
        data.addData("soal", "Apa output dari kode berikut?\nint a = 5, b = 10;\na++;\nb--; System.out.println(a + b);", "15", "14", "16", "Error", "16", "Java");
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
        data.addData("soal", "Apa hasil dari kode berikut?\nprint(2 ** 3)", "5", "6", "8", "9", "8", "Python");
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

        // Soal-soal kategori C++
        data.addData("soal", "Manakah tipe data berikut yang digunakan untuk menyimpan bilangan pecahan?", "int", "float", "char", "bool", "float", "C++");
        data.addData("soal", "Apa output dari kode berikut?\n#include <iostream>\nusing namespace std;\nint main(){\n    cout << \"Hello, World!\" << endl;\n    return 0;\n}", "Hello World", "Hello, World!", "Hello World!", "Error", "Hello, World!", "C++");
        data.addData("soal", "Pernyataan apa yang digunakan untuk mencetak output ke layar?", "printf", "print()", "cout", "scanf", "cout", "C++");
        data.addData("soal", "Di antara berikut, manakah header file standar untuk manipulasi input/output di C++?", "conio.h", "iostream", "stdlib.h", "string", "iostream", "C++");
        data.addData("soal", "Apa hasil dari kode berikut?\n#include <iostream>\nusing namespace std;\nint main(){\n    int x = 5;\n    int y = 10;\n    x += y;\n    cout << x << endl;\n    return 0;\n}", "10", "15", "x", "5", "15", "C++");
        data.addData("soal", "Apa fungsi dari keyword return di dalam fungsi C++?", "Menyimpan variabel", "Mengakhiri fungsi dan mengembalikan nilai", "Mencetak nilai", "Membuat loop", "Mengakhiri fungsi dan mengembalikan nilai", "C++");
        data.addData("soal", "Apa yang dimaksud dengan pointer di C++?", "Variabel yang menyimpan nilai integer", "Objek untuk menangani input/output", "Variabel yang menunjuk ke alamat memori variabel lain", "Fungsi untuk memanipulasi string", "Variabel yang menunjuk ke alamat memori variabel lain", "C++");
        data.addData("soal", "Apa hasil dari kode berikut?\n#include <iostream>\nusing namespace std;\nint main(){\n    int arr[3] = {7, 12, 48};\n    cout << arr[2] << endl;\n    return 0;\n}", "48", "7", "12", "2", "48", "C++");
        data.addData("soal", "Bagaimana cara membuat komentar di C++?", "/* Ini komentar", "# Ini komentar", "// Ini komentar", "< Ini komentar >", "// Ini komentar", "C++");
        data.addData("soal", "Method atau fungsi yang digunakan untuk mendapatkan panjang string dalam C++ adalah...", "length()", "getZise()", "len()", "getLen()", "length()", "C++");
        data.addData("soal", "Cara yang benar untuk mendeklarasikan array di C++ adalah...", "int nilai{10};", "int nilai[10];", "int [] nilai = new int[10];", "int nilai;", "int nilai[10];", "C++");
        data.addData("soal", "Apa yang akan terjadi jika kita mencoba menghapus pointer yang telah dihapus sebelumnya dengan delete?", "Program akan berjalan normal tanpa masalah", "Program akan secara otomatis mengabaikan operasi tersebut", "Program akan menghasilkan error kompilasi", "Program akan mengalami undefined behavior", "Program akan mengalami undefined behavior", "C++");
        data.addData("soal", "Apa efek dari penggunaan kata kunci explicit pada konstruktor di C++?", "Mencegah konstruktor dipanggil secara implisit dalam konteks konversi tipe", "Mengizinkan konversi tipe otomatis antara objek yang tidak kompatibel", "Membatasi penggunaan konstruktor hanya pada objek bertipe tertentu", "Menjamin bahwa konstruktor hanya bisa dipanggil sekali", "Mencegah konstruktor dipanggil secara implisit dalam konteks konversi tipe", "C++");
        data.addData("soal", "Apa yang dimaksud dengan fungsi virtual dalam C++?", "Fungsi yang hanya bisa digunakan dalam kelas dasar", "Fungsi yang implementasinya dapat diubah di kelas turunan", "Fungsi yang hanya digunakan untuk menangani pointer", "Fungsi yang harus di-declare ulang di setiap kelas turunan", "Fungsi yang implementasinya dapat diubah di kelas turunan", "C++");
        data.addData("soal", "Dalam C++, apa tujuan utama dari penggunaan virtual destructor dalam kelas dengan polimorfisme?", "Memastikan bahwa semua anggota data dari kelas dasar di-destroy dengan benar", "Menghindari penggunaan destructor secara eksplisit", "Memastikan destruktor dari kelas turunan dipanggil sebelum destruktor kelas dasar", "Mengizinkan objek dari kelas dasar untuk menghapus dirinya sendiri secara otomatis", "Memastikan destruktor dari kelas turunan dipanggil sebelum destruktor kelas dasar", "C++");

        // Soal-soal kategori Javascript
        data.addData("soal", "Apa tujuan dari fungsi console.log() di JavaScript?", "Untuk membaca input dari pengguna", "Untuk mencetak informasi ke konsol browser", "Untuk menghapus data variabel", "Untuk mendeklarasikan variabel baru", "Untuk mencetak informasi ke konsol browser", "Javascript");
        data.addData("soal", "Manakah dari pernyataan berikut yang benar tentang operator === di JavaScript?", "Selalu mengembalikan true", "Digunakan untuk melakukan operasi logika", "Membandingkan nilai tanpa memeriksa tipe data", "Membandingkan nilai dan tipe data", "Membandingkan nilai dan tipe data", "Javascript");
        data.addData("soal", "Apa yang akan dicetak oleh kode berikut?\nlet x = \"10\";\nlet y = 10;\nconsole.log(x == y);\nconsole.log(x === y);", "true, false", "true, true", "false, false", "false, true", "true, false", "Javascript");
        data.addData("soal", "Apa fungsi dari setTimeout() dalam JavaScript?", "Untuk menjalankan fungsi dalam interval waktu tertentu secara terus-menerus", "Untuk menghentikan sementara eksekusi fungsi lain", "Untuk menjalankan fungsi satu kali setelah waktu tertentu", "Untuk menghapus fungsi yang berjalan di latar belakang", "Untuk menjalankan fungsi satu kali setelah waktu tertentu", "Javascript");
        data.addData("soal", "Apa perbedaan utama antara null dan undefined di JavaScript?", "null adalah objek yang kosong, sedangkan undefined adalah variabel yang belum diinisialisasi", "null berarti nilai nol, sedangkan undefined adalah nilai numerik tak terdefinisi", "null digunakan dalam loop, sedangkan undefined digunakan dalam objek", "null hanya digunakan dengan tipe data string, sedangkan undefined digunakan dengan semua tipe data", "null adalah objek yang kosong, sedangkan undefined adalah variabel yang belum diinisialisasi", "Javascript");
        data.addData("soal", "Apa perbedaan utama antara let dan var di JavaScript?", "let bersifat block-scoped, sedangkan var bersifat function-scoped", "let lebih cepat dalam eksekusi daripada var", "var hanya bisa digunakan di dalam fungsi, sedangkan let bisa di luar fungsi", "let tidak mendukung hoisting, sedangkan var mendukung", "let bersifat block-scoped, sedangkan var bersifat function-scoped", "Javascript");
        data.addData("soal", "Apa fungsi dari try-catch di JavaScript?", "Untuk menghentikan eksekusi kode setelah terjadi error", "Untuk menangkap error selama eksekusi kode dan mencegah aplikasi berhenti", "Untuk memeriksa apakah variabel tertentu telah dideklarasikan", "Untuk memvalidasi input pengguna sebelum eksekusi", "Untuk menangkap error selama eksekusi kode dan mencegah aplikasi berhenti", "Javascript");
        data.addData("soal", "Apa hasil dari kode berikut?\nfunction test(){\n    console.log(a);\n    var a = 5;\n}\ntest();", "5", "Error karena variabel a belum dideklarasikan", "Error karena variabel a tidak diinisialisasi", "undefined", "undefined", "Javascript");
        data.addData("soal", "Apa yang dimaksud dengan fungsi callback di JavaScript?", "Fungsi yang dipanggil setelah fungsi utama selesai dieksekusi", "Fungsi yang hanya digunakan dalam loop", "Fungsi yang berjalan di latar belakang browser", "Fungsi yang hanya dapat dijalankan dengan async", "Fungsi yang dipanggil setelah fungsi utama selesai dieksekusi", "Javascript");
        data.addData("soal", "Apa hasil dari kode berikut?\nconst a = [1, 2];\nconst b = [...a, 3];\nconsole.log(b);", "[1, 2, 3]", "[3, 1, 2]", "[1, 2]", "Error karena spread operator tidak valid", "[1, 2, 3]", "Javascript");
        data.addData("soal", "Apa output dari kode berikut?\n(function(){\n    let a = 10;\n    console.log(a);\n})();\nconsole.log(a);", "10 diikuti oleh 0", "10 diikuti oleh undefined", "10 diikuti oleh ReferenceError", "10 diikuti oleh NaN", "10 diikuti oleh ReferenceError", "Javascript");
        data.addData("soal", "Apa yang dimaksud dengan \"closure\" dalam JavaScript?", "Fungsi yang mengembalikan nilai dari fungsi lain", "Fungsi yang memiliki akses ke variabel di luar lingkupnya meskipun lingkup tersebut telah selesai eksekusi", "Variabel yang dideklarasikan dalam sebuah blok loop", "Fungsi anonim yang dipanggil hanya sekali", "Fungsi yang memiliki akses ke variabel di luar lingkupnya meskipun lingkup tersebut telah selesai eksekusi", "Javascript");
        data.addData("soal", "Apa yang akan terjadi jika kode berikut dijalankan?\nconst obj = {\n    a: 10,\n    b: () => {\n        console.log(this.a);\n    },\n    c: function() {\n        console.log(this.a);\n    }\n};\nobj.b();\nobj.c();", "undefined dan 10", "undefined dan undefined", "10 dan 10", "Error pada obj.b()", "undefined dan 10", "Javascript");
        data.addData("soal", "Mana pernyataan yang benar tentang eksekusi kode berikut?\nlet a = 5;\nconst increment = (x = 10) => x + 1;\na = increment();\nconsole.log(a);", "Nilai a tetap 5 karena parameter default tidak berlaku", "Nilai a akan menjadi 6 karena a diubah oleh fungsi", "Error karena parameter default hanya bekerja dengan var", "Nilai a akan menjadi 11 karena parameter default digunakan", "Nilai a akan menjadi 11 karena parameter default digunakan", "Javascript");
        data.addData("soal", "Apa yang terjadi jika JavaScript menghadapi proses berat yang memakan waktu lama, misalnya loop besar atau fungsi sinkron yang kompleks?", "JavaScript akan memprioritaskan operasi asinkron sebelum menyelesaikan tugas berat", "Browser akan menghentikan eksekusi kode secara otomatis untuk mencegah gangguan pada event loop", "Call stack akan terblokir, sehingga operasi lainnya menjadi tidak responsif", "JavaScript akan memindahkan tugas berat ke web worker untuk menyelesaikan secara paralel", "Call stack akan terblokir, sehingga operasi lainnya menjadi tidak responsif", "Javascript");
        
        System.out.println("Semua soal berhasil ditambahkan!");
    }

}
