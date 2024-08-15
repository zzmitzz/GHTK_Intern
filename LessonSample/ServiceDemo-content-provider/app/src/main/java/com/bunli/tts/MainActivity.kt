package com.bunli.tts

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bunli.tts.provider.MyContentProvider
import com.bunli.tts.provider.MyContentProvider.Companion.COL_CODE
import com.bunli.tts.provider.MyContentProvider.Companion.CONTENT_URI
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //demo common content provider
        //uncomment to test
//        checkVideoPermissions()
//        checkContactPermissions()

        val edt = findViewById<EditText>(R.id.content_edt)

        findViewById<Button>(R.id.query_btn).setOnClickListener {
            queryData()
        }

        findViewById<Button>(R.id.insert_btn).setOnClickListener {
            insert(edt.text.toString())
        }

        findViewById<Button>(R.id.update_btn).setOnClickListener {
            update(edt.text.toString())
        }

        findViewById<Button>(R.id.delete_btn).setOnClickListener {
            delete(edt.text.toString())
        }
    }

    private fun update(value: String) {
        val id = Random.nextInt(0, 6)
        Log.e("@@@@@@@", "ID update: $id")
        val uri = Uri.parse("$CONTENT_URI/$id")
        contentResolver.update(uri, ContentValues().apply { put(COL_CODE, value) }, null, null)
        queryData()
    }

    private fun delete(id: String) {
        val uri = Uri.parse("$CONTENT_URI/$id")
        contentResolver.delete(uri, null, null)
        queryData()
    }

    private fun insert(value: String) {
        contentResolver.insert(
            CONTENT_URI,
            ContentValues().apply { put(COL_CODE, value) })
        queryData()
    }

    private fun queryData() {
        val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
        cursor?.let {
            var result = StringBuilder("")
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(MyContentProvider.COL_ID))
                val code = cursor.getString(cursor.getColumnIndex(COL_CODE))
                result
                    .append(id)
                    .append(" ").append(code).append("\n")
            }
            findViewById<TextView>(R.id.result_tv).text = result
        }
        cursor?.close()
    }

    private val REQUEST_READ_EXTERNAL_STORAGE = 1

    private fun checkVideoPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_VIDEO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_MEDIA_VIDEO),
                REQUEST_READ_EXTERNAL_STORAGE
            )
        } else {
            // Permission already granted, proceed with accessing media
            getAllVideos()
        }
    }

    private val REQUEST_WRITE_CONTACTS = 2

    private fun checkContactPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.WRITE_CONTACTS,
                    android.Manifest.permission.READ_CONTACTS
                ),
                REQUEST_WRITE_CONTACTS
            )
        } else {
            // Permission already granted, proceed with adding a contact
            deleteContact("John Doe", "1234567890")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with accessing media
                getAllVideos()
            } else {
                // Permission denied
            }
        } else if (requestCode == REQUEST_WRITE_CONTACTS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with adding a contact
                deleteContact("John Doe", "1234567890")
            } else {
                // Permission denied
            }
        }
    }

    private fun getAllVideos() {
        val contentResolver: ContentResolver = contentResolver
        val videoUri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }

        // Columns to retrieve
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DURATION
        )

        val selection = "${MediaStore.Video.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES).toString()
        )

        // Display videos in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"


        // Query the MediaStore
        contentResolver.query(videoUri, projection, selection, selectionArgs, sortOrder)
            ?.use { cursor ->
                val idIndex = cursor.getColumnIndex(MediaStore.Video.Media._ID)
                val displayNameIndex = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)
                val dataIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATA)
                val durationIndex = cursor.getColumnIndex(MediaStore.Video.Media.DURATION)

                while (cursor.moveToNext()) {
                    val id = cursor.getInt(idIndex)
                    val displayName = cursor.getString(displayNameIndex)
                    val data = cursor.getString(dataIndex)
                    val duration = cursor.getLong(durationIndex)

                    Log.e("VideoInfo", "ID: $id")
                    Log.e("VideoInfo", "Display Name: $displayName")
                    Log.e("VideoInfo", "Data: $data")
                    Log.e("VideoInfo", "Duration: $duration")
                }
            }
    }

    private fun addContact(name: String, phoneNumber: String) {
        val contentResolver: ContentResolver = contentResolver

        // Insert the new contact
        val rawContactUri =
            contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, ContentValues())
        val rawContactId = rawContactUri?.lastPathSegment?.toLong() ?: return

        // Insert the contact's name
        val nameValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name)
        }
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, nameValues)

        // Insert the contact's phone number
        val phoneValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
            put(
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
            )
        }
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, phoneValues)

        Log.e("ContactAdded", "Contact added: $name with phone number: $phoneNumber")
    }

    fun deleteContact(name: String, phoneNumber: String) {
        val contentResolver: ContentResolver = contentResolver

        // Query to find the contact ID by name
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
        val selection = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(name)

        contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            while (cursor.moveToNext()) {
                val contactId =
                    cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))

                // Delete the contact
                val contactUri =
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId)
                val deleteCount = contentResolver.delete(contactUri, null, null)

                if (deleteCount > 0) {
                    Log.e("ContactDeleted", "Contact deleted: $name")
                } else {
                    Log.e("ContactDeleted", "Failed to delete contact: $name")
                }
            }
        }
    }
}