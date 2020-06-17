package com.example.paginationactivity.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.afollestad.materialdialogs.MaterialDialog
import com.example.paginationactivity.R
import com.example.paginationactivity.ui.Injection
import com.example.paginationactivity.ui.adapter.ReposAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainFragment : Fragment() {

    private var searchJob: Job? = null

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: ReposAdapter

    private lateinit var errorDialog: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDialog()
        setUpAdapter()

        updateFilter("filter")
    }

    private fun setUpDialog() {
        errorDialog = MaterialDialog(requireContext()).apply {
            cancelable(false)
            title(text = "Error")
            message(text = "Error request! \nClick refresh.")
            positiveButton(res = R.string.action_refresh, click = {
                adapter.retry()
            })
        }
    }


    private fun setUpAdapter() {
        adapter = ReposAdapter { repo ->
            val action = MainFragmentDirections.nextStep()
            findNavController().navigate(action)
        }

        adapter.addLoadStateListener { loadState ->
            val progressState = when {
                loadState.refresh is LoadState.Loading -> {
                    loadState.refresh as LoadState.Loading
                }
                loadState.append is LoadState.Loading -> {
                    loadState.append as LoadState.Loading
                }
                loadState.prepend is LoadState.Loading -> {
                    loadState.prepend as LoadState.Loading
                }
                else -> {
                    null
                }
            }

            val errorState = when {
                loadState.refresh is LoadState.Error -> {
                    loadState.refresh as LoadState.Error
                }
                loadState.append is LoadState.Error -> {
                    loadState.append as LoadState.Error
                }
                loadState.prepend is LoadState.Error -> {
                    loadState.prepend as LoadState.Error
                }
                else -> {
                    null
                }
            }

            vProgress?.isInvisible = progressState == null

            if (errorState != null) {
                errorDialog.show()
            }
        }

        list.adapter = adapter
    }

    private fun updateFilter(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepo(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}