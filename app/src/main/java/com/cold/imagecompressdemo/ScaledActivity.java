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

public class ScaledActivity extends AppCompatActivity {

    private ImageView imgvOrgin;
    private ImageView imgvCompress;
    private TextView tvOrgin;
    private TextView tvCompress;
    private EditText edtvWidth;
    private EditText edtvHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaled);
        imgvOrgin = findViewById(R.id.imgv_orgin);
        imgvCompress = findViewById(R.id.imgv_compress);
        tvOrgin = findViewById(R.id.tv_orgin);
        tvCompress = findViewById(R.id.tv_compress);
        edtvWidth = findViewById(R.id.edtv_width);
        edtvWidth.setText("200");
        edtvHeight = findViewById(R.id.edtv_height);
        edtvHeight.setText("200");

    }

    public void onLoad(View v) {
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png");
        if(bitmap != null) {
            String info = "原始图片大小: " + (bitmap.getByteCount()) + " 宽度: " + bitmap.getWidth() + " 高度: " + bitmap.getHeight();
            Log.v("scale", info);
            tvOrgin.setText(info);
            imgvOrgin.setImageBitmap(bitmap);
        }
    }

    public void onCompress(View v) {
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png");
        String strx = edtvWidth.getText().toString();
        String stry = edtvHeight.getText().toString();

        int width = 200;
        int height = 200;
        try {
            width = Integer.parseInt(strx);
            height = Integer.parseInt(stry);
        } catch (Exception e) {
            Toast.makeText(this, "请输入有效数字内容", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return ;
        }
        if(bitmap != null) {
            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
            String info = " width: " + width + " height: " + height + " 压缩图片大小: " + (newBitmap.getByteCount()) + " 宽度为: " + newBitmap.getWidth() + " 高度为: " + newBitmap.getHeight();
            Log.v("scale", info);
            tvCompress.setText(info);
            imgvCompress.setImageBitmap(newBitmap);
        }
    }


}
