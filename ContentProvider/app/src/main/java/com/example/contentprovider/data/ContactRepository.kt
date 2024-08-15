package com.example.contentprovider.data

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
}
