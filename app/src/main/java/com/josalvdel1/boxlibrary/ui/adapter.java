package com.josalvdel1.boxlibrary.ui;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.josalvdel1.boxlibrary.R;
import com.josalvdel1.boxlibrary.entities.Book;
import com.josalvdel1.boxlibrary.ui.presenter.LibraryPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class adapter {
    public static class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.BooksViewHolder> {

        private List<Book> books;
        private LibraryPresenter libraryPresenter;

        @Inject
        public LibraryAdapter(LibraryPresenter libraryPresenter) {
            books = new ArrayList<>();
            this.libraryPresenter = libraryPresenter;
        }

        @Override
        public BooksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BooksViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(BooksViewHolder holder, int position) {
            Book book = books.get(position);
            holder.tvTitle.setText(book.getTitle());
            holder.item = book;
            Bitmap cover = book.getCover();
            if (cover != null) {
                holder.ivCover.setImageBitmap(cover);
            } else {
                holder.ivCover.setImageResource(R.drawable.ic_photo_56dp);
            }
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
            notifyDataSetChanged();
        }

        public void addBook(Book book) {
            books.add(book);
            notifyItemInserted(books.size());
        }

        protected class BooksViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.iv_cover)
            ImageView ivCover;
            @Bind(R.id.tv_title)
            TextView tvTitle;

            public Book item;

            public BooksViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @OnClick(R.id.cv_book_container)
            public void onBookClicked() {
                libraryPresenter.onBookClicked(item);
            }
        }
    }
}
