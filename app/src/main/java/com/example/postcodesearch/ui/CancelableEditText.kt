package com.example.postcodesearch.ui

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.example.postcodesearch.R

class CancelableEditText(context: Context, attrs: AttributeSet? = null) : LinearLayoutCompat(context, attrs) {

    private val editText: AppCompatEditText by lazy { findViewById(R.id.appCompatEditText) }
    private val cancelButton: Button by lazy { findViewById(R.id.button) }

    var onAfterTextChanged : AfterTextChanged? = null

    var text : String = ""
    set(value) {
        field = value
        editText.setText(value)
    }

    init {
        val inflatedView =  LayoutInflater.from(context).inflate(R.layout.view_cancelable_edit_text, this, false)
        this.addView(inflatedView)
        setup()
    }

    private fun setup () {
        editTextSetup()
        cancelButtonSetup()
    }

    private fun editTextSetup() {
        editText.doAfterTextChanged { text ->
            onAfterTextChanged?.onAfterTextChanged(text.toString())
            cancelButton.visibility = when (text.isNullOrBlank()) {
                true -> View.GONE
                false -> View.VISIBLE
            }
        }
    }

    private fun cancelButtonSetup() {
        cancelButton.setOnClickListener { editText.setText("") }
    }

    fun interface AfterTextChanged {
        fun onAfterTextChanged (text: String)
    }
}