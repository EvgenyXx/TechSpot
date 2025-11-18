import './styles.scss'

export function Register() {
    return (
        <div className='register__wrapper'>
            <div className="register-container">
                <div className="brand-header">
                    <div className="brand-logo">🛍️</div>
                    <h1 className="brand-title">TechSpot</h1>
                    <p className="brand-subtitle">Создайте свой аккаунт</p>
                </div>

                <form id="registerForm">
                    <div className="row">
                        <div className="col-md-6 mb-3">
                            <label htmlFor="firstname" className="form-label">Имя *</label>
                            <input type="text" className="form-control" id="firstname" name="firstname"
                                   placeholder="Введите имя" required minLength={2} maxLength={50}/>
                            <div className="form-text">От 2 до 50 символов</div>
                        </div>

                        <div className="col-md-6 mb-3">
                            <label htmlFor="lastname" className="form-label">Фамилия *</label>
                            <input type="text" className="form-control" id="lastname" name="lastname"
                                   placeholder="Введите фамилию" required minLength={2} maxLength={50}/>
                            <div className="form-text">От 2 до 50 символов</div>
                        </div>
                    </div>

                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Email *</label>
                        <input type="email" className="form-control" id="email" name="email"
                               placeholder="example@mail.com" required/>
                        <div className="form-text">Введите действующий email</div>
                    </div>

                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Пароль *</label>
                        <input type="password" className="form-control" id="password" name="password"
                               placeholder="Не менее 8 символов" required minLength={8}/>
                        <div className="form-text">Минимум 8 символов</div>
                    </div>

                    <div className="mb-4">
                        <label htmlFor="phoneNumber" className="form-label">Телефон *</label>
                        <div className="input-group">
                            <span className="input-group-text">+7</span>
                            <input type="tel" className="form-control with-prefix" id="phoneNumber" name="phoneNumber"
                                   placeholder="9123456789" pattern="\d{10}" required maxLength={10}/>
                        </div>
                        <div className="form-text">Формат: +7 912 345-67-89</div>
                    </div>

                    <button type="submit" className="btn btn-primary btn-register w-100">
                        🚀 Создать аккаунт
                    </button>

                    <div className="text-center mt-4">
                        <span className="text-muted">Уже есть аккаунт? </span>
                        <a href="/login" className="login-link">Войти</a>
                    </div>
                </form>

                <div id="message" className="mt-3"></div>
            </div>
        </div>
    )
}