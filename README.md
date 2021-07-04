# ChaRo-Android
## **Git Commit Message Convention**

### **Base Structure**

```
#{issue_number} [TYPE] : subject

body(선택사항)

footer(선택사항)
```

(이슈를 생성하면 이슈 번호가 부여됨. 커밋할 때 이슈단으로 커밋하고 이슈번호를 커밋메시지에 #과 함께 적어주면 어떤 이슈를 처리하는 작업이었는지 조회하기 편함.)

**예시** 

=> 하나의 이슈 처리하고 커밋 할 때

```
#1 [FEAT] : 로그인 버튼 클릭 이벤트 처리
```

=> 여러개의 이슈를 처리하고 커밋 할 때

```
[REFACTOR] : ReFactor Code From OtherPage
- OtherPage : 답변하지 않은 답변도 볼 수 있도록 수정
- OtherPage : 답변하지 않은 답변도 스크랩할 수 있도록 수정
- modified file : OtherPageAdapter, OtherPageActivity, OtherPageViewModel, item_other_page
issue track : #610, #612
```



### **Issue Number**

- 이슈 단위 커밋으로 기능 개발을 관리합니다
- GitHub에서 이슈를 트래킹을 할 수 있게 Issue Number를 커밋 메시지에 넣어줍니다

### **TYPE**

- FEAT: 새로운 기능 개발(kotlin 작업)
- FIX: 버그 수정
- UI: 스타일(xml file) 코드 변경
- MODIFY: kotlin 코드 수정
- TEST: 테스트 코드 추가
- CHORE: Gradle이나 설정 세팅할 때
- => 위의 Type들은 issue안에 Labels에서 생성해놓고 달아놓으면 보기 편해요
  라벨 생성하는 방법은 따로 올려드릴게요!
- => 위의 Type은 제가 주로 커밋메시지와 라벨에서 사용하는 타입으로 적어놓았어요 원하는 키워드로 만들어도 좋아요! 대신 다른 사람이 봐도 이해하기 쉽게 만드는게 좋겠죠?

### **Subject**

- Subject는 50글자를 넘기지 않도록 해요
- 첫 시작은 대문자로!!
- 마지막에 마침표(.)를 찍지 않기
- 작업한 내용을 명시해주세요
- 명령조를 사용한다(ex: Fix(o), Fixed(x), Modify(o), Modified(x))

### **Body**

- Subject(커밋제목) 이외에 부연 설명이 필요하거나, 여러 이슈를 한꺼번에 커밋할 때 적어주면 좋아요
- 아래와 같이 Subject를 작성하고 엔터를 치면 다음 줄로 넘어가고 추가로 설명을 적을 수 있어요
  git commit -m "[타입] : 제목 (엔터)
  Body(부연설명)"

### **Footer**

- 처리한 이슈 번호를 적을 때 사용
- 여러 이슈를 한꺼번에 커밋할 때 
  Subject에는 여러 이슈를 통틀어 말할 수 있는 작업 제목, 
  Body에는 작업 세부 내용==각각의 이슈 제목이 될 수 있겠죠?, 
  Footer에는 처리한 이슈 번호들
  을 적어서 커밋하는게 좋겠죠? 
  혼자서 작업할 때도 어떤 작업을 했는지 보기 좋지만 나중에 앱잼이나 협업할 때 팀원들이 보기도 좋더라구요!

```
subject

- body(이슈1)
- body(이슈2)
- body(이슈3)

issue track : #이슈1번호, #이슈2번호, #이슈3번호
```


# Android Naming Convention

안드로이드 작명법을 정리한 문서입니다.

<br>

### 1. Class file

- 클래스 파일 이름은 **UpperCamelCase(aka 파스칼 케이스(PascalCase))** 로 작성한다.

  **ex) SignInActivity, SiginInFragment, ImageUploaderService, ChangePasswordDialog**

<br>

### 2. Resources file

- 리소스 파일 이름은 **snake_case**로 작성한다.

  **ex) image_logo.png, ic_back.xml, menu_main.xml**

<br>

### 3. Layout file

- 레이아웃 파일 이름 또한 마찬가지로 **snake_case**로 작성한다.

  **ex) activity_main.xml, fragment_login.xml, dialog_change_password.xml**

<br>

### 4. Method

- 메소드 이름은 **lowerCamelCase**로 작성한다.

- "동사"로 시작하는 "동사구" 형태를 사용하되, 동사 원형만을 사용한다.

  **ex) showList, updateContacts**

- 한 단어 내에서는 대소문자 변경 없이 사용한다.

  **ex) InVisible :arrow_forward: Invisible**

- 약어 사전에 있는 단어는 되도록 약어를 사용한다.

  **ex) UserInterface :arrow_forward: UI 또는 Ui**

- 자주 사용하는 동사는 용법에 맞게 사용합니다.
  - **show: Invisible한 것을 Visible하게 바꾸는 동작**
  - **check: 어떤 것을 확인한 후 boolean 또는 값으로 반환하는 동작**
  - **is: 어떤 것인지 확인한 후 boolean으로 반환하는 동작**
  - **has: 어떤것을가지고 있는 확인 후 boolean으로 반환하는 동작**

<br>

### 5. 변수

- 변수 이름 또한 마찬가지로 **lowerCamelCase**로 작성한다.

  **ex) isEnd, viewPagerAdapter**

<br>

## :star: ​Case Naming Convention

### 1. UpperCamelCase(PascalCase)

- 쌍봉낙타 표기법이라고도 한다.
- 전체 이름의 첫 문자를 포함한 각 단어의 첫 문자를 대문자로 표시한다.

<br>

### 2. lowerCamelCase

- 단봉낙타 표기법이라고도 한다.
- 보통 카멜 케이스라고 하면 lower 카멜 케이스를 의미한다.
- 각 단어의 첫 문자를 대문자로 표시하되, 이름의 첫 문자는 소문자로 적는다.

<br>

### 3. 스네이크 케이스(snake_case)

- 각 단어의 사이를 **언더바 _** 로 구분해주는 표기법이다.

<br>

## :star: ​참고 자료

##### https://tadomstudio.tistory.com/24

##### http://guswnsxodlf.github.io/camelcase-pascalcase-snakecase
