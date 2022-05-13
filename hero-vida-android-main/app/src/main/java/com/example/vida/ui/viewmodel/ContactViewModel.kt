package com.example.vida.ui.viewmodel

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import com.example.vida.ui.model.Contact
import com.example.vida.ui.model.ContactRepository

class ContactViewModel(context: Context?) : BaseObservable() {
    private val contacts: ObservableArrayList<Contact>
    private val repository: ContactRepository
    fun getContacts(): List<Contact> {
        contacts.addAll(repository.fetchContacts())
        return contacts
    }

    init {
        contacts = ObservableArrayList()
        repository = ContactRepository(context!!)
    }
}