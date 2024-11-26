package com.example.secondhomework

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CatViewModel : ViewModel() {

    // Массив фотографий
    private val _catData = mutableStateListOf<Response?>(null)
    val catData: List<Response?> = _catData

    // Сотояние загрузки
    private val _loadingIndex = mutableStateOf<Int?>(null)
    val loadingIndex: State<Int?> = _loadingIndex


    // Сообщение Ошибки
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage


    // Взять изображние кота
    fun fetchRandomCat() {

        val newElementIndex = catData.size
        _loadingIndex.value = newElementIndex
        _catData.add(null)
        _errorMessage.value = null

        viewModelScope.launch {

            try {
                val response = CatRetrofitController.api.getRandomCat(1)
                if (response.isNotEmpty()) {
                    _catData[newElementIndex] = Response(
                        isGiphy = false, catResponse = response[0], giphyResponse = null)
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _catData.removeAt(newElementIndex)
            } finally {
                _loadingIndex.value = null
            }
        }
    }


    // Взять гифку кота
    fun fetchRandomGif() {

        val newElementIndex = catData.size
        _loadingIndex.value = newElementIndex
        _catData.add(null)
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val response = GiphyRetrofitController.api.getRandomGif(  "3QEPipprdmVmluQ3FUUFSzDotxmGJbpE")
                _catData[newElementIndex] = Response(
                    isGiphy = true,
                    catResponse = null,
                    giphyResponse = response
                )
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _catData.removeAt(newElementIndex)
            } finally {
                _loadingIndex.value = null
            }
        }
    }
}