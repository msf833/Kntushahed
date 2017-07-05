

package ir.madamas.kntushahed.kntushahed.introActivities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import ir.madamas.kntushahed.kntushahed.R;
import ir.madamas.kntushahed.kntushahed.SignupLoginActivity;

/**
 * Created by MSF on 7/5/2017.
 */

public class IntroActivity extends AppIntro {


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Intent item_intent = new Intent(getApplicationContext(), SignupLoginActivity.class);
        startActivity(item_intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance("درخواست کلاس", "میتونی با این برنامه هرکلاسی رو برای هر موضوعی که دنبالی رزرو کنی و خیلی راحت از خدمات آموزشی مدرن استفاده کنی ", R.mipmap.ic_logo, Color.parseColor("#10ba81")));
        addSlide(AppIntroFragment.newInstance("دانلود جزوه ", "میتونی جزوه های درسای مختلفو ببینی و جستجو کنی و دیگه مجبور نیستی سر کلاس جزوه بنویسی !! همه نکات رو باهم توی ی جزوه کامل میخونی و 20 میشی !", R.mipmap.ic_logo, Color.parseColor("#10ba81")));
        addSlide(AppIntroFragment.newInstance("ایجاد کلاس", "اگه میخوای کلاسی روبرای بقیه بزاری ، میتونی اینجا بزاریو هزینه اش رو تعیین کنی که بقیه هم بیان ثبتنام کنن !", R.mipmap.ic_logo, Color.parseColor("#10ba81")));
        addSlide(AppIntroFragment.newInstance("شروع", "برای شروع آماده اید ؟!!", R.mipmap.ic_logo, Color.parseColor("#10ba81")));


        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));


        showSkipButton(false);
        setVibrate(true);
        setVibrateIntensity(30);
        setFlowAnimation();
    }
}
