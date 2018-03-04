package com.example.soushinyamaoka.mytodolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    public Button editButton;

    //content_mainはActivityに表示する具体的なコンテンツとなるレイアウトファイル
    @Override
    protected void onCreate(Bundle savedInstanceState) {//破棄されたActivityの復元
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.listview);

        editText = findViewById(R.id.editText);
        editButton = findViewById(R.id.editButton);

        init();//初期値設定

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DBに登録
                saveList();
                editlist();
            }
        });
    }

    private void init() {
        editText.setText("");
    }

    private void saveList(){
        String task = editText.getText().toString();

        if(editText.equals("")){
            Toast.makeText(MainActivity.this, "※入力して下さい。", Toast.LENGTH_SHORT).show();
        }else{
            // DBへの登録処理
            DBAdapter dbAdapter = new DBAdapter(this);
            dbAdapter.openDB();                                         // DBの読み書き
            dbAdapter.saveDB(task);   // DBに登録
            dbAdapter.closeDB();                                        // DBを閉じる
        }
    }
    private void editlist(){
        ListViewAdapter lvAdapter = new ListViewAdapter();
        lvAdapter.ListViewAdapter();
    }
}
