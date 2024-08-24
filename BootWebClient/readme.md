# 웹소켓을 이용한 푸시서비스 샘플 코드

-추가로 임의의 에뮬레이터가 있어서 주기적으로 부트에게 정보를 보내주는 것을 시뮬레이션하기 위해 Scheduling 기능을 활용한다.

-RestTemplate을 이용해서 서버의 API를 호출하는 예제도 포함한다.

	. 현재는 객체 하나를 넘기는 예제가 포함되어 있다.

-실행 순서:

1. 부트 서버를 구동한다.

2. PostMan에서 WebSocket 테스트를 위한 탭을 만든다.

	- New - WebSocket
	
	- ws://localhost:8080/ws/chat
	
	- Connect
	
	- Scheduling 기능이 활성화되어 있으면 1초간격으로 데이터가 console 창에 출력된다.
	
3. PostMan - Message 탭에서 데이터 입력 - Send

	- 부트 console에 출력되면 정상		
