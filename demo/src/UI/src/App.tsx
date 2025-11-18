import './App.css'
import {NavLink, Route, Routes} from "react-router";
import {Home} from "./pages/home";
import {Products} from "./pages/products";
import {Login} from "./pages/login";
import {Register} from "./pages/register/register.tsx";

function App() {
  return (<>
        <nav className="navbar navbar-expand-lg navbar-light bg-white fixed-top">
            <div className="container">
              <NavLink className="navbar-brand nav-brand" to="/">
                  🛍️ TechSpot
              </NavLink>
              <div className="navbar-nav ms-auto">
                  <NavLink className="nav-link" to="/register">Регистрация</NavLink>
                  <NavLink className="nav-link" to="/login">Вход</NavLink>
                  <NavLink className="nav-link" to="/products">Товары</NavLink>
              </div>
            </div>
        </nav>
        <Routes>
            <Route path="/" element={<Home/>} />
            <Route path='/products' element={<Products/>}/>
            <Route path='/login' element={<Login/>}/>
            <Route path='/register' element={<Register/>}/>
        </Routes>
      </>
  )
}

export default App
