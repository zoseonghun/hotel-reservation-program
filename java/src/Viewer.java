import static common.Utility.input;
import java.util.List;
import static common.Utility.*;

public class Viewer {

    public final static String PROGRAM_VERSION = "1.0.0";

    public static void mainMenu() {
        while (true) {

            System.out.println("호텔 예약 프로그램 ver : " + PROGRAM_VERSION);
            System.out.println("메뉴를 선택해 주세요");
            System.out.println("1. 새로운 예약");
            System.out.println("2. 예약 수정 / 취소");
            System.out.println("3. 후기 관리");
            System.out.println("4. 프로그램 종료");

            switch (input(">>")) {
                case "1":
                    makeReservation();
                    break;
                case "2":
                    //예약리스트 받기
                    List<Reservation> rsvnList = searchReservationMenu();
                    if (rsvnList == null) {
                        break;
                    }
                    //예약리스트 중 수정대상인 예약건 선택하기
                    Reservation targetRsvn = selectReservation(rsvnList);
                    //수정 또는 삭제 중 선택하기
                    modifyOrDelete(targetRsvn);
                    break;
                case "3":
                    break;
                case "4":
                    System.exit(0);
                default:
                    System.out.println("없는 번호입니다. 다시 입력해주세요");
            }
        }
    }

    private static void makeReservation() {
        searchMemberMenu();
    }

    private static void searchMemberMenu() {
        System.out.println("회원을 검색합니다.");
        String inputName = input("이름을 입력해주세요 >> ");
        String inputPhone = input("전화번호를 입력해주세요 >> ");

        System.out.println(inputName + inputPhone);

        Member member = Controller.searchMember(inputName, inputPhone);

        if (member == null) {
            member = addNewMemberMenu(inputName, inputPhone);
        }
    }

    private static Member addNewMemberMenu(String inputName, String inputPhone) {

        return null;
    }

    //수정 또는 삭제 메서드
    private static void modifyOrDelete(Reservation targetRsvn) {
        outer:
        while (true) {
            System.out.println("1. 수정 \n2. 삭제");
            String select = input(">> ");
            switch (select) {
                case "1":
                    Controller.modifyReservation(targetRsvn);
                    break outer;
                case "2":
                    Controller.deleteReservation(targetRsvn);
                    break outer;
                default:
                    System.out.println("원하시는 메뉴의 번호를 선택해주세요");
            }
        }

    }

    //예약찾기 메뉴에서 받은 예약리스트 중 수정할 예약 1건 선택하는 메서드
    //조회한 전체 리스트 출력 후 번호를 선택합니다.
    //예약번호로 1건만 조회되었을때는 따로 선택하지 않으려고 했는데 프로그램 실행시 같은 예약번호로 2건 중복출력되는 버그가 있어서
    //예약번호 조회시에도 동일하게 진행합니다.
    private static Reservation selectReservation(List<Reservation> rsvnList) {
        for (int i = 0; i < rsvnList.size(); i++) {
            System.out.println((i + 1) + ". " + rsvnList.get(i));
        }
        Reservation targetRsvn = null;
        while (targetRsvn == null) {
            String inputIndex = input("수정 또는 삭제할 예약을 선택해주세오 >> ");
            int targetIndex = Integer.parseInt(inputIndex) - 1;
            try {
                targetRsvn = rsvnList.get(targetIndex);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("목록에 존재하는 예약건의 번호를 입력해주세요");
            }
        }
        return targetRsvn;
    }

    /**
     * 예약 찾기 메뉴
     * 1. 회원정보로 찾기   2. 예약번호로 예약찾기
     * 선택하여 예약리스트 리턴받기
     * 반환받은 예약리스트로 예약선택하기 메서드 호출
     */
    private static List<Reservation> searchReservationMenu() {
        String searchName;
        String searchNumber;
        String searchWith;
        List<Reservation> rsvnList;
        while (true) {
            System.out.println("1. 회원정보로 예약찾기 \n2. 예약번호로 예약찾기");
            String select = input(">> ");
            //1번 회원정보로 예약찾기
            if (select.equals("1")) {
                searchName = input("회원 이름 >>");
                searchNumber = input("회원 휴대폰 번호 >>");
                Member mbr = Controller.searchMember(searchName, searchNumber);
                //휴대폰 번호로 회원정보를 조회하여 일치하는 데이터가 없으면 선택으로 돌아갑니다
                if (mbr == null) {
                    System.out.println("일치하는 회원 정보가 없습니다");
                    pause();
                    continue;
                }
                //일치하는 정보가 있으면 예약 선택 메서드 호출
                rsvnList = Controller.searchReservation(mbr);
                break;
                //2번 예약번호로 메서드 호출하기
            } else if (select.equals("2")) {
                searchWith = input("예약번호 >>");
                rsvnList = Controller.searchReservation(searchWith);
                break;
            } else {
                //1, 2 이외 입력시 선택으로 돌아가기
                System.out.println("원하시는 예약찾기 방법을 숫자로 선택해주세요");
            }
        }
        //예약리스트 가지고 예약선택하기로 넘어갑니다
        return rsvnList;
    }

    public static void main(String[] args) {
        Viewer viewer = new Viewer();
        viewer.mainMenu();
    }
}
