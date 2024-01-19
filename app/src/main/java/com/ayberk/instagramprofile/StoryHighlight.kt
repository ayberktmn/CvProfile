package com.ayberk.instagramprofile

import android.view.View.OnClickListener
import androidx.compose.ui.graphics.painter.Painter

data class ImageWithText(
    val image: Painter,
    val text: String,
)
