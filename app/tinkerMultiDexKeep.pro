# 屏蔽tinker时，因为使用了改造application的方式接入tinker，所以仍然需要keep主放进main dex处理

#tinker multidex keep patterns:
-keep public class * implements com.tencent.tinker.loader.app.ApplicationLifeCycle {
    <init>(...);
    void onBaseContextAttached(android.content.Context);
}

-keep public class * extends com.tencent.tinker.loader.TinkerLoader {
    <init>(...);
}

-keep public class * extends android.app.Application {
     <init>();
     void attachBaseContext(android.content.Context);
}

-keep class com.tencent.tinker.loader.TinkerTestAndroidNClassLoader {
    <init>(...);
}

#your dex.loader patterns here
-keep class com.tencent.tinker.loader.** {
    <init>(...);
}

-keep class uooconline.com.teacher.utils.tinker.ProxyApplication {
    <init>(...);
}

