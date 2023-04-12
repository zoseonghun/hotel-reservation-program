import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public static void makeReservation() {
        Member targetMember = searchMemberMenu();

        List<AvailableDate> availableRooms = searchAvailableRoomsMenu(targetMember);

        RoomSize selectedRoomSize = showAndSelectAvailableRooms(availableRooms);

        int guestNum = selectGuestNum();

        showTempReservationinfo(targetMember, availableRooms, selectedRoomSize, guestNum);
    }

    private static void showTempReservationinfo(Member targetMember, List<AvailableDate> availableRooms, RoomSize selectedRoomSize, int guestNum) {
        System.out.println(targetMember.getName() + "님이 선택하신 예약 정보입니다.");
        System.out.println("체크인 : " + availableRooms.get(0).getDate());
        System.out.println("체크아웃 : " + availableRooms.get(availableRooms.size() - 1).getDate());
        System.out.println("룸 타입 : " + selectedRoomSize);
        System.out.println("인원 : " + guestNum);

        while (true) {
            String choice = input("예약을 확정하시겠습니까? [y/n]");

            switch (choice.toLowerCase().charAt(0)) {
                case 'y':
                    Controller.confirmReservation(targetMember, availableRooms, selectedRoomSize, guestNum);
                    break;
                case 'n':
                    break;
                default:
                    System.out.println("다시 입력해주세요");
            }
        }
    }

    private static int selectGuestNum() {

        while (true) {
            try {
                int inputNum = Integer.parseInt(input("인원 수를 입력해 주세요 ( 2 ~ 4 ) : "));

                if (inputNum < 0 || inputNum > 4)
                    throw new NumberFormatException();

                return inputNum;
            } catch (NumberFormatException e) {
                System.out.println("허용되지 않는 입력입니다.");
            }
        }
    }


    private static RoomSize showAndSelectAvailableRooms(List<AvailableDate> availableRooms) {

        System.out.println(availableRooms.get(0).getDate() + "일부터 " + availableRooms.get(availableRooms.size() - 1).getDate() + "일까지 가능한 객실 수 입니다.");

        Map<RoomSize, Integer> minRooms = calcMinRooms(availableRooms);

        minRooms.forEach((k, v) -> {
            System.out.print(k + ": ");

            for (int i = 0; i < v; i++) {
                System.out.print("#");
            }

            System.out.println(v + "개 남음!");
        });

        List<RoomSize> availableRoomSizeList = new ArrayList<>();

        System.out.println("방 사이즈를 선택해주세요");
        int count = 1;
        minRooms.forEach((k, v) -> {
            if (v > 0) {
                availableRoomSizeList.add(k);
                System.out.println(count + ": " + k);
            }
        });

        while (true) {
            try {
                return availableRoomSizeList.get(Integer.parseInt(input(">> ")) - 1);
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해 주세요");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("범위를 벗어난 숫자입니");
            }
        }
    }

    private static Map<RoomSize, Integer> calcMinRooms(List<AvailableDate> availableRooms) {

        // 초기 value 들은 일단 10으로 만들어놓고 나중에 수정할 예정
        Map<RoomSize, Integer> minRoomMap = new HashMap<>(Map.of(
                RoomSize.DELUXE_DOUBLE, 10, RoomSize.DELUXE_TWIN, 10,
                RoomSize.BOUTIQUE_KING, 10, RoomSize.JR_SUITE, 10,
                RoomSize.SUITE, 10, RoomSize.PRESIDENTIAL_SUITE, 10
        ));

        availableRooms.stream()
                .map(AvailableDate::getRoomVacancy)
                .collect(Collectors.toList())
                .forEach(m -> {
                    m.forEach((k, v) -> {
                        if (minRoomMap.get(k) > v)
                            minRoomMap.put(k, v);
                    });
                });

        return minRoomMap;

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
