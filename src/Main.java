import java.text.SimpleDateFormat; // Untuk memformat tanggal dan waktu
import java.util.Date; // Untuk menangani tanggal dan waktu
import java.util.Scanner; // Untuk membaca input dari pengguna
import java.util.Random; // Untuk menghasilkan captcha

// Kelas Barang (Parent Class)
class Barang {
    protected String kodeBarang;
    protected String namaBarang;
    protected double hargaBarang;

    public Barang(String kodeBarang, String namaBarang, double hargaBarang) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
    }

    public double getHargaBarang() {
        return hargaBarang;
    }

    public void displayBarang() {
        System.out.println("Kode Barang : " + kodeBarang);
        System.out.println("Nama Barang : " + namaBarang);
        System.out.println("Harga Barang: Rp " + hargaBarang);
    }
}

// Kelas Transaksi (Child Class)
class Transaksi extends Barang {
    private String noFaktur;
    private int jumlahBeli;
    private double totalHarga;

    public Transaksi(String noFaktur, String kodeBarang, String namaBarang, double hargaBarang, int jumlahBeli) {
        super(kodeBarang, namaBarang, hargaBarang);
        this.noFaktur = noFaktur;
        this.jumlahBeli = jumlahBeli;
        this.totalHarga = calculateTotal();
    }

    public double calculateTotal() {
        return jumlahBeli * hargaBarang;
    }

    public void displayTransaksi() {
        System.out.println("\n===== Detail Transaksi =====");
        System.out.println("No Faktur    : " + noFaktur);
        displayBarang();
        System.out.println("Jumlah Beli  : " + jumlahBeli);
        System.out.println("Total Harga  : Rp " + totalHarga);
    }
}

// Kelas Utama (Main Program)
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean loggedIn = false;

        // Login Section
        System.out.println("+-----------------------------------------------------+");
        System.out.println("Log in");
        while (!loggedIn) {
            System.out.print("Username : ");
            String username = scanner.nextLine().trim(); // Menghapus spasi di awal/akhir
            System.out.print("Password : ");
            String password = scanner.nextLine().trim(); // Menghapus spasi di awal/akhir

            // Generate captcha
            String captcha = generateCaptcha(random);
            System.out.print("Captcha (" + captcha + ") : ");
            String inputCaptcha = scanner.nextLine().trim(); // Menghapus spasi di awal/akhir

            if (username.equals("admin") && password.equals("1234") && inputCaptcha.equals(captcha)) {
                System.out.println("Login berhasil!");
                loggedIn = true;
            } else {
                System.out.println("Login gagal. Silakan coba lagi.");
            }
        }
        System.out.println("+-----------------------------------------------------+");

        // Menampilkan Selamat Datang
        System.out.println("Selamat Datang di Supermarket Maju Jaya");
        String currentDateTime = getCurrentDateTime(); // Mengambil waktu saat ini
        System.out.println("Tanggal dan Waktu : " + currentDateTime);
        System.out.println("+----------------------------------------------------+");

        // Transaksi Section
        String lanjut;
        do {
            try {
                System.out.print("Masukkan No Faktur      : ");
                String noFaktur = scanner.nextLine();

                System.out.print("Masukkan Kode Barang    : ");
                String kodeBarang = scanner.nextLine();

                System.out.print("Masukkan Nama Barang    : ");
                String namaBarang = scanner.nextLine();

                System.out.print("Masukkan Harga Barang   : ");
                double hargaBarang = Double.parseDouble(scanner.nextLine());

                if (hargaBarang <= 0) {
                    throw new IllegalArgumentException("Harga barang harus lebih dari 0!");
                }

                System.out.print("Masukkan Jumlah Beli    : ");
                int jumlahBeli = Integer.parseInt(scanner.nextLine());

                if (jumlahBeli <= 0) {
                    throw new IllegalArgumentException("Jumlah beli harus lebih dari 0!");
                }

                // Membuat objek transaksi
                Transaksi transaksi = new Transaksi(noFaktur, kodeBarang, namaBarang, hargaBarang, jumlahBeli);

                // Menampilkan detail transaksi
                transaksi.displayTransaksi();

                System.out.print("Kasir : ");
                String kasir = scanner.nextLine(); // Nama kasir diinput oleh pengguna
                System.out.println("+----------------------------------------------------+");

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }

            // Menanyakan apakah pengguna ingin melanjutkan transaksi
            System.out.print("\nApakah Anda ingin memasukkan transaksi lain? (ya/tidak): ");
            lanjut = scanner.nextLine();

        } while (lanjut.equalsIgnoreCase("ya"));

        scanner.close();
        System.out.println("Terima kasih telah menggunakan program ini.");
    }

    // Method untuk menghasilkan captcha acak
    private static String generateCaptcha(Random random) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            captcha.append(characters.charAt(random.nextInt(characters.length())));
        }
        return captcha.toString();
    }

    // Method untuk mendapatkan waktu saat ini dalam format yang sesuai
    private static String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // Format waktu
        Date date = new Date(); // Waktu sekarang
        return formatter.format(date); // Mengembalikan waktu dalam format
    }
}
