package com.example.cristian.everysale.Activity;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.annotation.NonNull;
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
import com.example.cristian.everysale.AsyncronousTasks.asincGoogleCalendarInsert;
import com.example.cristian.everysale.Fragments.Other.InsertionDisplayFragment;
import com.example.cristian.everysale.Interfaces.Deleter;
import com.example.cristian.everysale.Listeners.MenuListener;
import com.example.cristian.everysale.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class InsertionActivity extends AppCompatActivity implements Deleter, DialogInterface.OnClickListener, EasyPermissions.PermissionCallbacks {

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

    GoogleAccountCredential mCredential;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { CalendarScopes.CALENDAR };

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
        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(getApplicationContext(), Arrays.asList(SCOPES)).setBackOff(new ExponentialBackOff());
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
        //long userId = savedValues.getLong("userId", 1);
        switch(id){
            case R.id.add_to_favorite_button:
                if(this.isFavorite){
                    new asincRemoveFavorite(this).execute(userId, this.insertionId);
                    deleteEvent();
                    break;
                }
                else{
                    new asincAddFavorite(this).execute(userId, this.insertionId);
                    //insertEvent();
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

    public void insertEvent(){
        Log.e("EverySale", "Inserimento in corso...");
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2016, 8, 19, 0, 00);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                .putExtra(Events._ID, 208)
                .putExtra(Events.TITLE, "Yoga")
                .putExtra(Events.DESCRIPTION, "Group class")
                .putExtra(Events.EVENT_LOCATION, "The gym")
                .putExtra(Events.EVENT_COLOR, "red")
                .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
        startActivity(intent);
        Log.e("EverySale", "Inserito");
    }

    // Non è realizzabile
    public void deleteEvent(){
        long eventID = 208;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2016, 6, 19, 0, 00);
        Uri uri = ContentUris.withAppendedId(Events.CONTENT_URI, eventID);
        Intent intent = new Intent(Intent.ACTION_EDIT)
                .setData(uri)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        startActivity(intent);
    }
    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void getResultsFromApi() {
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (! isDeviceOnline()) {
            //mOutputText.setText("No network connection available.");
        } else {
            new asincGoogleCalendarInsert(mCredential, this).execute(String.valueOf(this.insertionId), "Titolo", "2016-08-12", "Piazza Della Vittoria, Genova");
        }
    }

    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    //mOutputText.setText("This app requires Google Play Services. Please install " + "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     * @param requestCode The request code passed in
     *     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(this, connectionStatusCode, REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }
}
