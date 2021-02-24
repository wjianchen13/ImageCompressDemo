package com.cold.imagecompressdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoActivity extends AppCompatActivity {

    private ImageView imgvOrgin;
    private ImageView imgvCompress;
    private TextView tvOrgin;
    private TextView tvCompress;
    private EditText edtvSx;
    private EditText edtvSy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

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
//        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png");
//        if(bitmap != null) {
//            String info = "原始图片大小: " + (bitmap.getByteCount()) + " 宽度: " + bitmap.getWidth() + " 高度: " + bitmap.getHeight();
//            Log.v("martix", info);
//            tvOrgin.setText(info);
//            imgvOrgin.setImageBitmap(bitmap);
//        }
//
//        saveBitmapInFile("/storage/emulated/0/test/hello.png", bitmap);

        String url = "134723172_5eb4fa839cb31.gif";
        String fileName = url.substring(url.lastIndexOf("/") + 1);
//        fileName = "134723172_5eb4fa839cb31";
        int index = fileName.lastIndexOf(".");
        if(index > 0)
            fileName = fileName.substring(0, fileName.lastIndexOf("."));

        System.out.println("===============> fileName: " + fileName);

    }

    private MediaScannerConnection mScanner;
    private void saveBitmapInFile(final String path, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            if (mScanner == null) {
                MediaScannerConnection connection = new MediaScannerConnection(PhotoActivity.this,
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            public void onMediaScannerConnected() {
                                mScanner.scanFile(path, "image/png" /* mimeType */);
                            }

                            public void onScanCompleted(String path1, Uri uri) {
                                if (path1.equals(path)) {
                                    mScanner.disconnect();
                                    mScanner = null;
                                }
                            }
                        });
                try {
                    connection.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mScanner = connection;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
