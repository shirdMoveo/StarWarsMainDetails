package com.starwarsmasterdetails.ui.main.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.starwarsmasterdetails.R
import com.starwarsmasterdetails.adapters.FilmsAdapter
import com.starwarsmasterdetails.adapters.PeopleAdapter
import com.starwarsmasterdetails.databinding.ListFragmentBinding
import com.starwarsmasterdetails.ui.main.MainViewModel
import com.starwarsmasterdetails.ui.main.showView
import com.starwarsmasterdetails.util.Constants.Companion.TAG
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var peopleAdapter: PeopleAdapter
    private lateinit var filmsAdapter: FilmsAdapter

    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMainRecyclerView()
        setUpFilmsRecyclerView()
        setSearchListener()
        subscribeObservers()
    }

    private fun setSearchListener() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val search = binding.etSearch.text.toString()
                lifecycleScope.launch {
                    viewModel.filterFlow.emit(search)
                }
            }
            false
        }
    }

    private fun setUpMainRecyclerView() {
        peopleAdapter = PeopleAdapter { people ->
            Log.d(TAG, "setUpRecyclerView: item clicked - $people")
            viewModel.setSelectedPeople(people)
            findNavController().navigate(R.id.action_listFragment_to_detailsFragment)
        }.apply {
            addLoadStateListener {
                Log.d(TAG, "setUpRecyclerView: addLoadStateListener: refresh = ${it.refresh}")
                binding.progressBar.showView(it.refresh == LoadState.Loading)
                val shouldShowEmptyView = it.append is LoadState.NotLoading
                            && it.append.endOfPaginationReached
                            && peopleAdapter.itemCount < 1
                binding.imageNoResult.showView(shouldShowEmptyView)
            }
        }
        binding.rvPeople.apply {
            adapter = peopleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setUpFilmsRecyclerView() {
        filmsAdapter = FilmsAdapter()
        binding.rvMoreInfo.apply {
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.peopleFlow.collectLatest {
                peopleAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.filmsFlow.collectLatest {
                filmsAdapter.submitData(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}