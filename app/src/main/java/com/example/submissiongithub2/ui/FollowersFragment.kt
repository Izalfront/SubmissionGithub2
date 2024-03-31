package com.example.submissiongithub2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissiongithub2.adapter.FollowersAdapter
import com.example.submissiongithub2.databinding.FragmentFollowersPageBinding
import com.example.submissiongithub2.viewmodel.DetailViewModel
import com.example.submissiongithub2.viewmodel.ViewModelFactory

class FollowersFragment : Fragment() {

    private var position = 0
    private var username: String = ""

    private lateinit var binding: FragmentFollowersPageBinding

    private lateinit var detailViewModel : DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = obtainDetailViewModel(requireActivity())

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: "Izalfront"
        }

        detailViewModel.getFollowing(username)
        detailViewModel.getFollower(username)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollower.layoutManager = layoutManager

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        if (position == 1) {
            detailViewModel.userFollower.observe(viewLifecycleOwner) {
                val adapter = FollowersAdapter()
                adapter.submitList(it)
                binding.rvFollower.adapter = adapter
            }
        } else {
            detailViewModel.userFollowing.observe(viewLifecycleOwner) {
                val adapter = FollowersAdapter()
                adapter.submitList(it)
                binding.rvFollower.adapter = adapter
            }
        }
    }

    companion object {
        const val ARG_USERNAME = "Izalfront"
        const val ARG_POSITION = "0"
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun obtainDetailViewModel(activity: FragmentActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(requireActivity(), factory)[DetailViewModel::class.java]
    }
}