package com.oxygen.yallagoom.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.entity.News.News_data_feeds;
import com.oxygen.yallagoom.entity.News.News_extended_entities_video_info_variants;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.util.ArrayList;

import cn.jzvd.JZVideoPlayerStandard;

public class RecycleViewNews extends RecyclerView.Adapter<RecycleViewNews.MyViewHolder> {


    private final ArrayList<News_data_feeds> resultClass;
    private final ImageLoader imageLoader;
    private final ArrayList<String> channel;
    public Context context;


    public RecycleViewNews(ArrayList<News_data_feeds> resultClass, ArrayList<String> channel) {
        this.resultClass = resultClass;
        this.channel = channel;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       /* if (position == 0) {
            holder.news_img.setVisibility(View.GONE);
            holder.videoplayer.setUp("https://video.twimg.com/ext_tw_video/976526722664411136/pu/vid/640x360/e_DY3DggspRduB0t.mp4"
                    , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "Hi");
            holder.videoplayer.thumbImageView.setImageURI(Uri.parse("https://pbs.twimg.com/ext_tw_video_thumb/976526722664411136/pu/img/gLzduVUYGBqOlula.jpg"));
        }*/
        holder.website_name.setText(channel.get(position));
        holder.website_name2.setText(channel.get(position));
        holder.news_time.setText(ToolUtils.getTimeSince(ToolUtils.converStringToDate(resultClass.get(position).getCreated_at(), "EEE MMM dd HH:mm:ss Z yyyy")));
        holder.news_time2.setText(ToolUtils.getTimeSince(ToolUtils.converStringToDate(resultClass.get(position).getCreated_at(), "EEE MMM dd HH:mm:ss Z yyyy")));
        if (resultClass.get(position).getEntities().getMedia() != null && resultClass.get(position).getEntities().getMedia().get(0).getIndices().length > 0) {
            holder.description_news.setText(resultClass.get(position).getText().substring(0, resultClass.get(position).getEntities().getMedia().get(0).getIndices()[0]));
        } else if (resultClass.get(position).getEntities().getUrls() != null && resultClass.get(position).getEntities().getUrls().size() > 0) {
            holder.description_news.setText(resultClass.get(position).getText().substring(0, resultClass.get(position).getEntities().getUrls().get(0).getIndices()[0]));
        } else {
            holder.description_news.setText(resultClass.get(position).getText());

        }
        if (resultClass.get(position).getExtended_entities() != null && resultClass.get(position).getExtended_entities().getMedia().size() > 0 &&
                resultClass.get(position).getExtended_entities().getMedia().get(0).getType().equalsIgnoreCase("video")) {
            holder.top_news_img.setVisibility(View.VISIBLE);
            holder.news_img.setVisibility(View.GONE);
            holder.news_time.setVisibility(View.VISIBLE);
            holder.news_time2.setVisibility(View.GONE);
            holder.title2.setVisibility(View.GONE);
            holder.website_name2.setVisibility(View.GONE);
            holder.progress_bar.setVisibility(View.GONE);
            holder.videoplayer.setVisibility(View.VISIBLE);
            ArrayList<News_extended_entities_video_info_variants> getVariants = resultClass.get(position).getExtended_entities().getMedia().get(0).getVideo_info().getVariants();
            for (int i = 0; i < getVariants.size(); i++) {
                if (getVariants.get(i).getBitrate() == 256000) {
                    ToolUtils.setImage(resultClass.get(position).getEntities().getMedia().get(0).getMedia_url(), holder.videoplayer.thumbImageView, imageLoader);
                    holder.videoplayer.setUp(getVariants.get(i).getUrl()
                            , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
                    break;
                }
            }

        } else if (resultClass.get(position).getEntities().getMedia() != null && resultClass.get(position).getEntities().getMedia().size() > 0 &&
                resultClass.get(position).getEntities().getMedia().get(0).getType().equalsIgnoreCase("photo")) {
            holder.top_news_img.setVisibility(View.VISIBLE);
            holder.news_time.setVisibility(View.VISIBLE);
            holder.news_time2.setVisibility(View.GONE);
            holder.title2.setVisibility(View.GONE);
            holder.website_name2.setVisibility(View.GONE);
            if (resultClass.get(position).getEntities().getHashtags() != null) {
                String hash = "";
                for (int i = 0; i < resultClass.get(position).getEntities().getHashtags().size(); i++) {
                    hash = hash + "#" + resultClass.get(position).getEntities().getHashtags().get(i).getText();
                }
                holder.title.setText(hash);
            }
            ToolUtils.setImageWithProgress(resultClass.get(position).getEntities().getMedia().get(0).getMedia_url(), holder.news_img, imageLoader,holder.progress_bar);
        } else {
            holder.top_news_img.setVisibility(View.GONE);
            holder.news_time.setVisibility(View.GONE);
            holder.news_time2.setVisibility(View.VISIBLE);
            holder.title2.setVisibility(View.VISIBLE);
            holder.website_name2.setVisibility(View.VISIBLE);
            if (resultClass.get(position).getEntities().getHashtags() != null) {
                String hash = "";
                for (int i = 0; i < resultClass.get(position).getEntities().getHashtags().size(); i++) {
                    hash = hash + "#" + resultClass.get(position).getEntities().getHashtags().get(i).getText();
                }
                holder.title2.setText(hash);
            } else {
                holder.title2.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return resultClass.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView title2;
        private final TextView website_name;
        private final TextView website_name2;
        private final TextView news_time;
        private final TextView description_news;
        private final TextView news_time2;
        private final RelativeLayout top_news_img;
        private final ImageView news_img;
        private final JZVideoPlayerStandard videoplayer;
        private final ProgressBar progress_bar;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            title = (TextView) itemView.findViewById(R.id.title);
            title2 = (TextView) itemView.findViewById(R.id.title2);
            website_name = (TextView) itemView.findViewById(R.id.website_name);
            website_name2 = (TextView) itemView.findViewById(R.id.website_name2);
            news_time = (TextView) itemView.findViewById(R.id.news_time);
            news_time2 = (TextView) itemView.findViewById(R.id.news_time2);
            description_news = (TextView) itemView.findViewById(R.id.description_news);
            news_img = (ImageView) itemView.findViewById(R.id.news_img);
            top_news_img = (RelativeLayout) itemView.findViewById(R.id.top_news_img);
            videoplayer = (JZVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
            progress_bar = (ProgressBar) itemView.findViewById(R.id.progress_bar);

        }
    }


    public int getItemViewType(int position) {
        return position;
    }

}