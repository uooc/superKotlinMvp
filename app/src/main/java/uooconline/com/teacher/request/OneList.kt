package uooconline.com.teacher.request

import uooconline.com.nucleus.retrofit.request.BaseRequest

/**
 * Create date: 2018/4/26 0026 on 18:07
 * Describe：
 * Author： GenGeGe
 */
class OneList: BaseRequest<OneList.Data>(){

data class Data(
    val id: String,
    val weather: Weather,
    val date: String,
    val content_list: List<Content>,
    val menu: Menu
)

data class Menu(
    val vol: String?,
    val list: List<X>
)

data class X(
    val content_type: String,
    val content_id: String,
    val title: String,
    val tag: Tag,
    val serial_list: List<String>
)

data class Tag(
    val id: String,
    val title: String
)

data class Weather(
    val city_name: String,
    val date: String,
    val temperature: String,
    val humidity: String,
    val climate: String,
    val wind_direction: String,
    val hurricane: String,
    val icons: Icons
)

data class Icons(
    val day: String,
    val night: String
)

data class Content(
    val id: String,
    val category: String,
    val display_category: Int,
    val item_id: String,
    val title: String,
    val forward: String,
    val img_url: String,
    val like_count: Int,
    val post_date: String,
    val last_update_date: String,
    val author: Author,
    val video_url: String,
    val audio_url: String,
    val audio_platform: Int,
    val start_video: String,
    val has_reading: Int,
    val volume: String,
    val pic_info: String,
    val words_info: String,
    val subtitle: String,
    val number: Int,
    val serial_id: Int,
    val serial_list: List<Any>,
    val movie_story_id: String,
    val ad_id: Int,
    val ad_type: Int,
    val ad_pvurl: String,
    val ad_linkurl: String,
    val ad_makettime: String,
    val ad_closetime: String,
    val ad_share_cnt: String,
    val ad_pvurl_vendor: String,
    val content_id: String,
    val content_type: String,
    val content_bgcolor: String,
    val share_url: String,
    val share_info: ShareInfo,
    val share_list: ShareList,
    val tag_list: List<Any>,
    val orientation: String,
    val answerer: Answerer,
    val music_name: String,
    val audio_author: String,
    val audio_album: String,
    val cover: String,
    val bg_color: String
)

data class ShareList(
    val wx: Wx,
    val wx_timeline: WxTimeline,
    val weibo: Weibo,
    val qq: Qq
)

data class WxTimeline(
    val title: String,
    val desc: String,
    val link: String,
    val imgUrl: String,
    val audio: String
)

data class Wx(
    val title: String,
    val desc: String,
    val link: String,
    val imgUrl: String,
    val audio: String
)

data class Qq(
    val title: String,
    val desc: String,
    val link: String,
    val imgUrl: String,
    val audio: String
)

data class Weibo(
    val title: String,
    val desc: String,
    val link: String,
    val imgUrl: String,
    val audio: String
)

data class Answerer(
    val user_id: String,
    val user_name: String,
    val web_url: String,
    val summary: String,
    val desc: String,
    val is_settled: String,
    val settled_type: String,
    val fans_total: String,
    val wb_name: String
)

data class Author(
    val user_id: String,
    val user_name: String,
    val desc: String,
    val wb_name: String,
    val is_settled: String,
    val settled_type: String,
    val summary: String,
    val fans_total: String,
    val web_url: String
)

data class ShareInfo(
    val url: String,
    val image: String,
    val title: String,
    val content: String
)


}