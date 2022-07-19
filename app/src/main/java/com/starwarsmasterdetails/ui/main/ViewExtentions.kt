package com.starwarsmasterdetails.ui.main

import android.view.View


fun View.showView(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.INVISIBLE
}