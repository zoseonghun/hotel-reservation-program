import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static common.Utility.*;

public class Controller {

    private static Map<LocalDate, Map<RoomSize, Integer>> availableDateList;
    private static List<Reservation> reservationList;
    private static List<Member> memberList;
    private static List<Review> reviewList;

    static {
        loadDatabaseInFile();
    }

    public static void loadDatabaseInFile() {
        availableDateList = new HashMap<>();
        reservationList = new ArrayList<>();
        memberList = new ArrayList<>();
        reviewList = new ArrayList<>();
    }

    public static Member searchMember(String name, String phone) {

        Member findMember = null;
        try {
            findMember = memberList.stream()
                    .filter(m -> m.getName().equals(name) && m.getPhone().equals(phone))
                    .collect(Collectors.toList()).get(0);
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }

        return findMember;
    }


    //예약번호로 예약 찾기
    //Controller 예약리스트 중 예약번호 일치하는 예약 필터링 후 리스트 반환
    public static List<Reservation> searchReservation(String searchWith) {
        List<Reservation> rsvnList = reservationList;
//        테스트용 코드입니다
//        Reservation rs1 = new Reservation(RoomSize.DELUXE_DOUBLE, new Member("한", "1111", "abc@gmail", Gender.FEMALE), LocalDate.now(), LocalDate.now().plusDays(2), 2);
//        reservationList.add(rs1);
//        System.out.println(rs1.getReservationId());
//        List<Reservation> rsvnList = null;
        try {
            int rsvnId = Integer.parseInt(searchWith);
            rsvnList = reservationList.stream()
                    .filter(r -> r.getReservationId() == rsvnId)
                    .collect(Collectors.toList());
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("입력하신 정보와 일치하는 예약이 없습니다");
            pause();
        }
        return rsvnList;
    }

    //멤버객체로 예약 찾기
    //멤버객체가 가지고 있는 리스트 반환
    public static List<Reservation> searchReservation(Member mbr) {
        List<Reservation> rsvnList = mbr.getReservationList();
        return rsvnList;
    }

    //예약 수정하기 선택시
    //예약삭제 메서드를 내부에서 호출합니다.
    //      -> 멤버 숙박일수, 마일리지, 멤버예약리스트에서 내역 삭제 진행
    // 예약 객체를 가지고 예약생성 메서드로 넘어갑니다.
    public static void modifyReservation(Reservation targetRsvn) {
        deleteReservation(targetRsvn);
        Viewer viewer = new Viewer();
        viewer.makeReservation(targetRsvn);
    }

    //예약삭제 메서드
    //      -> 멤버 숙박일수, 마일리지, 멤버예약리스트에서 내역 삭제
    public static void deleteReservation(Reservation targetRsvn) {
        Member targetMbr = targetRsvn.getMember();
        targetMbr.removeReservationList(targetRsvn);
        reservationList.remove(targetRsvn);
    }
}
