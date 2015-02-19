package kobe.things.activity;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import kobe.things.Constants;
import kobe.things.R;
import kobe.things.game.BluetoothGameService;

public class GameActivity extends ActionBarActivity
    implements SerfFragment.OnFragmentInteractionListener, CzarFragment.OnFragmentInteractionListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //If app is being restored from previous state,
        //don't do anything. avoid overlapping fragments
        if(savedInstanceState != null){
            return;
        }
        BluetoothGameService bgs =  BluetoothGameService.getBluetoothGameService();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //set fragment view based Extras bundled with intent
        switch(getIntent().getIntExtra(Constants.EXTRAS_GAME_CHOICE_ID, -1)){
            case R.id.host_game_button:
                transaction.add(R.id.container, new CzarFragment());
                Log.d("GAME CHOICE", "Host Game");
                break;
            case R.id.join_game_button:
                transaction.add(R.id.container, new SerfFragment());
                Log.d("GAME CHOICE", "Join Game");
                break;
            default:

                Log.d("GAME CHOICE", "No selection");
                break;
        }

        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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

    private void usurpRole(Fragment frag){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
    }

    // Implement abstract methods from fragment activities
    // Change container view to match players role

    public void onPlayerSwitchToCzar(){
        usurpRole(new CzarFragment());
    }

    public void onPlayerSwitchToSerf(){
        usurpRole(new SerfFragment());
    }

}
