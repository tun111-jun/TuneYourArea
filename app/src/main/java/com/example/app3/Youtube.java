package com.example.app3;




import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Reader;


import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import java.io.BufferedReader;
import java.io.Reader;

import org.json.JSONException;
import org.json.JSONTokener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Youtube extends YouTubeBaseActivity {
    YouTubePlayerView playerView;
    YouTubePlayer player;
    LinearLayout ll;
    //ImageButton temp=null;
    int temp_vid=-1;
    int flag=0;
    private ListView listview=null;
    //ImageButton imgbutton;
    //TextView textView;
    ImageView imageView1;
    private final long finishtimed=1500;
    ArrayAdapter<String> adapter;
    private long presstime=0;
    ArrayList<String> listitem_temp;

    ListView listview1;
    int n=0;
    AlertDialog.Builder oDialog;
    //API key AIzaSyDvycuGNf8DbrWxzRWkwQFsyREzkOju4TI
    private static String API_KEY = "AIzaSyDvycuGNf8DbrWxzRWkwQFsyREzkOju4TI";
    //?????? ?????????, ?????? videoId??? ?????? ????????? ???????????? ???????????? url?????? ???????????? ???????????? ??????.
    private static String[] videoId = new String[]{"p1hrdozsPcY","wJZwd8ZyD8Q","0vvCe4EHtus","H0NFTIhxOpc","zaIsVnmwdqg","U5TTMeIadME","ygTZZpVkmKg","iIWoYaJRryw","jU0V9AhFniI"};
    //private static ArrayList<String> videoId = new ArrayList<>(Arrays.asList("p1hrdozsPcY", "wJZwd8ZyD8Q"));
    //"p1hrdozsPcY", "wJZwd8ZyD8Q"


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPlayer();
        oDialog = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog);

        //ImageButton imgbutton = (ImageButton)findViewById(R.id.button);
        ArrayList<String> listitem=new ArrayList<String>();
        listitem_temp=new ArrayList<String>();
        //imageView1=findViewById(R.id.imageView1);


        //Glide.with(this).load("https://img.youtube.com/vi/zaIsVnmwdqg/default.jpg").into(imageView1);
        //Glide.with(this).load("https://img.youtube.com/vi/"+videoId[i]+"/"+"default.jpg").into(imageView1);
        //String url1 ="https://img.youtube.com/vi/"+ id+ "/" + "default.jpg"

        int num = videoId.length;
        //?????? ???????????? list??? ??????
        for (int i = 0; i< num; i++) {
            //Glide.with(this).load("https://img.youtube.com/vi/"+videoId[num]+"/"+"default.jpg").into(imageView1);
            listitem.add(videoId[i]);
            listitem_temp.add(videoId[i]);

        }




        //???????????? ??????, ?????? ????????? ????????? ?????? ?????? ???????????? ?????? ????????? ????????? ????????? ???
        Button button = findViewById(R.id.playAll);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                n=0;
                if(temp_vid!=-1){
                    listitem_temp.set(temp_vid,videoId[temp_vid]);
                    adapter.notifyDataSetChanged();
                }

                temp_vid=n;
                listitem_temp.set(n,videoId[n]+" - Playing...");
                adapter.notifyDataSetChanged();
                playVideo(n);

            }

        });

        //?????????????????? Back ??????
        Button button3 = findViewById(R.id.Back);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                long tempTime=System.currentTimeMillis();
                long intervalTime=tempTime-presstime;

                if(0<=intervalTime&&finishtimed>=intervalTime){
                    finish();
                    overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                }
                else{
                    presstime=tempTime;
                    Toast.makeText(getApplicationContext(), "?????? ??? ???????????? ??????????????? ???????????????", Toast.LENGTH_SHORT).show();
                }

            }

        });

        ////?????????, ??????????????? ????????? ??? ?????? playlistlayout??? ???????????? ?????? ???????????? ????????? ???????????? ????????? ??????
        adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listitem_temp){
            private ArrayList<String> items=listitem;

            @Override
            public View getView(int position, View convertView, ViewGroup parent)

            {

                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.playlist, null);
                }

                // ImageView ????????????


//                Glide.with(getApplicationContext()).load("https://img.youtube.com/vi/"+listitem_temp.get(position)+"/"+"default.jpg").into(imageView);
//                imageView.setClipToOutline(true);
                 //??????????????? ???????????? ???????????? ????????????.(?????????)
                System.out.println("where : "+position);
                ImageView imageView = (ImageView)v.findViewById(R.id.imageView);

                    for (int i = 0; i < num; i++) {
                        if (videoId[i].equals(listitem.get(position))){
                            System.out.println("TO : "+i);
                            Glide.with(getApplicationContext()).load("https://img.youtube.com/vi/" + videoId[i] + "/" + "default.jpg").into(imageView);
                            imageView.setClipToOutline(true);
                        }
                    }



                //????????? ?????? ???
                imageView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //playVideo(i);
                        n=position;
                        //player.cueVideo(items.get(position));
                        if(temp_vid!=-1){
                            listitem_temp.set(temp_vid,videoId[temp_vid]);
                            adapter.notifyDataSetChanged();
                        }
                        temp_vid=position;
                        listitem_temp.set(n,videoId[n]+" - Playing...");
                        adapter.notifyDataSetChanged();
                        player.loadVideo(items.get(position));
                        //player.loadVideo(listitem_temp.get(position));
                        //Toast.makeText(this, "text", Toast.LENGTH_SHORT).show();
                    }
                });


                //????????????, ????????????
                TextView textView = (TextView)v.findViewById(R.id.textView);
                //ImageButton imgbutton = (ImageButton)v.findViewById(R.id.button);
                textView.setText(listitem_temp.get(position));
                final String text = listitem_temp.get(position);

                //LinearLayout border=(LinearLayout)findViewById(R.id.border);

                //????????????, ???????????? ?????? ???
                textView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {

                        //playVideo(i);
//                        if(temp!=null)
//                            temp.setVisibility(View.VISIBLE);
                        //textView.setText(items.get(position)+" - Playing");
                        //imgbutton.setVisibility(View.INVISIBLE);
                        //temp=(ImageButton)imgbutton;
                        n=position;
                        System.out.println("Here::"+position);
                        //border.setBackgroundResource(R.drawable.roundborder2);
                        //player.cueVideo(items.get(position));
                        if(temp_vid!=-1){
                            listitem_temp.set(temp_vid,videoId[temp_vid]);
                            adapter.notifyDataSetChanged();
                        }
                        temp_vid=position;
                        listitem_temp.set(n,videoId[n]+" - Playing...");
                        adapter.notifyDataSetChanged();
                        player.loadVideo(items.get(position));

                        //playVideo(n);


                        //Toast.makeText(this, "text", Toast.LENGTH_SHORT).show();
                    }

                });

                ///////???????????? -> ???????????? ?????? ???????????? ?????? ????????? ?????? ????????????
//                ImageButton imgbutton = (ImageButton)v.findViewById(R.id.button);
//
//                imgbutton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //playVideo(i);
//                        n=position;
//
//
//                        button.setVisibility(View.INVISIBLE);
//                        //player.cueVideo(items.get(position));
//                        player.loadVideo(items.get(position));
//
//
//                        //Toast.makeText(this, "text", Toast.LENGTH_SHORT).show();
//                    }
//                });

                return v;
//                View view = super.getView(position, convertView, parent);
//
//                TextView tv = (TextView) view.findViewById(android.R.id.text1);
//
//                tv.setTextColor(Color.WHITE);

                //return view;

            }
        };
        //
        //ListView listview1=findViewById(R.id.listView1);
        listview1=findViewById(R.id.listView1);
        listview1.setAdapter(adapter);

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             // ????????????????????? ???????????? ????????????, ?????? ???????????? ???, ????????? ??????, ????????? ?????????
                                             @Override
                                             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                                 //????????????????????? ????????? ????????? ??????????????? ??? ????????? ??? ?????? ??????
                                                 //Toast.makeText(getApplicationContext(),listitem.get(i).toString() + " ??????.",Toast.LENGTH_SHORT).show();
                                                 n=i;
                                                 if(temp_vid!=-1){
                                                     listitem_temp.set(temp_vid,videoId[temp_vid]);
                                                     adapter.notifyDataSetChanged();
                                                 }
                                                 temp_vid=i;
                                                 listitem_temp.set(n,videoId[n]+" - Playing...");
                                                 adapter.notifyDataSetChanged();
                                                 System.out.println("Adapter: "+adapterView.getItemAtPosition(n).toString());

                                                 playVideo(n);

                                                 //adapter.notifyDataSetChanged();
                                             }
        });
    }
    @Override
    public void onBackPressed() {
        long tempTime=System.currentTimeMillis();
        long intervalTime=tempTime-presstime;

        if(0<=intervalTime&&finishtimed>=intervalTime){
            finish();
            overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
        }
        else{
            presstime=tempTime;
            Toast.makeText(getApplicationContext(), "?????? ??? ???????????? ??????????????? ???????????????", Toast.LENGTH_SHORT).show();
        }

    }




    //https://www.youtube.com/channel/UC-OcDPFxfY9Hhdf5P9zq7-A
    public void initPlayer(){
        //????????????
        playerView = findViewById(R.id.playerView);

        /* YouTubePlayerView ????????? ?????? */
        playerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b){
                player = youTubePlayer;
                player.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
                    @Override
                    public void onPlaying() {
                        //?????? ????????? ???????????? ??? 3?????? ????????? ???????????? ?????? ????????? ??? ?????? playing ?????? ????????? ????????????
                        if(temp_vid!=-1){
                            listitem_temp.set(temp_vid,videoId[temp_vid]);
                            adapter.notifyDataSetChanged();
                        }
                        System.out.println("onPlaying");
                        listitem_temp.set(n,videoId[n]+" - Playing...");
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onPaused() {
//                        if(temp_vid!=-1){
//                            listitem_temp.set(temp_vid,videoId[temp_vid]);
//                            adapter.notifyDataSetChanged();
//                        }
                        System.out.println("onPaused");
                        listitem_temp.set(n,videoId[n]+" - Paused");
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onStopped() {
                        System.out.println("onStopped");
                    }

                    @Override
                    public void onBuffering(boolean b) {
                        System.out.println("onBuffering");
                    }

                    @Override
                    public void onSeekTo(int i) {

                    }
                });


                player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                        System.out.println("onLoading");

                    }


                    @Override
                    public void onLoaded(String id) {

                        System.out.println("onLoaded");
//                        ImageButton imgbutton1 = (ImageButton)findViewById(R.id.button);
//
//                        imgbutton1.setVisibility(View.INVISIBLE);
                        player.play();  // ???????????? ?????????????????? ????????????



                    }

                    @Override
                    public void onAdStarted() {
                        System.out.println("onAdStarted");
                    }

                    @Override
                    public void onVideoStarted() {
                        System.out.println("OvVideoStarted");
                    }

                    @Override
                    public void onVideoEnded() {

                        if(n>=videoId.length-1){

                            listitem_temp.set(n,videoId[n]+" - Paused");
                            adapter.notifyDataSetChanged();

                            //???????????? ????????? ?????? ???????????? pause or ???????????? ??????.
                            oDialog.setMessage("playback is over. Would you like to play from the beginning?")
                                    .setTitle("Tune Your Area")
                                    .setPositiveButton("Stop", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            player.pause();
                                            Log.i("Dialog", "Stop");
                                            Toast.makeText(getApplicationContext(), "Thanks", Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .setNeutralButton("Play", new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            System.out.println("Here31::"+n);
                                            System.out.println("Video_ID: "+videoId.length);
                                            listitem_temp.set(n,videoId[n]);
                                            adapter.notifyDataSetChanged();
                                            n=0;
                                            temp_vid=n;
                                            System.out.println("Onclick : "+n+temp_vid);
                                            listitem_temp.set(n,videoId[n]+" - Playing...");
                                            adapter.notifyDataSetChanged();
                                            System.out.println("Onclick2 : "+videoId[0]+listitem_temp.get(0));
                                            System.out.println("Onclick3 : "+n+temp_vid);
                                            playVideo(n);
                                            //player.loadVideo(videoId[num]);
                                        }
                                    })
                                    .setCancelable(false) // ??????????????? ???????????? ????????? ????????? ??????.


                                    .show();


                        }
                        else{
                            //?????? ?????? ????????? ???????????? ?????? ???????????? ?????????
                            System.out.println("Here1::"+n);
                            System.out.println("Video_ID: "+videoId.length);
                            listitem_temp.set(n,videoId[n]);
                            adapter.notifyDataSetChanged();
                            n=n+1;
//                        if(temp!=null)
//                            temp.setVisibility(View.VISIBLE);
                            temp_vid=n;
                            listitem_temp.set(n,videoId[n]+" - Playing...");
                            adapter.notifyDataSetChanged();
                            playVideo(n);
                        }


                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {}
                });
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult){}
        });
    }

    public void playVideo(int num){
        if(player != null){
            if(player.isPlaying())
                System.out.println("isPlaying");
                player.pause();
        }



//                        if(temp!=null)
//                            temp.setVisibility(View.VISIBLE);
        System.out.println("Video_ID: "+videoId.length);

            player.loadVideo(videoId[num]);






        //Glide.with(this).load("https://img.youtube.com/vi/"+videoId[num]+"/"+"default.jpg").into(imageView1);
        //player.loadVideos(videoId);

        //????????????
        //player.cueVideo(videoId[num]);

        //loadVideo??? ????????? ????????? ??????


    }

}



