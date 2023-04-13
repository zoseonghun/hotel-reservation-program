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

        try (FileInputStream fis = new FileInputStream("java/src/sav/member.sav")){;
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
        try (FileOutputStream fos = new FileOutputStream("java/src/sav/member.sav")){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(memberList);
        } catch (IOException e) {
            System.out.println("멤버리스트를 저장하는데 실패하였습니다");
            e.printStackTrace();
        }
    }

    public static void loadDatabaseInFile() {

        loadAvailableDateList();

        reservationList = new ArrayList<>();
        memberList = new ArrayList<>();
        reviewList = new ArrayList<>();
    }

    private static void loadAvailableDateList() {

        try (FileInputStream fis = new FileInputStream("java/src/sav/availableDate.sav")) {

            ObjectInputStream ois = new ObjectInputStream(fis);

            availableDateList = (List<AvailableDate>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("데이터 로드 오류 발생");
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

    }

    private static void updateAvailableRoom() {

        try (FileOutputStream fos = new FileOutputStream("java/src/sav/availableDate.sav")) {

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

    }
}
