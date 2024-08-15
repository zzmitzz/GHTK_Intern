package com.example.contentprovider.data

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.ContactsContract

class MyContactDataSource(
    private val contentResolver: ContentResolver,
) : HeheMlemMlem {
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
                    val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phone = it.getString(it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    contacts.add(ContactSchema(id, name, phone))
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
