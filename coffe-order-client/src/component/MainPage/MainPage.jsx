import React from 'react'
import axios from "axios"
import qs from 'query-string'
import {useEffect, useState} from "react";
import {useHistory, useLocation} from "react-router-dom";
import ProductList from "./product/ProductList";
import Summary from "./suammary/Summary"
import SearchOrder from "./order/SearchOrder";
import OrderList from "./order/OrderList";
import 'bootstrap/dist/css/bootstrap.css'



const MainPage = () => {
    const [products, setProducts] = useState([]);
    const [items, setItems] = useState([]);
    const [order, setOrder] = useState({email: "", address: "", postcode: ""});
    const [email, setEmail] = useState("")
    const [orders, setOrders] = useState([])
    const location = useLocation()
    const history = useHistory()
    const query = qs.parse(location.search)

    useEffect(() => {
        axios.get("http://localhost:8080/api/v1/products")
            .then(res => {
                setProducts(res.data.result.products)
            })
            .catch(error => {
                if (error.response.status < 500) {
                    alert(error.message)
                } else {
                    alert("서버 에러:"+error.response.data.message)
                }
            })
    }, [])

    useEffect(() => {
        if(query.email == null) {
            setEmail("")
            return
        }

        setEmail(query.email)
        axios.get("http://localhost:8080/api/v1/orders",
            {params: {email: query.email}})
            .then(res => {
                setOrders(res.data.result.orders)
            })
            .catch(error => {
                if (error.response.status < 500) {
                    alert(error.response.data.message)
                } else {
                    alert("서버 에러:"+error.response.data.message)
                }
            })

    }, [query.email])

    const handleAddClick = (productId) => {
        const product = products.find(product => product.productId === productId)
        const found = items.find(item => item.productId === productId)
        const updatedItems =
            found ? items.map(item => (item.productId === productId) ? {
                ...item,
                count: item.count + 1
            } : item) : [...items, {...product, count: 1}]
        setItems(updatedItems);
    }

    const handleDecreaseClick = (productId) => {
        const found = items.find(item => item.productId === productId)
        if (found) {
            const updatedItems = items
                .map(item => (item.productId === productId) ? {...item, count: item.count - 1} : item)
                .filter(item => item.count !== 0)
            setItems(updatedItems);
        }
    }

    const handleOrderUpdate = (value) => {
        setOrder(value)
    }

    const handleEmailUpdate = (e) => {
        setEmail(e.target.value)
    }

    const handleOrderSubmit = (order) => {
        if (items.length === 0) {
            alert("아이템을 추가해주세요!")
        } else {
            axios.post("http://localhost:8080/api/v1/orders", {
                email: order.email,
                address: order.address,
                postcode: order.postcode,
                orderItems: items.map(v => ({
                    productId: v.productId,
                    quantity: v.count
                }))
            })
                .then(v => {
                    alert("주문이 정상적으로 접수되었습니다.")
                    setOrder({email: "", address: "", postcode: ""})
                    setItems([])
                })
                .catch(error => {
                    if (error.response.status < 500) {
                        alert(error.response.data.message)
                    } else {
                        alert("서버 에러")
                    }
                })
        }
    }

    const handleSearchByEmail = () => {
        if (email === '') delete query.email
        else query.email = email

        history.push({
            pathname: location.pathname,
            search: qs.stringify(query)
        })
    }

    return (
        <div className="container-fluid">
            <div className="content-area card">
                <div className="row">
                    <ProductList products={products} onAddClick={handleAddClick} onDecreaseClick={handleDecreaseClick}/>
                    <Summary order={order} items={items} onOrderInput={handleOrderUpdate} onOrderSubmit={handleOrderSubmit}/>
                </div>
            </div>

            <div className="content-area card">
                <SearchOrder email={email} onEmailInput={handleEmailUpdate} onSearchOrders={handleSearchByEmail}/>
                <OrderList orders={orders}/>
            </div>
        </div>
    )
}

export default MainPage