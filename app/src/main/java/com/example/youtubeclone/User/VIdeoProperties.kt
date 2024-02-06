package com.example.youtubeclone.User

class VideoProperties {
    private lateinit var channelName: String
    private lateinit var videoID: String
    private var likes: Int = 0
    private var dislikes: Int = 0
    private lateinit var description: String
    private lateinit var comments: List<String>
    private lateinit var uploadDate: String
    private var length: Int = 0
    private lateinit var thumbnail: String
    private lateinit var video: String
    private var views: Int = 0

    // Getters
    fun getChannelName(): String {
        return channelName
    }

    fun getVideoID(): String {
        return videoID
    }

    fun getLikes(): Int {
        return likes
    }

    fun getDislikes(): Int {
        return dislikes
    }

    fun getDescription(): String {
        return description
    }

    fun getComments(): List<String> {
        return comments
    }

    fun getUploadDate(): String {
        return uploadDate
    }

    fun getLength(): Int {
        return length
    }

    fun getThumbnail(): String {
        return thumbnail
    }

    fun getVideo(): String {
        return video
    }

    fun getViews(): Int {
        return views
    }

    // Setters
    fun setChannelName(name: String) {
        channelName = name
    }

    fun setVideoID(id: String) {
        videoID = id
    }

    fun setLikes(likesCount: Int) {
        likes = likesCount
    }

    fun setDislikes(dislikesCount: Int) {
        dislikes = dislikesCount
    }

    fun setDescription(desc: String) {
        description = desc
    }

    fun setComments(comments: List<String>) {
        this.comments = comments
    }

    fun setUploadDate(date: String) {
        uploadDate = date
    }

    fun setLength(len: Int) {
        length = len
    }

    fun setThumbnail(thumb: String) {
        thumbnail = thumb
    }

    fun setVideo(videoUrl: String) {
        video = videoUrl
    }

    fun setViews(viewsCount: Int) {
        views = viewsCount
    }
}
