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

public class QualityActivity extends AppCompatActivity {

    private ImageView imgvOrgin;
    private ImageView imgvCompress;
    private TextView tvOrgin;
    private TextView tvCompress;
    private EditText edtvQuality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality);

        imgvOrgin = findViewById(R.id.imgv_orgin);
        imgvCompress = findViewById(R.id.imgv_compress);
        tvOrgin = findViewById(R.id.tv_orgin);
        tvCompress = findViewById(R.id.tv_compress);
        edtvQuality = findViewById(R.id.edtv_quality);
        edtvQuality.setText("100");
    }

    public void onLoad(View v) {
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png");
        if(bitmap != null) {
            String info = "原始图片大小: " + (bitmap.getByteCount()) + " 宽度: " + bitmap.getWidth() + " 高度: " + bitmap.getHeight();
            Log.v("quality", info);
            tvOrgin.setText(info);
            imgvOrgin.setImageBitmap(bitmap);
        }
    }

    public void onCompress(View v) {
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String str = edtvQuality.getText().toString();
        int quality = 100;
        try {
            quality = Integer.parseInt(str);
        } catch (Exception e) {
            Toast.makeText(this, "请输入有效数字内容", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return ;
        }
        if(bitmap != null) {
//            bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos); // 设置Bitmap.CompressFormat.PNG，quality将不起作用，PNG是无损压缩
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            byte[] bytes = baos.toByteArray();
            Bitmap newBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            String info = " quality: " + quality + " 压缩图片大小: " + (newBitmap.getByteCount()) + " 压缩后文件大小: " + (bytes.length) + " 宽度为: " + newBitmap.getWidth() + " 高度为: " + newBitmap.getHeight();
            Log.v("quality", info);
            tvCompress.setText(info);
            imgvCompress.setImageBitmap(newBitmap);
        }
    }

}
