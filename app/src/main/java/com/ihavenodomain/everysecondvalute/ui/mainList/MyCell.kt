package com.ihavenodomain.everysecondvalute.ui.mainList

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.R.attr.selectableItemBackground
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import com.ihavenodomain.everysecondvalute.R
import com.ihavenodomain.everysecondvalute.model.CurrencyRate
import com.ihavenodomain.everysecondvalute.ui.UI
import android.util.TypedValue



class MyCell : ConstraintLayout, UI<CurrencyRate> {

    private var data : CurrencyRate? = null
    private lateinit var text : TextView
    private lateinit var editText : TextView

    constructor(context : Context) : super(context){
        init()
    }
    constructor(context : Context, attr : AttributeSet) : super(context, attr){
        init()
    }
    constructor(context : Context, attr : AttributeSet, defStyleAttr : Int) : super(context, attr, defStyleAttr){
        init()
    }

    private fun init(){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.item_currency_merge, this, true)

        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        setBackgroundResource(outValue.resourceId)

        text = findViewById(R.id.tv_abbreviation)
        editText = findViewById(R.id.et_currency)
    }

    override fun setData(data: CurrencyRate) {
        this.data = data
    }

    override fun getData(): CurrencyRate? {
        return data
    }
}