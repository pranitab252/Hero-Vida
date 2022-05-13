package com.example.vida.ui.dashboard

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vida.R
import com.example.vida.databinding.ActivityPanicAlertBinding
import com.example.vida.databinding.ContactDeleteSheetBinding
import com.example.vida.ui.dashboard.adapter.PanicContactRecyclerAdapter
import com.example.vida.ui.model.Contact
import com.example.vida.utils.extension.hide
import com.example.vida.utils.extension.show
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.Serializable

class PanicAlertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPanicAlertBinding
    private lateinit var panicAdapter: PanicContactRecyclerAdapter
    var contactList = ArrayList<Contact>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanicAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initializeView()
        setUpRecyclerView()
    }

    private fun getIntentData() {
        var isContactAdd: Boolean = false
        isContactAdd = intent.getBooleanExtra("fromContactListFragment", false)
        if (isContactAdd) {
            contactList = intent.getSerializableExtra("threeContact") as ArrayList<Contact>
        }
    }

    private fun initializeView() {
        binding.imgBackArrow.setOnClickListener {
            finish()
        }
        binding.fabAddContent.setOnClickListener {
            val intent = Intent(this, AllContentActivity::class.java)
            if (contactList.size > 0) {
                intent.putExtra("updateList", contactList as Serializable)
                intent.putExtra("isUpdateList", true)
            }
            startActivity(intent)
        }
        if (contactList.size > 0) {
            binding.tvAddUpTo3.hide()
            binding.llNoContactContent.hide()
            binding.rvPanicContact.show()
            binding.tvEmergencyContactList.text =
                "Emergency Contact List (${contactList.size.toString()}/3)"
            binding.fabAddContent.supportBackgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorMessageDisable))
            binding.fabAddContent.imageTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorUnSelectSeek))
        } else {
            binding.tvAddUpTo3.show()
            binding.llNoContactContent.show()
            binding.rvPanicContact.hide()
            binding.tvEmergencyContactList.text =
                "Emergency Contact List"
            binding.fabAddContent.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorRegisterButtonPrimary))
            binding.fabAddContent.imageTintList =
                ColorStateList.valueOf(resources.getColor(R.color.colorNero))
        }
    }

    private fun setUpRecyclerView() {
        panicAdapter = PanicContactRecyclerAdapter(this, contactList)
        binding.rvPanicContact.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvPanicContact.adapter = panicAdapter
        panicAdapter.setOnItemClickListener(object :
            PanicContactRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(contacts: Contact) {
                showBottomSheetDialog(contacts)
            }

        })
    }

    private fun showBottomSheetDialog(contact: Contact) {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.SheetDialog)
        val bottomBinding = ContactDeleteSheetBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomBinding.root)

        bottomBinding.tvDeleteContactName.text = "'${contact.name}'"

        bottomBinding.ivCrossContact.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomBinding.tvNo.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomBinding.tvYes.setOnClickListener {
            contactList.remove(contact)
            bottomSheetDialog.dismiss()
            panicAdapter.notifyDataSetChanged()
        }

        bottomSheetDialog.setOnDismissListener {
            // Instructions on bottomSheetDialog Dismiss
        }

        bottomSheetDialog.show()
    }
}