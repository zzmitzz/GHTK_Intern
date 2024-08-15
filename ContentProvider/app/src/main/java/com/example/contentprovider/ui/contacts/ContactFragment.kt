package com.example.contentprovider.ui.contacts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.contentprovider.MainViewModel
import com.example.contentprovider.ViewModelFactory
import com.example.contentprovider.base.BaseFragment
import com.example.contentprovider.databinding.FragmentContactControllerBinding

class ContactFragment : BaseFragment<MainViewModel, FragmentContactControllerBinding>() {
    private val mAdapter by lazy {
        ContactItemAdapter()
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(requireActivity().application),
            )[MainViewModel::class.java]
    }

    override fun initBinding() {
        binding = FragmentContactControllerBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.fetchContact()
    }

    private fun initRecyclerView() {
        binding.recycler.apply {
            adapter = mAdapter
            setHasFixedSize(true)
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        }
    }

    override fun initObserver() {
        viewModel.contacts.observe(viewLifecycleOwner) {
            it?.let {
                mAdapter.setData(it)
            }
        }
    }

    override fun initListener() {
    }
}
