package com.example.me.sqliteperson;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {
  //  private PeopleDataSource datasource;
    EditText editID, editName;
    private DBManager datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editID=(EditText)findViewById(R.id.editID);
        editName=(EditText)findViewById(R.id.editName);

        datasource=new DBManager(this);
        List<Person> values = datasource.getAllPeople();
        ArrayAdapter<Person> adapter = new ArrayAdapter<Person>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
    public void onClick(View view) {
        ArrayAdapter<Person> adapter = (ArrayAdapter<Person>) getListAdapter();
        Person person = null;
        switch (view.getId()) {
            // Thêm người vào danh sách.
            case R.id.add:
                String hoten=editName.getText().toString();
                if (!hoten.equals(""))
                {
                    Person pp=new Person(hoten);
                    datasource.addPerson(pp);
                    adapter.add(pp);
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(this,"Chưa nhập họ tên", Toast.LENGTH_SHORT).show();
                break;
            // Xóa người đầu tiên khỏi danh sách.
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    person = (Person) getListAdapter().getItem(0);
                    datasource.deletePerson(person);
                    adapter.remove(person);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.deleteName:
                String name=editName.getText().toString();
                person=(Person) datasource.getPersonByName(name);
                if (person==null)
                    Toast.makeText(MainActivity.this,
                        "Không có người này",Toast.LENGTH_LONG).show();
                else
                {
                    datasource.deletePerson(person);
                    adapter.remove(person);
                    adapter.notifyDataSetChanged();
                }
                break;
        }

    }

    @Override
    protected void onResume() {
      //  datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
     //   datasource.close();
        super.onPause();
    }
}
