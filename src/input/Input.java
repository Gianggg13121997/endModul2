package input;

import java.util.Scanner;

public class Input {
    private static Scanner input = new Scanner(System.in);

    public static int inputInteger() {
        do {
            try {
                String data = input.nextLine();
                return Integer.parseInt(data);
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số :");
            }
        } while (true);
    }

    public static String inputString() {
        return input.nextLine();
    }


}

