package com.example.excontactapp;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {

    Contact contact;
    EditText name, mobile, email;
    Button btnAdd, btnClose;
    MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.add_contact );
        name = findViewById( R.id.edName );
        mobile = findViewById( R.id.edMobile );
        email = findViewById( R.id.edEmail );
        btnAdd = findViewById( R.id.btnadd );
        btnClose = findViewById( R.id.btnClose );


        myDatabase = new MyDatabase( this );

        btnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contact = new Contact( name.getText().toString(), "", "", mobile.getText().toString(), email.getText().toString() );
                if (myDatabase.addContact( contact ) != 0) {
                    Toast.makeText( AddContact.this, "ADD SUCCESS", Toast.LENGTH_SHORT ).show();
                    Intent intentSendBack = new Intent( AddContact.this, MainActivity.class );
                    intentSendBack.putExtra( "", contact );
                    setResult( MainActivity.RESULT_CODE_ADD, intentSendBack );
                    finish();
                }

            }
        } );
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AddContact.this, MainActivity.class);
                startActivity(intent2);
            }
        });

    }
}