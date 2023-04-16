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
        input("계속 진행하시려면 엔터를 입력하세요\n");
    }

    /**
     * 시간에 따라 고유 문자열을 만들어 주는 함수
     * @param v : hashCode 값을 넣으면
     * @return : 고유 문자열이 출력된다
     */
    public static String longToBase64(long v) {
        final char[] digits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
                'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                'Y', 'Z', '-', '.'
        };
        int shift = 6;
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << shift;
        long mask = radix - 1;
        long number = v;
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= shift;
        } while (number != 0);
        return new String(buf, charPos, (64 - charPos));
    }

    public static void clear() {
        try {
            Runtime.getRuntime().exec("cls");
        } catch (IOException e) {
            System.out.println("클리어 하는데 오류 발생!");
        }
    }
}