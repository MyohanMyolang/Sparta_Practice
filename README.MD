# 프로젝트 목적

1. Postgresql ArrayColumn을 QueryDsl을 사용하여 탐색하기
2. Abstract Class로 CustomException을 정의하고 Advice에는 CustomException 하나만 두는 걸로 한다. <br/>
도메인 별로 나누고 싶을 때는 RestControllerAdvice를 Domain에 새로 정의하고, CustomException을 상속받아 {Example}Exception의 형태로 새로 만들고, Advice에는 {Example}Exception만 만들도록 한다.<br/>
위의 형태로 만들어진 Exception들은 각 Exception Class에서 어떻게 처리 할 것인지에 대한 책임을 가지게 한다.
3. 다른 서비스를 사용하는데 익숙해지기.
4. 배포 신경써보기