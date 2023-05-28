# hotel-reservation-program



<div align="center">

![img.png](logo.png)

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fjava-3rd-team-project%2Fhotel-reservation-program&count_bg=%23BE60FF&title_bg=%2385BFE3&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://github.com/java-3rd-team-project/hotel-reservation-program)

</div>


## Hotel Reservation Program v1.0

> 중앙정보처리학원 데이터융합 자바(JAVA) 응용 SW개발자 취업과정 4조<br>
> 객체지향 설계에 대한 이해<br>
> 개발기간 : 2023.4.10 ~ 2023.4.16


## 개발팀 소개

- 이진호
  - [깃허브](https://github.com/jhlee9462)
  - PM
  - 예약 로직 구현
- 한세진
  - [깃허브](https://github.com/Esther2112)
  - 
- 조성훈
  - [깃허브](https://github.com/nuhgnoesoj)
  -

## 프로젝트 소개
> 객체지향 설계 원칙인 SOLID 를 공부하기 위한 JAVA 프로그램입니다.<br>
> 호텔리어의 업무에 사용하는 프로그램으로 신규 예약, 수정, 삭제 및 게시판 기능이 있습니다.

## 기술 스택
### 환경
<div>
<img src="https://img.shields.io/badge/intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
</div>

### 개발
<div>
<img src="https://img.shields.io/badge/java-FFFFFF?style=for-the-badge&logo=openjdk&logoColor=black">
</div>

## 화면 구성

## 주요 기능

- 호텔 예약
- 예약 조회
- 예약 수정 및 삭제
- 리뷰 관리

## 아키텍쳐
```
.
├── README.md
├── ReadmeHistory.md
├── flow chart
│   ├── 순서도 이미지.png
│   └── 클래스 다이어그램.png
├── java
│   ├── java.iml
│   └── src
│       ├── AvailableDate.java
│       ├── BoardViewer.java
│       ├── Controller.java
│       ├── Gender.java
│       ├── META-INF
│       │   └── MANIFEST.MF
│       ├── Main.java
│       ├── Member.java
│       ├── Rate.java
│       ├── Reservation.java
│       ├── Review.java
│       ├── ReviewService.java
│       ├── RoomSize.java
│       ├── Viewer.java
│       ├── common
│       │   └── Utility.java
│       └── sav
│           ├── availableDate.sav
│           ├── member.sav
│           ├── reservation.sav
│           └── review.sav
├── logo.png
├── out
│   ├── artifacts
│   │   └── hotel_reservation_program_jar
│   │       └── hotel-reservation-program.jar
│   └── production
│       └── java
│           ├── AvailableDate.class
│           ├── BoardViewer$1.class
│           ├── BoardViewer.class
│           ├── Controller.class
│           ├── Gender.class
│           ├── META-INF
│           │   └── MANIFEST.MF
│           ├── Main.class
│           ├── Member.class
│           ├── Rate.class
│           ├── Reservation$1.class
│           ├── Reservation.class
│           ├── Review.class
│           ├── ReviewService.class
│           ├── RoomSize.class
│           ├── Viewer.class
│           ├── common
│           │   └── Utility.class
│           └── sav
│               ├── availableDate.sav
│               ├── member.sav
│               ├── reservation.sav
│               └── review.sav
└── reservation.exe
```