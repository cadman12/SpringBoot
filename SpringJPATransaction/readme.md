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
		* not_support        : 이미 진행 중인 트랜잭션이 있다면, 일시 중시하여 보류합니다. 트랜잭션 없이 작업을 수행합니다.
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

# 잠금 메카니즘


1.Optimistic Locking

		* 충돌이 거의 발생하지 않는다고 가정하고 @Version으로 버전 관리를 사용하여 충돌을 감지.
		* 충돌이 감지되면(버전 불일치) 'OptimisticLockException' 발생.
		* 엔티티를 수정하면 해당 엔티티의 버전을 자동 증가시켜 DB에 저장.
		* 만약 두 스레드가 동시에 수정하는 경우 먼저 수정한 쪽에서 versio을 증가시키고, 다른 스레드는 버전 번호가 다르므로 이 증가히게 되고 OptimisticLockException이 발생

		@Entitypublic class MyEntity {
			@Version
			private Long version;
		}

		@Service
		public class ProductService {
		    @Autowired
		    private ProductRepository productRepository;
		    @Transactional
			public Product updateProduct(Long productId, int newQuantity) {
				try {
					Product product = productRepository.findById(productId)
						.orElseThrow(() -> new EntityNotFoundException("Product not found"));
					product.setQuantity(newQuantity);
					return productRepository.save(product);
				} catch (OptimisticLockException e) {
					throw new RuntimeException("Product was updated by another transaction", e);
				}
			}
		}
	

2.Pessimistic Locking

		* Repository 인터페이스에 @Lock 설정으로 다른 트랜잭션이 행을 수정하지 못하도록 데이터베이스 수준에서 행을 잠급

		@Lock(LockModeType.PESSIMISTIC_WRITE)@Query("SELECT e FROM MyEntity e WHERE e.id = :id")
		MyEntity findByIdWithLock(@Param("id") Long id);


# 동기화 및 동시성 처리


* 여러 스레드 또는 서비스가 동일한 리소스에 액세스하는 경우

		@Synchronized: 코드의 중요한 섹션에 대한 액세스를 동기화
		@Transactional의 isolation의 수준을 엄격한 격리 수준을 적용

		@Synchronized
		@Transactional(isolation = Isolation.REPEATABLE_READ)
		public synchronized void criticalSection() {
			// synchronized가 설정된 메서드는 동기화 메서드 전체에 대히 상호 배타적으로 호출, 즉 한개의 동기화 메서드가 호출되면 다른 동기화 메서드 모두 호출 불가. 비동기화 메서드는 호출 가능
			// @Synchronized는 설정된 메서드만 상호 배타적으로 호출, 즉 한개의 설정 메서드가 호출되어도 다른 메서드 호출 가능
		}
