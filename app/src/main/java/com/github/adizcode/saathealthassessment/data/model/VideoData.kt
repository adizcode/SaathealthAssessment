package com.github.adizcode.saathealthassessment.data.model

import androidx.annotation.DrawableRes
import com.github.adizcode.saathealthassessment.R

data class VideoData(
    @DrawableRes val resId: Int,
    val desc: String,
    val streamUrl: String = "https://dash.akamaized.net/dash264/TestCasesIOP33/adapatationSetSwitching/5/manifest.mpd"
)

// Dummy data
private val videos = listOf(
    VideoData(R.drawable.daredevil, "Daredevil"),
    VideoData(R.drawable.breaking_bad, "Breaking Bad"),
    VideoData(R.drawable.into_the_night, "Into The Night"),
    VideoData(R.drawable.the_platform, "The Platform"),
    VideoData(R.drawable.love_death_robots, "Love Death Robots"),
    VideoData(R.drawable.friends, "Friends"),
    VideoData(R.drawable.office, "The Office"),
    VideoData(R.drawable.tbbt, "The Big Bang Theory"),
    VideoData(R.drawable.bvs, "Batman v Superman"),
    VideoData(R.drawable.good_will_hunting, "Good Will Hunting"),
)

val list1 = videos.shuffled()
val list2 = videos.shuffled()
val list3 = videos.shuffled()