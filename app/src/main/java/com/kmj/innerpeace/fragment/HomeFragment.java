package com.kmj.innerpeace.fragment;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.kmj.innerpeace.BTDeviceListAdapter;
import com.kmj.innerpeace.BluetoothDeviceDemoActivity;
import com.kmj.innerpeace.Data.EEGData;
import com.kmj.innerpeace.Data.KindOfMusic;
import com.kmj.innerpeace.Data.SendData;
import com.kmj.innerpeace.R;
import com.kmj.innerpeace.activity.MainActivity;
import com.kmj.innerpeace.retrofit.NetworkHelper;
import com.loopeer.shadow.ShadowView;
import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.DataType.MindDataType.FilterType;
import com.neurosky.connection.EEGPower;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    MainActivity mainActivity;
    ImageView connect;

    EEGData data;
    SendData sendData;
    TextView name;
    ArrayList<Integer> delta;
    ArrayList<Integer> theta;
    ArrayList<Integer> lowAlpha;
    ArrayList<Integer> highAlpha;
    ArrayList<Integer> lowBeta;
    ArrayList<Integer> highBeta;
    ArrayList<Integer> lowGamma;
    ArrayList<Integer> middleGamma;
    private int badPacketCount = 0;
    private static final String TAG = BluetoothDeviceDemoActivity.class.getSimpleName();
    private TgStreamReader tgStreamReader;
    int cnt = 0;
    // TODO connection sdk
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private String address = "C3:3C:01:04:0B:40";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    TextView text1,text2,text3,textConnecting;
    ShadowView shadowView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        text1=v.findViewById(R.id.home_text1);
        text2=v.findViewById(R.id.home_text2);
        text3=v.findViewById(R.id.home_text3);
        textConnecting=v.findViewById(R.id.home_connectingtext);
        shadowView=v.findViewById(R.id.home_box);

        name=v.findViewById(R.id.home_name);
        name.setText(MainActivity.name+"님");
        mainActivity = (MainActivity) getActivity();


        setup();
        connect = v.findViewById(R.id.home_connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // TODO
                    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                        Toast.makeText(mainActivity,"블루투스를 켜주세요", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    else{
                        theta.clear();
                        lowAlpha.clear();
                        highAlpha.clear();
                        lowBeta.clear();
                        highBeta.clear();
                        lowGamma.clear();
                        middleGamma.clear();
                        delta.clear();
                        badPacketCount = 0;
                        start();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.i(TAG, "error:" + e.getMessage());

                }

            }
        });

        return v;
    }


    void setup() {
        Logger.addLogAdapter(new AndroidLogAdapter());
        delta = new ArrayList<>();
        theta = new ArrayList<>();
        lowAlpha = new ArrayList<>();
        highAlpha = new ArrayList<>();
        lowBeta = new ArrayList<>();
        highBeta = new ArrayList<>();
        lowGamma = new ArrayList<>();
        middleGamma = new ArrayList<>();
    }

    public void scanDevice() {

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        setUpDeviceListView();
        //register the receiver for scanning
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mainActivity.registerReceiver(mReceiver, filter);

        mBluetoothAdapter.startDiscovery();
    }

    private void start() {
        if (address != null) {
            BluetoothDevice bd = mBluetoothAdapter.getRemoteDevice(address);
            createStreamReader(bd);

            tgStreamReader.connectAndStart();
        }
    }

    public void stop() {
        if (tgStreamReader != null) {
            tgStreamReader.stop();
            tgStreamReader.close();//if there is not stop cmd, please call close() or the data will accumulate
            tgStreamReader = null;
        }
    }

    private int currentState = 0;
    private TgStreamHandler callback = new TgStreamHandler() {

        @Override
        public void onStatesChanged(int connectionStates) {
            // TODO Auto-generated method stub
            Log.d(TAG, "connectionStates change to: " + connectionStates);
            currentState = connectionStates;
            switch (connectionStates) {
                case ConnectionStates.STATE_CONNECTED:
                    //sensor.start();
                    //Toast.makeText(mainActivity, "Connected", Toast.LENGTH_SHORT).show();
                    break;
                case ConnectionStates.STATE_WORKING:
                    //byte[] cmd = new byte[1];
                    //cmd[0] = 's';
                    //tgStreamReader.sendCommandtoDevice(cmd);
                    LinkDetectedHandler.sendEmptyMessageDelayed(1234, 5000); //do not
                    break;
                case ConnectionStates.STATE_GET_DATA_TIME_OUT:
                    Toast.makeText(mainActivity, "기기와의 연결이 끊겼습니다.", Toast.LENGTH_SHORT).show();
                    textConnecting.setVisibility(View.GONE);
                    text1.setVisibility(View.VISIBLE);
                    text2.setVisibility(View.VISIBLE);
                    text3.setVisibility(View.VISIBLE);
                    shadowView.setVisibility(View.VISIBLE);
                    cnt=0;
                    //get data time out
                    break;
                case ConnectionStates.STATE_COMPLETE:
                    //read file complete
                    break;
                case ConnectionStates.STATE_STOPPED:
                    textConnecting.setVisibility(View.GONE);
                    text1.setVisibility(View.VISIBLE);
                    text2.setVisibility(View.VISIBLE);
                    text3.setVisibility(View.VISIBLE);
                    shadowView.setVisibility(View.VISIBLE);
                    cnt=0;
                    break;
                case ConnectionStates.STATE_DISCONNECTED:
                    textConnecting.setVisibility(View.GONE);
                    text1.setVisibility(View.VISIBLE);
                    text2.setVisibility(View.VISIBLE);
                    text3.setVisibility(View.VISIBLE);
                    shadowView.setVisibility(View.VISIBLE);
                    cnt=0;
                    break;
                case ConnectionStates.STATE_ERROR:
                    textConnecting.setVisibility(View.GONE);
                    text1.setVisibility(View.VISIBLE);
                    text2.setVisibility(View.VISIBLE);
                    text3.setVisibility(View.VISIBLE);
                    shadowView.setVisibility(View.VISIBLE);
                    cnt=0;
                    Log.d(TAG, "Connect error, Please try again!");
                    break;


                case ConnectionStates.STATE_FAILED:
                    Toast.makeText(mainActivity, "기기와 연결 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                    break;
            }
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = MSG_UPDATE_STATE;
            msg.arg1 = connectionStates;
            LinkDetectedHandler.sendMessage(msg);


        }

        @Override
        public void onRecordFail(int a) {
            // TODO Auto-generated method stub
            Log.e(TAG, "onRecordFail: " + a);

        }

        @Override
        public void onChecksumFail(byte[] payload, int length, int checksum) {
            // TODO Auto-generated method stub

            badPacketCount++;
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = MSG_UPDATE_BAD_PACKET;
            msg.arg1 = badPacketCount;
            LinkDetectedHandler.sendMessage(msg);

        }

        @Override
        public void onDataReceived(int datatype, int data, Object obj) {
            // TODO Auto-generated method stub
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = datatype;
            msg.arg1 = data;
            msg.obj = obj;
            LinkDetectedHandler.sendMessage(msg);
            //Log.i(TAG,"onDataReceived");
        }

    };

    private boolean isPressing = false;
    private static final int MSG_UPDATE_BAD_PACKET = 1001;
    private static final int MSG_UPDATE_STATE = 1002;
    private static final int MSG_CONNECT = 1003;
    private boolean isReadFilter = false;


    private Handler LinkDetectedHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1234:
                    tgStreamReader.MWM15_getFilterType();
                    isReadFilter = true;
                    Log.d(TAG, "MWM15_getFilterType ");

                    break;
                case 1235:
                    tgStreamReader.MWM15_setFilterType(FilterType.FILTER_60HZ);
                    Log.d(TAG, "MWM15_setFilter  60HZ");
                    LinkDetectedHandler.sendEmptyMessageDelayed(1237, 1000); //asdf
                    break;
                case 1236:
                    tgStreamReader.MWM15_setFilterType(FilterType.FILTER_50HZ);
                    Log.d(TAG, "MWM15_SetFilter 50HZ ");
                    LinkDetectedHandler.sendEmptyMessageDelayed(1237, 1000);
                    break;

                case 1237:
                    tgStreamReader.MWM15_getFilterType();
                    Log.d(TAG, "MWM15_getFilterType ");

                    break;

                case MindDataType.CODE_FILTER_TYPE:
                    Log.d(TAG, "CODE_FILTER_TYPE: " + msg.arg1 + "  isReadFilter: " + isReadFilter);
                    if (isReadFilter) {
                        isReadFilter = false;
                        if (msg.arg1 == FilterType.FILTER_50HZ.getValue()) {
                            LinkDetectedHandler.sendEmptyMessageDelayed(1235, 1000);
                        } else if (msg.arg1 == FilterType.FILTER_60HZ.getValue()) {
                            LinkDetectedHandler.sendEmptyMessageDelayed(1236, 1000);
                        } else {
                            Log.e(TAG, "Error filter type");
                        }
                    }

                    break;


                case MindDataType.CODE_RAW:

                    break;
                case MindDataType.CODE_MEDITATION:
                    Log.d(TAG, "HeadDataType.CODE_MEDITATION " + msg.arg1);
                    break;
                case MindDataType.CODE_ATTENTION:
                    Log.e("ang", "CODE_ATTENTION " + msg.arg1);

                    break;
                case MindDataType.CODE_EEGPOWER:
                    EEGPower power = (EEGPower) msg.obj;
                    if (power.isValidate()) {
                        //Logger.e(String.valueOf(power.delta));
                        //here

                        textConnecting.setVisibility(View.VISIBLE);
                        text1.setVisibility(View.GONE);
                        text2.setVisibility(View.GONE);
                        text3.setVisibility(View.GONE);
                        shadowView.setVisibility(View.GONE);
                        cnt++;
                        textConnecting.setText("결과를 측정 중 입니다.  "+String.valueOf(30-cnt)+" left ...");
                        delta.add(power.delta);
                        theta.add(power.theta);
                        lowAlpha.add(power.lowAlpha);
                        highAlpha.add(power.highAlpha);
                        lowBeta.add(power.lowBeta);
                        highBeta.add(power.highBeta);
                        lowGamma.add(power.lowGamma);
                        middleGamma.add(power.middleGamma);


                        Logger.e(String.valueOf(cnt));
                        if (cnt == 30) {

                            if (tgStreamReader != null) {
                                textConnecting.setVisibility(View.GONE);
                                text1.setVisibility(View.VISIBLE);
                                text2.setVisibility(View.VISIBLE);
                                text3.setVisibility(View.VISIBLE);
                                shadowView.setVisibility(View.VISIBLE);
                                tgStreamReader.stop();
                            }
                            data = new EEGData(delta, theta, lowAlpha, highAlpha, lowBeta, highBeta, lowGamma, middleGamma);
                            sendData=new SendData(data);
                            Logger.e(String.valueOf(sendData.data.delta.size()));

                            NetworkHelper.getInstance().sendEEG(sendData).enqueue(new retrofit2.Callback<KindOfMusic>() {
                                @Override
                                public void onResponse(Call<KindOfMusic> call, Response<KindOfMusic> response) {
                                    if(response.isSuccessful() && response!=null){
                                        Logger.e(String.valueOf(response.body().getData()));
                                        theta.clear();
                                        lowAlpha.clear();
                                        highAlpha.clear();
                                        lowBeta.clear();
                                        highBeta.clear();
                                        lowGamma.clear();
                                        middleGamma.clear();
                                        delta.clear();
                                    }
                                    else{
                                        Logger.e(response.toString());
                                        theta.clear();
                                        lowAlpha.clear();
                                        highAlpha.clear();
                                        lowBeta.clear();
                                        highBeta.clear();
                                        lowGamma.clear();
                                        middleGamma.clear();
                                        delta.clear();
                                    }
                                }

                                @Override
                                public void onFailure(Call<KindOfMusic> call, Throwable t) {

                                }
                            });
                            Log.e("ohmygod","gimotti");
                            cnt=0;

                        }
                    }
                    break;
                case MindDataType.CODE_POOR_SIGNAL://
                    int poorSignal = msg.arg1;
                    Log.d(TAG, "poorSignal:" + poorSignal);


                    break;
                case MSG_UPDATE_BAD_PACKET:


                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private ListView list_select;
    private BTDeviceListAdapter deviceListApapter = null;
    private Dialog selectDialog;

    private void setUpDeviceListView() {

        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View view = inflater.inflate(R.layout.dialog_select_device, null);
        list_select = (ListView) view.findViewById(R.id.list_select);
        selectDialog = new Dialog(mainActivity, R.style.dialog1);
        selectDialog.setContentView(view);
        //List device dialog

        deviceListApapter = new BTDeviceListAdapter(mainActivity);
        list_select.setAdapter(deviceListApapter);
        list_select.setOnItemClickListener(selectDeviceItemClickListener);

        selectDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                // TODO Auto-generated method stub
                Log.e(TAG, "onCancel called!");
                mainActivity.unregisterReceiver(mReceiver);
            }

        });

        selectDialog.show();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            deviceListApapter.addDevice(device);
        }
        deviceListApapter.notifyDataSetChanged();
    }

    //Select device operation
    private AdapterView.OnItemClickListener selectDeviceItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            Log.d(TAG, "Rico ####  list_select onItemClick     ");
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            //unregister receiver
            mainActivity.unregisterReceiver(mReceiver);

            mBluetoothDevice = deviceListApapter.getDevice(arg2);
            selectDialog.dismiss();
            selectDialog = null;

            Log.d(TAG, "onItemClick name: " + mBluetoothDevice.getName() + " , address: " + mBluetoothDevice.getAddress());
            address = mBluetoothDevice.getAddress().toString();
            Logger.e(address);
            //ger remote device
            BluetoothDevice remoteDevice = mBluetoothAdapter.getRemoteDevice(mBluetoothDevice.getAddress().toString());

            //bind and connect
            //bindToDevice(remoteDevice); // create bond works unstable on Samsung S5
            //showToast("pairing ...",Toast.LENGTH_SHORT);

            tgStreamReader = createStreamReader(remoteDevice);
            tgStreamReader.connectAndStart();
            start();

        }

    };

    /**
     * If the TgStreamReader is created, just change the bluetooth
     * else create TgStreamReader, set data receiver, TgStreamHandler and parser
     *
     * @param
     * @return TgStreamReader
     */
    public TgStreamReader createStreamReader(BluetoothDevice bd) {

        if (tgStreamReader == null) {
            // Example of constructor public TgStreamReader(BluetoothDevice mBluetoothDevice,TgStreamHandler tgStreamHandler)
            tgStreamReader = new TgStreamReader(bd, callback);
            tgStreamReader.startLog();
        } else {
            // (1) Demo of changeBluetoothDevice
            tgStreamReader.changeBluetoothDevice(bd);

            // (4) Demo of setTgStreamHandler, you can change the data handler by this function
            tgStreamReader.setTgStreamHandler(callback);
        }
        return tgStreamReader;
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "mReceiver()");
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG, "mReceiver found device: " + device.getName());

                // update to UI
                deviceListApapter.addDevice(device);
                deviceListApapter.notifyDataSetChanged();

            }
        }
    };

}