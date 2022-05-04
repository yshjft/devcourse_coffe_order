import React from "react";

import Product from "./Product";

import "../../../App.css"
import "bootstrap/dist/css/bootstrap.css"

const ProductList = (props) => {
    const {products = [], onAddClick, onDecreaseClick} = props;

    return (
        <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
            <h5 className="flex-grow-0">
                <b>상품 목록</b>
            </h5>
            <ul className="list-group products">
                {products.map((product) =>
                    <li key={product.productId} className="list-group-item d-flex mt-3">
                        <Product {...product} onAddClick={onAddClick} onDecreaseClick={onDecreaseClick}/>
                    </li>
                )}
            </ul>
        </div>
    )
}

export default ProductList