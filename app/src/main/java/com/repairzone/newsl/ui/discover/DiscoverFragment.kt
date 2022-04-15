package com.repairzone.newsl.ui.discover

import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.repairzone.newsl.R
import com.repairzone.newsl.data.network.base.Resource
import com.repairzone.newsl.data.network.model.Articles
import com.repairzone.newsl.data.repository.ArticlesAction
import com.repairzone.newsl.data.repository.ArticlesEvent
import com.repairzone.newsl.databinding.FragmentDiscoverBinding
import com.repairzone.newsl.ui.base.BaseFragment
import com.repairzone.newsl.ui.base.ObservableListChangeCallback
import com.repairzone.newsl.ui.base.RecyclerViewAdapter
import com.repairzone.newsl.ui.webview.WebViewActivity
import com.repairzone.newsl.utils.ext.collectIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@FlowPreview
@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by viewModels()

    private val modelJustYou = ObservableArrayList<Articles>()
    private val adapterJustYou: RecyclerViewAdapter<Articles> by lazy {
        RecyclerViewAdapter(
            modelJustYou,
            R.layout.item_just_you_news,
            this::onItemClick
        )
    }

    override fun FragmentDiscoverBinding.initialize() {
        binding.edSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_GO){
                binding.rvJustForYou.visibility = View.GONE
                binding.shimmerJustYou.shimmerListJustYou.visibility = View.VISIBLE
                viewModel.dispatch(ArticlesAction.GetEveryThing(language = "id", sortBy = "relevancy", q = textView.text.toString()))
                binding.edSearch.clearFocus()
                return@setOnEditorActionListener true
            }
            false
        }
        viewModel.dispatch(ArticlesAction.GetEveryThing(language = "id", sortBy = "relevancy", q = "a"))
        binding.rvJustForYou.adapter = adapterJustYou
        modelJustYou.addOnListChangedCallback(ObservableListChangeCallback<Articles>(adapterJustYou))
        viewModel.eventFlow.collectIn(this@DiscoverFragment){ event ->
            when(event){
                is ArticlesEvent.ForYouSuccess -> {
                    event.data.collectIn(this@DiscoverFragment){
                        when(it){
                            is Resource.Success -> {
                                binding.rvJustForYou.visibility = View.VISIBLE
                                binding.shimmerJustYou.shimmerListJustYou.visibility = View.GONE
                                modelJustYou.clear()
                                it.data?.let { it1 -> modelJustYou.addAll(it1) }
                            }
                            is Resource.Error -> {
                                Snackbar.make(binding.root, it.error.toString(), Snackbar.LENGTH_SHORT).show()
                            }
                            is Resource.Loading -> {
                                binding.rvJustForYou.visibility = View.GONE
                                binding.shimmerJustYou.shimmerListJustYou.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                is ArticlesEvent.Failed -> {
                    event.throwable.printStackTrace()
                    Snackbar.make(binding.root, event.throwable.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onItemClick(type: Int, articles: Articles?, i: Int) {
        articles?.let {
            startActivity(
                WebViewActivity.newInstance(requireContext(), it.title!!, it.url!!)
            )
        }
    }
}