package com.example.codingchallange.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AcromineDataItem(
    val lfs: List<Lf>,
    val sf: String
): Parcelable