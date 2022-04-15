package com.repairzone.newsl.ui.discover

import androidx.lifecycle.viewModelScope
import com.repairzone.newsl.data.local.room.entity.ArticlesType
import com.repairzone.newsl.data.network.base.Resource
import com.repairzone.newsl.data.network.model.Articles
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
class DiscoverViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository
): BaseAndroidViewModel() {

    init {
        initArticles()
    }

    private fun initArticles(){
        actionFlow.filterIsInstance<ArticlesAction.GetEveryThing>()
            .flatMapMerge {
                flow {
                    with(it){
                        emit(articlesRepository.fetchEverything(language, q, SortBy.popularity, page, pageSize))
                    }
                }.catch {
                    events.send(ArticlesEvent.Failed(it))
                }.map {
                    events.send(ArticlesEvent.ForYouSuccess(it))
                }
            }.launchIn(viewModelScope)
    }
}