import React from 'react'
import {useHistory} from "react-router-dom";
import {translateOrderStatus} from "../../../Util";
import 'bootstrap/dist/css/bootstrap.css'


const Order = (props) => {
    const {order} = props
    const history = useHistory()

    const handleClick = () => {
        history.push(`/order/${order.orderId}`)
    }

    return (
        <div className="main-page-order card" onClick={handleClick}>
            <div className="card-header">
                {order.createdAt}
            </div>
            <div className="card-body">
                <h5 className="card-title">{order.orderId}</h5>
                <p className="card-text">{order.email}</p>
                <div className="status badge bg-dark">{translateOrderStatus(order.orderStatus)}</div>
                <div>
                    {order.orderItems.map((orderItem) => <div key={orderItem.productName} className="main-page-order-item">{orderItem.productName} x {orderItem.quantity}</div>)}
                </div>
            </div>
        </div>
    )
}

export default Order