import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Reservation implements Serializable {

    private long reservationId;
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
        this.reservationId = hashCode() + Integer.MAX_VALUE * 100L + 2;
        calcCost();
    }

    private void calcCost() {
        int wonPerDay = 0;
        switch (this.roomSize) {
            case DELUXE_DOUBLE:
            case DELUXE_TWIN:
                wonPerDay = 20;
            case BOUTIQUE_KING:
                wonPerDay = 30;
            case JR_SUITE:
                wonPerDay = 40;
            case SUITE:
                wonPerDay = 50;
            case PRESIDENTIAL_SUITE:
                wonPerDay = 60;
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
        return  "예약자명: " + member.getName() +
                "\t 객실타입: " + roomSize +
                "\t 체크인: " + checkIn +
                "\t 체크아웃: " + checkOut +
                "\t 투숙인원: " + guestNum +
                "\t 객실요금: " + cost +
                "\t 예약번호: " + hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(member, that.member) && Objects.equals(checkIn, that.checkIn) && Objects.equals(checkOut, that.checkOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, checkIn, checkOut, guestNum, cost);
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
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
