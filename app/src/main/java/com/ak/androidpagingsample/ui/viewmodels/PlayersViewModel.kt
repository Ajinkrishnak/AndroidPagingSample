package com.ak.androidpagingsample.ui.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.ak.androidpagingsample.data.model.PlayerItem
import com.ak.androidpagingsample.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private var currentResult: Flow<PagingData<PlayerItem>>? = null

    fun searchPlayers(): Flow<PagingData<PlayerItem>> {
        val newResult: Flow<PagingData<PlayerItem>> =
            repository.getPlayers().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }
}