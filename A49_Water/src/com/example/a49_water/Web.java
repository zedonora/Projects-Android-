package com.example.a49_water;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.VideoView;

public class Web extends Activity{
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.web);
      
      final VideoView vv = (VideoView)findViewById(R.id.videoView1);
      Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+ R.raw.opening_video);
      vv.setVideoURI(uri);
      vv.requestFocus();
      
      vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
          @Override
          public void onPrepared(MediaPlayer mp) {
              vv.start();
          }
      });
      
      Button b = (Button)findViewById(R.id.button1);
      b.setOnTouchListener(new OnTouchListener() {
         
         @Override
         public boolean onTouch(View v, MotionEvent event) {

            if(event.getAction() == MotionEvent.ACTION_DOWN){
               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(intent);
               finish();
            }
            return false;
         }
      });
      
   }
}