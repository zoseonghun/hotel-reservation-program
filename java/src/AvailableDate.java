import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class AvailableDate implements Serializable {

    private final LocalDate date;
    private Map<RoomSize, Integer> roomVacancy;

    public AvailableDate(LocalDate date, Map<RoomSize, Integer> roomVacancy) {
        this.date = date;
        this.roomVacancy = roomVacancy;
    }

    @Override
    public String toString() {
        return  date + " 일자 남은 방 갯수\n" +
                "디럭스 더블 : " + roomVacancy.get(RoomSize.DELUXE_DOUBLE) + "\n" +
                "디럭스 트윈 : " + roomVacancy.get(RoomSize.DELUXE_TWIN) + "\n" +
                "부띠끄 킹 : " + roomVacancy.get(RoomSize.BOUTIQUE_KING) + "\n" +
                "주니어 스위트 : " + roomVacancy.get(RoomSize.JR_SUITE) + "\n" +
                "스위트 : " + roomVacancy.get(RoomSize.SUITE) + "\n" +
                "프레지덴셜 스위트 : " + roomVacancy.get(RoomSize.PRESIDENTIAL_SUITE) + "\n";
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailableDate that = (AvailableDate) o;
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    public Map<RoomSize, Integer> getRoomVacancy() {
        return roomVacancy;
    }

    public void setRoomVacancy(Map<RoomSize, Integer> roomVacancy) {
        this.roomVacancy = roomVacancy;
    }

    public void reduceVacancy(RoomSize roomSize) {
        roomVacancy.put(roomSize, roomVacancy.get(roomSize) - 1);
    }

    public void increaseVacancy(RoomSize roomSize) {
        roomVacancy.put(roomSize, roomVacancy.get(roomSize) + 1);
    }
}
