import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static common.Utility.input;
import java.util.List;
import static common.Utility.*;

public class Viewer {

    private final static String PROGRAM_VERSION = "1.0.0"; // 프로그램 버전

    /**
     * 메인 메뉴
     */
    public static void mainMenu() {
        firstWindow();

        while (true) {

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
                    ReviewService.reviewMenu();
                    break;
                case "4":
                    System.exit(0);
                default:
                    System.out.println("없는 번호입니다. 다시 입력해주세요");
            }
        }
    }

    private static void firstWindow() {
        makeLine();
        makeSideWall();
        makeSideWall();
        makeSideWall();
        System.out.printf("#%31s             #\n", "호텔 예약 프로그램 ver : " + PROGRAM_VERSION);
        makeSideWall();
        makeSideWall();
        makeSideWall();
        makeLine();
        pause();
    }

    /**
     * 메인메뉴에서 1번 호출시 동작
     */
    public static void makeReservation() {
        Member targetMember = searchMemberMenu();

        List<AvailableDate> availableRooms = searchAvailableRoomsMenu(targetMember);

        RoomSize selectedRoomSize = showAndSelectAvailableRooms(availableRooms);

        int guestNum = selectGuestNum();

        showTempReservationInfo(targetMember, availableRooms, selectedRoomSize, guestNum);
    }

    /**
     * 수정 과정에서 호출
     * @param targetMember : 타겟 회원
     */
    public static void  makeReservation(Member targetMember) {
        List<AvailableDate> availableRooms = searchAvailableRoomsMenu(targetMember);

        RoomSize selectedRoomSize = showAndSelectAvailableRooms(availableRooms);

        int guestNum = selectGuestNum();

        showTempReservationInfo(targetMember, availableRooms, selectedRoomSize, guestNum);
    }

    /**
     * 예약정보 출력하는 메서드
     * @param targetMember : 회원
     * @param availableRooms : 체크인 날짜를 담고있는 객체
     * @param selectedRoomSize : 선택한 룸 사이즈
     * @param guestNum : 묵을 인원
     */
    private static void showTempReservationInfo(Member targetMember, List<AvailableDate> availableRooms, RoomSize selectedRoomSize, int guestNum) {
        System.out.println(targetMember.getName() + "님이 선택하신 예약 정보입니다.");
        System.out.println("체크인 : " + availableRooms.get(0).getDate());
        System.out.println("체크아웃 : " + availableRooms.get(availableRooms.size() - 1).getDate());
        System.out.println("룸 타입 : " + selectedRoomSize);
        System.out.println("인원 : " + guestNum);

        while (true) {
            String choice = input("예약을 확정하시겠습니까? [y/n] >> ");

            switch (choice.toLowerCase().charAt(0)) {
                case 'y':
                    Controller.confirmReservation(targetMember, availableRooms, selectedRoomSize, guestNum);
                    pause();
                    return;
                case 'n':
                    return;
                default:
                    System.out.println("다시 입력해주세요");
            }
        }
    }


    /**
     * 인원 선택 메뉴
     * @return : 선택한 인원
     */
    private static int selectGuestNum() {

        while (true) {
            try {
                int inputNum = Integer.parseInt(input("인원 수를 입력해 주세요 ( 1 ~ 4 ) >> "));

                if (inputNum < 0 || inputNum > 4)
                    throw new NumberFormatException();

                return inputNum;
            } catch (NumberFormatException e) {
                System.out.println("허용되지 않는 입력입니다.");
            }
        }
    }

    /**
     * 선택한 일자에서 가능한 방들을 보여주고 사이즈를 선택
     * @param availableRooms : 일자 및 방 수량을 가지고 있는 객체
     * @return : 방 사이즈
     */
    private static RoomSize showAndSelectAvailableRooms(List<AvailableDate> availableRooms) {

        System.out.println(availableRooms.get(0).getDate() + "일부터 " + availableRooms.get(availableRooms.size() - 1).getDate() + "일까지 가능한 객실 수 입니다.");

        Map<RoomSize, Integer> minRooms = new TreeMap<>(calcMinRooms(availableRooms));

        minRooms.forEach((k, v) -> {
            System.out.printf("%-18s:\t", k);

            for (int i = 0; i < v; i++) {
                System.out.print("##");
            }

            System.out.println("\t" + v + "개");
        });

        pause();

        List<RoomSize> availableRoomSizeList = new ArrayList<>();

        System.out.println("방 사이즈를 선택해주세요");
        AtomicInteger count = new AtomicInteger(1);
        minRooms.forEach((k, v) -> {
            if (v > 0) {
                availableRoomSizeList.add(k);
                System.out.println(count.getAndIncrement() + ": " + k);
            }
        });

        while (true) {
            try {
                return availableRoomSizeList.get(Integer.parseInt(input(">> ")) - 1);
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해 주세요");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("범위를 벗어난 숫자입니");
            }
        }
    }

    /**
     * 숙박하는 일자들 중에 가능한 방 사이즈의 최솟값들을 리턴하는 메서드
     * @param availableRooms : 숙박일자와 방 갯수를 가지고 있는 객체
     * @return : 방 타입과 최소 수량이 짝지어진 맵
     */
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
                .forEach(m -> m.forEach((k, v) -> {
                    if (minRoomMap.get(k) > v)
                        minRoomMap.put(k, v);
                }));

        return minRoomMap;

    }

    /**
     *
     * @param member : 회원
     * @return
     */
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

            if (checkDateSt.countTokens() == 4) {
                Queue<Integer> dateQueue = new LinkedList<>();

                try {
                    for (int i = 0; i < 4; i++) {
                        dateQueue.add(Integer.parseInt(checkDateSt.nextToken()));
                    }
                    return dateQueue;
                } catch (NumberFormatException e) {
                    System.out.println("구분자를 제외하고는 숫자만 입력해 주세요");
                }
            } else {
                System.out.println("입력 날짜의 수가 맞지 않습니다");
            }
        }

    }

    private static Member searchMemberMenu() {
        System.out.println("회원을 검색합니다.");
        String inputName = input("이름을 입력해주세요 >> ");
        String inputPhone = input("전화번호를 입력해주세요 >> ");

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
        String inputEmail = null;
        while (true) {
            inputEmail = input("email 을 입력해 주세요 >> ");

            // 유효성 검사
            if (!inputEmail.equals(""))
                break;

            System.out.println("올바르지 않은 입력입니다.");
        }

        return Controller.addNewMember(inputName, inputPhone, inputEmail, inputGender);
    }

    private static Gender inputGenderMenu() {
        while (true) {
            char genderInput;

            // 빈칸 입력시
            try {
                genderInput = input("성별을 입력해주세요 [f/m] >> ").toLowerCase().charAt(0);
            } catch (IndexOutOfBoundsException e) {
                genderInput = ' ';
            }

            switch (genderInput) {
                case 'f':
                    return Gender.FEMALE;
                case 'm':
                    return Gender.MALE;
                default:
                    System.out.println("올바른 값을 입력해주세요");
            }
        }
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
            int targetIndex;
            String inputIndex = input("수정 또는 삭제할 예약을 선택해주세오 >> ");
            try {
                targetIndex = Integer.parseInt(inputIndex) - 1;
                targetRsvn = rsvnList.get(targetIndex);
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
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

}
