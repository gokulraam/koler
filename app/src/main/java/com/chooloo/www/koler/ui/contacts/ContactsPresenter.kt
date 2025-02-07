package com.chooloo.www.koler.ui.contacts

import android.Manifest.permission.WRITE_CONTACTS
import com.chooloo.www.koler.R
import com.chooloo.www.koler.adapter.ContactsAdapter
import com.chooloo.www.koler.contentresolver.ContactsContentResolver
import com.chooloo.www.koler.data.ListBundle
import com.chooloo.www.koler.data.account.Contact
import com.chooloo.www.koler.ui.list.ListContract
import com.chooloo.www.koler.ui.list.ListPresenter

class ContactsPresenter<V : ListContract.View<Contact>>(view: V) :
    ListPresenter<Contact, V>(view),
    ListContract.Presenter<Contact, V> {

    override val adapter by lazy { ContactsAdapter(boundComponent) }

    override val noResultsIconRes = R.drawable.round_people_24
    override val noResultsTextRes = R.string.error_no_results_contacts
    override val noPermissionsTextRes = R.string.error_no_permissions_contacts
    override val requiredPermissions = ContactsContentResolver.REQUIRED_PERMISSIONS


    private val contactsLiveData by lazy {
        boundComponent.liveDataFactory.allocContactsProviderLiveData()
    }


    override fun observeData() {
        contactsLiveData.observe(boundComponent.lifecycleOwner, this::onDataChanged)
    }

    override fun applyFilter(filter: String) {
        contactsLiveData.filter = filter
    }

    override fun onDeleteItems(items: ArrayList<Contact>) {
        boundComponent.permissionInteractor.runWithPermissions(arrayOf(WRITE_CONTACTS), {
            boundComponent.permissionInteractor.runWithPrompt(R.string.warning_delete_contacts) {
                items.forEach { boundComponent.contactsInteractor.deleteContact(it.id) }
            }
        })
    }

    override fun onItemClick(item: Contact) {
        view.showItem(item)
    }

    override fun convertDataToListBundle(data: ArrayList<Contact>) =
        ListBundle.fromContacts(data, true)
}