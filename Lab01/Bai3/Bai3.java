package Lab01.Bai3;

import java.util.Arrays;
import java.util.Scanner;

class Diem implements Comparable<Diem> {
    int x, y;

    public Diem(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int compareTo(Diem d) {
        if (this.x == d.x) {
            return Integer.compare(this.y, d.y);
        }
        return Integer.compare(this.x, d.x);
    }
}

public class Bai3 {

    public static double tichCoHuong(Diem o, Diem a, Diem b) {
        return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
    }

    public static Diem[] timBaoLoi(Diem[] dsDiem) {
        int n = dsDiem.length;
        if (n <= 3) return dsDiem;

        Diem[] bao = new Diem[2 * n];
        Arrays.sort(dsDiem);
        int k = 0;
        for (int i = 0; i < n; i++) {
            while (k >= 2 && tichCoHuong(bao[k - 2], bao[k - 1], dsDiem[i]) <= 0) {
                k--;
            }
            bao[k++] = dsDiem[i];
        }

        for (int i = n - 2, t = k + 1; i >= 0; i--) {
            while (k >= t && tichCoHuong(bao[k - 2], bao[k - 1], dsDiem[i]) <= 0) {
                k--;
            }
            bao[k++] = dsDiem[i];
        }

        return Arrays.copyOfRange(bao, 0, k - 1);
    }

    public static void main(String[] args) {
        Scanner nhap = new Scanner(System.in);

        System.out.print("Nhap so luong diem: ");
        int n = nhap.nextInt();

        Diem[] dsDiem = new Diem[n];

        System.out.println("Nhap toa do cac diem (x y):");
        for (int i = 0; i < n; i++) {
            System.out.print("Diem " + (i + 1) + ": ");
            int x = nhap.nextInt();
            int y = nhap.nextInt();
            dsDiem[i] = new Diem(x, y);
        }

        Diem[] ketQua = timBaoLoi(dsDiem);

        System.out.println("\nCac diem thuoc bao loi la:");
        for (Diem d : ketQua) {
            System.out.println(d.x + " " + d.y);
        }

        nhap.close();
    }
}