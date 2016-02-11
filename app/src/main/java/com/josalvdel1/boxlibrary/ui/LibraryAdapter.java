package com.josalvdel1.boxlibrary.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.josalvdel1.boxlibrary.R;
import com.josalvdel1.boxlibrary.entities.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.BooksViewHolder> {

    private Picasso picasso;
    private List<Book> books;

    @Inject
    public LibraryAdapter(Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public BooksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BooksViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(BooksViewHolder holder, int position) {
        holder.tvTitle.setText(books.get(position).getTitle());
        // TODO: 10/02/2016 load image with picasso
        picasso.load(R.mipmap.ic_launcher).into(holder.ivCover);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (books != null) {
            count = books.size();
        }
        return count;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    protected class BooksViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_cover)
        ImageView ivCover;
        @Bind(R.id.tv_title)
        TextView tvTitle;

        public BooksViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
