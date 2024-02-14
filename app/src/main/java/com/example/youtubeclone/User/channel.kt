package com.example.youtubeclone.User

data class channel(
    var channelID : String,
    var channelName : String,
    var subscribers : Int,
    var video : List<VideoProperties>,
    var comments : Comments,
    var shorts : List<Short>,
    var playlist: Playlist

)
