import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

}
