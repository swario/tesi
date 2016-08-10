package com.example.cristian.everysale.AsyncronousTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.cristian.everysale.Activity.CalendarTryActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class asincGoogleCalendarInsert extends AsyncTask<String, Void, List<String>> {

    private com.google.api.services.calendar.Calendar mService = null;
    private Exception mLastError = null;
    private ProgressDialog mProgress;

    private Context context;
    private String insertionId;
    private String insertionTitle;
    private String insertionDate;
    private String insertionAddress;

    public asincGoogleCalendarInsert(GoogleAccountCredential credential, Context cont) {
        this.context = cont;
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.calendar.Calendar.Builder(transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();
    }

    /**
     * Background task to call Google Calendar API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected List<String> doInBackground(String... params) {
        this.insertionId = params[0];
        this.insertionTitle = params[1];
        this.insertionDate = params[2];
        this.insertionAddress = params[3];
        EventDateTime dateStart = new EventDateTime().setDate(new DateTime("2016-08-28T09:00:00-07:00"));
        EventDateTime dateEnd = new EventDateTime().setDate(new DateTime("2016-08-28T17:00:00-07:00"));
        try {
            /*Event event = new Event();
            event.setSummary(this.insertionTitle)
                .setStart(dateStart)
                .setEnd(dateEnd)
                .setLocation(this.insertionAddress);
            String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
            event.setRecurrence(Arrays.asList(recurrence));

            EventAttendee[] attendees = new EventAttendee[] {
                    new EventAttendee().setEmail("lpage@example.com"),
                    new EventAttendee().setEmail("sbrin@example.com"),
            };
            event.setAttendees(Arrays.asList(attendees));

            EventReminder[] reminderOverrides = new EventReminder[] {
                    new EventReminder().setMethod("email").setMinutes(24 * 60),
                    new EventReminder().setMethod("popup").setMinutes(10),
            };
            Event.Reminders reminders = new Event.Reminders()
                    .setUseDefault(false)
                    .setOverrides(Arrays.asList(reminderOverrides));
            event.setReminders(reminders);
            return event;*/
            Log.e("EverySale", "1");
            return getDataFromApi();
        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            Log.e("EverySale", "2 " + e.getMessage());
            return null;
        }
    }

    /**
     * Fetch a list of the next 10 events from the primary calendar.
     * @return List of Strings describing returned events.
     * @throws IOException
     */
    private List<String> getDataFromApi() throws IOException {
        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        List<String> eventStrings = new ArrayList<String>();
        Events events = mService.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        for (Event event : items) {
            DateTime start = event.getStart().getDateTime();
            if (start == null) {
                // All-day events don't have start times, so just use
                // the start date.
                start = event.getStart().getDate();
            }
            eventStrings.add(String.format("%s (%s)", event.getSummary(), start));
        }
        return eventStrings;
    }


    @Override
    protected void onPreExecute() {
        //mOutputText.setText("");
        mProgress = new ProgressDialog(this.context);
        mProgress.setMessage("Calling Google Calendar API ...");
        mProgress.show();
    }

    @Override
    protected void onPostExecute(List<String> output) {
        /*if(output!=null && mService!=null){
            try {
                mService.events().insert("primary", output).execute();
                Log.e("EverySale", "Evento inserito");
            }
            catch (Exception e){
                Log.e("EverySale", "Evento non inserito " + e.getMessage());
            }
        }*/
            Log.e("EverySale", "1 " + output);
        mProgress.hide();
        /*if (output == null || output.size() == 0) {
            mOutputText.setText("No results returned.");
        } else {
            output.add(0, "Data retrieved using the Google Calendar API:");
            mOutputText.setText(TextUtils.join("\n", output));
        }*/
    }

    @Override
    protected void onCancelled() {
        mProgress.hide();
        if (mLastError != null) {
            if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                //showGooglePlayServicesAvailabilityErrorDialog(((GooglePlayServicesAvailabilityIOException) mLastError).getConnectionStatusCode());
            } else if (mLastError instanceof UserRecoverableAuthIOException) {
                //startActivityForResult(((UserRecoverableAuthIOException) mLastError).getIntent(), CalendarTryActivity.REQUEST_AUTHORIZATION);
            } else {
                //mOutputText.setText("The following error occurred:\n" + mLastError.getMessage());
            }
        } else {
            //mOutputText.setText("Request cancelled.");
        }
    }
}
