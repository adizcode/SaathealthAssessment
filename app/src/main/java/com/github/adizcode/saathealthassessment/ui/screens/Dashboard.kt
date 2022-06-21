package com.github.adizcode.saathealthassessment.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.github.adizcode.saathealthassessment.R
import com.github.adizcode.saathealthassessment.data.model.VideoData
import com.github.adizcode.saathealthassessment.data.model.list1
import com.github.adizcode.saathealthassessment.data.model.list2
import com.github.adizcode.saathealthassessment.data.model.list3
import com.github.adizcode.saathealthassessment.navigation.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Dashboard") }, actions = {
            IconButton(onClick = { navController.navigate(Screen.Awards.route) }) {
                Icon(Icons.Filled.AccountBox, null)
            }
        })
    }) {
        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                DashboardHeader()
                Column(Modifier.padding(15.dp)) {
                    VideoSection(
                        sectionTitle = "Continue Watching",
                        videoList = list1,
                        itemWidthFraction = 0.6f,
                        itemHeightDp = 120.dp,
                        navController = navController
                    )
                    Spacer(Modifier.height(10.dp))
                    VideoSection(
                        sectionTitle = "Popular on Netflix",
                        videoList = list2,
                        itemWidthFraction = 0.32f,
                        itemHeightDp = 170.dp,
                        navController = navController
                    )
                    Spacer(Modifier.height(10.dp))
                    VideoSection(
                        sectionTitle = "You May Like...",
                        videoList = list3,
                        itemWidthFraction = 0.32f,
                        itemHeightDp = 170.dp,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardHeader() {
    Image(
        painter = painterResource(R.drawable.daredevil),
        contentDescription = "Daredevil",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun VideoSection(
    sectionTitle: String,
    videoList: List<VideoData>,
    itemWidthFraction: Float,
    itemHeightDp: Dp,
    navController: NavController
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            sectionTitle,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold, fontSize = 22.sp)
        )

        OutlinedButton(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.Red
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text("See All", fontWeight = FontWeight.Bold)
        }
    }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        items(videoList) {
            Image(
                painter = painterResource(it.resId),
                contentDescription = it.desc,
                modifier = Modifier
                    .fillParentMaxWidth(fraction = itemWidthFraction)
                    .height(itemHeightDp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable { navController.navigate(Screen.Video.route) },
                contentScale = ContentScale.Crop
            )
        }
    }
}