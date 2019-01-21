package com.wavesplatform.wallet.v2.ui.home.wallet.leasing.start

import android.text.TextUtils
import com.arellomobile.mvp.InjectViewState
import com.wavesplatform.wallet.v2.data.model.remote.response.GlobalTransactionCommission
import com.wavesplatform.wallet.v2.data.model.remote.response.ScriptInfo
import com.wavesplatform.wallet.v2.data.model.remote.response.Transaction
import com.wavesplatform.wallet.v2.ui.base.presenter.BasePresenter
import com.wavesplatform.wallet.v2.util.RxUtil
import com.wavesplatform.wallet.v2.util.TransactionUtil
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class StartLeasingPresenter @Inject constructor() : BasePresenter<StartLeasingView>() {

    var nodeAddressValidation = false
    var amountValidation = false
    var recipientIsAlias = false
    var wavesAssetBalance: Long = 0
    var fee = 0L

    fun isAllFieldsValid(): Boolean {
        return nodeAddressValidation && amountValidation && fee > 0L
    }

    fun loadCommission(address: String?) {
        if (TextUtils.isEmpty(address)) {
            return
        }

        viewState.showCommissionLoading()

        addSubscription(Observable.zip(
                matcherDataManager.getGlobalCommission(),
                nodeDataManager.scriptAddressInfo(address!!),
                BiFunction { t1: GlobalTransactionCommission,
                            t2: ScriptInfo ->
                    return@BiFunction Pair(t1, t2)
                })
                .debounce(300, TimeUnit.MILLISECONDS)
                .compose(RxUtil.applyObservableDefaultSchedulers())
                .subscribe({ triple ->
                    val commission = triple.first
                    val scriptInfo = triple.second
                    val params = GlobalTransactionCommission.Params()
                    params.transactionType = Transaction.LEASE
                    params.smartAccount = scriptInfo.extraFee != 0L
                    fee = TransactionUtil.countCommission(commission, params)
                    viewState.showCommissionSuccess(fee)
                }, {
                    it.printStackTrace()
                    fee = 0L
                    viewState.showCommissionError()
                }))
    }

}
