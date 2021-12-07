package com.example.fanverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fanverse.models.Post;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);

        recyclerView = findViewById(R.id.postRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                new LinearLayoutManager(getApplicationContext()).getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        query = FirebaseFirestore.getInstance()
                .collection("posts")
                .orderBy("datetime", Query.Direction.DESCENDING);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postintent = new Intent(getApplicationContext(), AddPost.class);
                startActivity(postintent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class)
                .build();

        FirestoreRecyclerAdapter firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Post, PostViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull PostViewHolder holder, int position, @NonNull @NotNull Post model) {
                holder.setCaption(model.getCaption());
                holder.setDatetime(model.getDatetime());
                holder.setUsername(model.getUsername());
                holder.setPostImage(model.getImage());
            }

            @NonNull
            @Override
            public MainActivity.PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.single_post, viewGroup, false);
                return new MainActivity.PostViewHolder(view);
            }
        };

        firestoreRecyclerAdapter.startListening();

        recyclerView.setAdapter(firestoreRecyclerAdapter);


    }

    private class PostViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setUsername(String name){
            TextView username = mView.findViewById(R.id.username);
            username.setText(name);
        }

        public void setCaption(String cap){
            TextView caption = mView.findViewById(R.id.caption);
            caption.setText(cap);
        }

        public void setPostImage(String post_url){
            ImageView imageView = mView.findViewById(R.id.image);
            Picasso.get().load(post_url).fit().centerCrop().into(imageView);

        }


        public void setDatetime(String time){
            TextView tim = mView.findViewById(R.id.datetime);
            tim.setText(time);
        }


    }
}