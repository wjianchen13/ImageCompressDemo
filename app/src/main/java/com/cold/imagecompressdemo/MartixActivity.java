package com.cold.imagecompressdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

public class MartixActivity extends AppCompatActivity {

    private ImageView imgvOrgin;
    private ImageView imgvCompress;
    private TextView tvOrgin;
    private TextView tvCompress;
    private EditText edtvSx;
    private EditText edtvSy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_martix);

        imgvOrgin = findViewById(R.id.imgv_orgin);
        imgvCompress = findViewById(R.id.imgv_compress);
        tvOrgin = findViewById(R.id.tv_orgin);
        tvCompress = findViewById(R.id.tv_compress);
        edtvSx = findViewById(R.id.edtv_sx);
        edtvSx.setText("0.5");
        edtvSy = findViewById(R.id.edtv_sy);
        edtvSy.setText("0.5");
    }

    public void onLoad(View v) {
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png");
        if(bitmap != null) {
            String info = "原始图片大小: " + (bitmap.getByteCount()) + " 宽度: " + bitmap.getWidth() + " 高度: " + bitmap.getHeight();
            Log.v("martix", info);
            tvOrgin.setText(info);
            imgvOrgin.setImageBitmap(bitmap);
        }
    }

    public void onCompress(View v) {
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png");
        String strx = edtvSx.getText().toString();
        String stry = edtvSy.getText().toString();

        float sx = 0.5f;
        float sy = 0.5f;
        try {
            sx = Float.parseFloat(strx);
            sy = Float.parseFloat(stry);
        } catch (Exception e) {
            Toast.makeText(this, "请输入有效数字内容", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return ;
        }
        if(bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.setScale(sx, sy);
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            String info = " sx: " + sx + " sy: " + sy + " 压缩图片大小: " + (newBitmap.getByteCount()) + " 宽度为: " + newBitmap.getWidth() + " 高度为: " + newBitmap.getHeight();
            Log.v("martix", info);
            tvCompress.setText(info);
            imgvCompress.setImageBitmap(newBitmap);
        }
    }


}
