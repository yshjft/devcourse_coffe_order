import React from 'react'


const SearchOrder = (props) => {
    const {email, onEmailInput, onSearchOrders} = props

    return (
        <div className="search">
            <input type="email" className="col-4" placeholder="이메일을 통해 주문을 확인하세요!" value={email || ""} onChange={onEmailInput}/>
            <button className="btn btn-dark col-1" onClick={onSearchOrders}>주문 내역 조회</button>
        </div>
    )
}

export default SearchOrder