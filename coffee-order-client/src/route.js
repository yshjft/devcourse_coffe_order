import MainPage from "./component/MainPage/MainPage";
import OrderPage from "./component/OrderPage/OrderPage";
import NotFoundPage from "./component/common/NotFound/NotFound";

export const routes = [
    {path: "/", component: MainPage, exact: true},
    {path: "/order/:orderId", component: OrderPage, exact: true},
    {path: '*', component: NotFoundPage}
]