import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static common.Utility.*;

public class Controller {

    private static List<AvailableDate> availableDateList;
    private static List<Reservation> reservationList;
    private static List<Member> memberList;
    private static List<Review> reviewList;

    static {
        loadDatabaseInFile();
        loadMemberList();
    }

    public static void loadMemberList() {
        try (FileInputStream fis = new FileInputStream(ROOT_DIRECTORY + "sav/member.sav")){;
            ObjectInputStream ois = new ObjectInputStream(fis);
            memberList = (List<Member>) ois.readObject();
        } catch (IOException e) {
            System.out.println("멤버리스트를 불러오지 못했습니다");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("멤버리스트를 불러오지 못했습니다");
        }
    }
    public static void saveMemberList() {
        try (FileOutputStream fos = new FileOutputStream(ROOT_DIRECTORY + "sav/member.sav")){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(memberList);
        } catch (IOException e) {
            System.out.println("멤버리스트를 저장하는데 실패하였습니다");
            e.printStackTrace();
        }
    }

    public static void modifyMemberProfile(Member targetMbr){
        System.out.println(targetMbr.getName() + "님의 회원정보를 변경합니다");
        String select = "3";
        while(true) {
            System.out.printf("%10s %10s\n", "1. 휴대폰번호", "2. 이메일주소");
            select = input("수정할 정보의 번호를 선택해주세요. ( 메인메뉴로 돌아가기: 0 )\n >> ");
            switch (select) {
                case "1":
                    String newNumber = input("새로운 휴대폰번호를 입력하세요. >> ");
                    targetMbr.setPhone(newNumber);
                    System.out.println(targetMbr + "님의 회원정보가 아래와 같이 변경되었습니다.");
                    System.out.println(targetMbr);
                    saveMemberList();
                    break;
                case "2":
                    String newEmail = input("새로운 이메일주소를 입력하세요. >> ");
                    targetMbr.setEmail(newEmail);
                    System.out.println(targetMbr + "님의 회원정보가 아래와 같이 변경되었습니다.");
                    System.out.println(targetMbr);
                    saveMemberList();
                    break;
                case "0":
                    Viewer.mainMenu();
                default:
                    System.out.println("수정할 정보의 번호를 선택해주세요. 0을 누르시면 메인메뉴로 돌아갑니다.");
            }
        }
    }

    public static void loadDatabaseInFile() {

        loadAvailableDateList();

        loadReservationList();

        memberList = new ArrayList<>();
        reviewList = new ArrayList<>();
    }

    private static void loadReservationList() {

        try (FileInputStream fis = new FileInputStream(ROOT_DIRECTORY + "sav/reservation.sav")) {

            ObjectInputStream ois = new ObjectInputStream(fis);

            reservationList = (List<Reservation>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("세이브 파일을 로드하지 못했습니다. 빈 세이브 파일로 진행합니다.");
            reservationList = new ArrayList<>();
        }

    }

    private static void loadAvailableDateList() {

        try (FileInputStream fis = new FileInputStream(ROOT_DIRECTORY + "sav/availableDate.sav")) {

            ObjectInputStream ois = new ObjectInputStream(fis);

            availableDateList = (List<AvailableDate>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("데이터 로드 오류 발생");
            System.out.println("세이브 파일들이 존재하는지 다시 확인하세요");
            System.exit(1);
        }

    }

    public static Member searchMember(String name, String phone) {

        try {

            return memberList.stream()
                    .filter(m -> m.getName().equals(name) && m.getPhone().equals(phone))
                    .findFirst().orElse(null);

        } catch (IndexOutOfBoundsException e) {

            return null;

        }
    }

    public static Member addNewMember(String name, String phone, String email, Gender gender) {
        Member newMember = new Member(name, phone, email, gender);
        memberList.add(newMember);
        saveMemberList();
        System.out.println("새로운 회원을 등록했습니다.");

        return newMember;
    }


    public static List<AvailableDate> searchAvailableRooms(LocalDate checkIn, LocalDate checkOut) {

        return availableDateList.stream()
                .filter(availableDate -> availableDate.getDate().isAfter(checkIn.minusDays(1)) &&
                        availableDate.getDate().isBefore(checkOut.plusDays(1)))
                .sorted(Comparator.comparing(AvailableDate::getDate))
                .collect(Collectors.toList());

    }


    public static void confirmReservation(Member targetMember, List<AvailableDate> availableRooms, RoomSize selectedRoomSize, int guestNum) {
        reduceAvailableRoom(availableRooms, selectedRoomSize);

        Reservation reservation = new Reservation(selectedRoomSize, targetMember, availableRooms.get(0).getDate(), availableRooms.get(availableRooms.size() - 1).getDate(), guestNum);

        System.out.println("예약이 완료되었습니다.");
        System.out.println("예약 번호 : " + reservation.getReservationId());
        System.out.println("가격 : " + reservation.getCost() + "만원");

        addReservation(reservation);

        updateAvailableRoom();
        updateReservation();

    }

    private static void updateReservation() {

        try (FileOutputStream fos = new FileOutputStream(ROOT_DIRECTORY + "sav/reservation.sav")) {

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(reservationList);

        } catch (IOException e) {
            System.out.println("파일 저장에 실패했습니다.");
        }

    }

    private static void updateAvailableRoom() {

        try (FileOutputStream fos = new FileOutputStream(ROOT_DIRECTORY + "sav/availableDate.sav")) {

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(availableDateList);

        } catch (IOException e) {
            System.out.println("파일 저장 오류 발생!!");
        }

    }

    private static void addReservation(Reservation reservation) {
        reservationList.add(reservation);

        reservation.getMember().addReservationList(reservation);
    }

    private static void reduceAvailableRoom(List<AvailableDate> availableRooms, RoomSize selectedRoomSize) {

        availableDateList.stream()
                .filter(availableRooms::contains) // 신기한 메서드 참조 - 자동완성 썼습니다
                .collect(Collectors.toList())
                .forEach(a -> a.reduceVacancy(selectedRoomSize));
    }

    private static void increaseAvailableRoom(List<AvailableDate> availableRooms, RoomSize selectedRoomSize){
        availableDateList.stream()
                .filter(availableRooms::contains) // 신기한 메서드 참조 - 자동완성 썼습니다
                .collect(Collectors.toList())
                .forEach(a -> a.increaseVacancy(selectedRoomSize));
    }


    //예약번호로 예약 찾기
    //Controller 예약리스트 중 예약번호 일치하는 예약 필터링 후 리스트 반환
    public static List<Reservation> searchReservation(String searchWith) {
        List<Reservation> rsvnList = null;
        try {
            long rsvnId = Long.parseLong(searchWith);
            rsvnList = reservationList.stream()
                    .filter(r -> r.getReservationId() == rsvnId)
                    .collect(Collectors.toList());
            if (rsvnList.size() == 0) {
                System.out.println("입력하신 정보와 일치하는 예약이 없습니다");
                pause();
                Viewer.mainMenu();
            }
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("입력하신 정보와 일치하는 예약이 없습니다");
            pause();
            Viewer.mainMenu();
        }
        return rsvnList;
    }

    //멤버객체로 예약 찾기
    //멤버객체가 가지고 있는 리스트 반환
    public static List<Reservation> searchReservation(Member mbr) {
        List<Reservation> rsvnList = mbr.getReservationList();
        if (rsvnList.size() == 0) {
            System.out.println("입력하신 정보와 일치하는 예약이 없습니다");
            pause();
            Viewer.mainMenu();

        }
        return rsvnList;
    }

    //예약 수정하기 선택시
    //예약삭제 메서드를 내부에서 호출합니다.
    //      -> 멤버 숙박일수, 마일리지, 멤버예약리스트에서 내역 삭제 진행
    // 예약 객체를 가지고 예약생성 메서드로 넘어갑니다.
    public static void modifyReservation(Reservation targetRsvn) {
        List<AvailableDate> availableRooms = searchAvailableRooms(targetRsvn.getCheckIn(), targetRsvn.getCheckOut());
        increaseAvailableRoom(availableRooms, targetRsvn.getRoomSize());
        deleteReservation(targetRsvn);
        Viewer.makeReservation(targetRsvn.getMember());
    }

    //예약삭제 메서드
    //      -> 멤버 숙박일수, 마일리지, 멤버예약리스트에서 내역 삭제
    public static void deleteReservation(Reservation targetRsvn) {
        Member targetMbr = targetRsvn.getMember();
        targetMbr.removeReservationList(targetRsvn);
        reservationList.remove(targetRsvn);

        updateReservation();
        updateAvailableRoom();
    }
}
