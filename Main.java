import java.util.Scanner;

interface back{
    void back();
}

abstract class kasBook{
    private String data = "";
    private int moneyLeft;
    private String status;
    
    public void setData(String data){
        this.data = data;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setMoneyLeft(int moneyLeft){
        this.moneyLeft = moneyLeft;
    }
    
    public String getData(){
        return data;
    }
    
    public String getStatus(){
        return status;
    }
    
    public int getMoneyLeft(){
        return this.moneyLeft;
    }
    
    abstract String updateStatus(String status);
}

class kasBookController extends kasBook{
    public String updateStatus(String status) {
        setStatus(status);
        return getStatus();
    }
    
    public String getHistory(){
        StringBuilder sb = new StringBuilder(updateStatus("\nMenampilkan histori kas...\n"));
        sb.append(getData());
        return sb.toString();
    }
    
    public String addMoney(int nominal, String description){
        StringBuilder sb = new StringBuilder(getData());
        setMoneyLeft(getMoneyLeft() + nominal);
         sb.append("\nNominal: +").append(nominal)
          .append("\nDeskripsi: ").append(description)
          .append("\nSisa uang: ").append(getMoneyLeft()).append("\n");
        setData(sb.toString());
        return updateStatus("\nAnda telah menambahkan kas\n");
    }
    
    public String removeMoney(int nominal, String description){
        StringBuilder sb = new StringBuilder(getData());
        setMoneyLeft(getMoneyLeft() - nominal);
         sb.append("\nNominal: -").append(nominal)
          .append("\nDeskripsi: ").append(description)
          .append("\nSisa uang: ").append(getMoneyLeft()).append("\n");
        setData(sb.toString());
        return updateStatus("\nAnda telah mengurangkan kas\n");
    }
    
    public String deleteHistory(){
        setData("");
        return updateStatus("\nHistori anda telah dihapus\n");
    }
}


class view{
    private final Scanner sc = new Scanner(System.in);
    
    public int firstMenu(){
        System.out.println("""
        
            Tugas CLI:
            1. Program Buku Kas
            2. Insight gua
            0. Exit
            
            Masukkan Pilihan:""");
        return sc.nextInt();
    }
    
    public int insight(){
        System.out.println("""
        
            Insight yang didapatkan pada project ini adalah:
            1. Memikirkan tempat penggunaan  abstract dan interface sangat susah, karena kode dapat dibuat tanpa abstract dan interface.
            2. Jika child hanya satu abstract tidak berguna kecuali ada rencana untuk menambah child untuk kedepannya.
            3. Jika bang Irham mengatakan 15 menit jadi, maka berharap paling minimal 3 jam baru jadi.
            4. Jika menggunakan AI untuk membuat tugas ini, rekomendasi untuk abstract adalah halaman CLI, AI susah memikirkan abstraksi lain yang dapat digunakan.
            
            Pilihan:
            0. Back
            
            Masukkan Pilihan:""");
        return sc.nextInt();
    }
    
    public int menu(){
        System.out.println("""
            Menu Buku Kas:
            1. Lihat history
            2. Tambah dana
            3. Kurang dana
            4. Hapus history
            0. Back
            
            Masukkan Pilihan:""");
        return sc.nextInt();
    }
    
    public Scanner getScanner() {
        return sc;
    }
}

class choise implements back{
    private view view;
    private kasBookController kasBook;
    private Scanner sc;
    
    public choise(view view, kasBookController kasBook) {
        this.view = view;
        this.kasBook = kasBook;
        this.sc = view.getScanner();
    }
    
    public void firstPage(int choise){
        switch(choise){
            case 1 -> menuPage(view.menu());
            case 2 -> insightPage(view.insight());
            case 0 -> System.out.println("\nTerima kasih telah memeriksa tugas saya ^v^");
            default -> {
                System.out.println("Pilihan anda salah\n");
                firstPage(view.firstMenu());
            }
        }
    }
    
    public void menuPage(int choise){
        
        switch(choise){
            case 1 -> {
                System.out.println(kasBook.getHistory());
                menuPage(view.menu());
            }
            case 2 -> {
                System.out.print("Nominal: ");
                int n = sc.nextInt();
                sc.nextLine();
                System.out.print("Deskripsi: ");
                String d = sc.nextLine();
                System.out.println(kasBook.addMoney(n, d));
                menuPage(view.menu());
            }
            case 3 -> {
                System.out.print("Nominal: ");
                int n = sc.nextInt();
                sc.nextLine();
                System.out.print("Deskripsi: ");
                String d = sc.nextLine();
                System.out.println(kasBook.removeMoney(n, d));
                menuPage(view.menu());
            }
            case 4 -> {
                System.out.println(kasBook.deleteHistory());
                menuPage(view.menu());
            }
            case 0 -> back();
            default -> {
                System.out.println("Pilihan anda salah\n");
                menuPage(view.menu());
            }
        }
    }
    
    public void insightPage(int choise){
        switch(choise){
            case 0 -> back();
            default -> {
                System.out.println("Pilihan anda salah\n");
                insightPage(view.insight());
            }
        }
    }
    
    public void back(){
        firstPage(view.firstMenu());
    }
}

public class Main
{
	public static void main(String[] args) {
		view view = new view();
        kasBookController kasBook = new kasBookController();
        choise choise = new choise(view, kasBook);
        choise.firstPage(view.firstMenu());
	}
}
