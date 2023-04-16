import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static common.Utility.longToBase64;

public class Reservation implements Serializable {

    private String reservationId;
    private RoomSize roomSize;
    private Member member;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int guestNum;
    private long cost;

    public Reservation() {
    }

    public Reservation(RoomSize roomSize, Member member, LocalDate checkIn, LocalDate checkOut, int guestNum) {
        this.roomSize = roomSize;
        this.member = member;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guestNum = guestNum;
        this.reservationId = makeId();
        calcCost();
    }

    private String makeId() {
        return longToBase64(hashCode());
    }

    private void calcCost() {
        int wonPerDay = 0;
        switch (this.roomSize) {
            case DELUXE_DOUBLE:
            case DELUXE_TWIN:
                wonPerDay = 20;
                break;
            case BOUTIQUE_KING:
                wonPerDay = 30;
                break;
            case JR_SUITE:
                wonPerDay = 40;
                break;
            case SUITE:
                wonPerDay = 50;
                break;
            case PRESIDENTIAL_SUITE:
                wonPerDay = 60;
                break;
        }

        int additionalCost = 0;

        switch (this.guestNum) {
            case 3:
                additionalCost = 2;
                break;
            case 4:
                additionalCost = 4;
                break;
        }

        this.cost = wonPerDay * ChronoUnit.DAYS.between(this.checkIn, this.checkOut) + additionalCost;

    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "예약자: {0}   객실타입: {1}   체크인: {2}  체크아웃: {3}  " +
                        "숙박일수: {4}박   인원: {5}명   요금: {6}만원   예약번호: {7}  " +
                        "현재 마일리지: {8}"
                , member.getName()
                , String.format("%20s", roomSize)
                , checkIn.format(DateTimeFormatter.ofPattern("MM/dd"))
                , checkOut.format(DateTimeFormatter.ofPattern("MM/dd"))
                , String.format("%2d", ChronoUnit.DAYS.between(checkIn,checkOut))
                , guestNum
                , String.format("%3d", cost)
                , String.format("%10s", reservationId)
                , member.getMileage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return guestNum == that.guestNum && roomSize == that.roomSize && Objects.equals(member, that.member) && Objects.equals(checkIn, that.checkIn) && Objects.equals(checkOut, that.checkOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomSize, member, checkIn, checkOut, guestNum);
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public RoomSize getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(RoomSize roomSize) {
        this.roomSize = roomSize;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getGuestNum() {
        return guestNum;
    }

    public void setGuestNum(int guestNum) {
        this.guestNum = guestNum;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }
}
