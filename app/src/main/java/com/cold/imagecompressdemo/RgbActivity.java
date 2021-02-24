package com.cold.imagecompressdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class RgbActivity extends AppCompatActivity {

    private ImageView imgvOrgin;
    private ImageView imgvCompress;
    private TextView tvOrgin;
    private TextView tvCompress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgb);

        imgvOrgin = findViewById(R.id.imgv_orgin);
        imgvCompress = findViewById(R.id.imgv_compress);
        tvOrgin = findViewById(R.id.tv_orgin);
        tvCompress = findViewById(R.id.tv_compress);
    }


    public void onLoad(View v) {
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png");
        if(bitmap != null) {
            String info = "原始图片大小: " + (bitmap.getByteCount()) + " 宽度: " + bitmap.getWidth() + " 高度: " + bitmap.getHeight();
            Log.v("rgb", info);
            tvOrgin.setText(info);
            imgvOrgin.setImageBitmap(bitmap);
        }
    }

    public void onArgb8888(View v) {
        compressBitmap(Bitmap.Config.ARGB_8888);
    }

    public void onArgb4444(View v) {
        compressBitmap(Bitmap.Config.ARGB_4444);
    }

    public void onRgb565(View v) {
        compressBitmap(Bitmap.Config.RGB_565);
    }

    private void compressBitmap(Bitmap.Config config) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png", options);
        if(bitmap != null) {
            String info = " 压缩图片大小: " + (bitmap.getByteCount()) + " 宽度为: " + bitmap.getWidth() + " 高度为: " + bitmap.getHeight();
            Log.v("rgb", info);
            tvCompress.setText(info);
            imgvCompress.setImageBitmap(bitmap);
        }
    }

}
