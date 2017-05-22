package ir.madamas.kntushahed.kntushahed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import java.util.Locale;

public class about extends AppCompatActivity {

    String text;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        webView = (WebView) findViewById(R.id.WebView_about);

        text = "باتوجه به اینکه دانشجوبان شاهد و ایثارگر دانشجویانی باهوش و متعهد هستند ولی در سالهای اخیر مشاهده کرده ایم که این دانشجویان با افت تحصیلی مواجه شده اند . لذا برآن شدیم که با ایجاد این طرح به پیشرفت هرچه بیشتر انها کمک نموده و یار یکدیگر باشیم این برنامه بخشی از سند اوج میباشد که برای افزایش سطح علمی  شاهد و ایثارگر دانشگاه طراحی شده  و تمام حقوق آن محفوظ است";

        String justifyTag = "<html><body style='direction:rtl;text-size:40px;padding:10px;text-align:justify;'>%s</body></html>";
        String dataString = String.format(Locale.US, justifyTag, text);
        webView.loadDataWithBaseURL("", dataString, "text/html", "UTF-8", "");

    }
}
