package com.album.kaktus.virtualid.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.album.kaktus.virtualid.Model.Contact;
import com.album.kaktus.virtualid.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ContactAdapter extends
        RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<Contact> mContacts;

    // Pass in the contact array into the constructor
    public ContactAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.contact_view, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Contact contact = mContacts.get(position);

        // Set item views based on your views and data model
        TextView txtNama = viewHolder.txtNama;
        txtNama.setText(contact.getName());
        TextView txtPhone = viewHolder.txtPhone;
        ArrayList<String> phone = contact.getNumber();
        int size = phone.size();
        String sPhone = "";
        for(int i=0;i<size;i++){
            if(i > 0){
                sPhone += "\n";
            }
            sPhone = sPhone + phone.get(i);
        }
        txtPhone.setText(sPhone);

        ImageView imgView = viewHolder.imgProfile;
        File imgFile = new  File(contact.getPicURI());

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgView.setImageBitmap(myBitmap);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView txtNama,txtPhone;
        public ImageView imgProfile;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            txtNama = itemView.findViewById(R.id.txtNama);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imgProfile = itemView.findViewById(R.id.imageProfile);
        }
    }
}
