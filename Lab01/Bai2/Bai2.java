package Lab01.Bai2;

public class Bai2 {

    public static double tinhPiGanDung() {
        double banKinh = 1.0;
        double buoc = banKinh/10000.0;
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

    public static void main(String[] args) {
        double pi = tinhPiGanDung();
        System.out.println("Gia tri Pi xap xi " + pi);
    }
}