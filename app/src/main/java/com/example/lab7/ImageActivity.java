package com.example.lab7;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btnBackImage;
    private TextView tvNowPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

         imageView = findViewById(R.id.imageView);
         btnBackImage = findViewById(R.id.btnBackImage);
        tvNowPlaying = findViewById(R.id.tvNowPlaying);

        Uri imageUri = getIntent().getData();
        imageView.setImageURI(imageUri);
        // Отображение названия файла
        tvNowPlaying.setText("Изображение: " + getFileName(imageUri));

        btnBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрываем активность и возвращаемся на предыдущий экран
            }
        });
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        if (displayNameIndex != -1) {
                            result = cursor.getString(displayNameIndex); // Получаем имя файла
                        }
                    }
                } finally {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment(); // Если имя не найдено, используем последнюю часть пути
        }
        return result != null ? result : "Неизвестный файл";
    }
}
