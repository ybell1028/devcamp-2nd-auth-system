# MSA로 구현하는 인증 시스템

## Architecture

![basic-architecture2](https://user-images.githubusercontent.com/54519245/146792554-44ee8b5c-9a88-4e3c-a7c7-7182044e5716.PNG)

## Spec
 
- 사용자 DB 설계
- 가입, 로그인 페이지 
- 인증 서버 (API)
- RDBMS DB 사용 (MySQL, PostgreSQL)
- Password Encryption
- 유저 관리 페이지 (Admin/BackOffice)
- E-Mail 인증 
- ~~비밀번호 찾기~~
- ~~캐시~~

## Tech Stack
### Front-end
- HTML / CSS
- Thymeleaf
- JQuery
- AJAX
- Bootstrap(Tailwind CSS로 변경 예정)

### Back-end
- Spring Boot
- Spring JPA
- Spring Cloud Gateway
- Spring Cloud Netflux Eureka(Service Discovery)
- MySQL RDS

### API Docs
- 회원가입 - **POST** `/user`
- 로그인 - **POST** `/auth/login`
- 이메일 인증 - **GET** `/user/confirmation`
- 유저 전체 정보 조회 - **GET** `/user`
- 특정 유저 정보 조회 - **GET** `/user?uuid=`
- 특정 유저 정보 삭제 - **DELETE** `/user?uuid=`

## Result
### 로그인

![2021-12-24-22-31-46](https://user-images.githubusercontent.com/54519245/147356873-ee4540b0-0fc0-4ef0-8119-bd4f92808105.png)

### 회원가입

![2021-12-24-22-30-44](https://user-images.githubusercontent.com/54519245/147356876-82295c35-d14d-486b-846f-61e879784389.png)
![2021-12-24-22-34-19](https://user-images.githubusercontent.com/54519245/147356879-fa2536a8-e59c-4587-91df-19653ee596d6.png)

### 이메일 인증

![2021-12-24-22-35-25](https://user-images.githubusercontent.com/54519245/147356880-761d00c3-1a6d-483b-b6af-51110cdc7dd4.png)

### 유저 관리 페이지

![2021-12-24-22-40-07](https://user-images.githubusercontent.com/54519245/147356882-a2503e2e-b5d2-46ac-b2bb-33c553b628c7.png)