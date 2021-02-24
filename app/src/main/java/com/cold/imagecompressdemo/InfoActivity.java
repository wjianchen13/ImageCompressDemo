package com.cold.imagecompressdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import static com.cold.imagecompressdemo.FileSizeUtil.SIZETYPE_B;
import static com.cold.imagecompressdemo.FileSizeUtil.SIZETYPE_GB;
import static com.cold.imagecompressdemo.FileSizeUtil.SIZETYPE_KB;
import static com.cold.imagecompressdemo.FileSizeUtil.SIZETYPE_MB;

public class InfoActivity extends AppCompatActivity {

    private ImageView imgvOrgin;
    private ImageView imgvCompress;
    private TextView tvOrgin;
    private TextView tvCompress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        imgvOrgin = findViewById(R.id.imgv_orgin);
        imgvCompress = findViewById(R.id.imgv_compress);
        tvOrgin = findViewById(R.id.tv_orgin);
        tvCompress = findViewById(R.id.tv_compress);
    }

    public void onLoad(View v) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/compresstest/test.png";
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        if(bitmap != null) {
            String info = "原始图片大小: " + (bitmap.getByteCount()) + " 宽度: " + bitmap.getWidth() + " 高度: " + bitmap.getHeight();
            Log.v("martix", info);
            tvOrgin.setText(info);
            imgvOrgin.setImageBitmap(bitmap);
        }

        File file = new File(filePath);
        double fileSize = getFileSize(filePath, SIZETYPE_MB);
        tvCompress.setText("文件大小：" + fileSize + "MB");
        doubleTest();
    }

    private MediaScannerConnection mScanner;
    private void saveBitmapInFile(final String path, Bitmap bitmap) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            if (mScanner == null) {
                MediaScannerConnection connection = new MediaScannerConnection(InfoActivity.this,
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
    
    public void doubleTest() {
        boolean r1 = Double.doubleToLongBits(3.0) >= Double.doubleToLongBits(3);
        boolean r2 = Double.doubleToLongBits(3.1) >= Double.doubleToLongBits(3);
        boolean r3 = Double.doubleToLongBits(3.1) < Double.doubleToLongBits(3);
        System.out.println("=============> r1: " + r1 + "  r2: " + r2 + "  r3: " + r3);
    }



    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @return double值的大小
     */
    public static double getFileSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                blockSize = fis.available();
            } else {
                Log.e("获取文件大小", "文件不存在!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FormetFileSize(blockSize, sizeType);
    }


    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

}
