package club.shitting.number2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.view.View;

/**
 * Created by Calvin Nguyen on 4/5/2015.
 */
public class AddActivity extends FragmentActivity implements View.OnClickListener {

    private Button addCancel = null;
    private Button addFinished = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        addCancel = (Button) findViewById(R.id.addCancel);
        addCancel.setOnClickListener(this);

        addFinished = (Button) findViewById(R.id.addFinished);
        addFinished.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == addCancel) {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }

        if (v == addFinished) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
