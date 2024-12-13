import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Data {
        private final Connection conn = Koneksi.getConnection();

        // Method untuk menambahkan data ke tabel yang sesuai (tambah data soal dan
        // pemain)
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
                data.addData("soal", "Apa fungsi dari keyword class dalam Java?", "Menghentikan eksekusi program",
                                "Mendeklarasikan sebuah kelas", "Membuat array",
                                "Mendeklarasikan variabel", "Mendeklarasikan sebuah kelas", "Java");
                data.addData("soal", "Apa ekstensi file untuk program Java?", ".java", ".class", ".jar", ".exe",
                                ".java", "Java");
                data.addData("soal", "Manakah tipe data yang digunakan untuk menyimpan angka desimal?", "int", "float",
                                "char", "boolean", "float", "Java");
                data.addData("soal", "Apa hasil dari perintah berikut? System.out.println(10 + 5);", "10", "5", "15",
                                "Error", "15", "Java");
                data.addData("soal",
                                "Apa output dari kode berikut?<br>String str = \"Java\";<br>System.out.println(str.length());",
                                "3", "4", "5", "Error", "4", "Java");
                data.addData("soal", "Apa yang terjadi jika main() tidak dideklarasikan sebagai static?",
                                "Program akan berjalan normal", "Error pada runtime",
                                "Error saat kompilasi", "Tidak ada yang terjadi", "Error saat kompilasi", "Java");
                data.addData("soal", "Bagaimana cara membuat array dengan 10 elemen bertipe int?", "int arr[] = {10};",
                                "int arr[10];", "int arr[] = new int[10];", "int arr = new int[10];",
                                "int arr[] = new int[10];", "Java");
                data.addData("soal",
                                "Apa output dari kode berikut?<br>int a = 5, b = 10;<br>a++;<br>b--; System.out.println(a + b);",
                                "15", "14", "16", "Error", "16", "Java");
                data.addData("soal", "Apa yang dimaksud dengan Overloading dalam Java?",
                                "Dua metode dengan nama yang sama, parameter berbeda",
                                "Dua metode dengan nama berbeda, parameter sama",
                                "Variabel yang digunakan dua kali", "Penggunaan memori berlebih",
                                "Dua metode dengan nama yang sama, parameter berbeda", "Java");
                data.addData("soal", "Apa tujuan dari keyword final pada variabel di Java?",
                                "Membuat variabel bersifat konstan", "Membuat variabel dapat diubah",
                                "Menentukan akhir program", "Membuat variabel static",
                                "Membuat variabel bersifat konstan", "Java");
                data.addData("soal", "Apa yang dimaksud dengan Generics di Java?",
                                "Membuat kelas atau metode yang hanya menerima tipe data tertentu",
                                "Membuat kelas atau metode yang dapat bekerja dengan berbagai tipe data",
                                "Membuat kelas abstrak", "Membuat metode statis",
                                "Membuat kelas atau metode yang dapat bekerja dengan berbagai tipe data", "Java");
                data.addData("soal", "Apa tujuan utama dari Garbage Collector di Java?",
                                "Menghapus variabel yang tidak digunakan", "Meningkatkan performa aplikasi",
                                "Mengelola memori dengan menghapus objek yang tidak direferensikan lagi",
                                "Mengoptimalkan pengolahan data dalam array",
                                "Mengelola memori dengan menghapus objek yang tidak direferensikan lagi", "Java");
                data.addData("soal", "Apa perbedaan utama antara HashMap dan TreeMap di Java?",
                                "HashMap menggunakan urutan alfanumerik, sedangkan TreeMap tidak.",
                                "TreeMap lebih cepat dibandingkan HashMap.",
                                "HashMap tidak menjamin urutan, sedangkan TreeMap mengurutkan berdasarkan kunci.",
                                "Tidak ada perbedaan antara keduanya.",
                                "HashMap tidak menjamin urutan, sedangkan TreeMap mengurutkan berdasarkankunci.",
                                "Java");
                data.addData("soal",
                                "Apa yang terjadi jika kita mencoba menambahkan elemen ke ArrayList saat iterasi menggunakan Iterator tanpa Iterator.remove()?",
                                "Elemen akan ditambahkan tanpa masalah.", "Terjadi ConcurrentModificationException.",
                                "Elemen ditambahkan, tetapi urutannya tidak terjamin.", "Program berhenti tanpa error.",
                                "Terjadi ConcurrentModificationException.", "Java");
                data.addData("soal",
                                "Apa yang terjadi jika dua thread mencoba mengakses metode yang disinkronkan (synchronized) pada objek yang sama secara bersamaan?",
                                "Kedua thread akan dieksekusi secara bersamaan tanpa masalah.",
                                "Salah satu thread akan dihentikan sementara waktu sampai thread lainnya selesai.",
                                "Kedua thread akan menghasilkan deadlock.",
                                "Kedua thread akan gagal dan throw exception IllegalStateException.",
                                "Salah satu thread akan dihentikan sementara waktu sampai thread lainnyaselesai.",
                                "Java");

                // Soal-soal kategori Python
                data.addData("soal", "Apa fungsi dari keyword def dalam Python?", "Mendeklarasikan fungsi",
                                "Mendeklarasikan variabel", "Menjalankan fungsi",
                                "Menentukan tipe data", "Mendeklarasikan fungsi", "Python");
                data.addData("soal", "Bagaimana cara mencetak teks 'Hello World' di Python?",
                                "System.out.println('Hello World')", "echo 'Hello World'", "print('Hello World')",
                                "console.log('Hello World')", "print('Hello World')", "Python");
                data.addData("soal", "Apa ekstensi file untuk program Python?", ".py", ".java", ".class", ".pyt", ".py",
                                "Python");
                data.addData("soal", "Manakah tipe data yang digunakan untuk menyimpan teks di Python?", "int",
                                "string", "float", "bool", "string", "Python");
                data.addData("soal", "Apa hasil dari kode berikut?<br>print(2 ** 3)", "5", "6", "8", "9", "8",
                                "Python");
                data.addData("soal", "Apa library Python untuk manipulasi data?", "Numpy", "Pandas", "Scikit-learn",
                                "Semua benar", "Semua benar", "Python");
                data.addData("soal", "Apa metode untuk menambahkan elemen ke list di Python?", "add()", "insert()",
                                "append()", "push()", "append()", "Python");
                data.addData("soal", "Apa perbedaan antara list dan tuple?", "List tidak dapat diubah, tuple dapat",
                                "Tuple tidak dapat diubah, list dapat",
                                "Tidak ada perbedaan", "Tuple hanya untuk angka",
                                "Tuple tidak dapat diubah, list dapat", "Python");
                data.addData("soal", "Apa output dari kode berikut?<br>x = [1, 2, 3]<br>print(x[3])", "3", "IndexError",
                                "None", "4", "IndexError", "Python");
                data.addData("soal", "Apa arti dari `*args` dalam sebuah fungsi?",
                                "Menyatakan jumlah argumen yang tetap", "Menyatakan argumen variabel",
                                "Menyatakan argumen keyword",
                                "Menyatakan argumen yang wajib", "Menyatakan argumen variabel", "Python");
                data.addData("soal", "Apa fungsi dari `len()` dalam Python?", "Menghapus elemen list",
                                "Mengembalikan panjang dari list atau string", "Menambahkan elemen ke list",
                                "Memotong string", "Mengembalikan panjang dari list atau string", "Python");
                data.addData("soal", "Apa itu dictionary di Python?", "Tipe data seperti array",
                                "Tipe data key-value pairs", "Tipe data immutable", "Tipe data berbentuk tuple",
                                "Tipe data key-value pairs", "Python");
                data.addData("soal", "Apa fungsi dari `lambda` dalam Python?", "Mendeklarasikan class",
                                "Mendeklarasikan fungsi anonymous", "Menginisialisasi variabel",
                                "Membuat loop", "Mendeklarasikan fungsi anonymous", "Python");
                data.addData("soal", "Apa output dari kode berikut?<br>`print(10 // 3)`", "3.33", "3", "Error", "4",
                                "3", "Python");
                data.addData("soal", "Apa itu Python Virtual Environment?", "Lingkungan fisik untuk Python",
                                "Isolasi lingkungan Python untuk proyek", "Lingkungan yang digunakan untuk deployment",
                                "Alat untuk debugging Python", "Isolasi lingkungan Python untuk proyek", "Python");

                // Soal-soal kategori C++
                data.addData("soal", "Manakah tipe data berikut yang digunakan untuk menyimpan bilangan pecahan?",
                                "int", "float", "char", "bool", "float", "C++");
                data.addData("soal",
                                "Apa output dari kode berikut?<br>#include &lt;iostream&gt;<br>using namespace std;<br>int main(){<br>cout &lt;&lt; \"Hello, World!\" &lt;&lt; endl; return 0;<br>}",
                                "Hello World", "Hello, World!", "Hello World!", "Error", "Hello, World!", "C++");
                data.addData("soal", "Pernyataan apa yang digunakan untuk mencetak output ke layar?", "printf",
                                "print()", "cout", "scanf", "cout", "C++");
                data.addData("soal",
                                "Di antara berikut, manakah header file standar untuk manipulasi input/output di C++?",
                                "conio.h", "iostream", "stdlib.h", "string", "iostream", "C++");
                data.addData("soal",
                                "Apa hasil dari kode berikut?<br>#include &lt;iostream&gt;<br>using namespace std;<br>int main(){ int x = 5; int y = 10; x += y;<br>    cout &lt;&lt; x &lt;&lt; endl; return 0; ",
                                "10", "15", "x", "5", "15", "C++");
                data.addData("soal", "Apa fungsi dari keyword return di dalam fungsi C++?", "Menyimpan variabel",
                                "Mengakhiri fungsi dan mengembalikan nilai", "Mencetak nilai", "Membuat loop",
                                "Mengakhiri fungsi dan mengembalikan nilai", "C++");
                data.addData("soal", "Apa yang dimaksud dengan pointer di C++?",
                                "Variabel yang menyimpan nilai integer", "Objek untuk menangani input/output",
                                "Variabel yang menunjuk ke alamat memori variabel lain",
                                "Fungsi untuk memanipulasi string",
                                "Variabel yang menunjuk ke alamat memori variabel lain", "C++");
                data.addData("soal",
                                "Apa hasil dari kode berikut?<br>#include &lt;iostream&gt;<br>using namespace std;<br>int main(){ int arr[3] = {7, 12, 48};<br>cout &lt;&lt; arr[2] &lt;&lt; endl; return 0; }",
                                "48", "7", "12", "2", "48", "C++");
                data.addData("soal", "Bagaimana cara membuat komentar di C++?", "/* Ini komentar", "# Ini komentar",
                                "// Ini komentar", "&lt; Ini komentar &gt;", "// Ini komentar", "C++");
                data.addData("soal",
                                "Method atau fungsi yang digunakan untuk mendapatkan panjang string dalam C++ adalah...",
                                "length()", "getZise()", "len()", "getLen()", "length()", "C++");
                data.addData("soal", "Cara yang benar untuk mendeklarasikan array di C++ adalah...", "int nilai{10};",
                                "int nilai[10];", "int [] nilai = new int[10];", "int nilai;", "int nilai[10];", "C++");
                data.addData("soal",
                                "Apa yang akan terjadi jika kita mencoba menghapus pointer yang telah dihapus sebelumnya dengan delete?",
                                "Program akan berjalan normal tanpa masalah",
                                "Program akan secara otomatis mengabaikan operasi tersebut",
                                "Program akan menghasilkan error kompilasi",
                                "Program akan mengalami undefined behavior",
                                "Program akan mengalami undefined behavior", "C++");
                data.addData("soal", "Apa efek dari penggunaan kata kunci explicit pada konstruktor di C++?",
                                "Mencegah konstruktor dipanggil secara implisit dalam konteks konversi tipe",
                                "Mengizinkan konversi tipe otomatis antara objek yang tidak kompatibel",
                                "Membatasi penggunaan konstruktor hanya pada objek bertipe tertentu",
                                "Menjamin bahwa konstruktor hanya bisa dipanggil sekali",
                                "Mencegah konstruktor dipanggil secara implisit dalam konteks konversi tipe", "C++");
                data.addData("soal", "Apa yang dimaksud dengan fungsi virtual dalam C++?",
                                "Fungsi yang hanya bisa digunakan dalam kelas dasar",
                                "Fungsi yang implementasinya dapat diubah di kelas turunan",
                                "Fungsi yang hanya digunakan untuk menangani pointer",
                                "Fungsi yang harus di-declare ulang di setiap kelas turunan",
                                "Fungsi yang implementasinya dapat diubah di kelas turunan", "C++");
                data.addData("soal",
                                "Dalam C++, apa tujuan utama dari penggunaan virtual destructor dalam kelas dengan polimorfisme?",
                                "Memastikan bahwa semua anggota data dari kelas dasar di-destroy dengan benar",
                                "Menghindari penggunaan destructor secara eksplisit",
                                "Memastikan destruktor dari kelas turunan dipanggil sebelum destruktor kelas dasar",
                                "Mengizinkan objek dari kelas dasar untuk menghapus dirinya sendiri secara otomatis",
                                "Memastikan destruktor dari kelas turunan dipanggil sebelum destruktor kelas dasar",
                                "C++");

                // Soal-soal kategori Javascript
                data.addData("soal", "Apa tujuan dari fungsi console.log() di JavaScript?",
                                "Untuk membaca input dari pengguna", "Untuk mencetak informasi ke konsol browser",
                                "Untuk menghapus data variabel", "Untuk mendeklarasikan variabel baru",
                                "Untuk mencetak informasi ke konsol browser", "Javascript");
                data.addData("soal", "Manakah dari pernyataan berikut yang benar tentang operator === di JavaScript?",
                                "Selalu mengembalikan true", "Digunakan untuk melakukan operasi logika",
                                "Membandingkan nilai tanpa memeriksa tipe data", "Membandingkan nilai dan tipe data",
                                "Membandingkan nilai dan tipe data", "Javascript");
                data.addData("soal",
                                "Apa yang akan dicetak oleh kode berikut?<br>let x = \"10\";<br>let y = 10;<br>console.log(x == y);<br>console.log(x === y);",
                                "true, false", "true, true", "false, false", "false, true", "true, false",
                                "Javascript");
                data.addData("soal", "Apa fungsi dari setTimeout() dalam JavaScript?",
                                "Untuk menjalankan fungsi dalam interval waktu tertentu secara terus-menerus",
                                "Untuk menghentikan sementara eksekusi fungsi lain",
                                "Untuk menjalankan fungsi satu kali setelah waktu tertentu",
                                "Untuk menghapus fungsi yang berjalan di latar belakang",
                                "Untuk menjalankan fungsi satu kali setelah waktu tertentu", "Javascript");
                data.addData("soal", "Apa perbedaan utama antara null dan undefined di JavaScript?",
                                "null adalah objek yang kosong, sedangkan undefined adalah variabel yang belum diinisialisasi",
                                "null berarti nilai nol, sedangkan undefined adalah nilai numerik tak terdefinisi",
                                "null digunakan dalam loop, sedangkan undefined digunakan dalam objek",
                                "null hanya digunakan dengan tipe data string, sedangkan undefined digunakan dengan semua tipe data",
                                "null adalah objek yang kosong, sedangkan undefined adalah variabel yang belum diinisialisasi",
                                "Javascript");
                data.addData("soal", "Apa perbedaan utama antara let dan var di JavaScript?",
                                "let bersifat block-scoped, sedangkan var bersifat function-scoped",
                                "let lebih cepat dalam eksekusi daripada var",
                                "var hanya bisa digunakan di dalam fungsi, sedangkan let bisa di luar fungsi",
                                "let tidak mendukung hoisting, sedangkan var mendukung",
                                "let bersifat block-scoped, sedangkan var bersifat function-scoped", "Javascript");
                data.addData("soal", "Apa fungsi dari try-catch di JavaScript?",
                                "Untuk menghentikan eksekusi kode setelah terjadi error",
                                "Untuk menangkap error selama eksekusi kode dan mencegah aplikasi berhenti",
                                "Untuk memeriksa apakah variabel tertentu telah dideklarasikan",
                                "Untuk memvalidasi input pengguna sebelum eksekusi",
                                "Untuk menangkap error selama eksekusi kode dan mencegah aplikasi berhenti",
                                "Javascript");
                data.addData("soal",
                                "Apa hasil dari kode berikut?<br>function test(){<br>    console.log(a);<br>    var a = 5; }<br>test();",
                                "5", "Error karena variabel a belum dideklarasikan",
                                "Error karena variabel a tidak diinisialisasi", "undefined", "undefined", "Javascript");
                data.addData("soal", "Apa yang dimaksud dengan fungsi callback di JavaScript?",
                                "Fungsi yang dipanggil setelah fungsi utama selesai dieksekusi",
                                "Fungsi yang hanya digunakan dalam loop",
                                "Fungsi yang berjalan di latar belakang browser",
                                "Fungsi yang hanya dapat dijalankan dengan async",
                                "Fungsi yang dipanggil setelah fungsi utama selesai dieksekusi", "Javascript");
                data.addData("soal",
                                "Apa hasil dari kode berikut?<br>const a = [1, 2];<br>const b = [...a, 3];<br>console.log(b);",
                                "[1, 2, 3]", "[3, 1, 2]", "[1, 2]", "Error karena spread operator tidak valid",
                                "[1, 2, 3]", "Javascript");
                data.addData("soal",
                                "Apa output dari kode berikut?<br>(function(){<br>    let a = 10;<br>    console.log(a); })();<br>console.log(a);",
                                "10 diikuti oleh 0", "10 diikuti oleh undefined", "10 diikuti oleh ReferenceError",
                                "10 diikuti oleh NaN", "10 diikuti oleh ReferenceError", "Javascript");
                data.addData("soal", "Apa yang dimaksud dengan \"closure\" dalam JavaScript?",
                                "Fungsi yang mengembalikan nilai dari fungsi lain",
                                "Fungsi yang memiliki akses ke variabel di luar lingkupnya meskipun lingkup tersebut telah selesai eksekusi",
                                "Variabel yang dideklarasikan dalam sebuah blok loop",
                                "Fungsi anonim yang dipanggil hanya sekali",
                                "Fungsi yang memiliki akses ke variabel di luar lingkupnya meskipun lingkup tersebut telah selesai eksekusi",
                                "Javascript");
                data.addData("soal",
                                "Apa yang akan terjadi jika kode berikut dijalankan?<br>const obj = { a: 10, b: () =&gt; {<br>        console.log(this.a); },<br>    c: function() { console.log(this.a); } };<br>obj.b(); obj.c();",
                                "undefined dan 10", "undefined dan undefined", "10 dan 10", "Error pada obj.b()",
                                "undefined dan 10", "Javascript");
                data.addData("soal",
                                "Mana pernyataan yang benar tentang eksekusi kode berikut?<br>let a = 5;<br>const increment = (x = 10) =&gt; x + 1;<br>a = increment();<br>console.log(a);",
                                "Nilai a tetap 5 karena parameter default tidak berlaku",
                                "Nilai a akan menjadi 6 karena a diubah oleh fungsi",
                                "Error karena parameter default hanya bekerja dengan var",
                                "Nilai a akan menjadi 11 karena parameter default digunakan",
                                "Nilai a akan menjadi 11 karena parameter default digunakan", "Javascript");
                data.addData("soal",
                                "Apa yang terjadi jika JavaScript menghadapi proses berat yang memakan waktu lama, misalnya loop besar atau fungsi sinkron yang kompleks?",
                                "JavaScript akan memprioritaskan operasi asinkron sebelum menyelesaikan tugas berat",
                                "Browser akan menghentikan eksekusi kode secara otomatis untuk mencegah gangguan pada event loop",
                                "Call stack akan terblokir, sehingga operasi lainnya menjadi tidak responsif",
                                "JavaScript akan memindahkan tugas berat ke web worker untuk menyelesaikan secara paralel",
                                "Call stack akan terblokir, sehingga operasi lainnya menjadi tidak responsif",
                                "Javascript");

                // Soal-soal kategori HTML
                data.addData("soal", "Apa kepanjangan dari HTML?", "Hyper Text Markup Language",
                                "High Text Markup Language", "Hyper Text Machine Language",
                                "Hyper Tool Markup Language", "Hyper Text Markup Language", "HTML");
                data.addData("soal", "Tag HTML mana yang digunakan untuk membuat teks tebal?", "<bold>", "<strong>",
                                "<b>", "<t>", "<b>", "HTML");
                data.addData("soal", "Tag HTML mana yang digunakan untuk baris baru?", "<br>", "<lb>", "<line>",
                                "<break>", "<br>", "HTML");
                data.addData("soal", "Apa tujuan dari tag &lt;title&gt; dalam HTML?",
                                "Untuk memberikan nama halaman yang muncul di tab browser",
                                "Untuk menambahkan teks di dalam dokumen", "Untuk membuat paragraf",
                                "Untuk membuat header pada halaman",
                                "Untuk memberikan nama halaman yang muncul di tab browser", "HTML");
                data.addData("soal", "Tag HTML mana yang digunakan untuk membuat daftar tanpa nomor?", "<ol>", "<ul>",
                                "<list>", "<li>", "<ul>", "HTML");
                data.addData("soal", "Apa tujuan dari tag &lt;p&gt; dalam HTML?", "Untuk membuat tautan",
                                "Untuk membuat paragraf", "Untuk membuat baris baru", "Untuk menampilkan gambar",
                                "Untuk membuat paragraf", "HTML");
                data.addData("soal", "Tag HTML mana yang digunakan untuk menyisipkan gambar?", "<img>", "<image>",
                                "<picture>", "<graphics>", "<img>", "HTML");
                data.addData("soal", "Atribut apa yang digunakan untuk menentukan URL gambar pada tag &lt;img&gt;?", "src",
                                "alt", "href", "link", "src", "HTML");
                data.addData("soal", "Apa tujuan dari atribut `href` pada tag &lt;a&gt;?", "Menentukan alamat hyperlink",
                                "Menentukan warna teks", "Menentukan posisi tautan", "Menambahkan teks alternatif",
                                "Menentukan alamat hyperlink", "HTML");
                data.addData("soal", "Tag HTML mana yang digunakan untuk membuat header atau judul?",
                                "<h1> hingga <h6>", "<header>", "<head>", "<title>", "<h1> hingga <h6>", "HTML");
                data.addData("soal", "Apa perbedaan antara tag &lt;ol&gt; dan &lt;ul&gt;?", "Tidak ada perbedaan",
                                "`<ol>` untuk daftar bernomor, `<ul>` untuk daftar tanpa nomor",
                                "`<ol>` untuk daftar tanpa nomor, `<ul>` untuk daftar bernomor",
                                "`<ul>` hanya digunakan untuk daftar dalam tabel",
                                "`<ol>` untuk daftar bernomor, `<ul>` untuk daftar tanpa nomor", "HTML");
                data.addData("soal", "Tag HTML mana yang digunakan untuk memasukkan baris horizontal di halaman?",
                                "<hr>", "<br>", "<line>", "<border>", "<hr>", "HTML");
                data.addData("soal", "Apa fungsi dari atribut `lang` dalam tag &lt;html&gt;?",
                                "Menentukan gaya bahasa teks", "Menentukan bahasa dokumen HTML",
                                "Mengatur format paragraf", "Menghubungkan file CSS", "Menentukan bahasa dokumen HTML",
                                "HTML");
                data.addData("soal", "Tag HTML mana yang digunakan untuk menyisipkan video?", "<video>", "<media>",
                                "<embed>", "<movie>", "<video>", "HTML");
                data.addData("soal", "Tag HTML mana yang digunakan untuk menyisipkan file audio?", "<audio>", "<sound>",
                                "<music>", "<media>", "<audio>", "HTML");

                // Soal-soal kategori CSS
                data.addData("soal", "Apa kepanjangan dari CSS?", "Cascading Style Sheets", "Creative Style Sheets",
                                "Computer Style Sheets", "Colorful Style Sheets", "Cascading Style Sheets", "CSS");
                data.addData("soal", "Apa fungsi utama dari CSS?", "Membuat struktur dokumen HTML",
                                "Mengatur gaya dan tata letak dokumen HTML",
                                "Menambahkan fungsi interaktif pada halaman", "Mengelola database halaman web",
                                "Mengatur gaya dan tata letak dokumen HTML", "CSS");
                data.addData("soal", "Bagaimana cara menyisipkan CSS langsung di dalam dokumen HTML?",
                                "Menggunakan tag `<style>` di dalam `<head>`",
                                "Menggunakan tag `<css>` di dalam `<body>`", "Menggunakan atribut `src` pada `<link>`",
                                "Menggunakan atribut `style` di `<head>`",
                                "Menggunakan tag `<style>` di dalam `<head>`", "CSS");
                data.addData("soal", "Atribut HTML apa yang digunakan untuk menambahkan CSS langsung ke elemen?",
                                "style", "css", "class", "id", "style", "CSS");
                data.addData("soal", "Apa tujuan dari file eksternal CSS?", "Mengatur struktur dokumen HTML",
                                "Memisahkan kode CSS dari HTML untuk memudahkan pemeliharaan",
                                "Menambahkan fungsi interaktif", "Menghubungkan HTML dengan database",
                                "Memisahkan kode CSS dari HTML untuk memudahkan pemeliharaan", "CSS");
                data.addData("soal", "Apa sintaks yang benar untuk mengubah warna teks menjadi merah di CSS?",
                                "color: red;", "text-color: red;", "font-color: red;", "background-color: red;",
                                "color: red;", "CSS");
                data.addData("soal", "Apa tujuan dari properti `margin` dalam CSS?",
                                "Menambahkan spasi di dalam elemen", "Mengatur jarak luar antara elemen",
                                "Mengubah warna latar belakang", "Mengatur ukuran teks",
                                "Mengatur jarak luar antara elemen", "CSS");
                data.addData("soal", "Bagaimana cara memilih elemen dengan ID tertentu dalam CSS?", "#idName",
                                ".idName", "idName", "&idName", "#idName", "CSS");
                data.addData("soal", "Apa fungsi dari properti `z-index` dalam CSS?", "Mengatur posisi vertikal elemen",
                                "Mengontrol urutan tumpukan elemen", "Mengubah ukuran elemen",
                                "Mengatur spasi antar elemen", "Mengontrol urutan tumpukan elemen", "CSS");
                data.addData("soal", "Apa fungsi dari properti `display` dalam CSS?", "Mengontrol visibilitas elemen",
                                "Mengatur cara elemen ditampilkan di dokumen", "Mengubah ukuran elemen",
                                "Mengatur posisi elemen", "Mengatur cara elemen ditampilkan di dokumen", "CSS");
                data.addData("soal", "Bagaimana cara membuat teks menjadi tebal menggunakan CSS?", "font-weight: bold;",
                                "text-style: bold;", "font-style: bold;", "font-bold: true;", "font-weight: bold;",
                                "CSS");
                data.addData("soal", "Apa yang dilakukan properti `position: absolute;` dalam CSS?",
                                "Mengatur elemen relatif terhadap elemen induknya",
                                "Mengatur elemen secara tetap di layar",
                                "Mengatur elemen relatif terhadap elemen terdekat dengan posisi tertentu",
                                "Menghapus elemen dari dokumen",
                                "Mengatur elemen relatif terhadap elemen terdekat dengan posisi tertentu", "CSS");
                data.addData("soal",
                                "Bagaimana cara memilih semua elemen &lt;p&gt; di dalam elemen dengan class `container`?",
                                ".container p", "p.container", ".container > p", "container p", ".container p", "CSS");
                data.addData("soal", "Apa perbedaan antara `padding` dan `margin` dalam CSS?",
                                "Padding adalah jarak luar elemen, margin adalah jarak dalam elemen",
                                "Padding adalah jarak dalam elemen, margin adalah jarak luar elemen",
                                "Padding mengatur ukuran elemen, margin mengatur posisi elemen", "Tidak ada perbedaan",
                                "Padding adalah jarak dalam elemen, margin adalah jarak luar elemen", "CSS");
                data.addData("soal", "Apa tujuan dari pseudo-class `:hover` dalam CSS?",
                                "Memilih elemen saat halaman dimuat",
                                "Memilih elemen saat pointer berada di atas elemen",
                                "Memilih elemen yang memiliki fokus", "Memilih elemen pertama dari jenisnya",
                                "Memilih elemen saat pointer berada di atas elemen", "CSS");
                data.addData("soal", "Apa fungsi dari properti `flex-direction` dalam CSS Flexbox?",
                                "Mengatur orientasi elemen flex (baris atau kolom)", "Mengatur ukuran elemen flex",
                                "Mengatur jarak antar elemen flex", "Mengatur posisi elemen flex",
                                "Mengatur orientasi elemen flex (baris atau kolom)", "CSS");
                data.addData("soal", "Bagaimana cara menyisipkan file CSS eksternal ke dalam dokumen HTML?",
                                "<link rel=\"stylesheet\" href=\"style.css\">",
                                "<css rel=\"stylesheet\" href=\"style.css\">", "<style src=\"style.css\">",
                                "<script href=\"style.css\">", "<link rel=\"stylesheet\" href=\"style.css\">", "CSS");
                data.addData("soal", "Apa tujuan dari properti `overflow` dalam CSS?",
                                "Mengontrol apa yang terjadi saat konten elemen melebihi batas elemen",
                                "Mengatur jarak antara elemen", "Mengubah posisi elemen", "Mengatur ukuran elemen",
                                "Mengontrol apa yang terjadi saat konten elemen melebihi batas elemen", "CSS");
                data.addData("soal", "Apa arti dari nilai `inherit` pada properti CSS?",
                                "Mengatur properti ke nilai default elemen",
                                "Mewarisi nilai properti dari elemen induknya", "Menghapus nilai properti elemen",
                                "Mengatur properti menjadi tidak aktif", "Mewarisi nilai properti dari elemen induknya",
                                "CSS");
                data.addData("soal", "Apa yang dimaksud dengan media query dalam CSS?",
                                "Sintaks untuk membuat elemen responsif terhadap perangkat",
                                "Cara untuk memilih elemen spesifik berdasarkan class",
                                "Teknik untuk mengatur warna elemen", "Kode untuk menjalankan animasi",
                                "Sintaks untuk membuat elemen responsif terhadap perangkat", "CSS");

                System.out.println("Semua soal berhasil ditambahkan!");
        }

}
