# Read It Together: 리딧투게더

<br/>

## 리딧투게더 기획 배경
- 도서의 종류와 분야가 다양해지면서 선호하는 도서를 찾기 어렵다
- 온라인에서 도서를 추천 받으며 독서 기록을 함께 하고 싶다 
- 독서 모임에 참여하는 것은 부담스럽지만 다른 사람들과 도서에 대한 이야기를 나누고 싶다

<br />

## 리딧투게더 소개
- 사용자의 과거 평점을 기반으로 미래 평점 예측, 장르·나이·성별을 기준으로 나누어 집단별 추천
- 도서 카드를 작성하여 다른 유저에게 추천사와 함께 전달
- 책장에 사용자의 독서 기록 정리 가능
- 서재 페이지를 나만의 공간으로 활용, 다른 사용자의 서재에 방문하여 방명록을 남기며 커뮤니케이션 기능 제공

<br/>

## 📝 회원가입 & 로그인
![로그인](/images/로그인.PNG)


![회원가입](/images/회원가입.PNG)


![임시_비밀번호](/images/임시_비밀번호.PNG)


## 내 서재 기능
![내_서재](/images/내_서재.PNG)


### 내 책장
![책장_디테일](/images/책장_디테일.PNG)


### 방명록
![방명록](/images/방명록.PNG)


![방명록_작성](/images/방명록_작성.PNG)


![방명록_디테일](/images/방명록_디테일.PNG)


### 우편함
![우편함](/images/우편함.PNG)


![카드_디테일](/images/카드_디테일.PNG)


### 다이어리
![카드_다이어리](/images/카드_다이어리.PNG)


### 서재 소개글
![소개글](/images/소개글.PNG)


## 기타 기능
![팔로우](/images/팔로우.PNG)


![프로필](/images/프로필.PNG)


### 책 상세 페이지
![도서_디테일](/images/도서_디테일.PNG)

![코멘트](/images/코멘트.PNG)



### 추천 페이지
![추천_페이지](/images/추천_페이지.PNG)


![추천_페이지2](/images/추천_페이지2.PNG)


### 검색 기능
![검색창](/images/검색창.PNG)


![검색](/images/검색.PNG)

 
## 기술 스택

### 프론트엔드

---

**Language |** JavaScript

**Framework |** React

**Library |** ChartJs, TailWind

### 백엔드

---

**IDE |** IntelliJ 23.3.4

**Language |** Java 17, python 3.12.0

**Framework |** Spring Boot 3.2.3, FastAPI 0.103.2

**DB |** Mysql 8.0.35 - RDS, Spring Data JPA, redis 6.0.16

**CI/CD |** AWS EC2, GCP, Docker, Docker-compose, Docker-hub, Nginx, Jenkins

**Build Tool |** Gradle

**Security |** Spring Security, JWT


### INFRA

---

AWS EC2

AWS RDS

AWS S3

docker 25.0.0

docker-hub

docker-compose 1.29.2

Nginx-RTMP

Niginx 1.18.0

Ubuntu 20.04.6 LTS

Jenkins-jdk17

### ETC

---

**UI/UX |** Figma

**Cooperation|** Notion, Discord, Gerrit, Git-Flow


### 서비스 아키텍처
<img src="/assets/아키텍처.PNG" width="400">

### 요구 사항 명세서
<img src="/assets/요구_사항_명세서.PNG" width="400">

### 기능 명세서
<img src="/assets/기능_명세서.PNG" width="400">

### API 설계서
<img src="/assets/API_설계서.PNG" width="400">

### Git 컨벤션

| 브랜치  | 용도 |
| --- | --- |
| master | 제품 출시/배포 |
| develop | 출시 전 병합 및 테스트 |
| feature | 기능개발 |

### ERD
<img src="/assets/ERD.PNG" width="400">

# :rocket: 노션 링크
[D206 노션 살펴보기](https://ordinary-meeting-f0b.notion.site/21e6b0e4119c45ce9747987867685187)

## 👪 팀원

### 1. Frontend

<table>
  <tr>
    <td style="border: 1px solid #ccc; padding: 10px; text-align: center;">
      <div style="border: 1px solid #ccc; padding: 10px; text-align: center;">
      <br/>
      <b><a href="https://github.com/BaekJaehee" style="text-align:center;">백재희</a></b>
      <br/>
      </div>
      <br/>
      <div style="text-align:center;">기획</div>
      <div style="text-align:center;">UI/UX 설계</div>
      <div style="text-align:center;">화면 구현</div>
    </td>
    <td style="border: 1px solid #ccc; padding: 10px; text-align: center;">
      <div style="border: 1px solid #ccc; padding: 10px; text-align: center;">
      <br/>
      <b><a href="https://github.com/Anhyunsung" style="text-align:center;">안현성</a></b>
      <br/>
      </div>
      <br/>
      <div style="text-align:center;">상태 관리</div>
      <div style="text-align:center;">UI/UX 설계</div>
      <div style="text-align:center;">화면 구현</div>
    </td>
  </tr>
</table>

### 2. Backend

<table>
  <tr>
    <td style="border: 1px solid #ccc; padding: 10px; text-align: center;">
      <div style="border: 1px solid #ccc; padding: 10px; text-align: center;">
      <br/>
      <b><a href="https://github.com/hyoseon1201" style="text-align:center;">곽효선</a></b>
      <br/>
      </div>
      <br/>
      <div style="text-align:center;">검색 API 구현</div>
      <div style="text-align:center;">우편함/방명록 API 구현</div>
      <div style="text-align:center;">SVD, 쿼리기반 추천 시스템 구현</div>
      <div style="text-align:center;">SMTP, Redis 메일링 서비스 구현</div>
    </td>
    <td style="border: 1px solid #ccc; padding: 10px; text-align: center;">
      <div style="border: 1px solid #ccc; padding: 10px; text-align: center;">
      <br/>
      <b><a href="https://github.com/sumin-mun" style="text-align:center;">문수민</a></b>
      <br/>
      </div>
      <br/>
      <div style="text-align:center;">JWT 인증/인가 구현</div>
      <div style="text-align:center;">회원가입 API 구현</div>
      <div style="text-align:center;">팔로우 API 구현</div>
    </td>
    <td style="border: 1px solid #ccc; padding: 10px; text-align: center;">
      <div style="border: 1px solid #ccc; padding: 10px; text-align: center;">
      <br/>
      <b><a href="https://github.com/Limkyuhwan" style="text-align:center;">임규환</a></b>
      <br/>
      </div>
      <br/>
      <div style="text-align:center;">콘텐츠 기반 빅데이터 추천</div>
      <div style="text-align:center;">책장 API 구현</div>
      <div style="text-align:center;">책 상세/리뷰 API 구현</div>
    </td>
    <td style="border: 1px solid #ccc; padding: 10px; text-align: center;">
      <div style="border: 1px solid #ccc; padding: 10px; text-align: center;">
      <br/>
      <b><a href="https://github.com/BokAndYoung" style="text-align:center;">복영석</a></b>
      <br/>
      </div>
      <br/>
      <div style="text-align:center;">책 카드 API 구현</div>
      <div style="text-align:center;">CI/CD</div>
    </td>
  </tr>
</table>
