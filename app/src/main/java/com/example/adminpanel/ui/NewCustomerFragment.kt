package com.example.adminpanel.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.adminpanel.R
import com.example.adminpanel.api.model.Customer
import com.example.adminpanel.data.CustomerViewModel
import com.example.adminpanel.databinding.FragmentNewCustomerBinding
import com.example.adminpanel.util.RealPathUtil
import java.io.File
import java.util.*


private const val REQUEST_CODE_PICK_IMAGE = 101
private const val REQUEST_WRITE_PERMISSION = 786

class NewCustomerFragment : Fragment() {
    private lateinit var binding: FragmentNewCustomerBinding
    private val viewModel by lazy {
        ViewModelProvider(this).get(CustomerViewModel::class.java)
    }
    private var selectedImageUri: Uri? = null
    private lateinit var avatar: File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_new_customer, container, false)

        binding.buttonUploadPicture.setOnClickListener {
            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png")
                it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
            }
        }

        binding.buttonSaveCustomer.setOnClickListener {
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_PERMISSION)
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    binding.imageViewAvatar.setImageURI(selectedImageUri)
                    avatar = File(RealPathUtil.getRealPath(requireContext(), selectedImageUri!!))
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
            viewModel.addCustomer(
                Customer(
                    UUID.randomUUID().toString(),
                    binding.inputCustomerName.text.toString(),
                    binding.inputCustomerEmail.text.toString(),
                    "NO_PIC"
                ), avatar
            )
        }
    }
}