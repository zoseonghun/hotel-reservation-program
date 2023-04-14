import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static common.Utility.ROOT_DIRECTORY;
import static common.Utility.input;

public class BoardViewer {
    Scanner scanner = new Scanner(System.in);

    public List<Review> boardList; // 게시글 객체를 저장할 List

    // 생성자
    public BoardViewer() {

        // 게시글 초기 상태로 리셋
//        this.boardList = resetBoardList();
//        saveBoardList();

        loadBoardList();

    }

    public void loadBoardList() {

        try (FileInputStream fis = new FileInputStream(ROOT_DIRECTORY + "/sav/review.sav")) {

            ObjectInputStream ois = new ObjectInputStream(fis);

            boardList = (List<Review>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("댓글목록 불러오기에 실패했습니다.");
        }

    }

    public void saveBoardList() {

        try (FileOutputStream fos = new FileOutputStream(ROOT_DIRECTORY + "/sav/review.sav")) {

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(boardList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 고정된 게시글
    public List<Review> resetBoardList() {

        this.boardList = new ArrayList<>();

        return List.of(
                new Review(boardList.size(),
                        "너무 좋아요! 무료업그레이드 감사합니다.ㅠㅠ",
                        "직원분들도 너무 친절하시고 또 부대시설도 만족스럽습니다. 안에 있는 사우나도 이용했는데 완전 좋았어요~",
                        "나홀로 여행",
                        Rate.EXCELLENT,
                        LocalDate.now(),
                        "970488461"),
                new Review(boardList.size(),
                        "좋은 위치 강남권에서 좋은 선택",
                        "신논현역에서 도보로 5분 정도 거리. 위치 좋음. 가격 위치 시설 고려 시 강남권에서 좋은 선택지~",
                        "떠돌이 방랑객",
                        Rate.GOOD,
                        LocalDate.of(2023, 4, 8),
                        "163580591"),
                new Review(boardList.size(),
                        "깔끔해서 편했습니다",
                        "시설전체가 깔끔해서 1박 편안하게 보냈습니다. 직원분들 응대도 친절했습니다",
                        "재방문 예정",
                        Rate.EXCELLENT,
                        LocalDate.of(2023, 3, 29),
                        "262597186"),
                new Review(boardList.size(),
                        "여기는 프론트가 왕이셔 , 피곤한분들 체크인도 조심해서 해야해",
                        "최악",
                        "다시는 안가요",
                        Rate.BAD,
                        LocalDate.of(2023, 2, 10),
                        "1355473404"),
                new Review(boardList.size(),
                        "숙박거부당했습니다",
                        "환불조치도 이루어지지 않았구요 주저리주저리 떠들기도 짜증나네요",
                        "별로요",
                        Rate.BAD,
                        LocalDate.of(2022, 12, 10),
                        "1720909456")
        );

    }

    public void getBoardList() {
        System.out.println("<<후기 게시판>>");
        System.out.printf("%3s%14s%15s%15s%15s%15s\n", "번호", "평점", "제목", "작성자", "작성일", "예약번호");
        System.out.println("==================================================================================================");
        if (boardList.isEmpty()) { // 게시글 객체들을 담은 리스트에 아무것도 없는 경우
            System.out.println("                                            게시글 없음");
        } else {
            for (Review rv : boardList) { // 글 번호를 1번부터 시작하기 위해 인덱스 +1
                // 제목, 작성자 글자가 5자 넘어가면 3항연산자 사용 나머지 부분 ...으로 표시
                // 제목, 작성자 5글자 한글기준으로 맞춤

                String rateInStar = "";
                switch (rv.getRate()) {
                    case EXCELLENT:
                        rateInStar = "★★★";
                        break;
                    case GOOD:
                        rateInStar = "★★";
                        break;
                    case BAD:
                        rateInStar = "★";
                        break;
                }

                System.out.printf("%5s%16s%15s%15s%17s%20s\n", boardList.indexOf(rv) + 1,
                        rateInStar,   // 평점
                        rv.getTitle().length() > 5 ? rv.getTitle().substring(0, 5) + "..." : rv.getTitle(), //  제목
                        rv.getWriter().length() > 5 ? rv.getWriter().substring(0, 5) + "..." : rv.getWriter(),  //  작성자
                        rv.getRegistDate(), rv.getReservation()); // 작성일, 예약번호
            }
        }
        System.out.println("==================================================================================================");
        System.out.println("1. 상세보기, 2. 삭제, 3. 목록");
    }

//    public void boardInsert() {// 개시글 새로쓰기
//        Review rv = new Review(); //게시글 객체 생성
//
//
//        System.out.println("글제목(취소 : quit):"); //제목 입력
//        String title = scanner.nextLine();
//        if (title.equals("quit")) {
//            System.out.println("작성이 취소되었습니다.");
//            return;
//        }
//
//        System.out.println("작성자(취소 : quit):"); //작성자 입력
//        String writer = scanner.nextLine();
//        if (writer.equals("quit")) {
//            System.out.println("작성이 취소되었습니다.");
//            return;
//        }
//
//        System.out.println("글내용(취소 : quit):"); // 내용 입력
//        String detail = scanner.nextLine();
//        if (detail.equals("quit")) {
//            System.out.println("작성이 취소되었습니다.");
//            return;
//        }
//
//        rv.setNum(boardList.size()); // 인덱스를 객체 번호에 저장
//        rv.setTitle(title); // 글 제목 저장
//        rv.setWriter(writer); // 글 작성자 저장
//        rv.setDetail(detail); // 글 내용 저장
//
//        // 현재 날짜 저장
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String registDate = dateFormat.format(new Date());
//        rv.setRegistDate(registDate);
//
//        boardList.add(rv); // List에 현재 작성한 게시글 저장
//        System.out.println("글이 추가되었습니다.\n");
//    }

    public void boardDetail(int select) { // 게시글 상세보기
        if (boardList.isEmpty()) { // boardList가 비어 있다면
            System.out.println("게시글이 존재하지 않습니다.");
            return;
        }
        Review rv = new Review(); // 게시글 객체 생성
        rv = boardList.get(select - 1); // 인덱스 = 글번호 -1. 해당 인덱스의 객체를 가져옴
        System.out.println("No. " + select); // 글 번호가 1부터 시작해서 + 1
        System.out.println("제목 : " + rv.getTitle());
        System.out.println("작성자 : " + rv.getWriter());
        System.out.println("------------------------------------------------------------------------");
        System.out.println("내용 : " + rv.getDetail());
        System.out.println("========================================================================");

        if (rv.getReply() != null)
            System.out.println("댓글 : " + rv.getReply());

        // 프로그램 종료되지 않게 try & catch 처리
        while (true) {
            System.out.println("(1. 댓글 등록 2. 댓글 수정, 3. 댓글 삭제, 4. 목록) :");
            try {
                select = Integer.parseInt(scanner.nextLine());
                if (select < 1 || select > 4)
                    throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("유효한 번호만 입력해주세요");
            }
        }
        if (select == 1) { // 현재 글 댓글 작성
            boardAddreply(rv);
            saveBoardList();
        } else if (select == 2) { // 현재 글 댓글 수정
            boardReplyUpdate(rv);
            saveBoardList();
        } else if (select == 3) { // 현재 글 댓글 삭제
            boardReplyDelete(rv);
            saveBoardList();
        } else { // 목록으로 이동
            return;
        }
    }

    // 댓글 작성
    public void boardAddreply(Review rv) {
        System.out.println("작성해주세요:");
        String comment = scanner.nextLine();
        rv.setReply(comment);
        System.out.println("추가 완료했습니다.");
//        System.out.println(rv.getNum());
        boardDetail(rv.getNum() + 1);
    }

    // 댓글 수정
    private void boardReplyUpdate(Review rv) {
        if (rv.getReply() == null) {
            System.out.println("등록된 댓글이 없습니다.");
            return;
        }
        System.out.println("수정할 댓글을 입력해주세요:");
        String updatedReply = scanner.nextLine();
        rv.setReply(updatedReply);
        System.out.println("댓글 수정이 완료되었습니다.");
        System.out.println(rv.getNum());
        boardDetail(rv.getNum() + 1);
    }

    // 댓글 삭제
    private void boardReplyDelete(Review rv) {
        if (rv.getReply() == null) {
            System.out.println("등록된 댓글이 없습니다.");
            return;
        }
        rv.setReply(null);
        System.out.println("댓글 삭제가 완료되었습니다.");
        System.out.println(rv.getNum());
        boardDetail(rv.getNum() + 1);
    }


//    public void boardUpdate(int select) { // 게시글 수정
//        if (boardList.isEmpty()) { // BoardList가 비어있는 경우
//            System.out.println("게시글이 없습니다.");
//            return;
//        }
//
//        Review rv = new Review(); // 게시글 객체 생성
//        rv = boardList.get(select - 1); // 인덱스 = 글번호 - 1. 해당 인덱스의 객체를 가져옴
//
//        System.out.println("글제목(취소 : quit):"); // 제목 수정
//        String title = scanner.nextLine();
//        if (title.equals("quit")) {
//            System.out.println("수정이 취소되었습니다.");
//            return;
//        }
//
//        System.out.println("작성자(취소 : quit):"); // 작성자 수정
//        String writer = scanner.nextLine();
//        if (writer.equals("quit")) {
//            System.out.println("수정이 취소되었습니다.");
//            return;
//        }
//
//        System.out.println("글내용(취소 : quit): "); // 내용 수정
//        String detail = scanner.nextLine();
//        if (detail.equals("quit")) {
//            System.out.println("수정이 취소되었습니다.");
//            return;
//        }
//        // 수정 취소를 하지 않았을 경우 입력한 값을 저장
//        rv.setTitle(title);
//        rv.setWriter(writer);
//        rv.setDetail(detail);
//
//        // 등록한 현재 날짜 저장
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd(수정됨)");
//        String registDate = dateFormat.format(new Date());
//        rv.setRegistDate(registDate); // 등록 날짜를 수정 날짜로 변경
//
//        boardList.set(boardList.indexOf(rv), rv); // 해당 객체의 인덱스 위치에 rv를 저장
//        System.out.println("글이 수정되었습니다.\n");
//    }

    public void boardDelete(int select) { // 게시글 삭제
        if (boardList.isEmpty()) {
            System.out.println("게시글이 없습니다.");
            return;
        }

        Review rv = new Review();
        rv = boardList.get(select - 1); // 선택한 번호의 객체
        boardList.remove(rv); // 해당 인덱스에 있는 객체 삭제
        System.out.println(select + "번 글이 삭제되었습니다.");

        saveBoardList();
    }

    public void txtRead() throws Exception { // 입력 스트림 (메모장 파일 읽기)
        Reader reader = new FileReader("D/Temp/BoardDB.txt"); // 해당 경로를 참조하는 입력 스트림 객체 생성
        while (true) {
            int data = reader.read(); // 데이터를 한 문자씩 읽어옴, 숫자로 저장됨
            if (data == -1) // 데이터가 없는 경우
                break;
            System.out.print((char) data); // 받아온 데이터를 문자로 반환하여 출력
        }
        reader.close(); // 입력 스트림 닫기
        System.out.println("데이터 로드 완료");
    }

    public void txtWrite() throws Exception { // 출력 스트림 (메모장 파일 쓰기)
        Writer writer = new FileWriter("D/Temp/BoardDB.txt"); // 해당 경로를 참조하는 출력 스트림 객체 생성
        String[] data = new String[10000]; // 내보낼 문자열을 저장할 변수,
        for (int i = 0; i < boardList.size(); i++) { // boardList의 객체를 하나씩 불러 data에 해당 값들을 저장
            Review rv = boardList.get(i);
            data[i] = boardList.indexOf(rv) + "|\t" + rv.getTitle() + "|\t" + rv.getDetail() + "|\t" + rv.getWriter()
                    + "|\t" + rv.getRegistDate() + "|\t\t";
            writer.write(data[i]); // 받아온 데이터를 출력
        }

        writer.flush(); // 버퍼에 남은 데이터를 파일로 출력(write) 후 버퍼를 비움
        // 버퍼가 꽉 차기 전에 프로그램이 종료되면 버퍼에 남아있는 내용이
        // 파일로 출력이 되지 않기 떄문에 사용 ( 사용자가 원할 때 사용하면 됨)
        writer.close(); // 출력 스트림 닫기
        System.out.println("현재 데이터 저장완료");
    }

}