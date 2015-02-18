package kobe.things.activity;


import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.cast.CastMediaControlIntent;

import kobe.things.Constants;
import kobe.things.R;


public class MainActivity extends ActionBarActivity {

    MediaRouter mMediaRouter;
    MediaRouteSelector mMediaRouteSelector;
    //Intent request codes
    private static final int REQUEST_ENABLE_BT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        TextView hostGameButton = (TextView)findViewById(R.id.host_game_button);
        TextView joinGameButton = (TextView)findViewById(R.id.join_game_button);
        View.OnClickListener startGameListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //create intent
                //put v.getId() in as extra (will be either host or join game
                if(!BluetoothAdapter.getDefaultAdapter().isEnabled()){
                    startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
                }
                else {
                //bundle extras start activity GameActivity
                    Intent i = new Intent(MainActivity.this, GameActivity.class);
                    i.putExtra(Constants.EXTRAS_GAME_CHOICE_ID, v.getId());
                    startActivity(i);
                }

            }
        };
        hostGameButton.setOnClickListener(startGameListener);
        joinGameButton.setOnClickListener(startGameListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        mMediaRouter = MediaRouter.getInstance(getApplicationContext());
        mMediaRouteSelector = new MediaRouteSelector.Builder()
//                .addControlCategory(CastMediaControlIntent.categoryForCast("APPLICATION_ID")).build();
                  .addControlCategory(CastMediaControlIntent.CATEGORY_CAST).build();
        MediaRouteActionProvider mediaRouteActionProvider =
                (MediaRouteActionProvider) MenuItemCompat.getActionProvider(mediaRouteMenuItem);
        mediaRouteActionProvider.setRouteSelector(mMediaRouteSelector);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
