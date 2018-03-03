package com.example.soushinyamaoka.mytodolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    public Button editbutton;
    public DBAdapter dba;

    //content_mainはActivityに表示する具体的なコンテンツとなるレイアウトファイル
    @Override
    protected void onCreate(Bundle savedInstanceState) {//破棄されたActivityの復元
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.listview);

        editText = findViewById(R.id.editText);
        editbutton = findViewById(R.id.editButton);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DBへの書き込み
                String wtext = editText.getText().toString();//書き込まれた内容(getText)をstrに格納
                dba.saveDB(wtext);
                Cursor rtext = dba.getDB();

                setContentView(listView);

                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(this, R.layout.listview, rtext);

                listView.setAdapter(arrayAdapter);
            }

        });
    }
}
