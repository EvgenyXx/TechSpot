let currentPage = 0;
let currentCategory = 'ALL';
let currentSort = 'price,desc';
let currentSearch = '';

// Загрузка при старте
(function() {
    loadCategories();
    loadProducts()
})();


// Загрузка категорий
async function loadCategories() {
    try {
        const response = await fetch('/api/v1/products/categories');
        const categories = await response.json();

        const container = document.getElementById('categoryFilters');
        categories.forEach(category => {
            container.innerHTML += `
                    <button class="btn btn-outline-primary category-btn"
                            onclick="filterByCategory('${category}')">
                        ${getCategoryIcon(category)} ${category}
                    </button>
                `;
        });
    } catch (error) {
        console.error('Ошибка загрузки категорий:', error);
    }
}

// Загрузка товаров
async function loadProducts() {
    try {
        debugger;
        let url = `/api/v1/products?page=${currentPage}&size=12&sort=${currentSort}`;

        if (currentCategory !== 'ALL') {
            url = `/api/v1/products/category/${currentCategory}?page=${currentPage}&size=12`;
        }

        if (currentSearch) {
            url = `/api/v1/products/search?query=${currentSearch}&page=${currentPage}&size=12`;
        }

        console.log('Загрузка по URL:', url);
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        console.log('Получены данные:', data);

        displayProducts(data.content);
        setupPagination(data.totalPages);

    } catch (error) {
        console.error('Ошибка загрузки товаров:', error);
        document.getElementById('productsGrid').innerHTML =
            '<div class="col-12 text-center text-danger">Ошибка загрузки товаров: ' + error.message + '</div>';
    }
}

// Отображение товаров
function displayProducts(products) {
    const grid = document.getElementById('productsGrid');

    if (!products || products.length === 0) {
        grid.innerHTML = `
                <div class="col-12 text-center">
                    <div class="alert alert-info">
                        🧐 Товары не найдены
                    </div>
                </div>
            `;
        return;
    }

    grid.innerHTML = products.map(product => `
            <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                <div class="card product-card">
                    <div class="product-image">
                        ${getCategoryIcon(product.productCategory)}
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">${product.productName}</h5>
                        <p class="card-text text-muted small">${product.description}</p>

                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <span class="product-price">$${product.price}</span>
                            <span class="product-stock ${product.quantity > 10 ? 'in-stock' : 'low-stock'}">
                                ${product.quantity > 10 ? '✓ В наличии' : '⚠ Осталось мало'}
                            </span>
                        </div>

                        <div class="d-grid gap-2">
                            <button class="btn btn-outline-primary btn-sm" onclick="viewProduct(${product.id})">
                                👀 Подробнее
                            </button>
                            <button class="btn btn-primary btn-sm" onclick="addToCart(${product.id})" ${product.quantity === 0 ? 'disabled' : ''}>
                                🛒 В корзину
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `).join('');
}

// Иконки категорий
function getCategoryIcon(category) {
    const icons = {
        'ELECTRONICS': '📱', 'CLOTHING': '👕', 'BOOKS': '📚',
        'HOME': '🏠', 'SPORTS': '⚽', 'OTHER': '📦'
    };
    return icons[category] || '📦';
}

// Поиск
function searchProducts() {
    currentSearch = document.getElementById('searchInput').value;
    currentPage = 0;
    loadProducts();
}

// Фильтрация по категории
function filterByCategory(category) {
    currentCategory = category;
    currentPage = 0;

    document.querySelectorAll('.category-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    event.target.classList.add('active');

    loadProducts();
}

// Сортировка
function sortProducts(sort) {
    currentSort = sort;
    loadProducts();
}

// Пагинация
function setupPagination(totalPages) {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = '';

    for (let i = 0; i < totalPages; i++) {
        pagination.innerHTML += `
                <li class="page-item ${i === currentPage ? 'active' : ''}">
                    <a class="page-link" href="#" onclick="changePage(${i})">${i + 1}</a>
                </li>а
            `;
    }
}

function changePage(page) {
    currentPage = page;
    loadProducts();
}

function viewProduct(productId) {
    alert('Просмотр товара ID: ' + productId);
}

function addToCart(productId) {
    alert('Товар ID: ' + productId + ' добавлен в корзину!');
}
