private fun initializeNumberPicker(picker: NumberPicker, maxNumber: Int, step: Int, callback: (Int)->Unit) {
    picker.displayedValues = (0..maxNumber).filter{it % step == 0}.map{ it.toString() }.toTypedArray()
    picker.minValue = 0
    picker.maxValue = maxNumber / step
    picker.wrapSelectorWheel = true
    picker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
    picker.setOnValueChangedListener {_, _, newValue -> callback(newValue)}
}
