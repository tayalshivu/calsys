package com.example.calsys1;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BUTTON=0;
    private static final int REQUEST_DISCOVERABLE_BUTTON=1;
    Button mOff,mOn,mPair,mDis;
    TextView tv1,tv2;

    BluetoothAdapter mBluetooth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mOff=findViewById(R.id.button1);
        mOn = findViewById(R.id.button);
        mPair=findViewById(R.id.button2);
        mDis=findViewById(R.id.button3);
        tv1=findViewById(R.id.tv);
        tv2=findViewById(R.id.tv1);

        mBluetooth=BluetoothAdapter.getDefaultAdapter();

        BluetoothManager b = (BluetoothManager) getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);


        if (mBluetooth == null){
            tv1.setText("BLUETOOTH IS NOT AVAILABLE");
        }
        else{
            tv1.setText("BLUETOOTH IS AVAILABLE");
        }

        mOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetooth.isEnabled()){
                    Toast.makeText(HomeActivity.this,"Turning On Bluetooth.........",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,REQUEST_ENABLE_BUTTON);
                }
                else{
                    Toast.makeText(HomeActivity.this,"Bluetooth is already on.........",Toast.LENGTH_LONG).show();
                }
            }
        });

        mDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetooth.isDiscovering()){
                    Toast.makeText(HomeActivity.this,"Making your device Discoverable",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent,REQUEST_DISCOVERABLE_BUTTON);
                }
            }
        });

        mOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetooth.isEnabled()){
                    mBluetooth.disable();
                    Toast.makeText(HomeActivity.this,"Turning Bluetooth Off.........",Toast.LENGTH_LONG).show();
                    tv2.setText(" ");
                }
                else{
                    Toast.makeText(HomeActivity.this,"Bluetooth is already Turned Off",Toast.LENGTH_LONG).show();
                }
            }
        });

        mPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetooth.isEnabled()){
                    tv2.setText("Paired Devices");
                    Set<BluetoothDevice> devices = mBluetooth.getBondedDevices();
                    for (BluetoothDevice device : devices){
                        tv2.append("\nDevice : "+device.getName()+ "," + device);
                    }
                }
                else{
                    Toast.makeText(HomeActivity.this,"Turn Bluetooth on to see Paired Device",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
