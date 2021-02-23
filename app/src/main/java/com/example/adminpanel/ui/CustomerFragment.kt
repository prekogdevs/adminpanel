package com.example.adminpanel.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adminpanel.R
import com.example.adminpanel.adapter.CustomerAdapter
import com.example.adminpanel.adapter.OnDeleteClickListener
import com.example.adminpanel.api.model.Customer
import com.example.adminpanel.data.CustomerViewModel
import com.example.adminpanel.databinding.FragmentCustomerBinding
import com.example.adminpanel.util.RealPathUtil
import com.example.adminpanel.util.UIUtils
import java.io.File


private const val REQUEST_CODE_PICK_IMAGE = 101
private const val REQUEST_WRITE_PERMISSION = 786

class CustomerFragment : Fragment() {
    private lateinit var binding: FragmentCustomerBinding
    private val customerViewModel by lazy {
        ViewModelProvider(this).get(CustomerViewModel::class.java)
    }
    private var selectedImageUri: Uri? = null
    private lateinit var selectedPicture: File
    private var avatar = ""

    private val onDeleteClickListener = object : OnDeleteClickListener {
        override fun onClick(customer: Customer) {
            customerViewModel.deleteCustomer(customer)
        }
    }

    private val customerAdapter by lazy {
        CustomerAdapter(onDeleteClickListener)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_customer, container, false)
        binding.lifecycleOwner = this
        binding.buttonUploadPicture.setOnClickListener {
            requestPermissions(
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_PERMISSION
            )
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png")
                it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
            }
        }

        binding.buttonSaveCustomer.setOnClickListener {
            if (binding.inputCustomerName.text.toString()
                    .isNotEmpty() && binding.inputCustomerEmail.text.toString().isNotEmpty()
            ) {
                customerViewModel.addCustomer(
                    Customer(
                        binding.inputCustomerName.text.toString(),
                        binding.inputCustomerEmail.text.toString(),
                        avatar
                    )
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.username_and_email_is_mandatory_toast_text),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        customerViewModel.newCustomerResponse.observe(viewLifecycleOwner, {
            it?.let {
                UIUtils.closeKeyboard(requireActivity())
                binding.inputCustomerName.text?.clear()
                binding.inputCustomerName.clearFocus()
                binding.inputCustomerEmail.text?.clear()
                binding.inputCustomerEmail.clearFocus()
                binding.imageViewAvatar.setImageResource(0)
                customerViewModel.getCustomers()
            }

        })

        binding.customerRecyclerView.apply {
            adapter = customerAdapter
        }

        customerViewModel.allCustomersResponse.observe(viewLifecycleOwner, {
            customerAdapter.submitList(it)
        })

        customerViewModel.deletedCustomerResponse.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(
                    requireContext(),
                    "Customer with name: ${it.name} has been removed",
                    Toast.LENGTH_SHORT
                ).show()
                customerViewModel.getCustomers()
            }
        })
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    binding.imageViewAvatar.setImageURI(selectedImageUri)
                    selectedPicture =
                        File(RealPathUtil.getRealPath(requireContext(), selectedImageUri!!))
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            avatar = Base64.encodeToString(selectedPicture.readBytes(), Base64.DEFAULT)
        }
    }
}