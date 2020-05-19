# TWebView
Android WebView 简单封装

功能
----
 1. 带加载中动画
 2. 带下拉刷新

gradle 使用
-------------
<pre>
buildscript {
    ...
    
    dependencies {
        ...
        
        classpath 'com.github.dcendents:android-maven-gradle-plugin:2.1'
    }
}

allprojects {
    repositories {
        ...
        
        maven { url "https://jitpack.io" }
        maven {
            url 'https://oss.sonatype.org/content/repositories/snapshots'
        }
    }
}

dependencies {
    ...

    implementation 'com.github.kingskys:TWebView:v1.1.3'
}
</pre>

致谢
----
 * https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh
 * https://github.com/Justson/AgentWeb
