package uooconline.com.nucleus.widget.richeditor.core;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImgJSI {
    private Context context;
    private RichEditor.OnClickInterceptListener imageListener;
    private String[] hrefUrls, imageUrls;

    ImgJSI(RichEditor.OnClickInterceptListener imageListener, Context context) {
        this.imageListener = imageListener;
        this.context = context;
    }

    @JavascriptInterface
    public void openImage(final int index,final String originHtml) {
        this.hrefUrls = returnHrefFromHtml(originHtml);
        this.imageUrls = returnImageUrlsFromHtml(originHtml);
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (imageUrls != null && imageUrls.length > 0)
                        imageListener.onImgClick(imageUrls, index, hrefUrls != null && hrefUrls.length > 0 ? hrefUrls[0] : null);
                }
            });
        }
    }

    //解析html中的超链接
    private String[] returnHrefFromHtml(String htmlCode) {
        String regex = "<a.*?/a>";//a
        Pattern pt = Pattern.compile(regex);
        Matcher mt = pt.matcher(htmlCode);
        List<String> hrefList = new ArrayList<String>();
        while (mt.find()) {
            String s3 = "href=.*?>";//href
            Pattern pt3 = Pattern.compile(s3);
            Matcher mt3 = pt3.matcher(mt.group());
            while (mt3.find()) {
                String s4 = "[a-zA-z]+://[^\\s]*";//http
                Pattern pt4 = Pattern.compile(s4);
                Matcher mt4 = pt4.matcher(mt3.group().replaceAll("href=|>", ""));
                while (mt4.find()) {
                    hrefList.add(mt4.group().replaceAll("\"", ""));
                }
            }
        }
        if (hrefList.size() == 0) {
            return null;
        }
        return hrefList.toArray(new String[hrefList.size()]);
    }

    //解析html中的图片地址
    private String[] returnImageUrlsFromHtml(String htmlCode) {
        List<String> imageSrcList = new ArrayList<String>();
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic|\\b)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote;
        String src;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("//s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        if (imageSrcList.size() == 0) {
            return null;
        }
        return imageSrcList.toArray(new String[imageSrcList.size()]);
    }
}
