export const translateOrderStatus = (orderStatus) => {
    switch (orderStatus) {
        case "ORDER_ACCEPTED":
            return "접수 완료"
        case "PREPARING_FOR_SHIPMENT":
            return "배송 준비"
        case "ON_DELIVERY":
            return "배송 중"
        case "DELIVERY_OVER":
            return "배송 완료"
        default:
            return "주문 취소";
    }
}