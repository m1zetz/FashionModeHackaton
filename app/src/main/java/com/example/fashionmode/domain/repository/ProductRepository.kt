package com.example.fashionmode.domain.repository

import com.example.fashionmode.ui.features.client.basket.BasketItem
import com.example.fashionmode.ui.features.client.ribbon.Product
import com.example.fashionmode.ui.features.client.ribbon.preview.ProductPreviewFull

interface ProductRepository {
    suspend fun getProducts() : Result<List<Product>>
    suspend fun getProductById(id: String) : Result<ProductPreviewFull>
    suspend fun getBasketItems(userId: String): Result<List<BasketItem>>
    suspend fun addToBasket(userId: String, item: BasketItem): Result<Unit>
    suspend fun removeFromBasket(userId: String, productId: String): Result<Unit>
}