import React from "react"

import '../../../App.css'
import 'bootstrap/dist/css/bootstrap.css'

const SummaryItem = (props) => {
    const {productName, count} = props;
    return (
        <div className="row">
            <h6 className="p-0">{productName}<span className="badge bg-dark text-">{count}</span>
            </h6>
        </div>
    )
}

export default SummaryItem