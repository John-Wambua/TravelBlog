package com.wambuacooperations.travelblog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.snackbar.Snackbar;
import com.wambuacooperations.travelblog.http.Blog;
import com.wambuacooperations.travelblog.http.BlogArticlesCallback;
import com.wambuacooperations.travelblog.http.BlogHttpClient;

import java.util.List;

public class BlogDetailsActivity extends AppCompatActivity {

    ImageView imageMain, imageAvatar, imageBack;
    TextView textTitle, textViewDate, textAuthor, textRating, textViews, textViewDescription;
    RatingBar ratingBar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        imageMain = findViewById(R.id.imageMain);
        imageAvatar = findViewById(R.id.imageAvatar);

        progressBar=findViewById(R.id.progressBar);

        textTitle = findViewById(R.id.textTitle);
        textViewDate = findViewById(R.id.textViewDate);
        textAuthor = findViewById(R.id.textAuthor);
        textRating = findViewById(R.id.textRating);
        textViews = findViewById(R.id.textViews);
        textViewDescription = findViewById(R.id.textViewDescription);
        ratingBar = findViewById(R.id.ratingBar);
        imageBack = findViewById(R.id.imageBack);

        loadData();

    }

    private void loadData() {
        BlogHttpClient.INSTANCE.loadBlogArticles(new BlogArticlesCallback() {
            @Override
            public void onSuccess(final List<Blog> blogList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showData(blogList.get(0));
                    }
                });
            }

            @Override
            public void onError() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showErrorSnackBar();
                    }
                });
            }
        });
    }

    private void showErrorSnackBar() {

        View rootView = findViewById(android.R.id.content);
        final Snackbar snackbar = Snackbar
                .make(rootView, "Error Loading blog articles", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    private  void showData(Blog blog) {
        progressBar.setVisibility(View.GONE);
        textTitle.setText(blog.getTitle());
        textViewDate.setText(blog.getDate());
        textAuthor.setText(blog.getAuthor().getName());
        textRating.setText(String.valueOf(blog.getRating()));
        textViews.setText(String.format("(%d views)",blog.getViews()));
        textViewDescription.setText(HtmlCompat.fromHtml(blog.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY));
        ratingBar.setRating(blog.getRating());

        Glide.with(this)
                .load(blog.getImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageMain);

        Glide.with(this)
                .load(blog.getAuthor().getAvatar())
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new CircleCrop())
                .into(imageAvatar);


    }

    public void imageBackClicked(View view){
        finish();
    }
}
