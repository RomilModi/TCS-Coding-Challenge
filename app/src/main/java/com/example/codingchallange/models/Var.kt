package com.example.codingchallange.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Var(
    val freq: Int, val lf: String, val since: Int
) : Parcelable