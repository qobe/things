package kobe.things.game;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

/**
 * Created by mkobe on 2/17/2015.
 */
public class BluetoothGameService {
    BluetoothAdapter mBluetoothAdapter;
    //host
    //slave

    private static BluetoothGameService mBluetoothGameService = null;
    protected BluetoothGameService(){}
    public static BluetoothGameService getBluetoothGameService(){
        if(mBluetoothGameService == null){
            mBluetoothGameService = new BluetoothGameService();
        }
        return mBluetoothGameService;
    }

    public void setHost(BluetoothDevice host){

    }

    private void ensureDiscoverable(){
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            //startActivity(discoverableIntent);
        }
    }
}
