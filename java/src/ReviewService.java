import common.Utility;

import java.util.Scanner;

import static common.Utility.*;

public class ReviewService {

    public static void reviewMenu() {
        Scanner scanner = new Scanner(System.in); // 입력 받는 객체
        BoardViewer dao = new BoardViewer(); // 게시판 접근 객체

        // 게시판 프로그램
        while (true) {
            dao.getBoardList(); // 게시판 목록 불러오기
            int answer = Integer.parseInt(scanner.nextLine());

            if (answer == 1) { // 상세보기
                System.out.println("게시글 번호를 선택하세요.>>");
                int select = Integer.parseInt(input(">>"));
                dao.boardDetail(select); // 선택한 글의 상세페이지 출력
            } else if (answer == 2) { //삭제하기
                System.out.println("삭제할 글 번호를 선택하세요>>");
                int select = Integer.parseInt(input(">>"));
                dao.boardDelete(select); // 선택한 글 삭제
            } else if (answer == 3) { // 종료
                System.out.println("게시판 프로그램이 종료되었습니다.");
                break;
            } else {// 그 외의 입력을 한 경우
                System.out.println("유효한 번호만 입력해주세요");
            }
        }
    }
}