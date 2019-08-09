package ar.com.wolox.android.example.model.youtube

data class YoutubeVideo(
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val prevPageToken: String,
    val regionCode: String,
    val pageInfo: PageInfo,
    val items: List<Item>
)