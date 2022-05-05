import React from 'react'
import OrderItem from "./OrderItem";

const OrderItemList = (props) => {
    const {total, orderStatus, orderItems, onQuantityChange, onUpdateQuantity, onDeleteOrderItem} = props

    return (
        <>
            <div className="order-item-total">
                <span>총금액 </span>
                <span>{total} 원</span>
            </div>
            {orderItems.map(orderItem => <OrderItem key={orderItem.orderItemId} orderStatus={orderStatus} orderItem={orderItem} onQuantityChange={onQuantityChange} onUpdateQuantity={onUpdateQuantity} onDeleteOrderItem={onDeleteOrderItem}/>)}
        </>
    )
}

export default OrderItemList