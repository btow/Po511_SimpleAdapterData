package com.example.samsung.po511_simpleadapterdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int CM_DELETE_ID = 1;

    //имена атрибутов для Map
    final String ATTR_NAME_TEXT = "text",
                 ATTR_NAME_IMAGE = "image";
    ListView lvSimple;
    SimpleAdapter simpleAdapter;
    ArrayList<Map<String, Object>> data;
    Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //генерируем и упаковываем данные в панятную для адаптера структуру
        data = new ArrayList<Map<String, Object>>();
        for (int index = 1; index < 5; index++) {
            map = new HashMap<String, Object>();
            map.put(ATTR_NAME_TEXT, "Sometext " + (data.size() + 1));
            map.put(ATTR_NAME_IMAGE, R.mipmap.ic_launcher);
            data.add(map);
        }
        //массив имен атрибутов, которым будут сопоставляться ID View-компонентов
        String[] from = {ATTR_NAME_TEXT, ATTR_NAME_IMAGE};
        //Ммассив ID View-компонентов, в которые будут вставляться данные из ArrayList'а
        int[] to = {R.id.tvText, R.id.ivImg};

        //создаём адаптер
        simpleAdapter = new SimpleAdapter(this,data,R.layout.item,from,to);

        //определяем список и присваиваем ему адаптер
        lvSimple = (ListView) findViewById(R.id.lvSimple);
        lvSimple.setAdapter(simpleAdapter);
        registerForContextMenu(lvSimple);
    }

    public void onButtonClic(View view) {
        //создаём новый Map
        map = new HashMap<String, Object>();
        map.put(ATTR_NAME_TEXT, "Sometext " + (data.size() + 1));
        map.put(ATTR_NAME_IMAGE, R.mipmap.ic_launcher);
        //добавляем его в коллекцию
        data.add(map);
        //уведомляем об изменении данных
        simpleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            //получаем инфо о пункте списка
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo
                    = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            //удаляем Map из коллекции data, используя позицию пункта в списке
            data.remove(adapterContextMenuInfo.position);
            //уведомляем, что данные изменились
            simpleAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
