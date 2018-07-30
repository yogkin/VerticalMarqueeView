package com.bx.verticalmarqueeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bx.marqueeviewlibrary.MarqueeView;
import com.bx.marqueeviewlibrary.NoticeBean;

import java.util.ArrayList;
import java.util.List;

/**
*@author small white
*@date 2018/4/13
*@fuction 
*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<NoticeBean> notices = new ArrayList<>();
        notices.add(new NoticeBean("套马的汉子你威武雄壮","2018.6.8"));
        notices.add(new NoticeBean("一望无际的原野随你去流浪","2019.6.8"));

        notices.add(new NoticeBean("一望无际的你的心海和","2888.6.8"));
        notices.add(new NoticeBean("一望无际的原野随你去流浪","2019.6.8"));
        MarqueeView marqueeView = findViewById(R.id.marqueeview);
        marqueeView.startMarquee(notices);
    }
}
