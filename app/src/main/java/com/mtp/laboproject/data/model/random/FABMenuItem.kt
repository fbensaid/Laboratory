package com.hlab.fabrevealmenu.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

class FABMenuItem {
    var id = 0
    var title: String
    var iconDrawable: Drawable? = null
    var iconBitmap: Bitmap? = null
        private set
    var isEnabled = true

    constructor(title: String, iconDrawable: Drawable?) {
        this.title = title
        this.iconDrawable = iconDrawable
    }

    constructor(id: Int, title: String, iconDrawable: Drawable?) {
        this.id = id
        this.title = title
        this.iconDrawable = iconDrawable
    }

    constructor(title: String, iconBitmap: Bitmap?) {
        this.title = title
        this.iconBitmap = iconBitmap
    }

}