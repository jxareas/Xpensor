package com.jxareas.xpensor.features.accounts.presentation.ui.adapter

import android.content.SharedPreferences
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.jxareas.xpensor.common.extensions.invoke
import com.jxareas.xpensor.databinding.ListItemAccountBinding
import com.jxareas.xpensor.features.accounts.presentation.model.AccountUi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountListAdapter @Inject constructor(
    private val preferences: SharedPreferences,
) : ListAdapter<AccountUi, AccountViewHolder>(
    AsyncDifferConfig.Builder(AccountDiffCallback).build()
) {

    private var onClickListener: AccountListAdapter.OnClickListener? = null

    class OnClickListener(val clickListener: (account: AccountUi) -> Unit) {
        fun onClick(account: AccountUi) = clickListener(account)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder =
        AccountViewHolder(
            preferences,
            parent invoke ListItemAccountBinding::inflate
        ).apply {
            val account by lazy { currentList[bindingAdapterPosition] }
            itemView.setOnClickListener {
                onClickListener?.onClick(account)
            }
        }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) =
        holder.bind(currentList[position])
}
