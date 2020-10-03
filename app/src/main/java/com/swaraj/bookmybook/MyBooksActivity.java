package com.swaraj.bookmybook;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyBooksActivity extends AppCompatActivity {

    FloatingActionButton fab1,fab2,fab3,fab4,fab5;
    Animation fabOpen,fabClose,rotateUpword,rotateDownward;
    boolean isOpen =false;

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    ImageView img_view;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private List<Book> mUploads;
    EditText searchInput;
    CharSequence search = "";
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_my_books);
        // hide the action bar

        //getSupportActionBar().hide();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        fab5 = (FloatingActionButton) findViewById(R.id.fab5);

        fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);

        rotateUpword = AnimationUtils.loadAnimation(this,R.anim.rotate_upward);
        rotateDownward = AnimationUtils.loadAnimation(this,R.anim.rotate_downward);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        img_view = findViewById(R.id.book_img_id);


        searchInput = findViewById(R.id.search_input);
        mRecyclerView = findViewById(R.id.recycler_view);

        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Book book = postSnapshot.getValue(Book.class);
                    assert book != null;
                    if(book.getBook_id().equals(firebaseUser.getUid()) && !mUploads.contains(book)) {
                        if(!mUploads.contains(book))
                        mUploads.add(book);
                    }
                }

                mAdapter = new ImageAdapter(MyBooksActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MyBooksActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mAdapter.getFilter().filter(s);
                search = s;


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                animateFab();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(MyBooksActivity.this,UserActivity.class);
                startActivity(intent);
                Toast.makeText(MyBooksActivity.this,"Profile", Toast.LENGTH_SHORT).show();
                animateFab();
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                startActivity(new Intent(MyBooksActivity.this,ImagesActivity.class));

                Toast.makeText(MyBooksActivity.this,"Home", Toast.LENGTH_SHORT).show();
                animateFab();
            }
        });
        fab4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(MyBooksActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(MyBooksActivity.this,"Add a Book", Toast.LENGTH_SHORT).show();
                animateFab();
            }
        });
        fab5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MyBooksActivity.this,StartActivity.class);
                startActivity(intent);
                Toast.makeText(MyBooksActivity.this,"Log out", Toast.LENGTH_SHORT).show();
                animateFab();
            }
        });

    }

    private void animateFab()
    {
        if (isOpen)
        {
            fab1.startAnimation(rotateUpword);
            fab2.startAnimation(fabClose);
            fab3.startAnimation(fabClose);
            fab4.startAnimation(fabClose);
            fab5.startAnimation(fabClose);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            fab5.setClickable(false);
            isOpen=false;
        }
        else {
            fab1.startAnimation(rotateDownward);
            fab2.startAnimation(fabOpen);
            fab3.startAnimation(fabOpen);
            fab4.startAnimation(fabOpen);
            fab5.startAnimation(fabOpen);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            fab5.setClickable(true);
            isOpen=true;
        }
    }
}
