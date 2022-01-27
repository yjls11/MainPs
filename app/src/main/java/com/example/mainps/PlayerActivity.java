package com.example.mainps;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    ImageButton btnPlay, btnNext, btnPrevious, btnFastForward, btnFastBackWard;
    TextView txtSongName, txtSongStart, txtSongEnd;
    SeekBar seekMusicBar;
    BarVisualizer barVisualizer;
    ImageView imageView;
    String songName;
    Button btnDown;

    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;

    ArrayList<File> mySongs;

    Thread updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnPlay = findViewById(R.id.BtnPlay);
        btnNext =  findViewById(R.id.BtnNext);
        btnPrevious =  findViewById(R.id.BtnPrevious);
        btnFastForward =  findViewById(R.id.BtnFastForward);
        btnFastBackWard =  findViewById(R.id.BtnFastRewind);
        btnDown = findViewById(R.id.btnDown);

        txtSongName =  findViewById(R.id.SongTxt);
        txtSongStart = findViewById(R.id.TxtSongStart);
        txtSongEnd =  findViewById(R.id.TxtSongEnd);

        seekMusicBar =  findViewById(R.id.SeekBar);
        barVisualizer = findViewById(R.id.wave);

        imageView = findViewById(R.id.MusicImage);

        //Checking if any song playing or not
        if (mediaPlayer != null) {

            //we will start mediaPlayer if currently there is no songs in it
            mediaPlayer.start();
            mediaPlayer.release();
        }

        //Getting the Required Details from the past Intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mySongs = (ArrayList) bundle.getIntegerArrayList("songs");
        position = bundle.getInt("pos");
        txtSongName.setSelected(true);

        //Extracting the fileName form the ArrayList

//        getIntent().getParcelableExtra("music");
        Uri uri =  getIntent().getParcelableExtra("music");
        Log.e("urigh",uri+"");
        String name = getIntent().getStringExtra("name");
        Bitmap bitmap = getIntent().getParcelableExtra("cover");
//        Uri uri = {Uri.parse(mySongs.get(position).toString())};
        songName = name;
        txtSongName.setText(songName);
        imageView.setImageBitmap(bitmap);

        //passing the song path to the Media Player
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri n = Uri.fromFile(new File("/sdcard/Download/Music.mp4"));
                    File path = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_MUSIC);

                    File dir = new File("/sdcard/Download");
                    if (!dir.exists()) {
                        if (!dir.mkdirs()) {
                            Log.d("App", "failed to create directory");
                        }
                    }
                    File File2 = new File(dir, name+".mp3");

                    InputStream in = getContentResolver().openInputStream(uri);
                    try {

                        OutputStream out = new FileOutputStream(File2);
                        try {
                            // Transfer bytes from in to out
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            }
                        } finally {
                            out.close();
                        }
                    } finally {
                        in.close();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

//        //Method to get the current media end time
        songEndTime();
//
//        //Method to show bar visualizers
        visualizer();


//        Thread to update the seekBar while playing song
        updateSeekBar = new Thread() {
            @Override
            public void run() {

                int TotalDuration = mediaPlayer.getDuration();
                int CurrentPosition = 0;

                while (CurrentPosition < TotalDuration) {
                    try {

                        sleep(500);
                        CurrentPosition = mediaPlayer.getCurrentPosition();
                        seekMusicBar.setProgress(CurrentPosition);

                    } catch (InterruptedException | IllegalStateException e) {

                        e.printStackTrace();
                    }
                }

            }
        };


//        Setting the seekbar's max progress to the maximum duration of the media file
        seekMusicBar.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();


        //Setting the Music player from the position of the seekbar
        seekMusicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                //getting the progress of the seek bar and setting it to Media Player
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });


        //Creating the Handler to update the current duration
        final Handler handler = new Handler();
        final int delay = 1000;
//
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//
//                //Getting the current duration from the media player
                String currentTime = createDuration(mediaPlayer.getCurrentPosition());

//                //Setting the current duration in textView
                txtSongStart.setText(currentTime);
                handler.postDelayed(this, delay);

            }
        }, delay);


//        Handler handler1 = new Handler(){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                //Implementing OnClickListener for Play and Pause Button
//                btnPlay.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
//                        mediaPlayer.start();
//
//                        //Checking playing any songs or not
//                        if (mediaPlayer.isPlaying()) {
//
//                            //setting the play icon
//                            btnPlay.setBackgroundResource(R.drawable.play_song_icon);
//                            btnPlay.setBackgroundResource(R.drawable.play_song_icon);
//
//                            //Pausing the current media
//                            mediaPlayer.pause();
//
//                        } else {
//
//                            //Setting the pause icon
//                            btnPlay.setBackgroundResource(R.drawable.pause_song_icon);
//
//                            //Starting the media player
//                            mediaPlayer.start();
//
//                            //Creating the Animation
//                            TranslateAnimation moveAnim = new TranslateAnimation(-25, 25, -25, 25);
//                            moveAnim.setInterpolator(new AccelerateInterpolator());
//                            moveAnim.setDuration(600);
//                            moveAnim.setFillEnabled(true);
//                            moveAnim.setFillAfter(true);
//                            moveAnim.setRepeatMode(Animation.REVERSE);
//                            moveAnim.setRepeatCount(1);
//
//                            //Setting the Animation for the Image
//                            imageView.startAnimation(moveAnim);
//
//                            //Calling the BarVisualizer
//                            visualizer();
//                        }
//                    }
//                });
//            }
//        };

//        RequestBody requestBody = new FormBody.Builder()
//                .build();
//
//        Request request = new Request.Builder()
//                .url("http://172.30.1.41:5000/Musicplay")
//                .post(requestBody)
//                .build();
//
//        OkHttpClient client = new OkHttpClient();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.e("즐겨찾기 실패", "즐겨찾기 *선택* 실패!");
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                Log.e("응답 성공", "응답성공");
//                try {
//                    int count;
//                    InputStream in = response.body().byteStream();
//                    reader = new BufferedInputStream(in);
//                    file = new File(Environment.getExternalStorageDirectory() , "_file.mp4");
//                    OutputStream out = new FileOutputStream(file);
//
//                    byte[] data = new byte[1024];
//                    long total = 0;
//                    while ((count = in.read(data)) != -1) {
//                        total += count;
//                        out.write(data, 0, count);
//                    }
//                    out.flush();
//                    out.close();
//                    in.close();
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//                Uri uri = Uri.fromFile(file);
//                Intent intent = new Intent();
//                String music = intent.getStringExtra("music");
////                int seqNum = intent.getIntExtra("seqNum",0);
////                MediaPlayer.create(getApplicationContext(), uri);
//                Message msg = handler1.obtainMessage();
//                handler1.sendMessage(msg);
//
//            }
//        });

        //Implementing OnClickListener for Play and Pause Button

//        btnPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                //Checking playing any songs or not
//                if (mediaPlayer.isPlaying()) {
//
//                    //setting the play icon
//                    btnPlay.setBackgroundResource(R.drawable.play_song_icon);
//
//                    //Pausing the current media
//                    mediaPlayer.pause();
//
//                } else {
//
//                    //Setting the pause icon
//                    btnPlay.setBackgroundResource(R.drawable.pause_song_icon);
//
//                    //Starting the media player
//                    mediaPlayer.start();
//
//                    //Creating the Animation
//                    TranslateAnimation moveAnim = new TranslateAnimation(-25, 25, -25, 25);
//                    moveAnim.setInterpolator(new AccelerateInterpolator());
//                    moveAnim.setDuration(600);
//                    moveAnim.setFillEnabled(true);
//                    moveAnim.setFillAfter(true);
//                    moveAnim.setRepeatMode(Animation.REVERSE);
//                    moveAnim.setRepeatCount(1);
//
//                    //Setting the Animation for the Image
//                    imageView.startAnimation(moveAnim);
//
//                    //Calling the BarVisualizer
//                    visualizer();
//                }
//            }
//        });

        //Performing the Button Click Operation after the completion of song
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btnNext.performClick();
            }
        });


        //Implementing OnclickListener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Stoping the currently playing media
//                mediaPlayer.stop();
//                mediaPlayer.release();


                //Getting the Current media position and incrementing it by 1
//                position = ((position + 1) % mySongs.size());

                //Extracting the media path form the array List
//                Uri uri1 = Uri.parse(mySongs.get(position).toString());


                //Setting the path to the media player
//                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri1);


                //Getting the current song Name and setting it to TextView
//                songName = mySongs.get(position).getName();
//                txtSongName.setText(songName);

                //Starting the Media Player
//                mediaPlayer.start();

                //Extracting the duration of the song
//                songEndTime();
//
//
//                //Animating the ImageView
//                startAnimation(imageView, 360f);
//                visualizer();


            }
        });


        //Implementing the OnClick Listener
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Stoping the media Player
//                mediaPlayer.stop();
//                mediaPlayer.release();


                //getting the  current media position and decrementing it by one
//                position = ((position - 1) % mySongs.size());
//                if (position < 0)
//                    position = mySongs.size() - 1;

                //Extracting the media path
//                Uri uri1 = Uri.parse(mySongs.get(position).toString());

                //Setting the media path to the media player
//                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri1);
//                songName = mySongs.get(position).getName();
//                txtSongName.setText(songName);
//                mediaPlayer.start();
//                songEndTime();


                //Animating the imageView
//                startAnimation(imageView, -360f);
//                visualizer();

            }

        });


        //Implementing the fastForward
        btnFastForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mediaPlayer.isPlaying()) {
//
//                    //Getting the current position and adding 10sec to it
//                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
//
//                }
            }
        });


        //Implementing the FastBackWard
        btnFastBackWard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mediaPlayer.isPlaying()) {

                    //Getting the curent Position of the song and decrease 10sec from it
//                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);

//                }
            }
        });

    }


    //Method to create animation for imageView
    public void startAnimation(View view, Float degree) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, degree);
        objectAnimator.setDuration(1000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator);
        animatorSet.start();

    }


    //Preparing the Time format for setting to textView
    public String createDuration(int duration) {

        String time = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        time = time + min + ":";

        if (sec < 10) {

            time += "0";

        }
        time += sec;
        return time;

    }


    //Setting the Visualizer
    public void visualizer() {

        //Extracting and Setting the current media id to the Visualizer
        int audioSessionId = mediaPlayer.getAudioSessionId();
        Log.e("이거테스트 - ",audioSessionId+"");
        if (audioSessionId != -3) {
            barVisualizer.setAudioSessionId(audioSessionId);
        }
    }

    //Method To extract the duration of the current media and setting it to TextView
    public void songEndTime() {
        String endTime = createDuration(mediaPlayer.getDuration());
        txtSongEnd.setText(endTime);
    }


    //Releasing the BarVisualizer on Closing the Activity
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (barVisualizer != null)
            barVisualizer.release();
    }
}