package one.digitalinnovation.coroutines.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import one.digitalinnovation.coroutines.data.model.Address2

@Database(entities = arrayOf(Address2::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
}