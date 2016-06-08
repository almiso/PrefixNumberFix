package org.almiso.prefixnumberfix.core;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.TextUtils;

import org.almiso.prefixnumberfix.R;
import org.almiso.prefixnumberfix.model.Contact;
import org.almiso.prefixnumberfix.util.AndroidUtilities;
import org.almiso.prefixnumberfix.util.Logger;
import org.almiso.prefixnumberfix.util.PhoneFormat;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alexandr on 08.06.16.
 */
public class ContactsController {

    private static final String TAG = Logger.makeLogTag(ContactsController.class);

    private String[] projectionPhones = {
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.LABEL
    };

    private String[] projectionNames = {
            ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID,
            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
            ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
            ContactsContract.Data.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME
    };

    private boolean contactsBookLoaded = false;
    public HashMap<Integer, Contact> contactsBook = new HashMap<>();


    private static volatile ContactsController Instance = null;

    public static ContactsController getInstance() {
        ContactsController localInstance = Instance;
        if (localInstance == null) {
            synchronized (ContactsController.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new ContactsController();
                }
            }
        }
        return localInstance;
    }

    private ContactsController() {
        init();
    }


    public HashMap<Integer, Contact> getContactsBook() {
        return contactsBook;
    }

    public void onPermissionGranded(){
        contactsBookLoaded = false;
        init();
    }


    /* private mehods */

    private void init() {
        if (!contactsBookLoaded) {
            return;
        }

        AndroidUtilities.globalQueue.postRunnable(new Runnable() {
            @Override
            public void run() {
                final HashMap<Integer, Contact> contactsMap = readContactsFromPhoneBook();

                AndroidUtilities.stageQueue.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        contactsBook = contactsMap;
                        contactsBookLoaded = true;
                    }
                });
            }
        });
    }

    private HashMap<Integer, Contact> readContactsFromPhoneBook() {
        HashMap<Integer, Contact> contactsMap = new HashMap<>();
        boolean hasError = false;
        try {
            if (!hasContactsPermission()) {
                return contactsMap;
            }

            ContentResolver cr = PrefixNumberApplication.applicationContext.getContentResolver();

            HashMap<String, Contact> shortContacts = new HashMap<>();
            ArrayList<Integer> idsArr = new ArrayList<>();
            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projectionPhones, null, null, null);
            if (pCur != null) {
                if (pCur.getCount() > 0) {
                    while (pCur.moveToNext()) {
                        String number = pCur.getString(1);
                        if (number == null || number.length() == 0) {
                            continue;
                        }
                        number = PhoneFormat.stripExceptNumbers(number, true);
                        if (number.length() == 0) {
                            continue;
                        }

                        String shortNumber = number;

                        if (number.startsWith("+")) {
                            shortNumber = number.substring(1);
                        }

                        if (shortContacts.containsKey(shortNumber)) {
                            continue;
                        }

                        Integer id = pCur.getInt(0);
                        if (!idsArr.contains(id)) {
                            idsArr.add(id);
                        }

                        int type = pCur.getInt(2);
                        Contact contact = contactsMap.get(id);
                        if (contact == null) {
                            contact = new Contact();
                            contact.first_name = "";
                            contact.last_name = "";
                            contact.id = id;
                            contactsMap.put(id, contact);
                        }

                        contact.shortPhones.add(shortNumber);
                        contact.phones.add(number);
                        contact.phoneDeleted.add(0);

                        if (type == ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM) {
                            contact.phoneTypes.add(pCur.getString(3));
                        } else if (type == ContactsContract.CommonDataKinds.Phone.TYPE_HOME) {
                            contact.phoneTypes.add(PrefixNumberApplication.applicationContext.getString(R.string.st_phone_home));
                        } else if (type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                            contact.phoneTypes.add(PrefixNumberApplication.applicationContext.getString(R.string.st_phone_mobile));
                        } else if (type == ContactsContract.CommonDataKinds.Phone.TYPE_WORK) {
                            contact.phoneTypes.add(PrefixNumberApplication.applicationContext.getString(R.string.st_phone_work));
                        } else if (type == ContactsContract.CommonDataKinds.Phone.TYPE_MAIN) {
                            contact.phoneTypes.add(PrefixNumberApplication.applicationContext.getString(R.string.st_phone_main));
                        } else {
                            contact.phoneTypes.add(PrefixNumberApplication.applicationContext.getString(R.string.st_phone_otherr));
                        }
                        shortContacts.put(shortNumber, contact);
                    }
                }
                pCur.close();
            }
            String ids = TextUtils.join(",", idsArr);


            pCur = cr.query(ContactsContract.Data.CONTENT_URI, projectionNames, ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " IN (" + ids + ") AND " + ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE + "'", null, null);
            if (pCur != null && pCur.getCount() > 0) {
                while (pCur.moveToNext()) {
                    int id = pCur.getInt(0);
                    String fname = pCur.getString(1);
                    String sname = pCur.getString(2);
                    String sname2 = pCur.getString(3);
                    String mname = pCur.getString(4);
                    Contact contact = contactsMap.get(id);
                    if (contact != null && contact.first_name.length() == 0 && contact.last_name.length() == 0) {
                        contact.first_name = fname;
                        contact.last_name = sname;
                        if (contact.first_name == null) {
                            contact.first_name = "";
                        }
                        if (mname != null && mname.length() != 0) {
                            if (contact.first_name.length() != 0) {
                                contact.first_name += " " + mname;
                            } else {
                                contact.first_name = mname;
                            }
                        }
                        if (contact.last_name == null) {
                            contact.last_name = "";
                        }
                        if (contact.last_name.length() == 0 && contact.first_name.length() == 0 && sname2 != null && sname2.length() != 0) {
                            contact.first_name = sname2;
                        }
                    }
                }
                pCur.close();
            }


            try {
                pCur = cr.query(ContactsContract.RawContacts.CONTENT_URI, new String[]{"display_name", ContactsContract.RawContacts.SYNC1, ContactsContract.RawContacts.CONTACT_ID}, ContactsContract.RawContacts.ACCOUNT_TYPE + " = " + "'com.whatsapp'", null, null);
                if (pCur != null) {
                    while ((pCur.moveToNext())) {
                        String phone = pCur.getString(1);
                        if (phone == null || phone.length() == 0) {
                            continue;
                        }
                        boolean withPlus = phone.startsWith("+");
                        phone = AndroidUtilities.parseIntToString(phone);
                        if (phone == null || phone.length() == 0) {
                            continue;
                        }
                        String shortPhone = phone;
                        if (!withPlus) {
                            phone = "+" + phone;
                        }

                        if (shortContacts.containsKey(shortPhone)) {
                            continue;
                        }

                        String name = pCur.getString(0);

                        // TODO: 08.06.16 check this situation
                        if (name == null || name.length() == 0) {
                            Logger.d(TAG, "Dont know what to do ((");
//                            name = PhoneFormat.getInstance().format(phone);
                        }

                        Contact contact = new Contact();
                        contact.first_name = name;
                        contact.last_name = "";
                        contact.id = pCur.getInt(2);
                        contactsMap.put(contact.id, contact);

                        contact.phoneDeleted.add(0);
                        contact.shortPhones.add(shortPhone);
                        contact.phones.add(phone);
                        contact.phoneTypes.add(PrefixNumberApplication.applicationContext.getString(R.string.st_phone_mobile));
                        shortContacts.put(shortPhone, contact);
                    }
                    pCur.close();
                }
            } catch (Exception e) {
                Logger.e(TAG, e);
                hasError = true;
            }


        } catch (Exception e) {
            contactsMap.clear();
            Logger.e(TAG, e);
            hasError = true;
        }

        if (!hasError) {
            contactsBookLoaded = true;
        }

        return contactsMap;
    }

    private boolean hasContactsPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            return PrefixNumberApplication.applicationContext.checkSelfPermission(android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
        }
        Cursor cursor = null;
        try {
            ContentResolver cr = PrefixNumberApplication.applicationContext.getContentResolver();
            cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projectionPhones, null, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                return false;
            }
        } catch (Exception e) {
            Logger.e(TAG, e);
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                Logger.e(TAG, e);
            }
        }
        return true;
    }

}
