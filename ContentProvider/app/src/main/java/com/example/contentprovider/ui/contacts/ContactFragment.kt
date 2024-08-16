package com.example.contentprovider.ui.contacts

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.contentprovider.MainViewModel
import com.example.contentprovider.R
import com.example.contentprovider.ViewModelFactory
import com.example.contentprovider.base.BaseFragment
import com.example.contentprovider.databinding.FragmentContactControllerBinding
import com.example.contentprovider.ui.detail.DetailFragment

class ContactFragment : BaseFragment<MainViewModel, FragmentContactControllerBinding>() {
    private val mAdapter by lazy {
        ContactItemAdapter()
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(
                requireActivity(),
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
        mAdapter.invoke = {
            viewModel.setContact(it)
            Log.d("TAG", "initRecyclerView: $it")
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment, DetailFragment())
                .commit()
        }
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
