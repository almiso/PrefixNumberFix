package org.almiso.prefixnumberfix.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import org.almiso.prefixnumberfix.R;
import org.almiso.prefixnumberfix.core.ContactsController;
import org.almiso.prefixnumberfix.model.Contact;
import org.almiso.prefixnumberfix.ui.cell.ContactCell;
import org.almiso.prefixnumberfix.ui.cell.EmptyCell;
import org.almiso.prefixnumberfix.ui.cell.base.Holder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexandr on 07.06.16.
 */
public class ActivityContacts extends AppCompatActivity {

    /* Controls */
    private UiAdapter adapter;
    private RecyclerView recyclerView;

    /* Fields */
    private int rowCount = 0;
    private int emptyContactsRow;

    private HashMap<Integer, Contact> contacts = new HashMap<>();

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /* set up view */
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        assert recyclerView != null;
        recyclerView.setHasFixedSize(false);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new UiAdapter(this);
        recyclerView.setAdapter(adapter);

        updateRowsIds();
    }

    private void updateRowsIds() {
        contacts.clear();

        rowCount = 0;
        emptyContactsRow = -1;

//        emptyContactsRow = rowCount++;

        HashMap<Integer,Contact> mapFromBook  = ContactsController.getInstance().getContactsBook();

        for (Map.Entry<Integer,Contact> entry : mapFromBook.entrySet()) {
            contacts.put(rowCount++, entry.getValue());
        }

    }

    private class UiAdapter extends RecyclerView.Adapter {

        private Context context;

        public UiAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = null;
            if (viewType == 0) {
                view = new EmptyCell(context);
            } else if (viewType == 1) {
                view = new ContactCell(context);
            }else if (viewType == 2) {
                view = new ContactCell(context);
            }


            if (view != null) {
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(lp);
            }
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder.getItemViewType() == 2) {
                ContactCell contactCell = (ContactCell) holder.itemView;

            }

        }


        @Override
        public int getItemViewType(int position) {
            if (position == emptyContactsRow) {
                return 1;
            } else if(contacts.containsKey(position)){
                return 2;
            }

            return 0;
        }
    }

}
