package com.example.postcodesearch.data

import android.content.Context
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import okhttp3.ResponseBody
import java.io.File

const val FILE_NAME = "postalCodeFile"

@Entity
data class AddressData(
    var fullAddress: String = "",
    val localName: String,
    @PrimaryKey
    val postalCode: String
) {

    companion object {
        private var transformedList: MutableList<AddressData> = mutableListOf()

        fun saveFile(context: Context, body: ResponseBody) {
            if (!addressFileExists(context)) {
                context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
                    it.write(body.bytes())
                }
            } else {
                Log.e("Wtest", "File Already Downloaded")
            }
        }

        fun mapFileDataIntoObjectList(context: Context): MutableList<AddressData> {
            val file = File(context.filesDir, FILE_NAME)
            val readList = file.readLines()
            transformedList = mutableListOf()
            for (line in readList) {
                val dataSelected = line.split(",")
                transformedList.add(
                    AddressData(
                        fullAddress = "${dataSelected[dataSelected.lastIndex - 2]}-${dataSelected[dataSelected.lastIndex - 1]} ${dataSelected.last()}",
                        localName = dataSelected.last().toString(),
                        postalCode = "${dataSelected[dataSelected.lastIndex - 2]}-${dataSelected[dataSelected.lastIndex - 1]}"
                    )
                )
            }
            transformedList.removeAt(0)
            return transformedList
        }

        private fun addressFileExists(context: Context): Boolean {
            return context.fileList().contains(FILE_NAME)
        }
    }
}
