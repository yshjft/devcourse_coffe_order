# COFFEE ORDER 
### 커피 주문 서비스 백엔드 어플리케이션입니다.

## 기능 설명
### 관리자
#### 카테고리 관리
* 카테고리 생성
* 전체 카테고리 조회
* 카테고리 상세 조회
* 카테고리 삭제
  * 카테고리에 연결된 상품이 없는 경우에만 가능

#### 상품 관리
* 상품 생성
* 상품 전체 조회
* 상품 조회 by 카테고리
* 상품 상세 조회
* 상품 수정
* 상품 삭제
  * 주문에 포함되어 있지 않은 경우에만 가능

#### 주문 관리
* **주문 상태**
  > 접수 완료, 배송 준비, 배송 중, 배송 완료, 주문 취소
* 주문 조회 by 주문 상태
* 주문 상세 조회
* 주문 배송 상태 변경
* 주문 배송 상태 자동 변경
    * 매일 오후 2시에 접수 완료 상태이면서 당일 오후 2시 이전에 생성된 모든 주문을 배송 준비 상태로 변경
* 주문 삭제
    * 주문이 취소된 경우에만 가능
    
### 사용자
#### 상품 목록 조회 ```GET /api/v1/products```

* **RESPONSE BODY EXAMPLE**
```
{
    "status": 200,
    "message": "get products successfully",
    "result": {
        "metaData": {
            "total": 4
        },
        "products": [
            {
                "productId": "1d801886-1633-42cc-a324-01ea8ba0201d",
                "productName": "르네샤이 에비더번 밀크티 베이스 아쌈 홍차 250g",
                "category": "차(tea)",
                "price": 19000,
                "description": "홍차는 정말 맛있어!",
                "createdAt": "2022-05-03T18:44:24.740005",
                "updatedAt": "2022-05-03T18:44:24.740041"
            }
            ...
        ]
    }
}
```

#### 주문 생성 ```POST /api/v1/orders```

* **REQUEST BODY EXAMPLE**
```
{
  "email": "tester@test.com",
  "address": "경기도 용인시 처인구 김량장동",
  "postcode": "23243",
  "orderItems": [
    {
      "productId": "ef400c4d-003f-43e5-9eb9-7fd84a083c2b",
      "quantity": 20
    }
  ]
}
```

#### 주문 내역 조회 ```GET /api/v1/orders?email={email}```

* **RESPONSE BODY EXAMPLE**
```
{
    "status": 200,
    "message": "get orders successfully",
    "result": {
        "metaData": {
            "total": 2
        },
        "orders": [
            {
                "orderId": "76ebb819-242c-44dd-8fa4-5c7a68087f03",
                "email": "tester@test.com",
                "orderStatus": "ORDER_CANCELLED",
                "orderItems": [
                    {
                        "quantity": 10,
                        "productName": "제로커피 베트남 사이공 200g"
                    }
                ],
                "createdAt": "2022-05-04T00:10:36.807068"
            },
            ...
        ]
    }
}
```

#### 주문 내역 상세 조회 ```GET /api/v1/orders/{orderId}```

* **RESPONSE BODY EXAMPLE**
```
{
    "status": 200,
    "message": "get order successfully",
    "result": {
        "orderId": "83b2d7b3-3475-4cea-8de2-d0f197c93815",
        "email": "jericoh@naver.com",
        "address": "경기도 용인시 처인구",
        "postcode": "21212",
        "orderStatus": "PREPARING_FOR_SHIPMENT",
        "orderItems": [
            {
                "orderItemId": 7,
                "quantity": 1,
                "productId": "d2e5fdfb-8d33-4e03-9d73-603dbbc10ae6",
                "productName": "516쿠키 딸기우유 쿠키",
                "category": "쿠키",
                "price": 15000
            },
            {
                "orderItemId": 8,
                "quantity": 1,
                "productId": "6febb720-6746-40ce-957b-1d7f4254e5d6",
                "productName": "믈레즈나 크림카라멜 얼그레이티 홍차 밀크티 30티백",
                "category": "차(tea)",
                "price": 15000
            }
        ],
        "createdAt": "2022-05-04T18:43:37.248042",
        "updatedAt": "2022-05-05T10:56:50.239584"
    }
}
```

#### 주문(배송지, 우편번호) 수정 ```PUT /api/v1/orders/{orderId}```
* 주문이 접수 완료 상태인 경우에만 수정 가능
* **REQUEST BODY EXAMPLE**
```
{
    "address": "경기도 성남시 분당구 판교동 100",
    "postcode": "23432"
}
```

#### 주문 취소 ```PUT /api/v1/orders/{orderId}/cancel```
* 주문이 접수 완료 상태인 경우에만 취소 가능

#### 주문 상품 수량 변경 ```/api/v1/order-items/{orderItemId}```
* 주문이 접수 완료 상태인 경우에만 변경 가능 
* **RESPONSE BODY EXAMPLE**
```
{
    "quantity": 10
}
```

### 상품을 주문에서 제거 ```DELETE /api/v1/order-items/{orderItemId}```
* 주문이 접수 완료 상태인 경우에만 제거 가능 
* 주문에 오로지 하나의 상품만 포함되어 있을 경우 제거 불가능
