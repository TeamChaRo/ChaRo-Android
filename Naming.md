## **Android Naming Convention**
- 클래스명, 함수명, 변수명 등 모든 이름은 어떤 역할을 하는지 한눈에 알 수 있도록 하기
- id명은 겹치지 않게 하기 위해 어떤 액티비티(프래그먼트)에서 쓰고 있는지 명시
<br>

### 1. Class file - UpperCamelCase 사용

- 클래스 파일 이름은 **UpperCamelCase(aka 파스칼 케이스(PascalCase))** 로 작성

- 쌍봉낙타 표기법이라고도 한다.

- 전체 이름의 첫 문자를 포함한 각 단어의 첫 문자를 대문자로 표시한다.

  **ex) SignInActivity, SiginInFragment, ImageUploaderService, ChangePasswordDialog**

<br>

### 2. Resources file - snake_case

- 리소스 파일 이름은 **snake_case**로 작성
- 모든 단어는 소문자로, 각 단어의 사이를 **언더바 _** 로 구분해주는 표기법
- Action bar -> *ab_xxx.png*
    Button -> *btn_xxx.png*
    Dialog -> *dialog_xxx.png*
    Divider -> *divider_xxx.png*
    Icon -> *ic_xxx.png*
    Menu -> *menu_xxx.png*
    Notification -> *notification_xxx.png*
    Tabs -> *tab_xxx.png*
- **ex) image_logo.png, ic_back.xml, menu_main.xml**
- **ex) image_logo.png, ic_back.xml, menu_main.xml**

<br>

### 3. Layout file - snake_case

- 레이아웃 파일 이름 또한 마찬가지로 **snake_case**로 작성
- [what]_ [where]
  - UserProfileActivity.kt -> *activity_user_profile.xml*
    SignUpFragment.kt -> *fragment_sign_up.xml*
    ChangePasswordDialog.kt -> *dialog_change_password.xml*
- **ex) activity_main.xml, fragment_login.xml, dialog_change_password.xml, item_**
- xml파일의 id명은 [what]_ [where]_ []  → home에서 쓰는 TextView라고 치면 text_home_user
- 
    TextView : text_ 
    <br> EditText : et_
    <br> ImageView : img_
    <br> Button : btn_
    <br> LinearLayout : ll_
    <br> RelativeLayout : rl_
    <br> ConstraintLayout : cl_
    <br> Recyclerview : recyclerview_

<br>

### 4. Method - lowerCamelCase

- 메소드 이름은 **lowerCamelCase**로 작성

  - 단봉낙타 표기법이라고도 한다.
  - 보통 카멜 케이스라고 하면 lower 카멜 케이스를 의미한다.
  - 각 단어의 첫 문자를 대문자로 표시하되, 이름의 첫 문자는 소문자로 적는다.

- "동사"로 시작하는 "동사구" 형태를 사용하되, 동사 원형만을 사용한다.

  **ex) showList, updateContacts**

- 한 단어 내에서는 대소문자 변경 없이 사용

  **ex) InVisible :arrow_forward: Invisible**

- 약어 사전에 있는 단어는 되도록 약어를 사용

  **ex) UserInterface :arrow_forward: UI 또는 Ui**

- 자주 사용하는 동사는 용법에 맞게 사용

  - **show: Invisible한 것을 Visible하게 바꾸는 동작**
  - **check: 어떤 것을 확인한 후 boolean 또는 값으로 반환하는 동작**
  - **is: 어떤 것인지 확인한 후 boolean으로 반환하는 동작**
  - **has: 어떤것을가지고 있는 확인 후 boolean으로 반환하는 동작**

<br>

### 5. 변수

- 변수 이름 또한 마찬가지로 **lowerCamelCase**로 작성

  **ex) isEnd, viewPagerAdapter**
