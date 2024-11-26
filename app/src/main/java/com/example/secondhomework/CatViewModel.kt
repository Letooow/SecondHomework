package com.example.secondhomework

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CatViewModel : ViewModel() {
    private val _catData = mutableStateListOf<CatResponse?>(null)
    val catData: List<CatResponse?> = _catData

    private val _loadingIndex = mutableStateOf<Int?>(null)
    val loadingIndex: State<Int?> = _loadingIndex

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun fetchRandomCat() {

        val newElementIndex = catData.size
        _loadingIndex.value = newElementIndex
        _catData.add(null)
        _errorMessage.value = null

        viewModelScope.launch {

            try {
                val response = RetrofitController.api.getRandomCat(1)
                if (response.isNotEmpty()) {
                    _catData[newElementIndex] = (response[0])
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _catData.removeAt(newElementIndex)
            } finally {
                _loadingIndex.value = null
            }
        }
    }
}