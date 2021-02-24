package com.cold.imagecompressdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE, // sd卡读取权限
            Manifest.permission.WRITE_EXTERNAL_STORAGE // sd卡写入权限
    };
    private final int PERMISSIONS_CODE = 1; // 权限请求代码

    private String outPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkPermissions()) {
            copyTestFile();
        }
    }

    public void onSample(View v) {
        gotoActivity(SampleActivity.class);
    }

    public void onMartix(View v) {
        gotoActivity(MartixActivity.class);
    }

    public void onQuality(View v) {
        gotoActivity(QualityActivity.class);
    }

    public void onRgb(View v) {
        gotoActivity(RgbActivity.class);
    }

    public void onScaled(View v) {
        gotoActivity(ScaledActivity.class);
    }

    public void onPhoto(View v) {
        gotoActivity(PhotoActivity.class);
    }

    public void onInfo(View v) {
        gotoActivity(InfoActivity.class);
    }

    private void gotoActivity(Class<?> cls) {
        Intent it = new Intent();
        it.setClass(this, cls);
        startActivity(it);
    }

    /**
     * 检查权限
     * @param
     * @return void
     */
    private boolean checkPermissions() {
        boolean flag = checkPermissions(permissions);
        if(!flag) {
            requestPermission(permissions);
        }
        return flag;
    }

    /**
     * 1.检查权限
     * @param
     * @return 权限是否允许标志
     */
    private boolean checkPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 2.如果没有权限，动态请求权限
     * @param
     * @return void
     */
    private void requestPermission(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_CODE) {
            boolean grantedFlag = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    grantedFlag = false;
                    break;
                }
            }

            if (!grantedFlag) {
                copyTestFile();
            }
        }
    }

    private void copyTestFile() {
        String outPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "compresstest" + File.separator;
        copyAssetsSingleFile(outPath, "test.png");
    }

    /**
     * 复制单个文件
     *
     * @param outPath String 输出文件路径
     * @param fileName String 要复制的文件名
     * @return
     */
    private void copyAssetsSingleFile(String outPath, String fileName) {
        File file = new File(outPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Toast.makeText(this, "创建文件夹失败", Toast.LENGTH_SHORT).show();
                return ;
            }
        }
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open(fileName);
            File outFile = new File(file, fileName);
            fileOutputStream = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = inputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileOutputStream != null) {
                    fileOutputStream.close();
                    fileOutputStream = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
