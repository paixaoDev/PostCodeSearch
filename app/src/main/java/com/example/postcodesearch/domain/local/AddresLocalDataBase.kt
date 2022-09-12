package com.example.postcodesearch.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.postcodesearch.data.AddresDataDao
import com.example.postcodesearch.data.AddressData

@Database(
    entities = [AddressData::class],
    version = 3
)
abstract class AddressLocalDataBase : RoomDatabase() {
    abstract val dao: AddresDataDao
}