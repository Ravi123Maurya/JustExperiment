package com.example.justexperiment.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justexperiment.presentation.utils.Content
import com.example.justexperiment.presentation.utils.contentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchState(
    val searchText: String = "",
    val searchedItems: List<Content> = emptyList(),
    val isSearching: Boolean = false
)

sealed interface SearchEvent {
    data class OnSearchBarTextChange(val text: String) : SearchEvent
    data class OnSearchedItemClick(val id: Int) : SearchEvent
}

class SearchViewModel() : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearchBarTextChange -> {
                _state.update { it.copy(searchText = event.text) }
                if (event.text.length >= 2) {
                    search(event.text)
                }
            }

            is SearchEvent.OnSearchedItemClick -> {

            }
        }
    }


    private fun search(text: String) {
        viewModelScope.launch {
            delay(300)
            _state.update {
                it.copy(
                    searchedItems = contentList.filter { content ->
                        content.title.contains(text, ignoreCase = true) or content.description.contains(text, ignoreCase = true)
                    }
                )
            }
        }
    }


}