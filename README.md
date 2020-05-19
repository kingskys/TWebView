# TWebView
Android WebView 简单封装

功能
----
 1. 带加载中动画
 2. 带下拉刷新

gradle 使用
-------------
<pre>
allprojects {
    repositories {
        ...
        
        maven { url "https://jitpack.io" }
        maven { url 'https://oss.sonatype.org/content/repositories/snapshots' }
    }
}

dependencies {
    ...

    implementation 'com.github.kingskys:TWebView:v1.1.4'
}
</pre>

代码使用示例
----------
<pre>
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TWebView view = new TWebView(this);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, params);

        view.loadUrl("http://www.baidu.com");
    }
}
</pre>

致谢
----
 * https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh
 * https://github.com/Justson/AgentWeb
