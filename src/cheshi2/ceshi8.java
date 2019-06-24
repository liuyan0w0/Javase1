package cheshi2;

public class ceshi8 {
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 1;i<=100;i++){
            if (i % 3 != 0) {
                sum = sum + i;
            }
        }
        System.out.println("1-100之间不能被3整除的数之和:"+sum);
    }

}
