package com.repairzone.newsl.ui.home

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.snackbar.Snackbar
import com.repairzone.newsl.R
import com.repairzone.newsl.data.network.base.Resource
import com.repairzone.newsl.data.network.model.Articles
import com.repairzone.newsl.data.repository.ArticlesAction
import com.repairzone.newsl.data.repository.ArticlesEvent
import com.repairzone.newsl.databinding.FragmentHomeBinding
import com.repairzone.newsl.ui.base.BaseFragment
import com.repairzone.newsl.ui.base.ObservableListChangeCallback
import com.repairzone.newsl.ui.base.PagedRecyclerAdapter
import com.repairzone.newsl.ui.base.RecyclerViewAdapter
import com.repairzone.newsl.ui.webview.WebViewActivity
import com.repairzone.newsl.utils.ext.collectIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest


@OptIn(FlowPreview::class)
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()

    private val modelHeadlines = ObservableArrayList<Articles>()
    private val adapterHeadlines: RecyclerViewAdapter<Articles> by lazy {
        RecyclerViewAdapter(
            modelHeadlines,
            R.layout.item_headline_news,
            this::onItemClick
        )
    }

    private var diffUtil: DiffUtil.ItemCallback<Articles> = object : DiffUtil.ItemCallback<Articles>(){
        override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem == newItem
        }
    }
    private val adapterJustYou: PagedRecyclerAdapter<Articles> by lazy {
        PagedRecyclerAdapter(
            R.layout.item_just_you_news,
            R.layout.shimmer_footer_everything,
            diffUtil,
            this::onItemClick
        )
    }

    override fun FragmentHomeBinding.initialize() {
        viewModel.dispatch(ArticlesAction.GetHeadlines(country = "id", null))
        viewModel.dispatch(ArticlesAction.GetPagingEveryThing(language = "id", sortBy = "publishedAt", q = "a"))
        binding.rvHeadlines.adapter = adapterHeadlines
        binding.rvJustForYou.adapter = adapterJustYou
        modelHeadlines.addOnListChangedCallback(ObservableListChangeCallback<Articles>(adapterHeadlines))
        viewModel.eventFlow.collectIn(viewLifecycleOwner){ event ->
            when(event){
                is ArticlesEvent.HeadlineSuccess -> {
                    event.data.collectIn(this@HomeFragment){
                        when(it){
                            is Resource.Success -> {
                                binding.rvHeadlines.visibility = View.VISIBLE
                                binding.shimmerHeadline.shimmerListHeadline.visibility = View.GONE
                                modelHeadlines.clear()
                                it.data?.let { it1 -> modelHeadlines.addAll(it1) }
                            }
                            is Resource.Error -> {
                                Snackbar.make(binding.root, it.error.toString(), Snackbar.LENGTH_SHORT).show()
                            }
                            is Resource.Loading -> {
                                binding.rvHeadlines.visibility = View.GONE
                                binding.shimmerHeadline.shimmerListHeadline.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                is ArticlesEvent.EverythingPagingSuccess -> {
                    binding.rvJustForYou.visibility = View.VISIBLE
                    binding.shimmerJustYou.shimmerListJustYou.visibility = View.GONE
                    viewModel.launchPagingAsync({
                        event.data
                    }){
                        launchOnLifecycleScope {
                            it.cachedIn(viewModel.viewModelScope)
                                .collectLatest{
                                    adapterJustYou.submitData(it)
                                }
                        }}
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