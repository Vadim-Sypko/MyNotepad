package com.example.notepad.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notepad.R;
import com.example.notepad.viewmodel.DatabaseHelper;

public class AddNotesActivity extends AppCompatActivity {
    // создание полей
    private EditText title, description;
    private Button addNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        // присваиваем  id полям
        title = findViewById(R.id.title_edit);
        description = findViewById(R.id.description_edit);
        addNote = findViewById(R.id.add_note);

        // обработка нажатия кнопки сохранения новой заметки
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //если исправленный текс не пустой то обновление записи в БД
                if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())){
                    //создание объекта БД в текущей активности
                    DatabaseHelper database = new DatabaseHelper(AddNotesActivity.this);
                    //создания записи в БД
                    database.addNotes(title.getText().toString(),description.getText().toString());

                    //создание намерения переключения активности
                    Intent intent = new Intent(AddNotesActivity.this,SecondActivity.class); // переключение обратно в активность демонстрации всех записей
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); //установление флага экономиии ресурсов
                    startActivity(intent);

                    finish(); // при нажатии на кнопку назад действие уничтожается и происходит переход на активность SecondActivity
                }else { // иначе тост об отсутствии изменений
                    Toast.makeText(AddNotesActivity.this, "Необходимо заполнить оба поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}