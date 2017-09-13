package gr.hua.android.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class DescriptionHandler extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descriptionhandler);

        Intent intent = this.getIntent();
        String description = intent.getExtras().getString("description");

        //Creat TextView to display description with links so user can click them if he/she wants
        TextView mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setText(Html.fromHtml(description));
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());

        Button mButton = (Button)findViewById(R.id.button2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When button is clicked then bring him to previous activity
                finish();
            }
        });

    }
}
