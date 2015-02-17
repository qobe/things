package kobe.things.activity;


import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.media.MediaRouteSelector;
import android.support.v7.media.MediaRouter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.cast.CastMediaControlIntent;

import kobe.things.R;


public class MainActivity extends ActionBarActivity {

    MediaRouter mMediaRouter;
    MediaRouteSelector mMediaRouteSelector;

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
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        boolean result = false;
                        switch(item.getItemId()){
                            case R.id.chromecast_game:
                                result = true;
                                break;
                            case R.id.local_game:
                                result = true;
                                break;
                            default:
                                result = false;
                        }
                        return result;
                    }
                });
                popup.getMenuInflater().inflate(R.menu.menu_new_game, popup.getMenu());
                popup.show();

                //bundle extras start activity GameActivity

            }
        };
        hostGameButton.setOnClickListener(startGameListener);
        joinGameButton.setOnClickListener(startGameListener);
    }

    public void castSelected(){

        //MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        mMediaRouter = MediaRouter.getInstance(getApplicationContext());
        mMediaRouteSelector = new MediaRouteSelector.Builder()
                .addControlCategory(CastMediaControlIntent.categoryForCast("APPLICATION_ID")).build();
        //MediaRouteActionProvider mediaRouteActionProvider =
       //         (MediaRouteActionProvider) MenuItemCompat.getActionProvider(mediaRouteMenuItem);
       // mediaRouteActionProvider.setRouteSelector(mMediaRouteSelector);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
