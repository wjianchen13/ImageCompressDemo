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

public class SampleActivity extends AppCompatActivity {

    private ImageView imgvOrgin;
    private ImageView imgvCompress;
    private TextView tvOrgin;
    private TextView tvCompress;
    private EditText edtvSample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        imgvOrgin = findViewById(R.id.imgv_orgin);
        imgvCompress = findViewById(R.id.imgv_compress);
        tvOrgin = findViewById(R.id.tv_orgin);
        tvCompress = findViewById(R.id.tv_compress);
        edtvSample = findViewById(R.id.edtv_sample);
        edtvSample.setText("2");
    }

    public void onLoad(View v) {
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png");
        if(bitmap != null) {
            String info = "原始图片大小: " + (bitmap.getByteCount()) + " 宽度: " + bitmap.getWidth() + " 高度: " + bitmap.getHeight();
            Log.v("sample", info);
            tvOrgin.setText(info);
            imgvOrgin.setImageBitmap(bitmap);
        }
    }

    public void onCompress(View v) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        String str = edtvSample.getText().toString();
        int sample = 2;
        try {
            sample = Integer.parseInt(str);
        } catch (Exception e) {
            Toast.makeText(this, "请输入有效数字内容", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return ;
        }
        options.inSampleSize = sample;

        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png", options);
        if(bitmap != null) {
            String info = "压缩图片大小: " + (bitmap.getByteCount()) + " 宽度: " + bitmap.getWidth() + " 高度: " + bitmap.getHeight();
            Log.v("sample", info);
            tvCompress.setText(info);
            imgvCompress.setImageBitmap(bitmap);
        }
    }

}
