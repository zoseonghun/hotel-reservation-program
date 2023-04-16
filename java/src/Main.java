import java.io.*;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.Utility.ROOT_DIRECTORY;

public class Main {

    public static void main(String[] args) {

//        resetAvailableDate();

        Viewer.mainMenu();

//        showMembers();

    }

    /**
     * 멤버 확인용 임시 메서드
     */
    public static void showMembers() {
        try (FileInputStream fis = new FileInputStream(ROOT_DIRECTORY + "sav/member.sav")) {

            ObjectInputStream ois = new ObjectInputStream(fis);

            List<Member> obj = (List<Member>) ois.readObject();

            obj.forEach(System.out::println);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 초기 데이터베이스 저장용 메서드 오늘부터 30일 까지의 객체를 생성한다.
     * 정보를 한번에 초기화 시키고 싶을 때 사용하자~~~~~~~~~~~~
     */
    private static void resetAvailableDate() {
        try (FileOutputStream fos = new FileOutputStream(ROOT_DIRECTORY + "sav/availableDate.sav")) {

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

}
