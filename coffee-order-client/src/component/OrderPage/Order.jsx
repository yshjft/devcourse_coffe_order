import React from 'react'
import {translateOrderStatus} from "../../Util";

const Order = (props) => {
    const {order, onAddressChange, onPostCodeChange, onUpdateOrder, onCancelOrder} = props

    return(
        <div className="order card">
            <div className="card-header">{order.orderId}</div>
            <div className="card-body">
                <div className="order-info-area">
                    <div className="order-info-label">이메일</div>
                    <div>{order.email}</div>
                </div>
                <div className="order-info-area">
                    <div className="order-info-label">배송지, 우편번호</div>
                    {order.orderStatus === "ORDER_ACCEPTED" ?
                        <>
                            <input type="text" className="order-input col-3" value={order.address || ""}
                                   onChange={onAddressChange}/>
                            <input type="text" className="order-input col-3" value={order.postcode || ""}
                                   onChange={onPostCodeChange}/>
                            <button type="button" className="order-button btn btn-dark" onClick={onUpdateOrder}>수정
                            </button>
                        </>
                        :
                        <>
                            <span>{order.address}, </span>
                            <span>{order.postcode} </span>
                        </>
                    }
                </div>
                <div className="order-info-area">
                    <div className="order-info-label">배송 상태</div>
                    <span className="status badge bg-dark">{translateOrderStatus(order.orderStatus)}</span>
                    {order.orderStatus === "ORDER_ACCEPTED" && <button type="button" className="order-button btn btn-danger" onClick={onCancelOrder}>주문 취소</button>}
                </div>
            </div>
        </div>
    )
}

export default Order