package com.example.fashionmode.ui.features.client.ribbon.preview

data class ProductPreviewFull(
    val id: String = "",
    val title: String = "",
    val category: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val size: String = "",
    val price: Long = 0
)

data class PreviewState(
    val product: ProductPreviewFull? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)

sealed class PreviewIntent {
    object LoadProduct : PreviewIntent()
    object AddToBasket : PreviewIntent()
    object OrderProduct : PreviewIntent()
}

sealed class PreviewEffect {
    object NavigateBack : PreviewEffect()

}