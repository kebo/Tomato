package info.iamkebo.tomato;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TomatoActivity extends Activity {
	
	private Button btn_work;
	private Button btn_break;
	private Button btn_cancel;
	private TextView txt_time;
	private CountDownTimer mCountDownTimer;
	private RelativeLayout start_layout;
	private RelativeLayout cancel_layout;
	private Vibrator mVibrator;
	
	private SharedPreferences sharedPref;
	private String minuteUnit;
	private String secondUnit;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        start_layout = (RelativeLayout)findViewById(R.id.start_layout);
        cancel_layout = (RelativeLayout)findViewById(R.id.cancel_layout);
        
        btn_work = (Button)findViewById(R.id.work_btn);
        btn_break = (Button)findViewById(R.id.break_btn);
        btn_cancel = (Button)findViewById(R.id.cancel_btn);
        txt_time = (TextView)findViewById(R.id.time_text);
        
        btn_work.setOnClickListener(workListener);
        btn_break.setOnClickListener(breakListener);
        btn_cancel.setOnClickListener(cancelListener);
        
        mVibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        sharedPref = getSharedPreferences(Constants.MY_SHARED_PREFERENCE, MODE_PRIVATE);
        
        minuteUnit = getString(R.string.minute_unit);
        secondUnit = getString(R.string.second_unit);
    }
    
    OnClickListener workListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			start_layout.setVisibility(View.GONE);
			cancel_layout.setVisibility(View.VISIBLE);
			int workProgress = sharedPref.getInt(Constants.SHARED_WORK_FRAGMENT, 30);
			mCountDownTimer = new CountDownTimer(workProgress*60*1000, 1000){

				@Override
				public void onFinish() {
					txt_time.setText(getString(R.string.finished));
					// show work & break buttons
					start_layout.setVisibility(View.VISIBLE);
					cancel_layout.setVisibility(View.GONE);
					
					if(sharedPref.getBoolean(Constants.SHARED_SOUND, true)){
						playSounds();
					}
					if(sharedPref.getBoolean(Constants.SHARED_VIBRATE, false)){
						mVibrator.vibrate(2000);
					}
					
				}

				@Override
				public void onTick(long millisUntilFinished) {
					long minute = (millisUntilFinished / 1000) / 60;
					long second = (millisUntilFinished / 1000) % 60;
					
					txt_time.setText(minute + minuteUnit + second + secondUnit);
				}
				
			};
			mCountDownTimer.start();
		}
	};
    
	OnClickListener breakListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			start_layout.setVisibility(View.GONE);
			cancel_layout.setVisibility(View.VISIBLE);
			int breakProgress = sharedPref.getInt(Constants.SHARED_BREAK_FRAGMENT, 5);
			mCountDownTimer = new CountDownTimer(breakProgress*60*1000, 1000){

				@Override
				public void onFinish() {
					txt_time.setText(getString(R.string.finished));
					// show work & break buttons
					start_layout.setVisibility(View.VISIBLE);
					cancel_layout.setVisibility(View.GONE);
					
					if(sharedPref.getBoolean(Constants.SHARED_SOUND, true)){
						playSounds();
					}
					if(sharedPref.getBoolean(Constants.SHARED_VIBRATE, false)){
						mVibrator.vibrate(2000);
					}
				}

				@Override
				public void onTick(long millisUntilFinished) {
					long minute = (millisUntilFinished / 1000) / 60;
					long second = (millisUntilFinished / 1000) % 60;
					
					txt_time.setText(minute + minuteUnit + second + secondUnit);
				}
				
			};
			mCountDownTimer.start();
			
		}
	};
	
	OnClickListener cancelListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			mCountDownTimer.cancel();
			txt_time.setText(getString(R.string.cancel));
			start_layout.setVisibility(View.VISIBLE);
			cancel_layout.setVisibility(View.GONE);
		}
		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0, Constants.MENU_SETTINGS, 1, "settings");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		case Constants.MENU_SETTINGS:
			startActivity(new Intent(TomatoActivity.this, SettingsActivity.class));
			return true;
		}
		
		return false;
	}

	private void playSounds(){
		MediaPlayer mp = new MediaPlayer();
		mp.reset();
		try{
			mp.setDataSource(TomatoActivity.this, 
					RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
			mp.prepare();
			mp.start();
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
}