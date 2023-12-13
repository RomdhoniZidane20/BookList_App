package com.example.book_list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    Context context;
    ArrayList<DataBase> list_book;

    public BookAdapter(Context context, ArrayList<DataBase> list_book) {
        this.context = context;
        this.list_book = list_book;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.scroll_data_index, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataBase book = list_book.get(position);
        holder.Title.setText(book.getBook_title());
        holder.Author.setText(book.getBook_author());
        holder.Year.setText(book.getBook_year());

        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Book")
                        .setMessage("Are you sure want to proceed ?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
                                deleteCategory(book, holder);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

    }

    private void deleteCategory(DataBase book, MyViewHolder holder) {
        String id = book.getBook_year();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("BookData");
        ref.child(id)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to Delete data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list_book.size();
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder {

        TextView Title, Author, Year;
        ImageView delete_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.book_title_text);
            Author = itemView.findViewById(R.id.book_author_text);
            Year = itemView.findViewById(R.id.book_year_text);
            delete_button = itemView.findViewById(R.id.delete_button);
        }
    }

}
