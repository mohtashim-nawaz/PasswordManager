package com.starklabs.passwordmanager;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AccountAdapter extends
        RecyclerView.Adapter<AccountAdapter.ViewHolder>
{

    private List<Accounts> mAccounts;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView accountName;
        ImageView passCopy, passView, passDelete, passEdit;

        // To initiate the custom view of each item
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            accountName = itemView.findViewById(R.id.account_name_tv);
            passCopy = itemView.findViewById(R.id.account_pass_copy);
            passView = itemView.findViewById(R.id.account_pass_view);
            passEdit = itemView.findViewById(R.id.account_edit);
            passDelete = itemView.findViewById(R.id.account_delete);

            passCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = accountName.getText().toString().trim();
                    String pass = null;
                    for(Accounts obj: mAccounts)
                    {
                        if(obj.getAccountName().equals(name))
                        {
                            pass = obj.getPassword();
                        }
                    }
                    if(pass!=null)
                    {
                        ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Password", pass);
                        clipboardManager.setPrimaryClip(clip);
                        Toast.makeText(mContext, "Password copied to clipboard!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(mContext,"Unable to fetch password!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            passView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = accountName.getText().toString().trim();
                    String pass = null;
                    for (Accounts obj: mAccounts)
                    {
                        if(obj.getAccountName().equals(name))
                        {
                            pass = obj.getPassword();
                            break;
                        }
                    }
                    if(pass!=null)
                    {
                        try{
                            Intent intent = new Intent(mContext, ShowActivity.class);
                            intent.putExtra("Type",1);
                            intent.putExtra("name", name);
                            intent.putExtra("pass",pass);
                            mContext.startActivity(intent);
                        }
                        catch(Exception e)
                        {
                            Toast.makeText(mContext, "Error in displaying password", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    else
                    {
                        Toast.makeText(mContext,"Unable to fetch password!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            passEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = accountName.getText().toString().trim();
                    String pass = null;
                    for (Accounts obj: mAccounts)
                    {
                        if(obj.getAccountName().equals(name))
                        {
                            pass = obj.getPassword();
                        }
                    }
                    if(pass!=null)
                    {
                        Intent intent = new Intent(mContext, ShowActivity.class);
                        intent.putExtra("Type",2);
                        intent.putExtra("name", name);
                        intent.putExtra("pass",pass);
                        mContext.startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(mContext,"Unable to fetch password!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            passDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name = accountName.getText().toString().trim();
                    String pass = null;
                    for (Accounts obj: mAccounts)
                    {
                        if(obj.getAccountName().equals(name))
                        {
                            pass = obj.getPassword();
                        }
                    }
                    if(pass!=null)
                    {
                        Intent intent = new Intent(mContext, ShowActivity.class);
                        intent.putExtra("Type",3);
                        intent.putExtra("name", name);
                        intent.putExtra("pass",pass);
                        mContext.startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(mContext,"Unable to fetch password!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



    // Constructor to initialize the list
    public AccountAdapter(List<Accounts> accounts, Context context)
    {
        mAccounts = accounts;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get the layout inflater
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //inflate the custom layout
        View accountView = inflater.inflate(R.layout.account_element,parent,false);
        ViewHolder viewHolder = new ViewHolder(accountView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Accounts account = mAccounts.get(position);
        TextView textView = holder.accountName;
        textView.setText(account.getAccountName());
    }

    @Override
    public int getItemCount() {
        return mAccounts.size();
    }

}
