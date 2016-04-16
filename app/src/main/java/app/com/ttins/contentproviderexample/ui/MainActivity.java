package app.com.ttins.contentproviderexample.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import app.com.ttins.contentproviderexample.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.button) Button mButton;
    @Bind(R.id.textView_first_name) TextView mFirstNameTextView;
    @Bind(R.id.textView_last_name) TextView mLastNameTextView;
    @Bind(R.id.textView_gender) TextView mGenderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }



}
