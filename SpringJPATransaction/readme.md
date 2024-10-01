# @Transactional

* Spring 내의 클라스, 메소드, 인터페이스 위에 붙여서 사용 가능.
* 보통은 Service 단에 붙여서 사용.
* Test 클래스에 붙여서 사용하는 경우 데이터베이스에 적용된 내용은 테스트 완료 후 자동으로 rollback 처리.

### 세부 옵션


1.isolation : 격리 수준

		@Transactional(isolation=Isolation.DEFAULT)

		* default         : 해당 DB의 기본 Isolation level을 따른다.
		* read_uncomitted : 가장 낮은 수준의 격리로 아직 커밋되지 않은 변경 사항도 읽을 수 있다.
		* read_comitted   : 다른 트랜잭션에 의해 커밋된 데이터만 읽을 수 있다. 커밋되지 않은 변경 사항을 보이지 않는다.
		* repeatable_read : 트랜잭션에 시작된 시점의 데이터만 읽는다. 트랜잭션 동안 다른 트랜잭션이 해당 데이터를 변경해도 보이지 않는다.
		* serializable    : 가장 높은 수준의 격리로 트랜잭션 동안 다른 트랜잭션에서 해당 데이터에 접근하지 못하게 막는다. 트랜젹션이 순서대로 실행되는 효과가 있다.


2.propagation : 전파 속성

		@Transactional(propagation=Propagation.REQUIRED)

		* default = required : 이미 진행 중인 트랜잭션이 있다면, 해당 트랜잭션의 속성을 따릅니다. 그렇지 않다면, 새로운 트랜잭션을 생성합니다.
		* requires_new       : 항상 새로운 트랜잭션을 생성합니다. 이미 진행 중인 트랜잭션이 있다면, 해당 트랜잭션을 잠시 일시정지 하고, 생성한 트랜잭션을 우선하여 진행합니다.
		* support            : 이미 실행 중인 트랜잭션이 있다면, 해당 트랜잭션 속성을 따릅니다. 실행 중인 트랜잭션이 없다면, 트랜잭션을 설정하지 않는다.
		* not_support        : 이미 진행 중인 트랜잭션이 있다면, 보류합니다. 트랜잭션 없이 작업을 수행합니다.
		* mandatory          : 이미 진행 중인 트랜잭션이 없다면, Exception을 발생시킵니다. 트랜잭션이 있다면, 작업을 수행합니다.
		* never              : 트랜잭션이 진행 중이라면, Exception을 발생시킵니다. 진행 중이지 않다면, 작업을 수행합니다.
		* nested             : 진행 중인 트랜잭션이 있다면, 중첩된 트랜잭션이 실행됩니다. 진행 중인 트랜잭션이 없다면, required와 동일하게 동작합니다.


3.noRollbackFor : 예외 무시 - 지정한 예외가 발생하는 경우, rollback 처리하지 않습니다.

		﻿@Transactional(noRollbackFor=Exception.class)


4.rollbackFor : 예외 추가 - 지정한 예외가 발생하는 경우, rollback 처리

		﻿@Transactional(rollbackFor=Exception.class)


5.timeout : 시간 지정 - 지정한 시간내에 트랜잭션이 완료되지 않으면, rollback 처리.

		﻿@Transactional(timeout=10)

		* default = -1 : timeout을 처리하지 않습니다


6.readOnly : 읽기 전용 - insert, update, delete 질의가 실행될 경우 Exception 발생.

		﻿@Transactional(readonly = true)

		* default = false
