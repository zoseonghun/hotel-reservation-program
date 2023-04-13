import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

//        saveAvailableDate();

        Viewer.mainMenu();

    }

    /**
     * 초기 데이터베이스 저장용 메서드 오늘부터 30일 까지의 객체를 생성한다.
     */
    private static void saveAvailableDate() {
        try (FileOutputStream fos = new FileOutputStream("java/src/sav/availableDate.sav")) {

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            ArrayList<AvailableDate> obj = new ArrayList<>();

            for (int i = 0; i < 30; i++) {
                obj.add(new AvailableDate(LocalDate.now().plusDays(i), new HashMap<>(Map.of(
                        RoomSize.DELUXE_DOUBLE, 10, RoomSize.DELUXE_TWIN, 10,
                        RoomSize.BOUTIQUE_KING, 10, RoomSize.JR_SUITE, 10,
                        RoomSize.SUITE, 10, RoomSize.PRESIDENTIAL_SUITE, 10
                ))));
            }

            oos.writeObject(obj);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveMember() {

        try (FileOutputStream fos = new FileOutputStream("java/src/sav/member.sav")) {

            ObjectOutputStream oos = new ObjectOutputStream(fos);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
