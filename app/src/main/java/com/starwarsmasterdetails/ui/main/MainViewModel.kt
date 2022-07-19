package com.starwarsmasterdetails.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.starwarsmasterdetails.models.Film
import com.starwarsmasterdetails.models.People
import com.starwarsmasterdetails.repository.StarWarsRepository
import com.starwarsmasterdetails.util.Constants.Companion.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val repository = StarWarsRepository()
    val selectedPeople: MutableLiveData<People> = MutableLiveData()
    val filterFlow = MutableStateFlow("")

    val peopleFlow: Flow<PagingData<People>> = filterFlow.flatMapMerge {
        Log.e(TAG, "peopleFlow: transform from $it" )
        repository.getPeopleByName(it)
            .cachedIn(viewModelScope)
    }

    val filmsFlow: Flow<PagingData<Film>> = repository.getAllFilms().cachedIn(viewModelScope)

    fun setSelectedPeople(people: People) {
        selectedPeople.value = people
    }

}
