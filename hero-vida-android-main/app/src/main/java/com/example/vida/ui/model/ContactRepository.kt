package com.example.vida.ui.model

import com.example.vida.ui.model.Contact
import android.provider.ContactsContract
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.vida.ui.model.ContactRepository
import java.util.ArrayList
import java.util.LinkedHashMap

class ContactRepository(private val context: Context) {
    private val contacts: List<Contact>
    fun fetchContacts(): List<Contact> {
        var contact: Contact
        //hold a list of Contacts without duplicates
        val cleanList: MutableMap<String, Contact> = LinkedHashMap()
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        if (cursor?.count ?: 0 > 0) {
            while (cursor!!.moveToNext()) {
                contact = Contact()
                @SuppressLint("Range") val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                @SuppressLint("Range") val phoneNo =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                @SuppressLint("Range") val photoUri =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                Log.e("contact", "getAllContacts: $name $phoneNo $photoUri")
                contact.name = name
                contact.phoneNumber = formatPhoneNumber(phoneNo)
                contact.photoUri = photoUri
                //contacts.add(contact);
                cleanList[contact.phoneNumber!!] = contact
            }
        }
        cursor?.close()
        return ArrayList(cleanList.values)
    }

    //Using LinkedHashMap to eliminate duplicate Contacts
    private fun clearListFromDuplicatePhoneNumber(list1: List<Contact>): List<Contact> {
        val cleanMap: MutableMap<String, Contact> = LinkedHashMap()
        for (i in list1.indices) {
            cleanMap[list1[i].phoneNumber!!] = list1[i]
        }
        return ArrayList(cleanMap.values)
    }

    companion object {
        //Format Phone Number
        private fun formatPhoneNumber(phone: String): String {
            var formatedPhone = phone.replace(" ".toRegex(), "")
            val phoneNumberLength = formatedPhone.length
            if (phoneNumberLength == 13) {
                formatedPhone = "0" + formatedPhone.substring(4)
            } else if (phoneNumberLength == 12) {
                formatedPhone = "0" + formatedPhone.substring(3)
            } else if (phoneNumberLength == 10) {
                return formatedPhone
            }
            return formatedPhone
        }
    }

    init {
        contacts = ArrayList()
    }
}