package com.example.android.dessertpusher.domain

import android.content.ContentResolver
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.annotation.RawRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class BackgroundMusicPlayer(
        lifecycle: Lifecycle,
        context: Context,
        @RawRes private val musicResourceId: Int) : LifecycleObserver {

    private var mediaPlayer: MediaPlayer = MediaPlayer()

    var startPosition = 0

    init {
        lifecycle.addObserver(this)
        // We can't just use MediaPlayer.create because that already calls prepare()
        // But if prepare() is already called we can't easily stop and restart the player in
        // onStop and onStart (Can't call prepare again after it's been done in create()).
        val songUri = Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority("com.example.android.dessertpusher")
                .path(musicResourceId.toString())
                .build()
        mediaPlayer.setDataSource(context, songUri)
        mediaPlayer.isLooping = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        mediaPlayer.prepare()
        mediaPlayer.seekTo(startPosition)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        mediaPlayer.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        mediaPlayer.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        startPosition = mediaPlayer.currentPosition
        mediaPlayer.stop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mediaPlayer.release()
    }
}