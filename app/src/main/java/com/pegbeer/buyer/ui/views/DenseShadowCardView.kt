package com.pegbeer.buyer.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

class DenseShadowCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val shadowPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        setWillNotDraw(false)
        shadowPaint.color = ContextCompat.getColor(context,android.R.color.black)
        shadowPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val shadowWidth = 16 // Ajusta el ancho de la sombra según tus necesidades
        val offsetX = 10 // Ajusta el desplazamiento en el eje X según tus necesidades

        canvas.drawRect(
            offsetX.toFloat(),
            (height - shadowWidth).toFloat(),
            (width + offsetX).toFloat(),
            height.toFloat(),
            shadowPaint
        )
    }
}
