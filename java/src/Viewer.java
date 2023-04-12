import java.time.LocalDate;
import java.util.*;

import static common.Utility.input;

public class Viewer {

    private final static String PROGRAM_VERSION = "1.0.0";

    public static void mainMenu() {
        while (true) {

            System.out.println("호텔 예약 프로그램 ver : " + PROGRAM_VERSION);
            System.out.println("메뉴를 선택해 주세요");
            System.out.println("1. 새로운 예약");
            System.out.println("2. 예약 수정 / 취소");
            System.out.println("3. 후기 관리");
            System.out.println("4. 프로그램 종료");

            switch (input(">> ")) {
                case "1":
                    makeReservation();
                    break;
                case "2":
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
        Member targetMember = searchMemberMenu();

        List<AvailableDate> availableRooms = searchAvailableRoomsMenu(targetMember);

        AvailableDate selectedDateAndRoom = showAndSelectAvailableRooms(availableRooms);
    }

    private static AvailableDate showAndSelectAvailableRooms(List<AvailableDate> availableRooms) {
        availableRooms.forEach(System.out::println);

        System.out.println("원하는 객실을 선택하세요");
        input("1. 디럭스 >> ");

        return null;
    }

    private static List<AvailableDate> searchAvailableRoomsMenu(Member member) {

        System.out.println(member.getName() + "님의 예약을 진행합니다.");

        Queue<Integer> checkQ = inputDateMenu();

        int thisYear = LocalDate.now().getYear();

        LocalDate checkIn = LocalDate.of(thisYear, checkQ.poll(), checkQ.poll());
        LocalDate checkOut = LocalDate.of(thisYear, checkQ.poll(), checkQ.poll());

        return Controller.searchAvailableRooms(checkIn, checkOut);
    }

    private static Queue<Integer> inputDateMenu() {
        // 날짜 구분자
        String delimiter = "/- ";

        while (true) {
            String checkDateString = input("체크인 날짜를 입력해 주세요 (월/일) >> ") + "/" + input("체크아웃 날짜를 입력해 주세요 (월/일) >> ");

            StringTokenizer checkDateSt = new StringTokenizer(checkDateString, delimiter);

            switch (checkDateSt.countTokens()) {
                case 4:
                    Queue<Integer> dateQueue = new LinkedList<>();

                    try {
                        for (int i = 0; i < 4; i++) {
                            dateQueue.add(Integer.parseInt(checkDateSt.nextToken()));
                        }
                        return dateQueue;
                    } catch (NumberFormatException e) {
                        System.out.println("구분자를 제외하고는 숫자만 입력해 주세요");
                        break;
                    }

                default:
                    System.out.println("입력 날짜의 수가 맞지 않습니다");
            }
        }

    }

    private static Member searchMemberMenu() {
        System.out.println("회원을 검색합니다.");
        String inputName = input("이름을 입력해주세요 >> ");
        String inputPhone = input("전화번호를 입력해주세요 >> ");

        System.out.println(inputName + inputPhone);

        Member member = Controller.searchMember(inputName, inputPhone);

        if (member == null) {
            member = addNewMemberMenu(inputName, inputPhone);
        }

        return member;
    }

    private static Member addNewMemberMenu(String inputName, String inputPhone) {

        System.out.println("존재하지 않는 회원입니다.");
        System.out.println("새 회원을 등록합니다.");
        Gender inputGender = inputGenderMenu();
        String inputEmail = input("email 을 입력해 주세요");

        return Controller.addNewMember(inputName, inputPhone, inputEmail, inputGender);
    }

    private static Gender inputGenderMenu() {
        while (true) {
            switch (input("성별을 입력해주세요 [f/m] >> ").toLowerCase().charAt(0)) {
                case 'f':
                    return Gender.FEMALE;
                case 'm':
                    return Gender.MALE;
                default:
                    System.out.println("올바른 값을 입력해주세요");
            }
        }
    }
}
