import java.io.Serializable;

public class Review implements Serializable {

    private int num;
    private String title;
    private String detail;
    private String writer;
    //    private Reservation reservation;
//    private Rate rate;
    private String reply;
    private String registDate;


    public Review() {}

    public Review(int num, String title, String detail, String writer, String reply, String registDate) {
        this.num = num;
        this.title = title;
        this.detail = detail;
//        this.writer = reservation.getMember();
//        this.reservation = reservation;
//        this.rate = rate;
        this.reply = reply;
        this.registDate = registDate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "num=" + num +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", writer=" + writer +
                ", reply='" + reply + '\'' +
                ", registDate='" + registDate + '\'' +
                '}';
    }

    public String getRegistDate() {
        return registDate;
    }

    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

//    public Reservation getReservation() {
//        return reservation;
//    }

//    public void setReservation(Reservation reservation) {
//        this.reservation = reservation;
//    }

//    public Rate getRate() {
//        return rate;
//    }

//    public void setRate(Rate rate) {
//        this.rate = rate;
//    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

}
