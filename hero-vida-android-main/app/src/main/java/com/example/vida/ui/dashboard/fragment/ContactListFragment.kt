package com.example.vida.ui.dashboard.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vida.databinding.FragmentContactListBinding
import com.example.vida.ui.dashboard.PanicAlertActivity
import com.example.vida.ui.dashboard.adapter.ContactAdapter
import com.example.vida.ui.model.Contact
import com.example.vida.ui.viewmodel.ContactViewModel
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 * Use the [ContactListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactListFragment : Fragment() {
    var selectedContactList = ArrayList<Contact>()
    private var contactViewModel: ContactViewModel? = null
    private var binding: FragmentContactListBinding? = null
    var adapter: ContactAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            selectedContactList =
                requireArguments().getSerializable("updateList") as ArrayList<Contact>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        contactViewModel = ContactViewModel(requireContext().applicationContext)
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        val view: View = binding!!.root
        initializeView()
        initRecyclerView()
        setUpSearch()
        return view
    }

    private fun initializeView() {
        binding!!.imgBackArrow.setOnClickListener {
            activity?.finish()
        }
        binding!!.tvContinue.setOnClickListener {
            val intent = Intent(requireActivity(), PanicAlertActivity::class.java)
            intent.putExtra("fromContactListFragment", true)
            intent.putExtra("threeContact", selectedContactList as Serializable)
            startActivity(intent)
            activity?.finish()

        }
    }

    private fun initRecyclerView() {
        binding!!.contactRecyclerView.layoutManager = LinearLayoutManager(
            binding!!.contactRecyclerView.context
        )
        adapter = ContactAdapter(binding!!.contactRecyclerView.context, selectedContactList)
        adapter!!.setContacts(contactViewModel!!.getContacts())
        binding!!.contactRecyclerView.adapter = adapter
        adapter!!.setOnItemClickListener(object : ContactAdapter.OnItemClickListener {
            override fun onItemClick(contacts: Contact) {
                if (selectedContactList.contains(contacts)) {
                    selectedContactList.remove(contacts)
                } else {
                    selectedContactList.add(contacts)
                    var name: String? = null
                    selectedContactList.forEach {
                        name = it.name
                    }
                    Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setUpSearch() {
        binding!!.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter!!.filter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ContactListFragment.
         */
        fun newInstance(): ContactListFragment {
            return ContactListFragment()
        }
    }
}