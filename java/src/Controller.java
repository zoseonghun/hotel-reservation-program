import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    private static List<AvailableDate> availableDateList;
    private static List<Reservation> reservationList;
    private static List<Member> memberList;
    private static List<Review> reviewList;

    static {
        loadDatabaseInFile();
    }

    public static void loadDatabaseInFile() {
        availableDateList = new ArrayList<>();
        reservationList = new ArrayList<>();
        memberList = new ArrayList<>();
        reviewList = new ArrayList<>();
    }

    public static Member searchMember(String name, String phone) {

        try {

            return memberList.stream()
                    .filter(m -> m.getName().equals(name) && m.getPhone().equals(phone))
                    .collect(Collectors.toList()).get(0);

        } catch (IndexOutOfBoundsException e) {

            return null;

        }
    }

    public static Member addNewMember(String name, String phone, String email, Gender gender) {
        Member newMember = new Member(name, phone, email, gender);
        memberList.add(newMember);

        System.out.println("새로운 회원을 등록했습니다.");

        return newMember;
    }

    public static List<AvailableDate> searchAvailableRooms(LocalDate checkIn, LocalDate checkOut) {

        return availableDateList.stream()
                .filter(availableDate -> availableDate.getDate().isAfter(checkIn) &&
                        availableDate.getDate().isBefore(checkOut))
                .collect(Collectors.toList());

    }
}
