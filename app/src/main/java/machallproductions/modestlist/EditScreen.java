package machallproductions.modestlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

public class EditScreen extends AppCompatActivity {

    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(getIntent().getStringExtra("text"));
        editText.setSelection(editText.length());
        editText.requestFocus();
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra("returnText", editText.getText().toString());
                data.putExtra("position", getIntent().getIntExtra("position", -1));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }


}
