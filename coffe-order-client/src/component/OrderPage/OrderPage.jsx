import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";
import {translateOrderStatus} from "../../Util";


const OrderPage = () => {
    const [order, setOrder] = useState({orderId:"", email:"", address:"", orderStatus:""})
    const [orderItems, setOrderItems] = useState([])
    const params = useParams()

    useEffect(() => {
        const {orderId} = params
        getOrder(orderId)
    }, [params])


    const handleAddressInputChanged = (e) => {
        setOrder({...order, address: e.target.value})
    }

    const handlePostcodeInputChange = (e) => {
        setOrder({...order, postcode: e.target.value})
    }

    const updateOrder = () => {
        if(order.address === "" || order.postcode === "") {
            alert("입력값을 확인해주세요!");
            return
        }

        axios.put("http://localhost:8080/api/v1/orders/"+order.orderId, {
            address: order.address,
            postcode: order.postcode
        })
            .then(res =>{
                alert("수정을 완료하였습니다!")
                getOrder(order.orderId)
            })
            .catch(error => {
                if (error.response.status < 500) {
                    alert(error.response.data.message)
                } else {
                    alert("서버 에러:"+error.response.data.message)
                }
            })
    }

    const cancelOrder = () => {
        axios.put("http://localhost:8080/api/v1/orders/"+order.orderId+"/cancel")
            .then(res =>{
                alert("주문이 취소 되었습니다!")
                getOrder(order.orderId)
            })
            .catch(error => {
                if (error.response.status < 500) {
                    alert(error.response.data.message)
                } else {
                    alert("서버 에러:"+error.response.data.message)
                }
            })
    }

    const getOrder = (orderId) => {
        axios.get("http://localhost:8080/api/v1/orders/"+orderId)
            .then(res => {
                const {orderId, email, address, postcode, orderStatus, orderItems} = res.data.result
                setOrder({orderId, email, address, postcode, orderStatus})
                setOrderItems(orderItems)
            })
            .catch(error => {
                if (error.response.status < 500) {
                    alert(error.response.data.message)
                } else {
                    alert("서버 에러:"+error.response.data.message)
                }
            })
    }


    return (
        <div className="container-fluid">
           <div className="order card">
               <div className="card-header">
                   {order.orderId+"  "}
               </div>
               <div className="card-body">
                   <div className="order-info-area">
                       <div className="order-info-label">이메일</div>
                       <div>{order.email}</div>
                   </div>
                   <div className="order-info-area">
                       <div className="order-info-label">배송지, 우편번호</div>
                       <input type="text" className="order-input col-3" value={order.address || ""} onChange={handleAddressInputChanged}/>
                       <input type="text" className="order-input col-3" value={order.postcode || ""} onChange={handlePostcodeInputChange}/>
                       <button type="button" className="order-button btn btn-dark" onClick={updateOrder}>수정</button>
                   </div>
                   <div className="order-info-area">
                       <div className="order-info-label">배송 상태</div>
                       <span className="status badge bg-dark">{translateOrderStatus(order.orderStatus)}</span>
                       <button type="button" className="order-button btn btn-danger" onClick={cancelOrder}>주문 취소</button>
                   </div>
               </div>
           </div>

            <div>
                {orderItems.map(orderItem =>
                    <div key={orderItem.orderItemId}>
                        <div>
                            <div>상품 이름 및 카테고리</div>
                            <div>
                                {orderItem.productName}
                                <span>{orderItem.category}</span>
                            </div>
                        </div>
                        <div>
                            <div>상품 가격</div>
                            <div>{orderItem.price} 원</div>
                        </div>
                        <div>
                            <div>주문 수량</div>
                            {
                                order.orderStatus === "ORDER_ACCEPTED" ?
                                    <>
                                        <input type="number" className="order-input col-1" value={orderItem.quantity || ""} onChange={handlePostcodeInputChange}/>
                                        <button type="button" className="order-button btn btn-dark">수정</button>
                                    </>
                                    : <div>{orderItem.quantity}</div>
                            }
                        </div>
                        {order.orderStatus === "ORDER_ACCEPTED" && <button type="button" className="order-button btn btn-danger" onClick={cancelOrder}>주문에서 제거</button>}
                    </div>
                )}
            </div>
        </div>
    )
}

export default OrderPage