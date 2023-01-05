package com.knotworking.inhabit.home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private var _count = 0
    val count: Int
        get() = _count
}