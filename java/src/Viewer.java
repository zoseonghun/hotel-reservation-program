import static common.Utility.input;

public class Viewer {

    public final static String PROGRAM_VERSION = "1.0.0";

    public static void mainMenu() {
        while (true) {

            System.out.println("호텔 예약 프로그램 ver : " + PROGRAM_VERSION);
            System.out.println("메뉴를 선택해 주세요");
            System.out.println("1. 새로운 예약");
            System.out.println("2. 예약 수정 / 취소");
            System.out.println("3. 후기 관리");
            System.out.println("4. 프로그램 종료");

            switch (input(">>")) {
                case "1":
                    makeReservation();
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    System.exit(0);
                default:
                    System.out.println("없는 번호입니다. 다시 입력해주세요");
            }
        }
    }

    private static void makeReservation() {
        searchMemberMenu();
    }

    private static void searchMemberMenu() {
        System.out.println("회원을 검색합니다.");
        String inputName = input("이름을 입력해주세요 >> ");
        String inputPhone = input("전화번호를 입력해주세요 >> ");

        System.out.println(inputName + inputPhone);

        Member member = Controller.searchMember(inputName, inputPhone);

        if (member == null) {
            member = addNewMemberMenu(inputName, inputPhone);
        }
    }

    private static Member addNewMemberMenu(String inputName, String inputPhone) {

        return null;
    }
}
