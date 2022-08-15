package com.example.fiappscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

class VarificationViewModel: ViewModel() {

    private var isPANvalid = MutableLiveData<Boolean>()
    var panLivedata: LiveData<Boolean> = isPANvalid

    private var isDOBvalid = MutableLiveData<Boolean>()
    var dobLivedata: LiveData<Boolean> = isDOBvalid

    fun pan_verification(number:String){

        CoroutineScope(Dispatchers.IO).launch {

            val s = number // get your editext value here

            val pattern: Pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")

            val matcher: Matcher = pattern.matcher(s)

            if (matcher.matches()) {
                isPANvalid.postValue(true)
            } else {
                isPANvalid.postValue(false)
            }
        }

    }

    fun DOBverification(date:Int, month:Int, year:Int){


        CoroutineScope(Dispatchers.IO).launch {
            val isleap = if (year % 4 == 0) {
                if (year % 100 == 0) {
                    // Century Year must be divisible by 400 for Leap year
                    year % 400 == 0
                } else true
            } else false

            if (isleap) {

                if (month <= 12 && month > 0) {

                    if (month % 2 == 0) {
                        if (month == 2) {
                            if (date > 0 && date <= 29) {
                                isDOBvalid.postValue(true)
                            } else {
                                isDOBvalid.postValue(false)
                            }
                        } else {
                            if (date > 0 && date <= 30) {
                                isDOBvalid.postValue(true)
                            } else {
                                isDOBvalid.postValue(false)
                            }
                        }
                    } else {
                        if (date > 0 && date <= 31) {
                            isDOBvalid.postValue(true)
                        } else {
                            isDOBvalid.postValue(false)
                        }
                    }
                } else {
                    isDOBvalid.postValue(false)
                }
            } else {
                if (month <= 12 && month > 0) {

                    if (month % 2 == 0) {
                        if (date > 0 && date <= 30) {
                            isDOBvalid.postValue(true)
                        } else {
                            isDOBvalid.postValue(false)
                        }
                    } else {
                        if (date > 0 && date <= 31) {
                            isDOBvalid.postValue(true)
                        } else {
                            isDOBvalid.postValue(false)
                        }
                    }
                } else {
                    isDOBvalid.postValue(false)
                }
            }
        }

    }


}