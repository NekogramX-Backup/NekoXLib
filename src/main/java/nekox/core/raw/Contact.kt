@file:Suppress(
    "unused"
)

package nekox.core.raw

import kotlinx.coroutines.*
import td.TdApi.*
import nekox.core.client.*

/**
 * Adds a user to the contact list or edits an existing contact by their user identifier
 *
 * @contact - The contact to add or edit
 *            Phone number can be empty and needs to be specified only if known, vCard is ignored
 * @sharePhoneNumber - True, if the new contact needs to be allowed to see current user's phone number
 *                     A corresponding rule to userPrivacySettingShowPhoneNumber will be added if needed
 *                     Use the field UserFullInfo.need_phone_number_privacy_exception to check whether the current user needs to be asked to share their phone number
 */
suspend fun TdAbsHandler.addContact(
        contact: Contact? = null,
        sharePhoneNumber: Boolean
) = sync<Ok>(
        AddContact(
                contact,
                sharePhoneNumber
        )
)

suspend fun TdAbsHandler.addContactOrNull(
        contact: Contact? = null,
        sharePhoneNumber: Boolean
) = syncOrNull<Ok>(
        AddContact(
                contact,
                sharePhoneNumber
        )
)

fun TdAbsHandler.addContact(
        contact: Contact? = null,
        sharePhoneNumber: Boolean,
        block: (suspend CoroutineScope.(Ok) -> Unit)
) = send(
        AddContact(
                contact,
                sharePhoneNumber
        ), block = block
)

/**
 * Adds new contacts or edits existing contacts by their phone numbers
 * Contacts' user identifiers are ignored
 *
 * @contacts - The list of contacts to import or edit
 *             Contacts' vCard are ignored and are not imported
 */
suspend fun TdAbsHandler.importContacts(
        contacts: Array<Contact>
) = sync<ImportedContacts>(
        ImportContacts(
                contacts
        )
)

suspend fun TdAbsHandler.importContactsOrNull(
        contacts: Array<Contact>
) = syncOrNull<ImportedContacts>(
        ImportContacts(
                contacts
        )
)

fun TdAbsHandler.importContacts(
        contacts: Array<Contact>,
        block: (suspend CoroutineScope.(ImportedContacts) -> Unit)
) = send(
        ImportContacts(
                contacts
        ), block = block
)

/**
 * Removes users from the contact list
 *
 * @userIds - Identifiers of users to be deleted
 */
suspend fun TdAbsHandler.removeContacts(
        userIds: IntArray
) = sync<Ok>(
        RemoveContacts(
                userIds
        )
)

suspend fun TdAbsHandler.removeContactsOrNull(
        userIds: IntArray
) = syncOrNull<Ok>(
        RemoveContacts(
                userIds
        )
)

fun TdAbsHandler.removeContacts(
        userIds: IntArray,
        block: (suspend CoroutineScope.(Ok) -> Unit)
) = send(
        RemoveContacts(
                userIds
        ), block = block
)

/**
 * Returns the total number of imported contacts
 */
suspend fun TdAbsHandler.getImportedContactCount() = sync<Count>(
        GetImportedContactCount()
)

suspend fun TdAbsHandler.getImportedContactCountOrNull() = syncOrNull<Count>(
        GetImportedContactCount()
)

fun TdAbsHandler.getImportedContactCount(
        block: (suspend CoroutineScope.(Count) -> Unit)
) = send(
        GetImportedContactCount(), block = block
)

/**
 * Changes imported contacts using the list of current user contacts saved on the device
 * Imports newly added contacts and, if at least the file database is enabled, deletes recently deleted contacts
 * Query result depends on the result of the previous query, so only one query is possible at the same time
 *
 * @contacts - The new list of contacts, contact's vCard are ignored and are not imported
 */
suspend fun TdAbsHandler.changeImportedContacts(
        contacts: Array<Contact>
) = sync<ImportedContacts>(
        ChangeImportedContacts(
                contacts
        )
)

suspend fun TdAbsHandler.changeImportedContactsOrNull(
        contacts: Array<Contact>
) = syncOrNull<ImportedContacts>(
        ChangeImportedContacts(
                contacts
        )
)

fun TdAbsHandler.changeImportedContacts(
        contacts: Array<Contact>,
        block: (suspend CoroutineScope.(ImportedContacts) -> Unit)
) = send(
        ChangeImportedContacts(
                contacts
        ), block = block
)

/**
 * Clears all imported contacts, contact list remains unchanged
 */
suspend fun TdAbsHandler.clearImportedContacts() = sync<Ok>(
        ClearImportedContacts()
)

suspend fun TdAbsHandler.clearImportedContactsOrNull() = syncOrNull<Ok>(
        ClearImportedContacts()
)

fun TdAbsHandler.clearImportedContacts(
        block: (suspend CoroutineScope.(Ok) -> Unit)
) = send(
        ClearImportedContacts(), block = block
)
