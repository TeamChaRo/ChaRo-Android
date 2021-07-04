## **Git Branch Convention**

1. branch 이름은 snake_case 표기법으로!
2. "feat/이슈명_이름" 으로 통일
  ex) feat/1_branch
  
default branch는 develop <br>
-> develop에서 본인 브랜치 생성 <br>
-> 본인 브랜치에서 develop으로 merge <br>
-> merge 후 다시 develop에서 새로운 브랜치 생성 (새로운 기능 구현할 때) <br>
-> 반복


<br><br><br>


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
- ADD: File (xml, kt, png ..) 추가
- DOCS: 문서 수정 (README.md)
- => 위의 Type들은 issue안에 Labels에서 생성해놓고 달아놓으면 보기 편해요
- => 위의 Type은 원하는 키워드로 만들어도 좋아요! 대신 다른 사람이 봐도 이해하기 쉽게 만드는게 좋겠죠?

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

