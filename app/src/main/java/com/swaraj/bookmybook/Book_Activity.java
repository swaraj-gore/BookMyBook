package com.swaraj.bookmybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Book_Activity extends AppCompatActivity {

    private TextView tvtitle,tvdescription;
    private ImageView img;
    private Button btn_request,btn_accept,btn_delete;
    FirebaseUser firebaseUser;
    private DatabaseReference mDatabaseRef;
    boolean isRequested,isAccepted;
    String Title,requester_id,book_id ,imageurl,Description;
    DataSnapshot tempSnapShot;
    Intent intent1;
    Book tempBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_);

        intent1 = new Intent(Book_Activity.this, ImagesActivity.class);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        img = (ImageView) findViewById(R.id.bookthumbnail);
        btn_request = (Button) findViewById(R.id.btn_request);
        btn_accept = (Button) findViewById(R.id.btn_accept);
        btn_delete = (Button) findViewById(R.id.btn_delete);


        Intent intent = getIntent();
        Title = intent.getExtras().getString("Title");
         Description = intent.getExtras().getString("Description");
        imageurl = intent.getExtras().getString("Thumbnail");
         book_id = intent.getExtras().getString("BOOK_ID");
        isRequested = intent.getExtras().getBoolean("book_status");
        isAccepted = intent.getExtras().getBoolean("accept_status");
        requester_id = intent.getExtras().getString("Requester_id");
        System.out.println(isRequested);
        tvtitle.setText(Title);
        tvdescription.setText(Description);
        Glide.with(Book_Activity.this).load(imageurl).into(img);
        if((firebaseUser.getUid().equals(book_id)))
        {
            btn_request.setEnabled(false);
        }else
        {
            btn_accept.setEnabled(false);
            btn_delete.setEnabled(false);
        }
        if(isRequested) {
            btn_request.setText("Requested");
        }else{
        btn_request.setText("Initiate Request");
            if((firebaseUser.getUid().equals(book_id))){
                btn_accept.setEnabled(false);
            }

        }

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(book_id.equals(firebaseUser.getUid())){
                    mDatabaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Book book = postSnapshot.getValue(Book.class);
                                if (Title.equals(book.getTitle()) && book_id.equals(book.getBook_id())) {
                                    mDatabaseRef.child(postSnapshot.getKey()).removeValue();
                                    Toast.makeText(Book_Activity.this, "Book Deleted", Toast.LENGTH_SHORT).show();
                                    Intent intent2 = new Intent(Book_Activity.this,MyBooksActivity.class);
                                    startActivity(intent2);
                                    finish();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
            if (isRequested) {
                btn_request.setEnabled(false);

                //Accept
                if((firebaseUser.getUid().equals(book_id))) {
                    btn_accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (isRequested && !isAccepted) {
                                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            Book book = postSnapshot.getValue(Book.class);
                                            if (Title.equals(book.getTitle()) && book_id.equals(book.getBook_id())) {
                                                mDatabaseRef.child(postSnapshot.getKey()).removeValue();
                                                Toast.makeText(Book_Activity.this, "Book Accepted", Toast.LENGTH_SHORT).show();
                                                startActivity(intent1);
                                                finish();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    });
                }
                else btn_accept.setEnabled(false);

            } else {
                btn_request.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!(firebaseUser.getUid().equals(book_id))){
                            btn_delete.setEnabled(false);
                            btn_accept.setEnabled(false);
                            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                        Book book = postSnapshot.getValue(Book.class);
                                        if (Title.equals(book.getTitle()) && book_id.equals(book.getBook_id())) {
                                            tempSnapShot = postSnapshot;
                                            tempBook = book;
                                            book.setRequester_id(firebaseUser.getUid());
                                            book.setRequested(true);
                                            mDatabaseRef.child(postSnapshot.getKey()).setValue(book);
                                            btn_request.setText("Requested");
                                            isRequested = true;
                                            Toast.makeText(Book_Activity.this, "Book request initiated", Toast.LENGTH_SHORT).show();
                                            startActivity(intent1);
                                            finish();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });

            }
    }
}


