import React from 'react'
import 'bootstrap/dist/css/bootstrap.css'
import Order from "./Order";

const OrderList = (props) => {
    const {orders} = props

    return(
        <div className="order-list">
            {orders.map(order=> <Order key={order.orderId} order={order}/>)}
        </div>
    )
}

export default OrderList