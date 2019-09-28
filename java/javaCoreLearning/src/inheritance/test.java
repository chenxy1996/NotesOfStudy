package inheritance;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        int height;
        int width;
        Scanner in = new Scanner(System.in);
        System.out.print("width: ");
        width = in.nextInt();
        System.out.print("height: ");
        height = in.nextInt();
        if (width > 0 && height > 0) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                        System.out.print('*');
                    } else {
                        System.out.print(' ');
                    }
                }
                System.out.print('\n');
            }
        } else {
            System.out.println("Please enter valid width and height!!!");
        }
    }
}
