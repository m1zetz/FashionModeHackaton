package com.example.fashionmode.data.repository

import com.example.fashionmode.domain.repository.ProductRepository
import com.example.fashionmode.ui.features.client.basket.BasketItem
import com.example.fashionmode.ui.features.client.ribbon.Product
import com.example.fashionmode.ui.features.client.ribbon.preview.ProductPreviewFull
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRepositoryImpl(private val db: FirebaseFirestore) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            val snapshot = db.collection("products").get().await()
            val products = snapshot.documents.mapNotNull { doc ->
                try {
                    doc.toObject(Product::class.java)?.copy(id = doc.id)
                } catch (e: Exception) {
                    null
                }
            }
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductById(id: String): Result<ProductPreviewFull> {
        return try {
            val document = db.collection("products").document(id).get().await()
            val product = document.toObject(ProductPreviewFull::class.java)

            if (product != null) {
                Result.success(product.copy(id = document.id))
            } else {
                Result.failure(Exception("Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBasketItems(userId: String): Result<List<BasketItem>> {
        return try {
            val snapshot = db.collection("basket")
                .document(userId)
                .collection("items")
                .get()
                .await()

            val items = snapshot.documents.mapNotNull { doc ->
                doc.toObject(BasketItem::class.java)?.copy(id = doc.id)
            }
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addToBasket(userId: String, item: BasketItem): Result<Unit> {
        return try {
            db.collection("basket")
                .document(userId)
                .collection("items")
                .document(item.id)
                .set(item)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeFromBasket(userId: String, productId: String): Result<Unit> {
        return try {
            db.collection("basket")
                .document(userId)
                .collection("items")
                .document(productId)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}