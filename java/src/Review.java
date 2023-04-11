public class Review {

    private String title;
    private String detail;
    private Member writer;
    private Reservation reservation;
    private Rate rate;
    private String reply;

    public Review() {}

    public Review(String title, String detail, Reservation reservation, Rate rate) {
        this.title = title;
        this.detail = detail;
        this.reservation = reservation;
        this.rate = rate;
        this.writer = reservation.getMember();
    }

    @Override
    public String toString() {
        return "Review{" +
                "title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", writer=" + writer +
                ", reservation=" + reservation +
                ", rate=" + rate +
                ", reply='" + reply + '\'' +
                '}';
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

    public Member getWriter() {
        return writer;
    }

    public void setWriter(Member writer) {
        this.writer = writer;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
