package com.jxareas.xpensor.ui.accounts.adapter

import android.content.SharedPreferences
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import com.jxareas.xpensor.databinding.ListItemAccountBinding
import com.jxareas.xpensor.domain.model.Account
import com.jxareas.xpensor.utils.invoke
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountsListAdapter @Inject constructor(
    private val preferences: SharedPreferences,
) : ListAdapter<Account, AccountsViewHolder>(
    AsyncDifferConfig.Builder(AccountDiffCallback).build()
) {

    private var onClickListener: AccountsListAdapter.OnClickListener? = null

    class OnClickListener(val clickListener: (account: Account) -> Unit) {
        fun onClick(account: Account) = clickListener(account)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder =
        AccountsViewHolder(preferences,
            parent invoke ListItemAccountBinding::inflate).apply {
            val account by lazy { currentList[bindingAdapterPosition] }
            itemView.setOnClickListener {
                onClickListener?.onClick(account)
            }
        }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }


    override fun onBindViewHolder(holder: AccountsViewHolder, position: Int) =
        holder.bind(currentList[position])

}