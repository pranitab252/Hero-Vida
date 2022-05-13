package com.example.vida.ui.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.vida.R
import com.example.vida.databinding.ActivityAllContentBinding
import com.example.vida.ui.dashboard.fragment.ContactListFragment
import com.example.vida.ui.model.Contact

class AllContentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllContentBinding
    private var contactList: ArrayList<Contact> = ArrayList()
    val REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initializeView()
    }

    private fun getIntentData() {
        var isUpdateList: Boolean = false
        isUpdateList = intent.getBooleanExtra("isUpdateList", false)
        if (isUpdateList) {
            contactList = intent.getSerializableExtra("updateList") as ArrayList<Contact>
        }
    }

    private fun initializeView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission("android.permission.READ_CONTACTS") == PackageManager.PERMISSION_GRANTED) {
                val fragment: Fragment = ContactListFragment.newInstance()
                val bundle = Bundle()


//                if (contactList.size > 0) {
                bundle.putParcelableArrayList("updateList", contactList)
//                }
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
                    .commitNow()
                return
            } else {
                if (!hasPhoneContactsPermission(Manifest.permission.READ_CONTACTS))
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.READ_CONTACTS
                        ), REQUEST_CODE
                    )
            }
            /*if (!hasPhoneContactsPermission(Manifest.permission.READ_CONTACTS))
                requestPermissions(
                arrayOf(
                    Manifest.permission.READ_CONTACTS
                ), REQUEST_CODE
            )*/
        } /*else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ContactListFragment.newInstance())
                .setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
                .commitNow()
        }*/


        /*binding.imgBackArrow.setOnClickListener {
            finish()
        }*/
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ContactListFragment.newInstance())
                .setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
                .commitNow()
        }
    }

    private fun hasPhoneContactsPermission(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hasPermission = ContextCompat.checkSelfPermission(applicationContext, permission)
            hasPermission == PackageManager.PERMISSION_GRANTED
        } else true
    }
}