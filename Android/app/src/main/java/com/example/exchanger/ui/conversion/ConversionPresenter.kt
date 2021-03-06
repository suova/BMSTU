package com.example.exchanger.ui.conversion

import com.antonpriyma.android.exchanger.domain.conversion.models.Conversion
import com.example.exchanger.data.FromType
import com.example.exchanger.data.ToType
import com.example.exchanger.domain.conversion.GetConversionsListUsecase
import com.example.exchanger.domain.conversion.Params
import com.example.exchanger.mvp.CleanPresenter
import javax.inject.Inject

class ConversionPresenter @Inject constructor(private val getConversionsListUsecase: GetConversionsListUsecase) :
    CleanPresenter<ConversionView>() {

    override fun initialise() {
        getView()?.initialiseView()
        getView()?.showProgress()
        var list: List<ToType> = listOf(ToType.EUR)
        getConversionsListUsecase.execute(
            ConversionListObserver(this),
            Params(days = 10, fromType = FromType.BTC, toTypes = list)
        )
    }

    override fun disposeSubscriptions() {
        getConversionsListUsecase.dispose()
    }

    fun showArticleList(articlesList: List<Conversion>) {
        getView()?.hideProgress()
        getView()?.showArticleList(articlesList)
    }

    fun update(limit: Int?, toType: String, crypto: String) {
        getView()?.hideConversions()
        getView()?.showProgress()

        var newLimit: Int
        if (limit == null) {
            newLimit = 10
        } else {
            newLimit = limit
        }

        var converted: ToType = ToType.EUR
        when (toType) {
            "EUR" -> {
                converted = ToType.EUR
            }
            "USD" -> {
                converted = ToType.USD
            }
            "RUB" -> {
                converted = ToType.RUB
            }
        }

        var fromType: FromType = FromType.BTC
        when (crypto) {
            "BTC" -> {
                fromType = FromType.BTC
            }
            "ETH" -> {
                fromType = FromType.ETH
            }
        }
        var list: List<ToType> = listOf(converted)
        getConversionsListUsecase.execute(
            ConversionListObserver(this),
            Params(days = newLimit, fromType = fromType, toTypes = list)
        )
    }
}