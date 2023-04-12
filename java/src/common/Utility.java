package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utility {

    public static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static String input(String message) {
        System.out.print(message);

        try {
            return br.readLine();
        } catch (IOException e) {
            System.out.println("입력 오류 발생!");
            return "";
        }

    }

    public static void makeLine() {
        System.out.println("====================================================");
    }

    public static void pause(){
        input("계속 진행하시려면 엔터를 입력하세요");
    }
}