package com.cesar.school.avaliacaofinal.ui.settings

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesar.school.avaliacaofinal.MainActivity
import com.cesar.school.avaliacaofinal.R

class SettingsViewModel : ViewModel() {
    var temp = MutableLiveData<Int>()
    var lang = MutableLiveData<Int>()
    var _temp = 0
    var _lang = 0

    internal fun setLists(tempUnit: Int, language: Int){
        if(tempUnit == 0){
            temp = MutableLiveData<Int>().apply {
                value = R.id.rbCelsius
            }
        } else {
            changeTemp(tempUnit)
        }

        if(language == 0){
            lang = MutableLiveData<Int>().apply {
                value = R.id.rbEn
            }
        } else {
            changeLang(language)
        }
    }

    internal fun changeTemp(id: Int){
        _temp = id
        temp.value = _temp
    }

    internal fun changeLang(id: Int){
        _lang = id
        lang.value = _lang
    }
}