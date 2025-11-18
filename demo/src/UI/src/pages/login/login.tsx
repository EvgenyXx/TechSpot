import './styles.scss';
import {Link} from "react-router";

export function Login() {
     return (
         <>
             <title>Вход - TechSpot</title>
             <div className="login__wrapper">
                 <div className="login-container" id="content">
                     <div className="text-center mb-4">
                         <h2>🛍️ TechSpot</h2>
                         <p className="text-muted">Войдите в свой аккаунт</p>
                     </div>

                     <form action="/login" method="post">
                         <div className="mb-3">
                             <label className="form-label">Email</label>
                             <input type="email" className="form-control" name="username" placeholder="your@email.com"
                                    required/>
                         </div>
                         <div className="mb-3">
                             <label className="form-label">Пароль</label>
                             <input type="password" className="form-control" name="password" placeholder="Ваш пароль"
                                    required/>
                         </div>
                         <button type="submit" className="btn btn-primary w-100">Войти</button>
                     </form>

                     <div className="text-center mt-3">
                         <Link to="/register" className="text-decoration-none">Нет аккаунта? Зарегистрируйтесь</Link>
                     </div>
                 </div>
             </div>
         </>
     )
}