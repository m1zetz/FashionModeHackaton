import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fashionmode.ui.features.client.ribbon.Product
import com.example.fashionmode.ui.features.client.ribbon.RibbonEffect
import com.example.fashionmode.ui.features.client.ribbon.RibbonViewModel
import org.koin.androidx.compose.koinViewModel


import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fashionmode.Navigation.Routes
import com.example.fashionmode.ui.features.client.ribbon.RibbonIntent
import com.example.fashionmode.ui.theme.ErrorColor

@Composable
fun Ribbon(viewModel: RibbonViewModel = koinViewModel(), navController: NavController) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel.channel) {
        viewModel.channel.collect { effect ->
            when (effect) {

                is RibbonEffect.NavigateToDetails -> {
                    navController.navigate("${Routes.DETAILS}/${effect.id}")
                }

            }
        }
    }
    if(state.isLoading){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }else{
        if(state.error.isNotEmpty()){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(state.error, color = ErrorColor)
                IconButton(
                    onClick = {
                        viewModel.handleIntent(RibbonIntent.UpdatePage)
                    }
                ) {
                    Icon(Icons.Default.Refresh, null)
                }
            }
        } else{
            LazyVerticalGrid(
                GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item(span = { GridItemSpan(2) }) {
                    Banner("https://img.freepik.com/free-photo/panorama-shot-canal-lake-pukaki-twisel-surrounded-with-mountains_181624-45343.jpg?semt=ais_hybrid&w=740&q=80","FRESH")
                }
                item(span = { GridItemSpan(2) }) {
                    Column(Modifier.padding(8.dp)) {
                        Text("Новинки", style = MaterialTheme.typography.titleLarge)
                    }

                }
                items(state.products) {product ->
                    ProductCard(
                        product,
                        toDetails = {id ->
                            viewModel.handleIntent(RibbonIntent.NavigateToDetails(id))
                        }
                    )
                }

            }
        }

    }

}

@Composable
fun Banner(url: String, collectionName: String){
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(120.dp),
        shape = RoundedCornerShape(12.dp)
    ){
        Box(modifier = Modifier.fillMaxWidth()){
            AsyncImage(
                model = url,
                null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier.padding(8.dp)
                    .align(Alignment.BottomStart),

            ) {
                Text(
                    text = collectionName,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
            }
        }
    }
}
@Composable
fun ProductCard(product: Product, toDetails: (id: String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            toDetails(product.id)
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()

            )
            Row(modifier = Modifier.fillMaxWidth()){
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = product.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Размер: ${product.size}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Text(
                        text = "${product.price} ₸",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

            }


        }

    }

}