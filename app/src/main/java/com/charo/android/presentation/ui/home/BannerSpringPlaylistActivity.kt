package com.charo.android.presentation.ui.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.charo.android.databinding.ActivityBannerSpringPlaylistBinding
import com.charo.android.presentation.util.Define

class BannerSpringPlaylistActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityBannerSpringPlaylistBinding
    private lateinit var define: Define

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerSpringPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        define = Define()
        initListener()

    }

    private fun initListener(){
        binding.tvBtnGoPlaylist.setOnClickListener(this)
        binding.clPlaylistPoolside.setOnClickListener(this)
        binding.clPlaylistOpenseason.setOnClickListener(this)
        binding.clPlaylistLastnightonearth.setOnClickListener(this)
        binding.clPlaylistMalibu.setOnClickListener(this)
        binding.clPlaylistThing.setOnClickListener(this)
        binding.clPlaylistPride.setOnClickListener(this)
        binding.clPlaylistYellowhearts.setOnClickListener(this)
        binding.clPlaylistSayyou.setOnClickListener(this)
        binding.clPlaylistTomorrowtonight.setOnClickListener(this)
        binding.clPlaylistSpeechless.setOnClickListener(this)
        binding.clPlaylist1245.setOnClickListener(this)
        binding.clPlaylistThickandthin.setOnClickListener(this)
        binding.clPlaylistSomeonethat.setOnClickListener(this)
        binding.clPlaylistIlysb.setOnClickListener(this)
        binding.clPlaylistNoises.setOnClickListener(this)
    }

    private fun openYoutue(youtubeId : String){
        val uri = Uri.parse("https://www.youtube.com/$youtubeId")

        val Instagram_Intent = Intent(Intent.ACTION_VIEW, uri)
        Instagram_Intent.setPackage("com.google.android.youtube")

        kotlin.runCatching {
            startActivity(Instagram_Intent)
        }.onFailure{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/$youtubeId")))
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.tvBtnGoPlaylist -> openYoutue(define.YOUTUBE_PLAYLIST_ALL)
            binding.clPlaylistPoolside -> openYoutue(define.YOUTUBE_PLAYLIST_POOLSIDE)
            binding.clPlaylistOpenseason -> openYoutue(define.YOUTUBE_PLAYLIST_OPENSEASON)
            binding.clPlaylistLastnightonearth -> openYoutue(define.YOUTUBE_PLAYLIST_LASTNIGHTONEARTH)
            binding.clPlaylistMalibu -> openYoutue(define.YOUTUBE_PLAYLIST_MALIBU)
            binding.clPlaylistThing -> openYoutue(define.YOUTUBE_PLAYLIST_THING)
            binding.clPlaylistPride -> openYoutue(define.YOUTUBE_PLAYLIST_PRIDE)
            binding.clPlaylistYellowhearts -> openYoutue(define.YOUTUBE_PLAYLIST_YELLOWHEARTS)
            binding.clPlaylistSayyou -> openYoutue(define.YOUTUBE_PLAYLIST_SAYYOU)
            binding.clPlaylistTomorrowtonight -> openYoutue(define.YOUTUBE_PLAYLIST_TOMORROWTONIGHT)
            binding.clPlaylistSpeechless -> openYoutue(define.YOUTUBE_PLAYLIST_SPEECHLESS)
            binding.clPlaylist1245 -> openYoutue(define.YOUTUBE_PLAYLIST_1245)
            binding.clPlaylistThickandthin -> openYoutue(define.YOUTUBE_PLAYLIST_THICKANDTHIN)
            binding.clPlaylistSomeonethat -> openYoutue(define.YOUTUBE_PLAYLIST_SOMEONETHAT)
            binding.clPlaylistIlysb -> openYoutue(define.YOUTUBE_PLAYLIST_ILYSB)
            binding.clPlaylistNoises -> openYoutue(define.YOUTUBE_PLAYLIST_NOISES)
        }
    }

}