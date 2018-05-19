package com.oxygen.yallagoom.adapter.event.chat;

/**
 * Created by pdx on 5/7/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oxygen.yallagoom.R;
import com.oxygen.yallagoom.activity.FullScreenImageActivity;
import com.oxygen.yallagoom.entity.Chat.UserConversations;
import com.oxygen.yallagoom.entity.Chat.UserCredentials;
import com.oxygen.yallagoom.entity.User;
import com.oxygen.yallagoom.utils.Constant;
import com.oxygen.yallagoom.utils.ToolUtils;

import java.io.IOException;
import java.util.ArrayList;

public class RecycleViewChatAdapter extends RecyclerView.Adapter<RecycleViewChatAdapter.MyViewHolder> {

    private final ImageLoader imageLoader;
    private final ArrayList<UserConversations> userConversations;
    private final ArrayList<UserCredentials> listOfUser;
    private final User user;
    public Context context;
    private static final int RIGHT_MSG = 0;
    private static final int LEFT_MSG = 1;
    private static final int RIGHT_MSG_IMG = 2;
    private static final int LEFT_MSG_IMG = 3;
    private static final int LEFT_MSG_VID = 4;
    private static final int RIGHT_MSG_VID = 5;
    private MediaPlayer mediaPlayer;
    private MyViewHolder lastholder;
    private CountDownTimer countDownTimer;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final SelectableRoundedImageView ivUserChat;
        private final TextView txtMessage;
        private final TextView timestamp;
        private final SimpleDraweeView img_chat;
        private final TextView user_url_image;
        private final ImageButton play_btn_audio;
        private final ImageButton pause_btn_audio;
        private final TextView audio_total_duration;
        private final TextView audio_current_duration;
        private final TextView timestamp_top;
        private final SeekBar audio_progress_bar;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            ivUserChat = (SelectableRoundedImageView) view.findViewById(R.id.ivUserChat);
            txtMessage = (TextView) view.findViewById(R.id.txtMessage);
            timestamp = (TextView) view.findViewById(R.id.timestamp);
            user_url_image = (TextView) view.findViewById(R.id.user_url_image);
            img_chat = (SimpleDraweeView) view.findViewById(R.id.img_chat);
            play_btn_audio = (ImageButton) view.findViewById(R.id.play_btn_audio);
            pause_btn_audio = (ImageButton) view.findViewById(R.id.pause_btn_audio);
            audio_total_duration = (TextView) view.findViewById(R.id.audio_total_duration);
            audio_current_duration = (TextView) view.findViewById(R.id.audio_current_duration);
            timestamp_top = (TextView) view.findViewById(R.id.timestamp_top);
            audio_progress_bar = (SeekBar) view.findViewById(R.id.audio_progress_bar);


        }
    }


    public RecycleViewChatAdapter(ArrayList<UserConversations> userConversations, User user, ArrayList<UserCredentials> listOfUser) {
        this.userConversations = userConversations;
        this.user = user;
        this.listOfUser = listOfUser;
        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == RIGHT_MSG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outcoming_text_message, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == LEFT_MSG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incoming_text_message, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == RIGHT_MSG_IMG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outcoming_image_message, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == LEFT_MSG_IMG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incoming_image_message, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == RIGHT_MSG_VID) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outcoming_voice_message, parent, false);
            return new MyViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incoming_voice_message, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.timestamp.setText(ToolUtils.getDate(userConversations.get(position).getTimestamp() * 1000));
        holder.timestamp_top.setText(ToolUtils.getDate(userConversations.get(position).getTimestamp() * 1000));
        if (position > 0 && ToolUtils.getDate(userConversations.get(position).getTimestamp() * 1000).equalsIgnoreCase(
                ToolUtils.getDate(userConversations.get(position - 1).getTimestamp() * 1000)
        )) {
            holder.timestamp_top.setVisibility(View.GONE);
        } else {
            holder.timestamp_top.setVisibility(View.VISIBLE);
        }
        if (userConversations.get(position).getType().equalsIgnoreCase("text")) {
            holder.txtMessage.setText(userConversations.get(position).getContent());

        }
        for (int i = 0; i < listOfUser.size(); i++) {
            if (listOfUser.get(i).getKey().equalsIgnoreCase(userConversations.get(position).getFromID())) {
                if (listOfUser.get(i).getProfilePicLink() != null) {
                    ToolUtils.setImage(listOfUser.get(i).getProfilePicLink(), holder.ivUserChat, imageLoader);
                    holder.user_url_image.setText(user.getFirst_name() + " " + user.getLast_name() + "===" + listOfUser.get(i).getProfilePicLink());
                }
            } else {

            }

        }
        if (userConversations.get(position).getFromID().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
            if (user.getImg_url() != null) {
                ToolUtils.setImage(Constant.imageUrl + user.getImg_url(), holder.ivUserChat, imageLoader);
                holder.user_url_image.setText(user.getFirst_name() + " " + user.getLast_name() + "===" + Constant.imageUrl + user.getImg_url());
            }
        }
        if (userConversations.get(position).getType().equalsIgnoreCase("photo")) {
            // ToolUtils.setImage(userConversations.get(position).getContent(), holder.img_chat, imageLoader);
            holder.img_chat.setImageURI(userConversations.get(position).getContent());
            holder.img_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] data = holder.user_url_image.getText().toString().split("===");
                    Intent intent = new Intent(context, FullScreenImageActivity.class);
                    intent.putExtra("nameUser", data[0]);
                    intent.putExtra("urlPhotoUser", data[1]);
                    intent.putExtra("urlPhotoClick", userConversations.get(position).getContent());
                    context.startActivity(intent);
                }
            });
        }
        if (userConversations.get(position).getType().equalsIgnoreCase("audio")) {
            holder.audio_progress_bar.getThumb().mutate().setAlpha(0);
       /*     MediaMetadataRetriever mFFmpegMediaMetadataRetriever = new MediaMetadataRetriever();
            mFFmpegMediaMetadataRetriever .setDataSource(userConversations.get(position).getContent(), new HashMap<String, String>());
            String mVideoDuration =  mFFmpegMediaMetadataRetriever .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long mTimeInMilliseconds= Long.parseLong(mVideoDuration);*/
            holder.audio_progress_bar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
      /*      final String time = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(userConversations.get(position).getDuration()),
                    TimeUnit.MILLISECONDS.toSeconds(userConversations.get(position).getDuration()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(userConversations.get(position).getDuration()))
            );*/
            holder.audio_total_duration.setText(ToolUtils.timeFromTimestamp(userConversations.get(position).getDuration()));
            holder.audio_current_duration.setText("00:00");
            holder.audio_progress_bar.setProgress(0);
            holder.play_btn_audio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.play_btn_audio.setVisibility(View.GONE);
                    holder.pause_btn_audio.setVisibility(View.VISIBLE);

                    if (mediaPlayer == null) {
                        playAudio(userConversations.get(position).getContent(),
                                holder.play_btn_audio, holder.pause_btn_audio);
                    } else {
                        if (lastholder != null && holder != lastholder) {
                            lastholder.play_btn_audio.setVisibility(View.VISIBLE);
                            lastholder.pause_btn_audio.setVisibility(View.GONE);
                            countDownTimer.cancel();
                        }
                        killMediaPlayer();
                        playAudio(userConversations.get(position).getContent(),
                                holder.play_btn_audio, holder.pause_btn_audio);
                    }
                    countDownTimer = new CountDownTimer(userConversations.get(position).getDuration(), 1000) {

                        public void onTick(long millisUntilFinished) {
                            int second = (int) ((userConversations.get(position).getDuration() - millisUntilFinished) / 1000);
                           /* String time = String.format("%02d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(((second) * 1000)),
                                    TimeUnit.MILLISECONDS.toSeconds(((second) * 1000)) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(((second + 1) * 1000)))
                            );*/
                            holder.audio_current_duration.setText(ToolUtils.timeFromTimestamp((second) * 1000));
                            holder.audio_progress_bar.setProgress(ToolUtils.getProgressPercentage(((second) * 1000), userConversations.get(position).getDuration()));
                            //here you can have your logic to set text to edittext
                        }

                        public void onFinish() {
                            holder.audio_current_duration.setText(ToolUtils.timeFromTimestamp(userConversations.get(position).getDuration()));
                            holder.audio_progress_bar.setProgress(ToolUtils.getProgressPercentage(userConversations.get(position).getDuration(), userConversations.get(position).getDuration()));

                        }

                    }.start();
                    lastholder = holder;
                }
            });
            holder.pause_btn_audio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.play_btn_audio.setVisibility(View.VISIBLE);
                    holder.pause_btn_audio.setVisibility(View.GONE);
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                    }
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (userConversations.get(position).getType().equalsIgnoreCase("text")
                && userConversations.get(position).getFromID().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
            return RIGHT_MSG;
        } else if (userConversations.get(position).getType().equalsIgnoreCase("text")
                && !userConversations.get(position).getFromID().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
            return LEFT_MSG;
        } else if (userConversations.get(position).getType().equalsIgnoreCase("photo")
                && userConversations.get(position).getFromID().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
            return RIGHT_MSG_IMG;
        } else if (userConversations.get(position).getType().equalsIgnoreCase("photo")
                && !userConversations.get(position).getFromID().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
            return LEFT_MSG_IMG;
        } else if (userConversations.get(position).getType().equalsIgnoreCase("audio")
                && userConversations.get(position).getFromID().equalsIgnoreCase(user.getFirebase_auth_user_id())) {
            return RIGHT_MSG_VID;
        } else {
            return LEFT_MSG_VID;
        }

    }


    @Override
    public int getItemCount() {

        return userConversations.size();
    }

    private void playAudio(String url, final ImageButton play_btn_audio, final ImageButton pause_btn_audio) {

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                killMediaPlayer();
                play_btn_audio.setVisibility(View.VISIBLE);
                pause_btn_audio.setVisibility(View.GONE);
            }
        });

    }

    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}