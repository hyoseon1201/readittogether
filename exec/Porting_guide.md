

# Read it Together : 빅데이터를 활용한 도서추천 서비스


깃랩주소
---
+ https://lab.ssafy.com/s10-bigdata-recom-sub2/S10P22D206

   





개발 환경
---
+ Front-end
    - React : ^18.2.0
    - "react-dom": "^18.2.0"
    - tailwindcss : ^3.4.1 
+ Back-end
    - fastapi : 0.109.1
    - JDK:17
    - JPA
+ DB
    - redis
    - mysql

+ Cloud 
      -GCP,EC2


환경 설정
--
### 토큰관리
Refresh Tokken : Cookie / Acess tokken :local storage


# [FAST API]
#### miniconda 다운로드

-   https://docs.conda.io/projects/miniconda/en/latest/ 에 접속해서 miniconda 설치

#### 가상환경 생성 및 접속

-   본 프로젝트는 python version 3.8로 진행

-   myenv는 본인이 설정할 환경 이름

```
conda create --name {myenv} python=3.8
```

-   생성한 환경 접속

```
conda activate {myenv}
```

#### 필요한 라이브러리 설치 

```
pip install -r requirement.txt
```

### Front-end .env 설정
```
HTTPS=true
REACT_APP_API_BASE_URL=https://localhost:8000/
```

### Back-end .env 설정
```
DATABASE_URL={mySQL 도메인}

SECRET_KEY={JWT secret key}
SESSION_SECRET_KEY={세션 관리용 secret key}

GCP_SERVICE_ACCOUNT_JSON={GCP 서비스 계정 권한 JSON TEXT}


AWS_SECRET_KEY=${AWS_SECRET_KEY} 
AWS_ACCESS_KEY=${AWS_ACCESS_KEY}

BASIC_PROFILE_IMAGE=${BASIC_PROFILE_IMAGE} # 기본이미지
BUCKET_NAME=${BUCKET_NAME}  #S3_NAME


KAKAO_CLIENT_ID={Kakao OAuth ID}
KAKAO_CLIENT_SECRET={Kakao OAuth Password}

HOST_URL={기본 도메인 주소}


SMTP_SERVER=smtp.gmail.com
SMTP_PORT=587
SMTP_USERNAME={SMTP 이메일 인증용 계정 ID}
SMTP_PASSWORD={SMTP 이메일 인증용 계정 App Password}

ENV BACK_URL=${BACK_URL}

```


### Front-end .gitignore
```
# See https://help.github.com/articles/ignoring-files/ for more about ignoring files.1
.env
# dependencies
/node_modules
/.pnp
.pnp.js
.env

# testing
/coverage


# production
/build

# misc1
.DS_Store
.env
.env.local
.env.development.local
.env.test.local
.env.production.local

npm-debug.log*
yarn-debug.log*
yarn-error.log*
```

### Back-end .gitignore
```
.gradle
build/
!gradle/wrapper/gradle-wrapper.jar
!**/src/main/**/build/
!**/src/test/**/build/
gcs_cloud.json
### STS ###
src/main/resources/env.yml
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache
bin/
!**/src/main/**/bin/
!**/src/test/**/bin/
src/main/resources/env.yml
.env
### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr
out/
!**/src/main/**/out/
!**/src/test/**/out/

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/

### VS Code ###
.vscode/

```

# 외부 사용목록

 EC2, GCP, KaKao Ouath2(social) 




# 배포환경


![캡처](/uploads/f915b386aebe16a857e29bd3db2f77d6/캡처.PNG)



![캡처1](/uploads/db7d79b15eabf7d7b3c65c0cbcf73f28/캡처1.PNG)

![캡처2](/uploads/f97287a67ce978f09d88e24fb7b2d822/캡처2.PNG)

![캡처3](/uploads/51d2807935cc90beec155e1c2987bb67/캡처3.PNG)

![캡처.4PNG](/uploads/ba8f299a9712bdf388ccb0f33d5ccfb6/캡처.4PNG.PNG)

![캡처5](/uploads/bf6bd2f2441717fdacf9e793697c95a8/캡처5.PNG)

![캡처6](/uploads/949ce7d42718f36f9611b9632b0a3410/캡처6.PNG)

![캡처7](/uploads/89136b6b9b0ff56a16d43248bbfdefd5/캡처7.PNG)

![캡처8](/uploads/b2d2f9d9cb22fe893782762aaad2766e/캡처8.PNG)

![캡처9](/uploads/2e804b59e05cc4cea16d6e9b5eeaf8ff/캡처9.PNG)




[추신]..해킹당한날

비번은 최대한 어렵게 포트는 기본포트를 쓰지말자..


![해킹당한날_24.3.29](/uploads/4c4ef248c833585d3b4960db74187ebf/해킹당한날_24.3.29.PNG)
