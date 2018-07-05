package com.example.hilrudt.testurmmap;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Openmap extends AppCompatActivity {


    public ListView listMap;

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openmap);

        listMap = (findViewById(R.id.listMap));


        try {

            String path = Environment.getExternalStorageDirectory().toString()+getString(R.string.pathMaps);
            Log.d("Files", "Path: " + path);
            File directory = new File(path);
            File[] files = directory.listFiles();
            Log.d("Files", "Size: "+ files.length);
            for (int i = 0; i < files.length; i++)
            {
                Log.d("Files", "FileName:" + files[i].getName());
            }

            ArrayAdapter<File> adapter = new ArrayAdapter<>(this,
                    R.layout.listmap_item, files);
            listMap.setAdapter(adapter);

        } catch (Exception ex) {
            Toast.makeText(this, "Разместите карты в папке: "+ Environment.getExternalStorageDirectory().toString()+getString(R.string.pathMaps),
                    Toast.LENGTH_LONG).show();

                    Intent openmap = new Intent(Openmap.this, MainActivity.class);
                    startActivity(openmap);

        }

//        searchFile();




//        Клик файла в списке
        listMap.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View itemClicked,
                                    int position, long id) {
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);

                ((TextView) itemClicked).setTextColor(getResources().getColor(R.color.colorAccent));
                Toast.makeText(getApplicationContext(),"Выбрана карта: " + ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();

                Intent openmap = new Intent(Openmap.this, MainActivity.class);
                openmap.putExtra("usermap", ((TextView) itemClicked).getText());
                openmap.putExtra("usermapbool", true);
                startActivity(openmap);
            }
        });




    }

//    private void searchFile() {
//
//

//
//    }

}
