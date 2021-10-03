package one.digitalinnovation.coroutines.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import one.digitalinnovation.coroutines.data.model.Address2

@Dao
interface AddressDao {
    @Query("SELECT * FROM via_cep_address")
    suspend fun getAll(): List<Address2>

    @Insert
    suspend fun insertAll(vararg addresses: Address2)

    @Delete
    suspend fun delete(address: Address2)

}