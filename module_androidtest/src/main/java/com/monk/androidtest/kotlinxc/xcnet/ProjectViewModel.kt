package com.monk.androidtest.kotlinxc.xcnet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectViewModel:ViewModel() {

    val mProjectTreeLiveData = MutableLiveData<List<Any>>()

    fun loadProjectTree(){
        viewModelScope.launch(Dispatchers.IO){

        }
    }
}