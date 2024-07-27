package com.example.ghtk_intern_week2.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.ghtk_intern_week2.MainViewModel
import com.example.ghtk_intern_week2.R
import com.example.ghtk_intern_week2.base.BaseFragment
import com.example.ghtk_intern_week2.base.SpaceItemDecoration
import com.example.ghtk_intern_week2.databinding.FragmentHomeBinding
import com.example.ghtk_intern_week2.ui.adapter.AccountViewPagerAdapter
import com.example.ghtk_intern_week2.ui.adapter.ItemServicesAdapter
import com.example.ghtk_intern_week2.ui.adapter.ServiceItem
import com.example.ghtk_intern_week2.ui.screen.account.AccountViewModel
import com.example.ghtk_intern_week2.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AccountFragment : BaseFragment<AccountViewModel>() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private val serviceAdapter by lazy {
        ItemServicesAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun initListener() {

    }

    override fun initView() {
        serviceAdapter.apply {
            setList(serviceList())
        }
        binding.apply {
            services.adapter = serviceAdapter
            services.addItemDecoration(
                SpaceItemDecoration(15)
            )
        }
        viewPager = binding.viewPager2
        tabLayout = binding.tabLayout
        viewPager.adapter = AccountViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position){
                0 -> tab.text = "Hành trình"
                1 -> tab.text = "Hoạt động"
            }
        }.attach()
        viewModel.apply {
            fetchingData()
        }
    }
    override fun observeLiveData() {
        viewModel.userName.observe(this@AccountFragment){
            binding.name.text =  it
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory())[AccountViewModel::class.java]
        Log.d("TAG", viewModel.hashCode().toString())
    }

    private fun serviceList() : List<ServiceItem> {
        return listOf(
            ServiceItem(R.drawable.img1, "Nạp data"),
            ServiceItem(R.drawable.img2, "Di động trả trước"),
            ServiceItem(R.drawable.img3, "Bảo hiểm PVI"),
            ServiceItem(R.drawable.img4, "Hoá đơn điện"),
            ServiceItem(R.drawable.img1, "Hoá đơn nước"),
            ServiceItem(R.drawable.img2, "Hoá đơn Internet"),
            ServiceItem(R.drawable.img3, "Hoá đơn thuyết trình"),
            ServiceItem(R.drawable.img4, "Di động trả sau"),
            ServiceItem(R.drawable.img1, "LMAO"),
        )
    }
}