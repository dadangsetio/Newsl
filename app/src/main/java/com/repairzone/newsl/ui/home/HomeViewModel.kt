package com.repairzone.newsl.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.repairzone.newsl.data.repository.ArticlesAction
import com.repairzone.newsl.data.repository.ArticlesEvent
import com.repairzone.newsl.data.repository.ArticlesRepository
import com.repairzone.newsl.data.repository.SortBy
import com.repairzone.newsl.ui.base.BaseAndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
): BaseAndroidViewModel() {

    init {
        initArticles()
    }

    private fun initArticles(){
        actionFlow.filterIsInstance<ArticlesAction.GetHeadlines>()
            .flatMapMerge {
                flow {
                    emit(articlesRepository.fetchHeadlines(it.country, it.category))
                }.catch {
                    events.send(ArticlesEvent.Failed(it))
                }.map {
                    events.send(ArticlesEvent.HeadlineSuccess(it))
                }
            }.launchIn(viewModelScope)
        actionFlow.filterIsInstance<ArticlesAction.GetEveryThing>()
        .flatMapMerge {
            flow {
                with(it){
                    emit(articlesRepository.fetchEverything(language, q, SortBy.publishedAt, page, pageSize))
                }
            }.catch {
                events.send(ArticlesEvent.Failed(it))
            }.map {
                events.send(ArticlesEvent.ForYouSuccess(it))
            }
        }.launchIn(viewModelScope)
        actionFlow.filterIsInstance<ArticlesAction.GetPagingEveryThing>()
            .flatMapMerge {
                flow {
                    with(it){
                        emit(articlesRepository.fetchPagingEverything(language, q, SortBy.publishedAt))
                    }
                }.catch {
                    events.send(ArticlesEvent.Failed(it))
                }.map {
                    events.send(ArticlesEvent.EverythingPagingSuccess(it))
                }
            }.launchIn(viewModelScope)
    }
}