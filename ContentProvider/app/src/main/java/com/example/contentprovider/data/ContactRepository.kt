package com.example.contentprovider.data

import com.example.contentprovider.data.model.ContactSchema
import com.example.contentprovider.data.model.DetailContact
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepository(
    private val source: MyContactDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun fetchContact(): List<ContactSchema>? {
        var result: List<ContactSchema>? = null
        withContext(dispatcher) {
            when (source.fetchContact()) {
                is Result.Success -> {
                    result = (source.fetchContact() as Result.Success<List<ContactSchema>>).data
                }
                is Result.Fail -> {
                    result = null
                }
            }
        }
        return result
    }

    suspend fun getDetailContact(id: String): DetailContact? {
        var result: DetailContact? = null
        withContext(dispatcher) {
            when (source.getDetailContact(id)) {
                is Result.Success -> {
                    result = (source.getDetailContact(id) as Result.Success<DetailContact>).data
                }
                is Result.Fail -> {
                    result = null
                }
            }
        }
        return result
    }
}
