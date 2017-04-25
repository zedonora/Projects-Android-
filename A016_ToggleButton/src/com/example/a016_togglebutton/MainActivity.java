package com.example.a016_togglebutton;

//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.ToggleButton;
//
//public class MainActivity extends Activity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		
//		final CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);
//		final ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton1);
//		final Switch sw = (Switch) findViewById(R.id.switch1);
//		final TextView tv = (TextView) findViewById(R.id.textView1);
//		Button b = (Button) findViewById(R.id.button1);
////		cb.isChecked(); // 현재 상태 값을 읽어오기
////		tb.isChecked(); 
////		sw.isChecked();
//		
//		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				Toast.makeText(getApplicationContext(),
//						buttonView.getText().toString()+(isChecked?" 체크됨":" 해제됨"),
//						Toast.LENGTH_SHORT).show();
//				
//			}
//		});
//		tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				Toast.makeText(getApplicationContext(),
//						buttonView.getText().toString()+(isChecked?" 체크됨":" 해제됨"),
//						Toast.LENGTH_SHORT).show();
//			}
//		});
//		sw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				Toast.makeText(getApplicationContext(),
//						buttonView.getText().toString()+(isChecked?" 체크됨":" 해제됨"),
//						Toast.LENGTH_SHORT).show();
//			}
//		});
//		b.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				tv.setText(cb.getText().toString()+": "+(cb.isChecked()?" 체크됨":" 해제됨")
//						+","+tb.getTag().toString()+tb.getText().toString()+
//						": "+(tb.isChecked()?" 체크됨":" 해제됨")
//						+","+sw.getText().toString()+": "+(sw.isChecked()?" 체크됨":" 해제됨"));
//				
//			}
//		});
//	} // end of onCreate
//} // end of class

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
   TextView tv;
   Toast t;
   Switch sw;
   ToggleButton tb;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      CheckBox cb = (CheckBox)findViewById(R.id.checkBox1);
      tb = (ToggleButton)findViewById(R.id.toggleButton1);
      sw = (Switch)findViewById(R.id.switch1);
      tv = (TextView)findViewById(R.id.textView1);
      /*
      cb.isChecked();
      tb.isChecked();
      sw.isChecked();*/
      //cb.setOnCheckedChangeListener(listener);
      OnCheckedChangeListener me = new OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String result="";
            if(buttonView==tb){
               t = Toast.makeText(getApplicationContext(),tb.getTag().toString()+buttonView.getText().toString(), Toast.LENGTH_LONG);
               t.show();
               result+= tb.getTag().toString()+buttonView.getText().toString();
               tv.setText(result);
            }else{
               t=Toast.makeText(getApplicationContext(), buttonView.getText().toString()+(isChecked?"불켜짐":"불꺼짐"), Toast.LENGTH_LONG);
               t.show();
               result+=buttonView.getText().toString()+(isChecked?"불켜짐":"불꺼짐");
               tv.setText(result);}
         }
      };
      cb.setOnCheckedChangeListener(me);
      tb.setOnCheckedChangeListener(me);
      sw.setOnCheckedChangeListener(me);
   
   }//end of void onCreate
   @Override
   protected void onPause() {
      super.onPause();
      if(t!=null)
      t.cancel();
   }
   /*@Override
   public void onBackPressed() {
      super.onBackPressed();
      t.cancel();
   }
   */
}//end of class MainActivity