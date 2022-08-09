package com.example.hilt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/*
    activity에서는 @AndroidEntryPoint 어노테이션을 사용합니다.
    이 어노테이션은 Hilt가 제공하는 종속항목을 사용하기 위해서 필요합니다.
    이제 MainActivity에서 NoteViewModel을 Hilt로 부터 제공받기 때문에 종속성이 해결되었음을 알 수 있습니다.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.save_btn).setOnClickListener {
            noteViewModel.saveNote(
                title = findViewById<EditText>(R.id.note_title).text.toString(),
                description = findViewById<EditText>(R.id.note_description).text.toString()
            )
        }

        noteViewModel.note.observe(this, {
            findViewById<EditText>(R.id.note_title).setText(it.title)
            findViewById<EditText>(R.id.note_description).setText(it.description)
        })
    }
}