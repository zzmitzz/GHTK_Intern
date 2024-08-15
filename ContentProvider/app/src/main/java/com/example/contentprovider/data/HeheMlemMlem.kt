package com.example.contentprovider.data

interface HeheMlemMlem {
    fun fetchContact(): Result<List<ContactSchema>>

    fun update(contact: ContactSchema): Result<Any>

    fun delete(contact: ContactSchema): Result<Any>

    fun insert(contact: ContactSchema): Result<Any>
}
