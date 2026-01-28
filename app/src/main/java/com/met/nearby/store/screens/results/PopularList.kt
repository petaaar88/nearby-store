package com.met.nearby.store.screens.results

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.met.nearby.store.R
import com.met.nearby.store.domain.StoreModel

@Composable
fun ItemsPopular(
    item: StoreModel,
    onClick: () -> Unit
){
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
            .wrapContentSize()
            .background(colorResource(R.color.black3),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp)
            .clickable{onClick()}
    ) {
        AsyncImage(
            model = item.ImagePath,
            contentDescription = null,
            modifier = Modifier.size(135.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorResource(R.color.gray),
                    shape = RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Text(text = item.Title,
            color = colorResource(R.color.white),
            modifier = Modifier.padding(top = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Row(
            Modifier.padding(top = 8.dp)
        ) {
            Image(painter = painterResource(R.drawable.location),
                contentDescription = null
            )
            Text(text = item.ShortAddress,
                color = colorResource(R.color.white),
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 8.dp),
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
@Preview
fun ItemPopularPreview(){
    ItemsPopular(StoreModel(Id = 0, CategoryId = "", Title = "Store Title", Latitude = 0.0, Longitude = 0.0, Address = "123 Main St", ShortAddress = "Main St", ImagePath = ""), onClick = {})
}

@Composable
fun PopularSection(
    list: List<StoreModel>,
    showPopularStores: Boolean,
    onStoreClick: (StoreModel) -> Unit
){

    Column{

        Text(
            text = "Popular Stores",
            color = colorResource(R.color.gold),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        if(showPopularStores){
            Box(Modifier.fillMaxWidth()
                .height(100.dp),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        else{
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                items(list.size){ index ->
                    val item = list[index]
                    ItemsPopular(item = item, onClick = { onStoreClick(item) })

                }
            }
        }
    }
}

@Composable
@Preview
fun PopularSectionPreview(){
    val list = listOf(
        StoreModel(Title = "Store 1", ShortAddress = "Address 1"),
        StoreModel(Title = "Store 2", ShortAddress = "Address 2")
    )

    PopularSection(
        list = list,
        showPopularStores = false,
        onStoreClick = {}
    )
}