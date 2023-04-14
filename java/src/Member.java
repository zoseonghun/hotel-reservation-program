import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Member implements Serializable {

    private String name;
    private String phone;
    private String email;
    private Gender gender;
    private int mileage;
    private int history;
    private List<Reservation> reservationList;


    public Member() {
    }

    public Member(String name, String phone, String email, Gender gender) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.history = 0;
        this.mileage = 0;
        this.reservationList = new ArrayList<>();
    }

    public void increaseMileage(int cost){
        double mileageRate = getMileageRate();
        this.mileage += (cost * mileageRate);
    }

    public double getMileageRate() {
        double result;
        switch (this.history / 10) {
            case 0 : result = 0.01;
            case 1 : result = 0.02;
            case 2 : case 3 : case 4 : result = 0.05;
            default : result = 0.1;
        };
        return result;
    }
    public void addReservationList(Reservation reservation){
        increaseMileage((int)reservation.getCost());
        this.reservationList.add(reservation);
        setHistory(getHistory() + 1);
    }
    public void removeReservationList(Reservation reservation){
        setHistory(getHistory() - 1);
        this.reservationList.remove(reservation);
        decreaseMileage((int)reservation.getCost());
    }

    private void decreaseMileage(int cost) {
        double mileageRate = getMileageRate();
        this.mileage -= (cost * mileageRate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return phone.equals(member.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }

    @Override
    public String toString() {
        return String.format("고객명: %s\n성별: %s\n연락처: %s\n이메일: %s\n누적포인트: %d\n누적숙박일수: %d"
            , name, gender == Gender.FEMALE ? "여성" : "남성", phone, email, mileage, history);
    }
}