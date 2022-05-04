import React from 'react'

import '../../../App.css'
import 'bootstrap/dist/css/bootstrap.css'

const Product = (props) => {
    const {productId, productName, description, category, price, onAddClick, onDecreaseClick} = props

    const handleAddBtnClick = e => {
        onAddClick(productId);
    }

    const handleDecreaseBtnClick = e => {
        onDecreaseClick(productId);
    }

    return (
        <>
            <div className="col-2">
                <img className="img-fluid" src="https://i.imgur.com/HKOFQYa.jpeg" alt=""/>
            </div>
            <div className="col">
                <div className="row text-muted">{category}</div>
                <div className="row">{productName}</div>
                <div className="row description">{description}</div>
            </div>
            <div className="col text-center price">{price}원</div>
            <div className="col text-end action">
                <button onClick={handleAddBtnClick} className="btn btn-small btn-outline-dark">+</button>
                <button onClick={handleDecreaseBtnClick} className="btn btn-small btn-outline-dark">-</button>
            </div>
        </>
    )
}

export default Product