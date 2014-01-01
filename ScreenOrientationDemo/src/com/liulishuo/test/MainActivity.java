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
 * ����Ļ��������ʱ�������ж���Ļ����ı仯
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
		
		// ��ȡ������������
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		// ��ȡ���򴫸���
		orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		// �󶨼�����
		mSensorListener = new MySensorListener();
		sensorManager.registerListener(mSensorListener, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
		
	}
	
	
	
	@Override
	protected void onPause() {
		super.onPause();
		// ��activity��ͣʱ������Դ������ļ���
		sensorManager.unregisterListener(mSensorListener);
		
	}



	class MySensorListener implements SensorEventListener{

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			
			// ��λ�ǣ�x����ˮƽ���ϵ�ͶӰ����������ļн� 0/360��������90=������180=���ϣ�270=����
			float azimuthX = event.values[0];
			// ��б�ǣ�y����ˮƽ��ļн� ��Χ��-180~180������ǰ��Ϊ��
			float inclinationY = event.values[1];
			// ��ת�ǣ�z����ˮƽ��ļн� ��Χ����90~90����������Ϊ��
			float rotationZ = event.values[2];
			
			Log.e(TAG, "X = " + azimuthX);
			Log.e(TAG, "Y = " + inclinationY);
			Log.e(TAG, "Z = " + rotationZ);
			
            // ���֮ǰΪ�������ҵ�ǰΪ����
			if(screenOrientation == SCREEN_VERTICAL && (rotationZ > 45 || rotationZ < -45) && (inclinationY > -45 && inclinationY < 45)){
				
				screenOrientation = SCREEN_HORIZONTAL;
				Toast.makeText(getApplicationContext(), "HorinzontalScreen", Toast.LENGTH_SHORT).show();
				Log.e(TAG, "HorinzontalScreen");
			// ���֮ǰΪ�������ҵ�ǰΪ����
			}else if(screenOrientation == SCREEN_HORIZONTAL && (rotationZ > -45 && rotationZ < 45) && (inclinationY > 45 || inclinationY < -45)){
				
				screenOrientation = SCREEN_VERTICAL;
				Toast.makeText(getApplicationContext(), "VerticalScreen", Toast.LENGTH_SHORT).show();
				Log.e(TAG, "VerticalScreen");
				
			}
			
		}
		
	}

}
