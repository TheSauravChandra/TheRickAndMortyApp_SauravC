package com.saurav.therickandmorty_sauravc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saurav.therickandmorty_sauravc.beans.Creature
import com.saurav.therickandmorty_sauravc.repo.CreatureRepo
import kotlinx.coroutines.*

class CreatureViewModel(private val mainRepository: CreatureRepo) : ViewModel() {
    var pageNo = 1

    val errorMessage = MutableLiveData<String>()
    val creatureList = MutableLiveData(ArrayList<Creature>())
    val loading = MutableLiveData(false)
    var job: Job? = null

    fun getErr(): LiveData<String> = errorMessage
    fun getLoading(): LiveData<Boolean> = loading
    fun getCreatureList(): LiveData<ArrayList<Creature>> = creatureList

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getMoreCreatures() {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getAllCreatures(pageNo)

            if (response.isSuccessful) {
                response.body()?.creatures?.let {
                    ArrayList(creatureList.value).apply {
                        addAll(it)
                        creatureList.postValue(this)
                    }
                }
                ++pageNo
            } else {
                onError("Error : ${response.message()} ")
            }
            loading.postValue(false)
        }

    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}