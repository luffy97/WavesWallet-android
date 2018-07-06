package com.wavesplatform.wallet.v2.ui.home.profile.address_book.add

import com.arellomobile.mvp.InjectViewState
import com.wavesplatform.wallet.v2.ui.base.presenter.BasePresenter
import javax.inject.Inject

@InjectViewState
class AddAddressPresenter @Inject constructor() : BasePresenter<AddAddressView>() {
    var nameFieldValid = false
    var addressFieldValid = false

    fun isAllFieldsValid(): Boolean {
        return nameFieldValid  && addressFieldValid
    }
}