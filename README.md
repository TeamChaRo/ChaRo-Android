# ChaRo
![차로_홍보_표지_안드로이드](https://user-images.githubusercontent.com/53166299/124790650-4cadc380-df86-11eb-97bd-1cc08e2f1bba.png)

# ChaRo-Android Developer
<table align="center" style = "table-layout: auto; width: 100%; table-layout: fixed;">
  <colgroup>
    <col style="width:33%"/>
    <col style="width:34%"/>
    <col style="width:33%"/>
  </colgroup>
  <tr>
    <td>
      <img src= "https://user-images.githubusercontent.com/53166299/124790932-90083200-df86-11eb-9000-548b8872511d.png"/> 
    </td>
    <td>
      <img src="https://user-images.githubusercontent.com/53166299/124791483-0efd6a80-df87-11eb-8b3c-b6ebc28aab29.png"/> 
    </td>
    <td>
      <img src="https://user-images.githubusercontent.com/53166299/124790923-8da5d800-df86-11eb-94bb-560e98d92726.png"/> 
    </td>
  </tr>
  <tr>
    <th align="center">한진희</th>
    <th align="center">곽호택</th>
    <th align="center">한승현</th>
  </tr>
  <tr>
    <td align="center">
     Github: <a href="https://github.com/HJinhee">HJinhee</a>
    </td>
    <td align="center">
     Github: <a href="https://github.com/ho-taek">ho-taek</a>
    </td>
    <td align="center">
     Github: <a href="https://github.com/hansh0101">hansh0101</a>
    </td>
  </tr>
</table>

# 서비스 소개

### 차에서의 오늘이 최고가 될 수 있게, 당신의 드라이브 메이트 차로
```
차로는 경험 기반 드라이브 코스 공유 플랫폼입니다.

차로의 시작은 
"내가 좋아하는 드라이브를 어떻게 하면 더 재미있게 즐길 수 있을까?" 라는
질문에서 시작되었습니다. 

내가 좋아서,

내가 사용하고 싶어서,

이를 다른 사람들과 함께 즐기고 싶어서,

차로는 시작되었습니다.
```

# 기능 설명
#### 스플래쉬
```
차로 애니메이션 나타나는 단계
```

#### 온보딩
```
앱에서 제공해주는 서비스와 기능을 간단하게 설명해주는 단계
```

#### 로그인
```
사용자의 ID, PW를 입력받아 서버와의 통신 후 로그인하는 단계
```

#### 메인
```
추천해 주는 코스와 테마, 로컬, 사용자들이 많이 찾는 코스들을 4개씩 띄워주는 단계
```

#### 더보기
```
메인 뷰에서 테마, 로컬, 사용자들이 많이 찾는 코스의 더보기를 클릭하여 각각의 주제에 대한 코스를 더 많이 볼 수 있도록 하는 단계
```

#### 검색(필터링)
```
지역, 테마, 주의 사항 들을 선택하여 원하는 코스를 검색할 수 있도록 하는 단계
```

#### 작성하기(글 작성하기)
```
Multipart-form을 활용하여 글 작성 시 이미지 업로드를 처리하며 드라이브 코스에 대한 '경로를 제외한' 정보를 작성하는 단계
```

#### 작성하기(경로 작성하기)
```
사용자가 입력하는 텍스트를 기반으로 T Map API를 활용하여 검색결과를 자동으로 보여주고, 사용자가 선택한 위치를 출발 / 경유 / 도착지로 선택하여 T Map API를 활용하여 경로를 그리는 단계
```

#### 마이페이지
```
사용자의 프로필 정보와 사용자가 작성한 글, 저장한 글을 보여주는 단계
```

#### 상세보기
```
T Map API를 활용하여 자신 및 다른 사용자들이 작성한 글을 볼 수 있는 단계
```

# 과제 항목
### 뉴모피즘으로 디자인된 경우 에셋 다운 받아서 사용 

# Task
진희
```
게시글 작성하기 단계 중 글 작성하기, 경로 작성하기, 이미지 업로드
```
호택
```
메인, 더보기, 검색, 온보딩
```
승현
```
로그인, 마이페이지, 게시글 상세보기, 게시글 작성하기 단계 중 경로 작성하기
```
# Rule
## <a href="https://github.com/TeamChaRo/ChaRo-Android/blob/convention/Commit%20Message.md" >Git Convention</a>

## <a href="https://github.com/TeamChaRo/ChaRo-Android/blob/convention/Naming.md" >Android Naming Convention</a>

## Packaging

```
🚙CharoAndroid
 ┣ 📂data
 ┣ 📂api
 ┃ ┣ 📂request
 ┃ ┣ 📂response
 ┣  📂ui
 ┃ ┣ 📂adapter
 ┃ ┃ ┣ 📂home
 ┃ ┃ ┣ 📂charo
 ┃ ┃ ┣ 📂write
 ┃ ┣ 📂home
 ┃ ┣ 📂charo
 ┃ ┣ 📂write
 ┃ ┣ 📂sign
 ┃ ┗ 📂onboarding
 ┗📂utils
 ```


# Tech Stack
- Android Jetpack
<!--LifeCycle(ViewModel, LiveData, LifeCycleObserver) -->
```
  - DataBinding
  - Navigation Component
  - ViewPager2
  - Android KTX
  ```
- Tmap Api





