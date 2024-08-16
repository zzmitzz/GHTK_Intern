package com.example.contentprovider.data

import com.example.contentprovider.data.model.ContactSchema
import com.example.contentprovider.data.model.DetailContact

interface HeheMlemMlem {
    fun getDetailContact(id: String): Result<DetailContact>

    fun fetchContact(): Result<List<ContactSchema>>

    fun update(contact: ContactSchema): Result<Any>

    fun delete(contact: ContactSchema): Result<Any>

    fun insert(contact: ContactSchema): Result<Any>
}
