package com.example.TechSpot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/")
	public String homePage() {
		return "index";  // ← вернет index.html
	}

	@GetMapping("/register")
	public String registerPage() {
		return "register";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/products")
	public String productsPage() {
		return "products";  // ← новая страница товаров
	}

	@GetMapping("/my-products")
	public String myProductsPage() {
		return "my-products";  // ← для продавцов
	}
}
