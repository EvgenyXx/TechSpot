import './styles.scss';

export function Products() {
     return (
         <div className='products__wrapper'>
             <div className="container mt-8">
                 <div className="row mb-4">
                     <div className="col-md-6">
                         <h1 className="h3">🛒 Все товары</h1>
                         <p className="text-muted">Найдите идеальный товар для себя</p>
                     </div>
                     <div className="col-md-6">
                         <div className="input-group">
                             <input type="text" className="form-control search-box" id="searchInput"
                                    placeholder="Поиск товаров..."/>
                             <button className="btn btn-primary"
                                     // onClick="searchProducts()"
                             >🔍 Поиск</button>
                         </div>
                     </div>
                 </div>

                 <div className="filters-section">
                     <div className="row">
                         <div className="col-md-8">
                             <h6 className="mb-3">Категории:</h6>
                             <div id="categoryFilters">
                                 <button className="btn btn-outline-primary category-btn active"
                                         // onClick="filterByCategory('ALL')"
                                 >Все
                                 </button>
                             </div>
                         </div>
                         <div className="col-md-4">
                             <h6 className="mb-3">Сортировка:</h6>
                             <select className="form-select"
                                     // onChange="sortProducts(this.value)"
                             >
                                 <option value="price,desc">По убыванию цены</option>
                                 <option value="price,asc">По возрастанию цены</option>
                                 <option value="productName,asc">По названию</option>
                             </select>
                         </div>
                     </div>
                 </div>

                 <div className="row" id="productsGrid">
                     <div className="col-12 text-center">
                         <div className="spinner-border text-primary" roleV1="status">
                             <span className="visually-hidden">Загрузка...</span>
                         </div>
                         <p>Загружаем товары...</p>
                     </div>
                 </div>

                 <nav aria-label="Page navigation" className="mt-5">
                     <ul className="pagination justify-content-center" id="pagination"></ul>
                 </nav>
             </div>
         </div>
     )
}