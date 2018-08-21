package com.wavesplatform.wallet.v2.ui.home.profile.addresses.alias

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wavesplatform.wallet.R
import com.wavesplatform.wallet.v2.data.model.remote.response.Alias
import com.wavesplatform.wallet.v2.util.copyToClipboard
import kotlinx.android.synthetic.main.aliases_layout.view.*
import pers.victor.ext.click
import pers.victor.ext.gone
import javax.inject.Inject

class AliasesAdapter @Inject constructor() : BaseQuickAdapter<Alias, BaseViewHolder>(R.layout.aliases_layout) {

    override fun convert(helper: BaseViewHolder, item: Alias) {
        helper.itemView.text_alias_name.text = item.alias
        helper.itemView.image_copy.click {
            it.copyToClipboard(helper.itemView.text_alias_name.text.toString(), R.drawable.ic_copy_18_submit_400)
        }

        if (data.indexOf(item) == data.size - 1) {
            helper.itemView.view_dashed.gone()
        }
    }
}
