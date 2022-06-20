package com.github.adizcode.saathealthassessment.ui.screens

import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.github.adizcode.saathealthassessment.data.model.VideoData
import com.github.adizcode.saathealthassessment.data.model.fakeUser
import com.github.adizcode.saathealthassessment.data.model.list2
import com.github.adizcode.saathealthassessment.navigation.Screen
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource

@Composable
fun VideoScreen(navController: NavController) {
    val landscapeOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context as? ComponentActivity ?: return@DisposableEffect onDispose { }
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = landscapeOrientation
        onDispose { activity.requestedOrientation = originalOrientation }
    }

    // TODO: Replace this with the clicked video's streamUrl
    val url =
        "https://dash.akamaized.net/dash264/TestCasesIOP33/adapatationSetSwitching/5/manifest.mpd"
    Row(Modifier.fillMaxWidth()) {
        VideoPlayer(modifier = Modifier.weight(0.8f), videoUrl = url) { fakeUser.incrementPoints() }
        Recommendations(
            modifier = Modifier.weight(0.2f),
            videoList = list2,
            itemHeightFraction = 0.32f,
            navController = navController
        )
    }
}

@Composable
fun VideoPlayer(modifier: Modifier = Modifier, videoUrl: String, onVideoEnded: () -> Unit) {
    val context = LocalContext.current

    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            val dataSource = DefaultDataSource.Factory(context)
            val source = DashMediaSource.Factory(dataSource)
                .createMediaSource(MediaItem.fromUri(videoUrl))
            addMediaSource(source)

            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)

                    if (playbackState == Player.STATE_ENDED) {
                        onVideoEnded()
                    }
                }
            })
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(
        AndroidView(
            modifier = modifier,
            factory = {
                StyledPlayerView(it).apply {
                    this.player = player
                    useController = true
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    setShowBuffering(StyledPlayerView.SHOW_BUFFERING_ALWAYS)
                }
            },
        ),
    ) {
        onDispose { player.release() }
    }
}

@Composable
fun Recommendations(
    modifier: Modifier = Modifier,
    videoList: List<VideoData>,
    itemHeightFraction: Float,
    navController: NavController
) {

    // TODO: Modify the click listener to simply change the current media item, instead of creating a new VideoScreen
    LazyColumn(
        modifier = modifier.background(Color.Black),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(videoList) {
            Image(
                painter = painterResource(it.resId),
                contentDescription = it.desc,
                modifier = Modifier
                    .fillParentMaxHeight(fraction = itemHeightFraction)
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { navController.navigate(Screen.Video.route) { popUpTo(Screen.Dashboard.route) } },
                contentScale = ContentScale.Crop
            )
        }
    }
}