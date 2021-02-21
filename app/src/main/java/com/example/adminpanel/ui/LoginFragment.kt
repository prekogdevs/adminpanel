package com.example.adminpanel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.adminpanel.R
import com.example.adminpanel.api.model.Admin
import com.example.adminpanel.data.AdminViewModel
import com.example.adminpanel.databinding.FragmentLoginBinding
import com.example.adminpanel.util.UIUtils

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val adminViewModel by lazy {
        ViewModelProvider(this).get(AdminViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this
        adminViewModel.adminResponse.observe(viewLifecycleOwner, {
            if (it != null) {
                findNavController().navigate(R.id.action_loginFragment_to_customerFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.invalid_login_toast_text),
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        binding.loginButton.setOnClickListener {
            UIUtils.closeKeyboard(requireActivity())
            adminViewModel.login(
                Admin(
                    binding.inputEdtUsername.text.toString(),
                    binding.inputEdtPassword.text.toString()
                )
            )
        }
        return binding.root
    }
}