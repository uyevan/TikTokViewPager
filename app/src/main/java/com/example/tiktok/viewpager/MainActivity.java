package com.example.tiktok.viewpager;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_main);
        final ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        List<VideoItems> list = new ArrayList<VideoItems>();

        String[] data = {
                "https://dwz.mk/FvQVNb",
                "http://b6i.cn/6yHq3",
                "http://mtw.so/66fctR",
                "http://mtw.so/6d5rQ6",
                "http://mtw.so/6llOO4",
                "http://mtw.so/5JKMrO",
                "http://mtw.so/66faVz",
                "https://dwz.mk/Q3YBjm",
                "https://dwz.mk/MVZVjy",
                "http://mtw.so/6llPR4",
                "http://mtw.so/6sS2VL",
                "http://mtw.so/5YNa04",
                "http://mtw.so/6llPRe",
                "http://mtw.so/5Ceznr",
                "http://mtw.so/6d5qi8",
                "http://mtw.so/5uImiU",
                "http://mtw.so/6d5qii",
                "http://mtw.so/5JKMu4",
                "http://mtw.so/6llPRI",
                "http://mtw.so/5uImkQ",
                "http://mtw.so/5YNa2k",
                "http://mtw.so/6llPRS",
                "http://mtw.so/5JKMuo",
                "http://mtw.so/6d5qiM",
                "http://mtw.so/6llPS2",
                "http://mtw.so/5JKMuy",
                "http://mtw.so/5YNa2E"
        };

        // 数组加载
        for (int i = 0; i < data.length; i++) {
            VideoItems videoItems = new VideoItems();
            videoItems.videoURL = data[i];
            videoItems.videoTitle = "Hello Made Fuck - " + i;
            videoItems.videoDescription = data[i];
            list.add(videoItems);
        }

        // 接口加载
        /*for (int i = 0; i < 100; i++) {
            VideoItems videoItems = new VideoItems();
            videoItems.videoURL = "http://api.awit.ltd/api/douyin.php";
            videoItems.videoTitle = "Hello Made Fuck - " + i;
            videoItems.videoDescription = "this model is videos , cool!";
            list.add(videoItems);
        }*/

        // 给ViewPager2设置适配器
        viewPager2.setAdapter(new VideoAdapter(list));

    }
}