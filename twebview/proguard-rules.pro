# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep class org.apache.mina.** {*;}
-keep interface org.apache.mina.** {*;}
-dontwarn org.apache.mina.**

-keep class ietf.jgss.** {*;}

# 下拉刷新
-keep class in.srain.cube.** {*;}
-keep interface in.srain.cube.** {*;}
-dontwarn in.srain.cube.**

-keep class okhttp3.** {*;}
#-dontwarn okhttp3.**

-keep class com.yanzhenjie.nohttp.** {*;}
-dontwarn com.yanzhenjie.nohttp.**

-keep class com.squareup.okhttp3.** {*;}
-dontwarn com.squareup.okhttp3.**

-keep class com.squareup.okio.** {*;}
-keep interface com.squareup.okio.** {*;}

-dontwarn okio.**
-dontwarn okhttp3.**

-keep class org.slf4j.** {*;}
-keep interface org.slf4j.** {*;}
-dontwarn org.slf4j.**
