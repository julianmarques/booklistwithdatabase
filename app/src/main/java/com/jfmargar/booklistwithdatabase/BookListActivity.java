package com.jfmargar.booklistwithdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jfmargar.booklistwithdatabase.model.Book;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class BookListActivity extends AppCompatActivity {

    FloatingActionButton fabAdd;
    RecyclerView rvBooks;
    TextView tvEmpty;


    BookAdapter adapter;
    ArrayList<Book> books = new ArrayList<>();
    Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);

        fabAdd = findViewById(R.id.fabAdd);
        rvBooks = findViewById(R.id.rvBooks);
        tvEmpty = findViewById(R.id.tvEmpty);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookListActivity.this, CreateBookActivity.class);
                startActivity(intent);
            }
        });


        adapter = new BookAdapter(this, books, R.layout.book_item);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvBooks.setLayoutManager(layoutManager);

        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = adapter.getItemFromView(view);
                long id = book.getId();
                Intent intent = new Intent(BookListActivity.this, CreateBookActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });

        adapter.setLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = adapter.getPositionFromView(view);
                Book book = books.get(position);
                realm.beginTransaction();
                book.setReaded(!book.getReaded());
                realm.commitTransaction();

                adapter.notifyItemChanged(position);
                return true;
            }
        });


        rvBooks.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

        RealmResults<Book> results = realm.where(Book.class).sort("id").findAll();
        books.clear();
        books.addAll(results);
        adapter.notifyDataSetChanged();

        if (books.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.INVISIBLE);
        }
    }
}
