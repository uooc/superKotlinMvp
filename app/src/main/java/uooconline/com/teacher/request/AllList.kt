package uooconline.com.teacher.request

import uooconline.com.nucleus.retrofit.request.BaseRequest
import java.util.*

/**
 * Create date: 2018/4/26 0026 on 18:07
 * Describe：
 * Author： GenGeGe
 */
class AllList: BaseRequest<List<AllList.Data>>(){

    /**
     * {"res":0,"data":[{"id":104,"cover":"http:\/\/image.wufazhuce.com\/FruJZnkTCsYrhAqsuVdC2VaoqMAg?bid=104","title":"\u7b2c90\u5c4a\u5965\u65af\u5361\u83b7\u5956\u53ca\u63d0\u540d\u7684\u7cbe\u9009\u5f71\u7247","category":11,"content_id":"94","is_stick":false,"serial_list":[],"link_url":""},{"id":95,"cover":"http:\/\/image.wufazhuce.com\/FjQxZDF5xxaK3ohBPAc0Q9UypCcQ?bid=95","title":"\u603b\u6709\u4e00\u53e5\u6c11\u8c23\uff0c\u5531\u51fa\u4f60\u7684\u8fc7\u5f80","category":11,"content_id":"83","is_stick":false,"serial_list":[],"link_url":""},{"id":67,"cover":"http:\/\/image.wufazhuce.com\/FpBr24SvKVpBVFEzh6PFPcnp7FFQ?bid=67","title":"\u6211\u53c8\u4e0d\u662f\u602a\u517d","category":11,"content_id":"3","is_stick":false,"serial_list":[],"link_url":""},{"id":63,"cover":"http:\/\/image.wufazhuce.com\/FosOhpGiCpWNJ1aaPgxxMIe2iBSv?bid=63","title":"\u5931\u610f\u8005\u9152\u9986","category":11,"content_id":"59","is_stick":false,"serial_list":[],"link_url":""},{"id":48,"cover":"http:\/\/image.wufazhuce.com\/FhlzYJ6Zk7Xy5POY42itvlJNFLO_?bid=48","title":"\u7528\u6700\u77ed\u7684\u65f6\u95f4\uff0c\u4f53\u4f1a\u6700\u6709\u8da3\u7684\u6545\u4e8b\u3002","category":11,"content_id":"43","is_stick":false,"serial_list":[],"link_url":""},{"id":15,"cover":"http:\/\/image.wufazhuce.com\/Fm-oM2BswK3Y1KogvkoSUNT-Z0TS?bid=15","title":"\u5f20\u7693\u5bb8\u4f5c\u54c1\u7cbe\u9009","category":11,"content_id":"15","is_stick":false,"serial_list":[],"link_url":""},{"id":2,"cover":"http:\/\/image.wufazhuce.com\/Fkjd-C9nzZSQd2nTaKfJhz0DP-yP?bid=2","title":"ONE\u5f80\u671f\u9ad8\u8d5eTOP10","category":11,"content_id":"5","is_stick":false,"serial_list":[],"link_url":""}]}
     */
//    data class Data(
//    val id: String,
//    val cover: String,
//    val title: String,
//    val category: String,
//    val content_id: String,
//    val is_stick: Boolean,
//    val serial_list:Objects?,
//    val link_url: String
//)

data class Data(
    val id: Int,
    val cover: String,
    val title: String="",
    val category: Int=0,
    val content_id: String="0",
    val is_stick: Boolean=false,
    val serial_list: Any?,
    val with_x: Int=0,
    val link_url: String?
)



}