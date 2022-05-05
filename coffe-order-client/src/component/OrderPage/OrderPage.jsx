import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";
import Order from "./Order";
import OrderItemList from "./OrderItemList";


const OrderPage = () => {
    const [order, setOrder] = useState({orderId: "", email: "", address: "", orderStatus: ""})
    const [orderItems, setOrderItems] = useState([])
    const [total, setTotal] = useState(0)
    const params = useParams()

    useEffect(() => {
        const {orderId} = params
        getOrder(orderId)
    }, [params])


    const handleAddressInputChange = (e) => {
        setOrder({...order, address: e.target.value})
    }

    const handlePostcodeInputChange = (e) => {
        setOrder({...order, postcode: e.target.value})
    }

    const handleQuantityInputChange = (orderItemId, e) => {
        const updatedOrderItem = orderItems.map(orderItem => (orderItem.orderItemId === orderItemId) ? {
            ...orderItem,
            quantity: e.target.value
        } : orderItem)
        setOrderItems(updatedOrderItem)
        getTotal(updatedOrderItem)
    }

    const updateOrder = () => {
        if (order.address === "" || order.postcode === "") {
            alert("입력값을 확인해주세요!");
            return
        }

        axios.put("http://localhost:8080/api/v1/orders/" + order.orderId, {
            address: order.address,
            postcode: order.postcode
        })
            .then(res => {
                alert("수정을 완료하였습니다!")
                getOrder(order.orderId)
            })
            .catch(error => {
                if (error.response.status < 500) {
                    alert(error.response.data.message)
                } else {
                    alert("서버 에러:" + error.response.data.message)
                }
            })
    }

    const cancelOrder = () => {
        console.log("취소")
        axios.put("http://localhost:8080/api/v1/orders/" + order.orderId + "/cancel")
            .then(res => {
                alert("주문이 취소 되었습니다!")
                getOrder(order.orderId)
            })
            .catch(error => {
                if (error.response.status < 500) {
                    alert(error.response.data.message)
                } else {
                    alert("서버 에러:" + error.response.data.message)
                }
            })
    }

    const updateQuantity = (orderItemId) => {
        const found = orderItems.find(orderItem => orderItem.orderItemId === orderItemId)

        if (found.quantity === "" || found.quantity <= 0) {
            alert("올바른 수량을 입력해주세요(최소: 1개)")
            return
        }

        axios.put("http://localhost:8080/api/v1/order-items/" + orderItemId, {
            quantity: found.quantity
        })
            .then(res => {
                alert("수량이 수정 되었습니다!")
                getOrder(order.orderId)
            })
            .catch(error => {
                if (error.response.status < 500) {
                    alert(error.response.data.message)
                } else {
                    alert("서버 에러:" + error.response.data.message)
                }
            })
    }

    const deleteOrderItem = (orderItemId) => {
        axios.delete("http://localhost:8080/api/v1/order-items/" + orderItemId)
            .then(res => {
                alert("주문 상품이 제거되었습니다!")
                getOrder(order.orderId)
            })
            .catch(error => {
                if (error.response.status < 500) {
                    alert(error.response.data.message)
                } else {
                    alert("서버 에러:" + error.response.data.message)
                }
            })
    }

    const getOrder = (orderId) => {
        axios.get("http://localhost:8080/api/v1/orders/" + orderId)
            .then(res => {
                const {orderId, email, address, postcode, orderStatus, orderItems} = res.data.result
                setOrder({orderId, email, address, postcode, orderStatus})
                setOrderItems(orderItems)
                getTotal(orderItems)
            })
            .catch(error => {
                if (error.response.status < 500) {
                    alert(error.response.data.message)
                } else {
                    alert("서버 에러:" + error.response.data.message)
                }
            })
    }

    const getTotal = (orderItems) => {
        let totalPrice = 0
        orderItems.forEach(orderItem => totalPrice += (orderItem.price * orderItem.quantity))
        setTotal(totalPrice)
    }


    return (
        <div className="container-fluid">
            <Order order={order} onAddressChange={handleAddressInputChange} onPostCodeChange={handlePostcodeInputChange}
                   onUpdateOrder={updateOrder} onCancelOrder={cancelOrder}/>
            <OrderItemList total={total} orderStatus={order.orderStatus} orderItems={orderItems}
                           onQuantityChange={handleQuantityInputChange} onUpdateQuantity={updateQuantity}
                           onDeleteOrderItem={deleteOrderItem}/>
        </div>
    )
}

export default OrderPage