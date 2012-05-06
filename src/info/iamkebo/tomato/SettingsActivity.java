package info.iamkebo.tomato;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * 设置页面
 * @author kebo
 *
 */

public class SettingsActivity extends Activity {

	private SeekBar workSeekBar;
	private SeekBar breakSeekBar;
	private TextView workTextView;
	private TextView breakTextView;
	private CheckBox soundCK;
	private CheckBox vibrateCK;
	private CheckBox lightCK;
	
	
	
	@Override 
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		workSeekBar = (SeekBar)findViewById(R.id.work_seekBar);
		breakSeekBar = (SeekBar)findViewById(R.id.break_seekBar);
		workTextView = (TextView)findViewById(R.id.work_text);
		breakTextView = (TextView)findViewById(R.id.break_text);
		soundCK = (CheckBox)findViewById(R.id.sound_ck);
		vibrateCK = (CheckBox)findViewById(R.id.vibrate_ck);
		lightCK = (CheckBox)findViewById(R.id.light_ck);
		
		workSeekBar.setOnSeekBarChangeListener(workSeekBarChangeListener);
		breakSeekBar.setOnSeekBarChangeListener(breakSeekBarChangeListener);
		soundCK.setOnCheckedChangeListener(soundCheckChangeListener);
		vibrateCK.setOnCheckedChangeListener(vibrateCheckedChangeListener);
		lightCK.setOnCheckedChangeListener(lightCheckedChangeListener);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		SharedPreferences sharedPref = getSharedPreferences(Constants.MY_SHARED_PREFERENCE, MODE_PRIVATE);
		int workProgress = sharedPref.getInt(Constants.SHARED_WORK_FRAGMENT, 30);
		int breakProgress = sharedPref.getInt(Constants.SHARED_BREAK_FRAGMENT, 5);
		boolean sound = sharedPref.getBoolean(Constants.SHARED_SOUND, true);
		boolean vibrate = sharedPref.getBoolean(Constants.SHARED_VIBRATE, false);
		boolean light = sharedPref.getBoolean(Constants.SHARED_LIGHT, false);
		
		workSeekBar.setProgress(workProgress);
		breakSeekBar.setProgress(breakProgress);
		soundCK.setChecked(sound);
		vibrateCK.setChecked(vibrate);
		lightCK.setChecked(light);
		
		workTextView.setText(workSeekBar.getProgress() + getString(R.string.time_unit));
		breakTextView.setText(breakSeekBar.getProgress() + getString(R.string.time_unit));
		
	}
	
	private OnSeekBarChangeListener workSeekBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			workTextView.setText(progress + getString(R.string.time_unit));
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			//save to sharedpreferences
			SharedPreferences sharedPref = 
					getSharedPreferences(Constants.MY_SHARED_PREFERENCE, MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putInt(Constants.SHARED_WORK_FRAGMENT, seekBar.getProgress());
			editor.commit();
		}
		
		
	};
	
	private OnSeekBarChangeListener breakSeekBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			breakTextView.setText(progress + getString(R.string.time_unit));
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			//save to sharedpreferences
			SharedPreferences sharedPref = 
					getSharedPreferences(Constants.MY_SHARED_PREFERENCE, MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putInt(Constants.SHARED_BREAK_FRAGMENT, seekBar.getProgress());
			editor.commit();
		}
		
		
	};
	
	private OnCheckedChangeListener soundCheckChangeListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			//save to sharedpreferences
			SharedPreferences sharedPref = 
					getSharedPreferences(Constants.MY_SHARED_PREFERENCE, MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(Constants.SHARED_SOUND, isChecked);
			editor.commit();
		}
	};
	
	private OnCheckedChangeListener vibrateCheckedChangeListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			//save to sharedpreferences
			SharedPreferences sharedPref = 
					getSharedPreferences(Constants.MY_SHARED_PREFERENCE, MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(Constants.SHARED_VIBRATE, isChecked);
			editor.commit();
		}
	};
	
	private OnCheckedChangeListener lightCheckedChangeListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			//save to sharedpreferences
			SharedPreferences sharedPref = 
					getSharedPreferences(Constants.MY_SHARED_PREFERENCE, MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(Constants.SHARED_LIGHT, isChecked);
			editor.commit();
		}
	};
}
