package com.example.contentprovider.data

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.ContactsContract
import android.util.Log
import com.example.contentprovider.data.model.ContactSchema
import com.example.contentprovider.data.model.DetailContact

class MyContactDataSource(
    private val contentResolver: ContentResolver,
) : HeheMlemMlem {
    override fun getDetailContact(id: String): Result<DetailContact> {
        val cursor =
            contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.CONTACT_ID + " = ?",
                arrayOf(id),
                null,
            )
        cursor?.let {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumberColumn =
                    it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                if (phoneNumberColumn != -1) {
                    val phoneNumber = it.getString(phoneNumberColumn)
                }
                Log.d("TAG", "getDetailContact:$phoneNumber")
            }
        }
        return Result.Success(DetailContact())
    }

    @SuppressLint("Range")
    override fun fetchContact(): Result<List<ContactSchema>> {
        val cursor =
            contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null,
            )
        val contacts: MutableList<ContactSchema> = mutableListOf()
        return try {
            cursor?.let {
                while (it.moveToNext()) {
                    val id = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))
                    val name =
                        it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    contacts.add(ContactSchema(id, name))
                }
            }
            cursor?.close()
            Result.Success(contacts)
        } catch (e: Exception) {
            Result.Fail(e)
        }
    }

    override fun update(contact: ContactSchema): Result<Any> = Result.Fail(NotImplementedError())

    override fun delete(contact: ContactSchema): Result<Any> = Result.Fail(NotImplementedError())

    override fun insert(contact: ContactSchema): Result<Any> = Result.Fail(NotImplementedError())
}
