package com.met.nearby.store.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import com.met.nearby.store.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
@Preview
fun TopBar() {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (title1, title2, profile, box) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                .constrainAs(ref = profile){
                    top.linkTo(anchor = parent.top)
                    end.linkTo(anchor = parent.end)
                }
        )

        Text(
            text = "Good Morning, Tina",
            fontSize = 20.sp,
            color = colorResource(id = R.color.gold),
            modifier = Modifier.constrainAs(ref = title1){
                top.linkTo(anchor = profile.top)
                start.linkTo(anchor = parent.start)
                bottom.linkTo(anchor = profile.bottom)
                end.linkTo(anchor = parent.end)
            }
        )

        Text(
            text = "What are you doing today?",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.gold),
            modifier = Modifier
                .padding(top = 24.dp)
                .constrainAs(ref = title2){
                    top.linkTo(anchor = profile.bottom)
                    start.linkTo(anchor = parent.start)
                    end.linkTo(anchor = parent.end)
                }
        )

        ConstraintLayout(
            modifier = Modifier.padding(horizontal = 24.dp)
                .padding(top = 32.dp)
                .fillMaxWidth()
                .height(height = 110.dp)
                .background(color = colorResource(id = R.color.black3),
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .constrainAs(ref = box){
                    bottom.linkTo(anchor = parent.bottom)
                    top.linkTo(anchor = title2.bottom)
                }
                .clip(shape = RoundedCornerShape(10.dp))
        ) {
            val (icon1, icon2, balance, amount, reward, wallet, line1, line2) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.wallet),
                contentDescription = null,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    .constrainAs(ref = icon1){
                        top.linkTo(anchor = parent.top)
                        start.linkTo(anchor = parent.start)
                    }
            )

            Text(
                text = "Wallet",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier.padding(start = 8.dp)
                    .constrainAs(ref = wallet){
                        bottom.linkTo(anchor = icon1.bottom)
                        start.linkTo(anchor = icon1.end)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.medal),
                contentDescription = null,
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                    .constrainAs(ref = icon2){
                        bottom.linkTo(anchor = parent.bottom)
                        start.linkTo(anchor = parent.start)
                    }
            )

            Text(
                text = "Reward",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier.padding(start = 8.dp)
                    .constrainAs(ref = reward){
                        top.linkTo(anchor = icon2.top)
                        start.linkTo(anchor = icon2.end)
                    }
            )

            Box(modifier = Modifier.width(width = 1.dp)
                .fillMaxHeight()
                .padding(vertical = 16.dp)
                .background(color = colorResource(id = R.color.gray))
                .constrainAs(ref = line1){
                    centerTo(other = parent)
                }
            )

            Box(Modifier.height(height = 1.dp)
                .width(width = 170.dp)
                .padding(horizontal = 16.dp)
                .background(color = colorResource(id = R.color.gray))
                .constrainAs(ref = line2){
                    top.linkTo(anchor = parent.top)
                    bottom.linkTo(anchor = parent.bottom)
                    start.linkTo(anchor = parent.start)
                }
            )

            Text(
                text = "Balance",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp, top = 32.dp)
                    .constrainAs(ref = balance){
                        top.linkTo(anchor = parent.top)
                        start.linkTo(anchor = line1.end)
                    }
            )
            
            Text(
                text = "150.00 USD",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                    .constrainAs(ref = amount){
                        top.linkTo(anchor = balance.bottom)
                        start.linkTo(anchor = balance.start)
                    }
            )
        }
    }

}