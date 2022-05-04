import React from 'react'

import SummaryItem from "./SummaryItem";

import '../../../App.css'
import 'bootstrap/dist/css/bootstrap.css'

const Summary = (props) => {
    const {order, items = [], onOrderInput, onOrderSubmit} = props;

    const totalPrice = items.reduce((prev, curr) => prev + (curr.price * curr.count), 0)

    const handleEmailInputChanged = (e) => {
        onOrderInput({...order, email: e.target.value})
    }

    const handleAddressInputChanged = (e) => {
        onOrderInput({...order, address: e.target.value})
    }

    const handlePostcodeInputChange = (e) => {
        onOrderInput({...order, postcode: e.target.value})
    }

    const handleSubmit = (e) => {
        if(order.address === "" || order.email === "" || order.postcode === "") {
            alert("입력값을 확인해주세요!");
        }else{
            onOrderSubmit(order);
        }
    }

    return (
        <div className="col-md-4 summary p-4">
            <div>
                <h5 className="m-0 p-0"><b>Summary</b></h5>
            </div>
            <hr/>
            {items.map(v => <SummaryItem key={v.productId} count={v.count} productName={v.productName}/>)}
            <form>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">이메일</label>
                    <input type="email" className="form-control mb-1" value={order.email} onChange={handleEmailInputChanged} id="email"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="address" className="form-label">주소</label>
                    <input type="text" className="form-control mb-1" value={order.address} onChange={handleAddressInputChanged} id="address"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="postcode" className="form-label">우편번호</label>
                    <input type="text" className="form-control" value={order.postcode} onChange={handlePostcodeInputChange} id="postcode"/>
                </div>
            </form>
            <div className="row pt-2 pb-2 border-top">
                <h5 className="col">총금액</h5>
                <h5 className="col text-end">{totalPrice}원</h5>
            </div>
            <button className="btn btn-dark col-12" onClick={handleSubmit}>결제하기</button>
        </div>
    )
}

export default Summary