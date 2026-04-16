package Lab01.Bai4;

import java.util.*;

class KetQuaDayCon {
    int maxLen;
    int start;
    int end;

    public KetQuaDayCon(int maxLen, int start, int end) {
        this.maxLen = maxLen;
        this.start = start;
        this.end = end;
    }
}

class XuLyDayCon {
    public KetQuaDayCon timDayConDaiNhat(int[] a, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int sum = 0;
        int maxLen = 0;
        int start = -1, end = -1;

        for (int i = 0; i < a.length; i++) {
            sum += a[i];

            if (map.containsKey(sum - k)) {
                int prevIndex = map.get(sum - k);
                int len = i - prevIndex;

                if (len > maxLen) {
                    maxLen = len;
                    start = prevIndex + 1;
                    end = i;
                }
            }
            map.putIfAbsent(sum, i);
        }
        
        return new KetQuaDayCon(maxLen, start, end);
    }
}


public class Bai4 {
    public static void main(String[] args) {
        Scanner nhap = new Scanner(System.in);
        nhap.useDelimiter("[,\\s]+");

        if (!nhap.hasNextInt()) {
            System.out.println("Lỗi input");
            nhap.close();
            return;
        }

        int n = nhap.nextInt();
        int k = nhap.nextInt();

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = nhap.nextInt();
        }

        XuLyDayCon xuLy = new XuLyDayCon();
        KetQuaDayCon ketQua = xuLy.timDayConDaiNhat(a, k);


        if (ketQua.maxLen == 0) {
            System.out.println("Khong tim thay day con nao co tong bang " + k);
        } else {
            System.out.println("Day con dai nhat co tong bang " + k + ":");
            for (int i = ketQua.start; i <= ketQua.end; i++) {
                System.out.print(a[i] + (i < ketQua.end ? ", " : ""));
            }
            System.out.println();
        }

        nhap.close();
    }
}