package com.example.tiktok.viewpager;

import android.media.MediaPlayer;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<VideoItems> videoItemList; // 数据源
    private final LruCache<String, Boolean> videoCache; // 缓存池

    public VideoAdapter(List<VideoItems> videoItemList) {
        this.videoItemList = videoItemList;
        // 前后缓存各五个视频 + 正在播放的视频 = 11
        int cacheSize = 6;
        videoCache = new LruCache<>(cacheSize);
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_vedio, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.setVideoData(videoItemList.get(position));
        // 预加载当前视频后5个视频
        for (int i = 0; i <= 5; i++) {
            int preloadPosition = position + i;
            Log.e("TAG:", "正在缓存视频：" + preloadPosition + "条");
            if (preloadPosition >= 0 && preloadPosition < videoItemList.size()) {
                String preloadVideoUrl = videoItemList.get(preloadPosition).videoURL;
                Boolean isPreloaded = videoCache.get(preloadVideoUrl);
                if (isPreloaded == null || !isPreloaded) { // 没有缓存继续
                    Log.e("TAG:", "没有缓存继续");
                    VideoView videoView = new VideoView(holder.itemView.getContext()); // 创建下一个视频播放类
                    videoView.setVideoPath(preloadVideoUrl);
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            videoCache.put(preloadVideoUrl, true);
                            videoView.stopPlayback();
                        }
                    });
                }
            }
        }
        /*
        // 只预加载下一条
        int nextPosition = position + 1;
        if (nextPosition < videoItemList.size()) {
            String nextVideoUrl = videoItemList.get(nextPosition).videoURL;
            Boolean isPreloaded = videoCache.get(nextVideoUrl);
            if (isPreloaded == null || !isPreloaded) {
                VideoView videoView = new VideoView(holder.itemView.getContext());
                videoView.setVideoPath(nextVideoUrl);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        videoCache.put(nextVideoUrl, true);
                        videoView.stopPlayback();
                    }
                });
            }
        }
        */
    }

    @Override
    public int getItemCount() {
        return videoItemList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {

        VideoView videoView;
        TextView textVideoTitle, textVideoDescription;
        ProgressBar progressBar;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            progressBar = itemView.findViewById(R.id.videoProgressBar);
        }

        void setVideoData(VideoItems videoItems) {
            textVideoTitle.setText(videoItems.videoTitle);
            textVideoDescription.setText(videoItems.videoDescription);
            videoView.setVideoPath(videoItems.videoURL);
            // 加载监听，加载完成就执行此方法
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.e("TAG:", "加载监听完成");
                    progressBar.setVisibility(View.GONE);
                    mp.start();

                    float videoRate = mp.getVideoWidth() / (float) mp.getVideoHeight();
                    float screenRate = videoView.getWidth() / (float) videoView.getHeight();
                    float scale = videoRate / screenRate;

                    if (scale >= 1f) {
                        videoView.setScaleX(scale);
                    } else {
                        videoView.setScaleX(1f / scale);
                    }

                }
            });
            // 播放完成播放下一个
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                    Log.e("TAG:", "播放完成播放下一个");
                }
            });
        }
    }
}
