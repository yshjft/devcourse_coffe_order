import {BrowserRouter as Router, Route, Switch} from "react-router-dom"
import {routes} from './route'
import Header from "./component/common/Header/Header";
import './App.css'

function App() {
  return (
    <div className="App">
      <Router>
          <Header/>
          <Switch>
              {routes.map((route) => (
                  <Route key={route.path} {...route} />
              ))}
          </Switch>
      </Router>
    </div>
  );
}

export default App;
