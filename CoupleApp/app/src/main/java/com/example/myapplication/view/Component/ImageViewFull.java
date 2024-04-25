package com.example.myapplication.view.Component;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

public class ImageViewFull extends FrameLayout {
    private TouchImageView imageZoom;
    private Runnable onClose;
    private Runnable onOpen;
    public ImageViewFull(@NonNull Context context) {
        super(context);
    }

    public ImageViewFull(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewFull(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageViewFull(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void changeImage(String url) {
        try {
            Picasso.get().load(url).into(imageZoom);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void changeImage(Uri uri) {
        imageZoom.setImageURI(uri);
    }

    private void init(String urlAvatar) {
        setBackground(ContextCompat.getDrawable(getContext(), R.color.black_alpha));
        bringToFront();

        imageZoom = new TouchImageView(getContext());
        imageZoom.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));
        try {
            Picasso.get().load(urlAvatar).into(imageZoom);
        } catch (Exception e) {e.printStackTrace();}
        addView(imageZoom);

        TextView btnClose = new TextView(getContext());
        btnClose.setText("X");
        btnClose.setTextSize(30);
        ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        ((FrameLayout.LayoutParams)params).gravity = Gravity.TOP | Gravity.RIGHT;
        btnClose.setLayoutParams(params);
        btnClose.setTextColor(Color.WHITE);
        btnClose.setWidth(150);
        btnClose.setHeight(150);
        btnClose.setPadding(0,20,50,0);
        btnClose.setGravity(Gravity.TOP | Gravity.RIGHT);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(View.INVISIBLE);
                if (onClose != null)
                    onClose.run();
            }
        });
        btnClose.bringToFront();
        addView(btnClose);
    }

    public void open() {
        if (onOpen != null)
            onOpen.run();
        setVisibility(View.VISIBLE);
        if (imageZoom != null)
            imageZoom.resetZoom();
    }

    public static ImageViewFull create(Context context, String urlAvatar, Runnable open, Runnable close) {
        ImageViewFull result = new ImageViewFull(context);
        result.onOpen = open;
        result.onClose = close;
        result.init(urlAvatar);
        return result;
    }
}
