package com.example.postcodesearch.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AddresDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(list: List<AddressData>)

    @Query("DELETE FROM AddressData")
    suspend fun deleteAllAddress()

    @Query("SELECT * FROM AddressData WHERE fullAddress GLOB '*' || :searchQuery || '*' OR fullAddress GLOB '*' || :searchQuery || '*' OR fullAddress  GLOB :searchQuery || '*' OR fullAddress  GLOB '*' || :searchQuery || '*' ")
    suspend fun searchAddress(searchQuery: String?): List<AddressData>

    @Query("SELECT * FROM AddressData")
    suspend fun getAllAddressFromRemote(): List<AddressData>
}