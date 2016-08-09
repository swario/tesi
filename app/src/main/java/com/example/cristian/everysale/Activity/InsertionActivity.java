package com.example.cristian.everysale.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.cristian.everysale.AsyncronousTasks.Senders.asincAddFavorite;
import com.example.cristian.everysale.AsyncronousTasks.Senders.asincDeleteInsertion;
import com.example.cristian.everysale.AsyncronousTasks.Senders.asincRemoveFavorite;
import com.example.cristian.everysale.Fragments.Other.InsertionDisplayFragment;
import com.example.cristian.everysale.Interfaces.Deleter;
import com.example.cristian.everysale.Listeners.MenuListener;
import com.example.cristian.everysale.R;

public class InsertionActivity extends AppCompatActivity implements Deleter, DialogInterface.OnClickListener {

    private long insertionId;
    private long userId;

    private Toolbar toolbar;
    public Menu menu;
    private SharedPreferences savedValues;
    private Boolean isFavorite = false;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Bundle bundle;

    private Integer EasterEgg=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
        setContentView(R.layout.activity_insertion);
        Intent intent = getIntent();
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        this.userId = savedValues.getLong("userId", 1);
        if(intent != null){
            this.insertionId = intent.getLongExtra("insertionId", 0);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        MenuListener menuListener = new MenuListener(this, navigationView, drawerLayout);
        drawerLayout.addDrawerListener(menuListener);
        navigationView.setNavigationItemSelectedListener(menuListener);
    }

    public long getInsertionId(){
        return this.insertionId;
    }

    //Favorite
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.insertion_menu_action_bar, menu);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new InsertionDisplayFragment(), "displayFragment")
                .commit();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.add_to_favorite_button:
                if(this.isFavorite){
                    new asincRemoveFavorite(this).execute(userId, this.insertionId);
                    break;
                }
                else{
                    new asincAddFavorite(this).execute(userId ,this.insertionId);
                }
                break;

            case R.id.rate_insertion_button:

                View box=getSupportFragmentManager().findFragmentByTag("displayFragment").getView().findViewById(R.id.feedback_box);
                if(box.getVisibility()==View.GONE){
                    box.setVisibility(View.VISIBLE);
                }
                else if(box.getVisibility()==View.VISIBLE){
                    box.setVisibility(View.GONE);
                }
                break;

            case R.id.remove_item_button:
                new AlertDialog.Builder(this)
                        .setMessage(R.string.insertion_delete_warning)
                        .setPositiveButton(R.string.confim, this)
                        .setNegativeButton(R.string.deny, this)
                        .show();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setFavorite(boolean isFavorite){
        this.isFavorite = isFavorite;
        if(isFavorite){
            menu.getItem(1).setTitle("Remove Favorite");
            menu.getItem(1).setIcon(R.drawable.ic_star_24dp);
            EasterEgg++;
        }
        else{
            menu.getItem(1).setTitle("Add to Favorite");
            menu.getItem(1).setIcon(R.drawable.ic_star_outline_24dp);
            EasterEgg++;
        }
        if(EasterEgg==7){
            Toast.makeText(getApplication(), "Stai calmo!!!" , Toast.LENGTH_LONG).show();
            EasterEgg=0;
        }
    }

    public void OnDeletion(String message){
        if(message.contains("good")){
            this.finish();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which==DialogInterface.BUTTON_POSITIVE){
            new asincDeleteInsertion(this, this).execute(this.userId, this.insertionId);
        }
        else if(which==DialogInterface.BUTTON_NEGATIVE){
            dialog.cancel();
        }
    }
}
