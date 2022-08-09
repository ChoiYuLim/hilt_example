package com.example.hilt

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module // Hilt module
@InstallIn(SingletonComponent::class)   // Hilt 모듈을 설치할 안드로이드 컴포넌트를 나타냅니다. SingletonComponent는 Application에 설치한다는 의미
class DiModule {
    @Singleton  // Hilt로 제공할 컴포넌트가 Application 범위에 존재합니다. 즉 앱이 꺼질 때까지 유효합니다.
    @Provides   // 함수가 인스턴스를 제공함을 Hilt에 알려주는 역할을 합니다.
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room
            .databaseBuilder(
                context,
                NoteDatabase::class.java,
                NoteDatabase.DATABASE_NAME
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideNoteDAO(noteDB: NoteDatabase): NoteDAO {
        return noteDB.noteDao()
    }

    /*
        repository에서는 데이터 저장을 해야하기 때문에 noteDAO를 변수로 가져야 합니다.
     */
    @Singleton
    @Provides
    fun provideNoteRepository(
        noteDAO: NoteDAO
    ): NoteRepository {
        return NoteRepository(noteDAO)
    }
}

/*
    즉, 정리해보면 싱글톤 객체로 database와 dao가 제공됨을 알 수 있습니다.
    또한 @ApplicationContext를 통해 activity나 fragmet로부터 context를 주입하지 않아도 바로 사용할 수 있습니다.
 */