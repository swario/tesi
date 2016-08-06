package com.example.cristian.everysale.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.cristian.everysale.BaseClasses.Feedback;
import com.example.cristian.everysale.BaseClasses.Insertion;
import com.example.cristian.everysale.Fragments.InsertionDisplayFragment;
import com.example.cristian.everysale.Fragments.evaluateInsertionFragment;
import com.example.cristian.everysale.Fragments.loginFragment;
import com.example.cristian.everysale.Listeners.MenuListener;
import com.example.cristian.everysale.R;
import com.example.cristian.everysale.AsyncronousTasks.asincAddFavorite;
import com.example.cristian.everysale.AsyncronousTasks.asincDownloadInsertion;
import com.example.cristian.everysale.AsyncronousTasks.asincImageDownload;
import com.example.cristian.everysale.AsyncronousTasks.asincRemoveFavorite;

import java.util.ArrayList;
import java.util.HashMap;

public class InsertionActivity extends AppCompatActivity  {

    private long insertionId;

    private Toolbar toolbar;
    public Menu menu;
    private SharedPreferences savedValues;
    private Boolean isFavorite = false;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;
        setContentView(R.layout.activity_insertion);
        Intent intent = getIntent();
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
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
                long userId = savedValues.getLong("userId", 1);
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
        }
        else{
            menu.getItem(1).setTitle("Add to Favorite");
            menu.getItem(1).setIcon(R.drawable.ic_star_outline_24dp);
        }
    }
}
