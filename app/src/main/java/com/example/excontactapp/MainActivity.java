package com.example.excontactapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnClickNodeListener,ContactAdapter.SendData {

    public static final int REQUEST_CODE = 1;
    public static final int RESULT_CODE_ADD = 1;
    public static int RESULT_CODE_UPDATE = 1;

    List<Contact> ListContact;
    ContactAdapter contactAdapter;
    RecyclerView mRecyclerView;
    MyDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        mRecyclerView = findViewById( R.id.mRecyclerView );


        ListContact = new ArrayList<Contact>();
        db = new MyDatabase( this );
        ListContact = db.getAllContacts();

        initContact();


        Button fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, AddContact.class );
                startActivityForResult( intent, REQUEST_CODE );
            }

        } );
    }

    private void initContact() {
        contactAdapter = new ContactAdapter( this, R.layout.item_contact, ListContact, this,this );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        mRecyclerView.setLayoutManager( layoutManager );
        mRecyclerView.setAdapter(contactAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_CODE_ADD:
                    if (data != null) {
                        ListContact.clear();
                        ListContact = db.getAllContacts();
                        initContact();
                        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
    @Override
    public void onItemRecyclerClicked(int postion, int actions) {

        switch (actions){
            case 1 :
                Contact contact = ListContact.get(postion);
                Toast.makeText(this, " " + contact.getmId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, EditContact.class);
                intent.putExtra("Object", contact);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case 2 :
                Intent intent2 = new Intent(Intent.ACTION_CALL);
                intent2.setData(Uri.parse("tel:" + ListContact.get(postion).getmMobile()));
                startActivity(intent2);
                break;
        }
    }


    @Override
    public void sendData(int pos) {
        Contact contact = ListContact.get(pos);
        if (db.deleteContact(contact)){
            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();

            contactAdapter.removeItem(pos);
        }
    }
}