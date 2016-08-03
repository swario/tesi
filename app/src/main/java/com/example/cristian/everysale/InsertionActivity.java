package com.example.cristian.everysale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.cristian.everysale.BaseClasses.Insertion;
import com.example.cristian.everysale.asincronousTasks.asincDownloadInsertion;

public class InsertionActivity extends AppCompatActivity {

    private Insertion insertion;
    private long insertionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            this.insertionId = intent.getLongExtra("insertionId", 0);
        }
        Toast.makeText(this, String.valueOf(insertionId), Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_insertion);

        new asincDownloadInsertion(this).execute(this.insertionId);
    }

    public void setUpInsertion(Insertion insertion){

        this.insertion = insertion;
        setUpLayout();

        Log.e("Debug", this.insertion.getCity());
        Log.e("Debug", this.insertion.getAddress());
    }

    public void setUpLayout(){

    }
}
