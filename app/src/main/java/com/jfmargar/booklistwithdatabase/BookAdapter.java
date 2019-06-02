package com.jfmargar.booklistwithdatabase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jfmargar.booklistwithdatabase.model.Book;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    Context context;
    ArrayList<Book> books;
    int resource;

    private View.OnClickListener clickListener;
    private View.OnLongClickListener longClickListener;

    public BookAdapter(Context context, ArrayList<Book> books, int resource) {
        this.context = context;
        this.books = books;
        this.resource = resource;
    }

    public void setLongClickListener(View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public void setClickListener(View.OnClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Esta línea siempre es igual
        View itemView = LayoutInflater.from(context).inflate(resource, viewGroup, false);

        BookViewHolder bookViewHolder = new BookViewHolder(itemView);

        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i) {
        //Saco el libro de la lista que está en la posición "i"
        Book book = books.get(i);
        //lo uso para rellenar el viewholder
        bookViewHolder.bindBook(book);

        if (clickListener != null) {
            bookViewHolder.itemView.setOnClickListener(clickListener);
        }

        if (longClickListener != null) {
            bookViewHolder.itemView.setOnLongClickListener(longClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public Book getItemFromView(View view) {
        BookViewHolder viewHolder = (BookViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();
        return books.get(position);
    }

    public int getPositionFromView(View view) {
        BookViewHolder viewHolder = (BookViewHolder) view.getTag();
        return viewHolder.getAdapterPosition();
    }


    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvSubtitle;
        ImageView ivReaded;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvAuthor);
            ivReaded = itemView.findViewById(R.id.ivReaded);
            itemView.setTag(this);
        }

        public void bindBook(Book book) {
            tvTitle.setText(book.getTitle());
            tvSubtitle.setText(book.getAuthor());
            if (book.getReaded()) {
                Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_readed)).into(ivReaded);
            } else {
                Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_bookmark_not_readed)).into(ivReaded);
            }

        }
    }
}
