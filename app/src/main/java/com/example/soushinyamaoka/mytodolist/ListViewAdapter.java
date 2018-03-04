package com.example.soushinyamaoka.mytodolist;

/**
 * Created by SoushinYamaoka on 2018/03/03.
 */

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 品名一覧 (ListViewAdapter×ArrayAdapter)画面に関連するクラス
 * SelectSheetProduct
 */
public class ListViewAdapter extends AppCompatActivity {

    private DBAdapter dbAdapter;                // DBAdapter
    private ArrayAdapter<String> adapter;       // ArrayAdapter
    private ArrayList<String> items;            // ArrayList
    private ListView ListView;        // ListView
    private Button mButton02AllDelete;          // 全削除ボタン

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        dbAdapter = new DBAdapter(this);
        dbAdapter.openDB();     // DBの読み込み(読み書きの方)

        findViews();            // 各部品の結び付け

        // ArrayListを生成
        items = new ArrayList<>();

        // DBのデータを取得
        String[] columns = {DBAdapter.COL_TASK};     // DBのカラム：タスク
        //String[] projection = {FeedEntry._ID,FeedEntry.COLUMN_NAME_TITLE,FeedEntry.COLUMN_NAME_SUBTITLE};
        Cursor c = dbAdapter.getDB(columns);
        //「Cursor」.たくさんのレコードを管理する働きをもつクラス。レコード群の１つ１つを選択したり移動したりすることができ、選択されたレコードの値を取り出し処理します。

        if (c.moveToFirst()) {//「moveToFirst」というメソッドで１番最初のレコードを選択しています。もし選択できない（つまりレコードがない）場合はfalseが返されるので、trueの場合にのみ処理を実行させる
            do {//保管されている値は「getString」で取り出す。
                items.add(c.getString(0));//getString(1)→１番のフィールドからString値を取り出します。
                Log.d("取得したCursor:", c.getString(0));//ログに出力(Cusorの中身を見る)
            } while (c.moveToNext());//「moveToLast」「moveToPrevious」「moveToNext」これらを使うことで、自由にレコードの選択位置を移動できる
        }
        c.close();
        dbAdapter.closeDB();    // DBを閉じる

        // ArrayAdapterのコンストラクタ
        // 第1引数：Context
        // 第2引数：リソースとして登録されたTextViewに対するリソースID 今回は元々用意されている定義済みのレイアウトファイルのID
        // 第3引数：一覧させたいデータの配列
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        ListView.setAdapter(adapter);     //ListViewにアダプターをセット(=表示)

        // ArrayAdapterに対してListViewのリスト(items)の更新
        adapter.notifyDataSetChanged();

        // 全削除ボタン押下時処理
        mButton02AllDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!items.isEmpty()) {
                    dbAdapter.openDB();     // DBの読み込み(読み書きの方)
                    dbAdapter.allDelete();  // DBのレコードを全削除
                    dbAdapter.closeDB();    // DBを閉じる

                    //ArrayAdapterに対してListViewのリスト(items)の更新
                    adapter.clear();
                    adapter.addAll(items);
                    adapter.notifyDataSetChanged(); // // Viewの更新

                }else {
                    Toast.makeText(ListViewAdapter.this, "登録されているデータがありません。", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 各部品の結びつけ処理
     * findViews()
     */
    private void findViews() {
        ListView = (ListView) findViewById(R.id.listView);       // 品名一覧用のListView
        //mButton02AllDelete = (Button) findViewById(R.id.listView);         // 全削除ボタン
    }

}
