import java.util.Scanner;

public class Bai1 {

    public static double tinhDienTichHinhTron(double banKinh) {
        double buoc = banKinh / 10000.0; 
        double tongDienTich = 0.0;

        for (double x = -banKinh; x <= banKinh; x += buoc) {
            for (double y = -banKinh; y <= banKinh; y += buoc) {
                if (x * x + y * y <= banKinh * banKinh) {
                    tongDienTich += buoc * buoc;
                }
            }
        }
        return tongDienTich;
    }

    public static double tinhPiGanDung() {
        return tinhDienTichHinhTron(1.0);
    }

    public static void main(String[] args) {
        Scanner nhap = new Scanner(System.in);
        
        System.out.print("Nhập bán kính hình tròn: ");
        double banKinh = nhap.nextDouble();

        double dienTich = tinhDienTichHinhTron(banKinh);
        double pi = tinhPiGanDung();

        System.out.println("Diện tích hình tròn xấp xỉ : " + dienTich);
        System.out.println("Giá trị PI xấp xỉ          : " + pi);

        nhap.close();
    }
}