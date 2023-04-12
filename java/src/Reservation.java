import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Reservation {

    private int reservationId;
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
        this.reservationId = hashCode();
    }

    private void calcCost() {
        int dolorPerDay = 0;
        switch (this.roomSize) {
            case DELUXE_DOUBLE:
            case DELUXE_TWIN:
                dolorPerDay = 20;
            case BOUTIQUE_KING:
                dolorPerDay = 30;
            case JR_SUITE:
                dolorPerDay = 40;
            case SUITE:
                dolorPerDay = 50;
            case PRESIDENTIAL_SUITE:
                dolorPerDay = 60;
        };

        this.cost = dolorPerDay * ChronoUnit.DAYS.between(this.checkIn, this.checkOut);

    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", roomSize=" + roomSize +
                ", member=" + member +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", guestNum=" + guestNum +
                ", cost=" + cost +
                '}';
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
        return Objects.hash(member, checkIn, checkOut);
    }

    public int getReservationId() {
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
