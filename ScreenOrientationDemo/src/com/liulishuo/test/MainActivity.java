package com.liulishuo.test;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.liulishuo.R;
/**
 * 当屏幕方向锁定时，仍能判断屏幕方向的变化
 * 
 * @author Strong
 * @version 2013-12-31
 *
 */
public class MainActivity extends Activity {

	private SensorManager sensorManager;
	private Sensor orientationSensor;
	private MySensorListener mSensorListener;
	
	private boolean screenOrientation;
	private final boolean SCREEN_VERTICAL = false;
	private final boolean SCREEN_HORIZONTAL = true;
	
	private static final String TAG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 获取传感器管理者
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		// 获取方向传感器
		orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		// 绑定监听器
		mSensorListener = new MySensorListener();
		sensorManager.registerListener(mSensorListener, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
		
	}
	
	
	
	@Override
	protected void onPause() {
		super.onPause();
		// 当activity暂停时，解除对传感器的监听
		sensorManager.unregisterListener(mSensorListener);
		
	}



	class MySensorListener implements SensorEventListener{

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			
			// 方位角：x轴在水平面上的投影与正北方向的夹角 0/360＝正北，90=正东，180=正南，270=正西
			float azimuthX = event.values[0];
			// 倾斜角：y轴与水平面的夹角 范围：-180~180，后翘前倾为正
			float inclinationY = event.values[1];
			// 旋转角：z轴与水平面的夹角 范围：－90~90，右翘左倾为正
			float rotationZ = event.values[2];
			
			Log.e(TAG, "X = " + azimuthX);
			Log.e(TAG, "Y = " + inclinationY);
			Log.e(TAG, "Z = " + rotationZ);
			
            // 如果之前为竖屏并且当前为横屏
			if(screenOrientation == SCREEN_VERTICAL && (rotationZ > 45 || rotationZ < -45) && (inclinationY > -45 && inclinationY < 45)){
				
				screenOrientation = SCREEN_HORIZONTAL;
				Toast.makeText(getApplicationContext(), "HorinzontalScreen", Toast.LENGTH_SHORT).show();
				Log.e(TAG, "HorinzontalScreen");
			// 如果之前为横屏并且当前为竖屏
			}else if(screenOrientation == SCREEN_HORIZONTAL && (rotationZ > -45 && rotationZ < 45) && (inclinationY > 45 || inclinationY < -45)){
				
				screenOrientation = SCREEN_VERTICAL;
				Toast.makeText(getApplicationContext(), "VerticalScreen", Toast.LENGTH_SHORT).show();
				Log.e(TAG, "VerticalScreen");
				
			}
			
		}
		
	}

}
