package com.architecturestudy.upbitmarket

import android.view.View
import android.widget.TextView
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.architecturestudy.data.upbit.UpbitRepository
import com.architecturestudy.data.upbit.UpbitTicker
import com.architecturestudy.data.upbit.source.UpbitDataSource
import com.architecturestudy.util.NumberFormatter

class UpbitViewModel( // TODO BaseViewModel 추가
    private val upBitRepository: UpbitRepository
) : UpbitDataSource.GetTickerCallback, BaseObservable() { // TODO callback 제거하기, Line break 하기

    var marketPriceList = ObservableField<List<Map<String, String>>>()
    var errMsg = ObservableField<Throwable>()
    var selectedTextView = ObservableField<TextView>() // TODO TextView 제거하기

    init {
        upBitRepository.getMarketPrice("KRW", this)
    }

    override fun onTickerLoaded(marketPrice: List<UpbitTicker>) {
        marketPriceList.set(NumberFormatter.convertTo(marketPrice))
    }

    override fun onDataNotAvailable(t: Throwable) {
        errMsg.set(t)
    }

    fun showMarketPrice(v: View) {
        if (v is TextView) {
            selectedTextView.set(v)
            upBitRepository.getMarketPrice(v.text.toString(), this)
        }
    }
}