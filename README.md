# 메장 테이블 예약 서비스

## Api목록
- 회원 가입 : Role 설정
- 매장 등록 : 이미 등록된 매장인지 확인 > lat과 lon과 매장 이름으로 확인 / partner인지 확인
- 매장 수정 :
- 매장 삭제 : 해당 매장의 등록자인지 확인
- 예약 진행 : 해당 시간대에 예약 없는지 확인
- 도착 확인 : 해당 예약을 등록한 사용자에게 요청이 왔는지 확인, 예약보다 10분 이상 전이면 못함
- 리뷰 작성 : 도착을 한 사람만 가능
- 리뷰 수정 : 작성자만 가능
- 리뷰 삭제 : 작성자, 관리자 모두 가능

## [ERD](https://www.erdcloud.com/d/smDikgt573kNAKBsC) 
![img.png](img.png)

## 사용 스택
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-%236DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Spring Security](https://img.shields.io/badge/mysql-%234479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)


