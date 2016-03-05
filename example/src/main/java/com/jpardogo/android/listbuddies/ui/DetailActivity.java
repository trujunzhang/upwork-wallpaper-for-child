package com.jpardogo.android.listbuddies.ui;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jpardogo.android.listbuddies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailActivity extends BaseActivity {

    public static final String EXTRA_URL = "url";
    @InjectView(R.id.image)
    ImageView mImageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.inject(this);
        mBackground = mImageView;
        String imageUrl = getIntent().getExtras().getString(EXTRA_URL);
        Picasso.with(this).load(imageUrl).into((ImageView) findViewById(R.id.image), new Callback() {
            @Override
            public void onSuccess() {
                moveBackground();
            }

            @Override
            public void onError() {
            }
        });

        findViewById(R.id.llSetWallpaper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.this.bitmap = ((BitmapDrawable)DetailActivity.this.mBackground.getDrawable()).getBitmap();
                try
                {
                    WallpaperManager.getInstance(DetailActivity.this.getApplicationContext()).setBitmap(DetailActivity.this.bitmap);
                    Toast.makeText(DetailActivity.this, "Wallpaper Set!", Toast.LENGTH_SHORT).show();
                    return;
                }
                catch (IOException localIOException)
                {
                    localIOException.printStackTrace();
                    return;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}


