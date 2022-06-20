package com.github.adizcode.saathealthassessment.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.adizcode.saathealthassessment.R
import com.github.adizcode.saathealthassessment.data.model.User
import com.github.adizcode.saathealthassessment.data.model.fakeUser
import com.github.adizcode.saathealthassessment.ui.theme.AwardsBackground
import java.text.NumberFormat

val RANGE_OF_LEVELS = 1..10
val RANGE_OF_BADGES = 1..10

@Composable
fun AwardsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AwardsBackground)
            .padding(20.dp)
    ) {
        TotalPoints(fakeUser.points)
        Spacer(Modifier.height(20.dp))
        AwardSection(
            modifier = Modifier.weight(1f),
            sectionName = "Levels",
            prefix = "Level",
            currentValue = fakeUser.currentLevel,
            range = RANGE_OF_LEVELS,
            sectionDrawable = R.drawable.level
        )
        AwardSection(
            modifier = Modifier.weight(1f),
            sectionName = "Badges",
            prefix = "Badge",
            currentValue = fakeUser.currentBadge,
            range = RANGE_OF_BADGES,
            sectionDrawable = R.drawable.badge
        )
    }
}

@Composable
fun TotalPoints(points: Int) {
    val formattedPoints = NumberFormat.getInstance().format(points)

    Column {
        Text("Total", style = MaterialTheme.typography.h5)
        Text("$formattedPoints points", style = MaterialTheme.typography.h3)
    }
}

@Composable
fun AwardSection(
    modifier: Modifier = Modifier,
    sectionName: String,
    prefix: String,
    currentValue: Int,
    range: IntRange,
    @DrawableRes sectionDrawable: Int
) {
    Column(modifier = modifier) {
        Text(
            text = sectionName,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(10.dp))
        SectionColumn(
            currentValue = currentValue,
            range = range,
            prefix = prefix,
            sectionDrawable = sectionDrawable
        )
    }
}

@Composable
fun SectionColumn(
    modifier: Modifier = Modifier,
    prefix: String,
    currentValue: Int,
    range: IntRange,
    @DrawableRes sectionDrawable: Int
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(range.toList()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillParentMaxHeight(0.3f)
                    .clip(RoundedCornerShape(15.dp))
                    .clickable { },
                backgroundColor = if (it <= currentValue) Color.White else AwardsBackground,
                elevation = 0.dp
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(sectionDrawable),
                        contentDescription = prefix,
                        modifier = Modifier.background(
                            Color.Transparent
                        ),
                        colorFilter = if (it > currentValue) ColorFilter.tint(Color.Gray) else null
                    )
                    Text(
                        "$prefix $it",
                        style = MaterialTheme.typography.h5.copy(color = if (it <= currentValue) Color.DarkGray else Color.Gray)
                    )
                }
            }
        }
    }
}