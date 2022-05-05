import React from 'react'

const OrderItem = (props) => {
    const {orderStatus, orderItem, onQuantityChange, onUpdateQuantity, onDeleteOrderItem} = props

    return (
        <div className={"order-item-info card"}>
            <div className="card-body">
                <div className="order-info-area">
                    <div className="order-info-label">상품 이름 및 카테고리</div>
                    <div>
                        {orderItem.productName}
                        <span className="order-item-category">{orderItem.category}</span>
                    </div>
                </div>
                <div className="order-info-area">
                    <div className="order-info-label">상품 가격</div>
                    <div>{orderItem.price} 원</div>
                </div>
                <div className="order-info-area">
                    <div className="order-info-label">주문 수량</div>
                    {
                        orderStatus === "ORDER_ACCEPTED" ?
                            <>
                                <input type="number" className="order-input col-1" min="1"
                                       value={orderItem.quantity || ""}
                                       onChange={(e) => onQuantityChange(orderItem.orderItemId, e)}/>
                                <button type="button" className="order-button btn btn-dark"
                                        onClick={() => onUpdateQuantity(orderItem.orderItemId)}>수정
                                </button>
                            </>
                            : <div>{orderItem.quantity}</div>
                    }
                </div>
                {orderStatus === "ORDER_ACCEPTED" &&
                    <button type="button" className="order-button btn btn-danger"
                            onClick={() => onDeleteOrderItem(orderItem.orderItemId)}>주문에서 제거</button>}
            </div>
        </div>
    )
}

export default OrderItem