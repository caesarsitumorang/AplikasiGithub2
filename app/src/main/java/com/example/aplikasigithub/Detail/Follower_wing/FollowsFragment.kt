package com.example.aplikasigithub.Detail.Follower_wing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.aplikasigithub.Detail.DetailViewModel
import com.example.aplikasigithub.Model.ItemsItem
import com.example.aplikasigithub.Model.ResponseDetailUser
import com.example.aplikasigithub.R
import com.example.aplikasigithub.Result.Result
import com.example.aplikasigithub.UserAdapter
import com.example.aplikasigithub.databinding.FragmentFollowsBinding
import java.lang.annotation.ElementType
import java.sql.ResultSet

class FollowsFragment : Fragment() {

    private var binding :FragmentFollowsBinding? = null
    private val adapter by lazy {
        UserAdapter {

        }
    }

    private val viewModel by activityViewModels<DetailViewModel> ()
    var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFollows?.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@FollowsFragment.adapter
        }

        when(type) {
            FOLLOWERS -> {
                viewModel.resultFollowersUser.observe(viewLifecycleOwner, this ::manageResultFollows)
            }
            FOLLOWING -> {
                viewModel.resultFollowingUser.observe(viewLifecycleOwner, this::manageResultFollows)
            }
        }
    }
    private fun manageResultFollows(state: Result) {
        when(state) {
            is Result.Success<*> -> {
                adapter.setData(state.data as MutableList<ItemsItem>)
            }
            is Result.Error -> {
                Toast.makeText(requireActivity(),state.exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
            is Result.Loading -> {
                binding?.progressBar?.isVisible = state.isLoading
            }
        }
    }

    companion object {
        const val FOLLOWING = 100
        const val FOLLOWERS = 101

        fun newInstance(type : Int) = FollowsFragment()
            .apply {
                this.type=type
            }

    }
}