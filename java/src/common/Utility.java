package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utility {

    public static final String ROOT_DIRECTORY = "java/src/";

    public static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static String input(String message) {

        while (true) {
            System.out.print(message);
            try {
                return br.readLine();
            } catch (IOException e) {
                System.out.println("입력 오류 발생! 다시 입력해주세요");
            }
        }

    }

    public static void makeLine() {
        System.out.println("====================================================");
    }

    public static void makeSideWall() {
        System.out.println("#                                                  #");
    }

    public static void pause(){
        input("계속 진행하시려면 엔터를 입력하세요");
    }

    public static void clear() {
        try {
            Runtime.getRuntime().exec("cls");
        } catch (IOException e) {
            System.out.println("클리어 하는데 오류 발생!");
        }
    }
}