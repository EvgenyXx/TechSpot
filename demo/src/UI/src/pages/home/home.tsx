import './styles.scss';
import {Link} from "react-router";

export function Home() {
    return (
        <>
            <title>TechSpot - Главная</title>
            <div className="container" id="content">
                <div className="row justify-content-center">
                    <div className="col-lg-8">
                        <div className="hero-section">
                            <h1 className="welcome-title">🎉 Добро пожаловать в TechSpot!</h1>
                            <p className="welcome-subtitle">
                                Лучший маркетплейс электроники и техники.
                                Теперь вы часть нашего сообщества!
                            </p>

                            <div className="row mt-5">
                                <div className="col-md-4">
                                    <div className="feature-card">
                                        <div className='icon-btn-fz'>📱</div>
                                        <h5>Электроника</h5>
                                        <p>Современные гаджеты и техника</p>
                                    </div>
                                </div>
                                <div className="col-md-4">
                                    <div className="feature-card">
                                        <div className={'icon-btn-fz'}>👕</div>
                                        <h5>Одежда</h5>
                                        <p>Стильная и качественная одежда</p>
                                    </div>
                                </div>
                                <div className="col-md-4">
                                    <div className="feature-card">
                                        <div className={'icon-btn-fz'}>🚚</div>
                                        <h5>Быстрая доставка</h5>
                                        <p>Доставка за 1-2 дня</p>
                                    </div>
                                </div>
                            </div>

                            <div className="mt-5">
                                <Link to="/products" className="btn btn-primary btn-lg">
                                    🛒 Начать покупки
                                </Link>
                                <Link to="/my-products" className="btn btn-outline-primary btn-lg ms-2">
                                    📦 Мои товары
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}