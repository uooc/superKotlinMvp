package uooconline.com.teacher.request

import uooconline.com.nucleus.retrofit.request.BaseRequest

/**
 * Create date: 2018/4/26 0026 on 18:07
 * Describe：
 * Author： GenGeGe
 */
class Bymonth: BaseRequest<Bymonth.Data>(){

data class Data(
    val hpcontent_id: String,
    val hp_title: String,
    val author_id: String,
    val hp_img_url: String,
    val hp_img_original_url: String,
    val hp_author: String,
    val ipad_url: String,
    val hp_content: String,
    val hp_makettime: String,
    val hide_flag: String,
    val last_update_date: String,
    val web_url: String,
    val wb_img_url: String,
    val image_authors: String,
    val text_authors: String,
    val image_from: String,
    val text_from: String,
    val content_bgcolor: String,
    val template_category: String,
    val maketime: String,
    val share_list: ShareList,
    val praisenum: Int,
    val sharenum: Int,
    val commentnum: Int
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

}