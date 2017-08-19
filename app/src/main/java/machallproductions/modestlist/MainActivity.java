package machallproductions.modestlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditDialogFragment.OnFragmentInteractionListener {

    ArrayList<String> toDoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE_EDIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                toDoItems.remove(i);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                FragmentManager fm = getSupportFragmentManager();
                String text = lvItems.getItemAtPosition(position).toString();
                EditDialogFragment editDialog = EditDialogFragment.newInstance(text, position);
                editDialog.show(fm, "editText_field");
                //Intent intent = new Intent(MainActivity.this, EditScreen.class);
                //intent.putExtra("returnText", lvItems.getItemAtPosition(position).toString());
                //intent.putExtra("position", position);
                //startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
        int position = data.getIntExtra("position", -1);
        String txt = data.getStringExtra("returnText");
        toDoItems.set(position, txt); //error handling could be better
        aToDoAdapter.notifyDataSetChanged();
        writeItems();
    }

    public void populateArrayItems() {
        toDoItems = new ArrayList<String>();
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "toDo.txt");
        try {
            toDoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {

        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "toDo.txt");
        try {
            FileUtils.writeLines(file, toDoItems);
        } catch (IOException e) {

        }
    }

    public void onFragmentInteraction(String text, int position) {
        toDoItems.set(position, text); //error handling could be better
        aToDoAdapter.notifyDataSetChanged();
        writeItems();
    }

}
