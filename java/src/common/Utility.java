package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utility {

    public static String input(String message) {
        System.out.println(message);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            return br.readLine();
        } catch (IOException e) {
            System.out.println("입력 오류 발생!");
        }

        return null;
    }

    public static void makeLine() {
        System.out.println("====================================================");
    }
}
