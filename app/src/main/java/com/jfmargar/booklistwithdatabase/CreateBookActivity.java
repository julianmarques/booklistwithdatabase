package com.jfmargar.booklistwithdatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jfmargar.booklistwithdatabase.model.Book;

import io.realm.Realm;

public class CreateBookActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etAuthor;
    CheckBox cbReaded;
    Button btnSave;
    Long id;

    Book book;
    Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        cbReaded = findViewById(R.id.cbReaded);
        btnSave = findViewById(R.id.btnSave);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = (Long) bundle.get("id");
            if (id != null) {
                book = realm.where(Book.class).equalTo("id", id).findFirst();
            }
        }


        if (book != null) {
            etTitle.setText(book.getTitle());
            etAuthor.setText(book.getAuthor());
            cbReaded.setChecked(book.getReaded());
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString();
                String author = etAuthor.getText().toString();
                Boolean readed = cbReaded.isChecked();

                realm.beginTransaction();
                if (book == null) {
                    book = new Book();
                    Number maxId = realm.where(Book.class).max("id");
                    long nextID;
                    if (maxId == null) {
                        nextID = 1;
                    } else {
                        nextID = maxId.longValue() + 1;
                    }
                    book.setId(nextID);
                }
                book.setTitle(title);
                book.setAuthor(author);
                book.setReaded(readed);

                realm.copyToRealmOrUpdate(book);
                realm.commitTransaction();
                finish();
            }
        });


    }
}
