package com.example.codingchallange.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lf(
    val freq: Int,
    val lf: String,
    val since: Int,
    val vars: List<Var>
) : Parcelable