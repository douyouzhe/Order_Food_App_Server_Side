package com.example.youzhedou.order_food_app_server_side;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.youzhedou.order_food_app_server_side.Common.Common;
import com.example.youzhedou.order_food_app_server_side.Interface.ItemClickListener;
import com.example.youzhedou.order_food_app_server_side.Model.Category;
import com.example.youzhedou.order_food_app_server_side.Model.Food;
import com.example.youzhedou.order_food_app_server_side.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.Scanner;
import java.util.UUID;

import info.hoang8f.widget.FButton;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    RelativeLayout rootLayout;

    FloatingActionButton fab;

    FirebaseDatabase db;
    DatabaseReference foodList;
    FirebaseStorage storage;
    StorageReference storageReference;

    String categoryId = "";

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    MaterialEditText editFoodName, editFoodDescription, editFoodPrice, editFoodDiscount;
    FButton buttonSelect,buttonUpload;

    Food newFood;
    Uri saveUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        db = FirebaseDatabase.getInstance();
        foodList = db.getReference("Food");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFoodDialog();
            }
        });

        if(getIntent()!=null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        if(categoryId!=null){
            loadListFood(categoryId);
        }
    }

    private void showAddFoodDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(FoodList.this);
        alertDialog.setTitle("Add new Food");
        alertDialog.setMessage("Please fill information");

        LayoutInflater inflater = this.getLayoutInflater();
        View addFoodLayout = inflater.inflate(R.layout.add_new_food_layout,null);

        editFoodName=  addFoodLayout.findViewById(R.id.editFoodName);
        editFoodDescription=  addFoodLayout.findViewById(R.id.editFoodDescription);
        editFoodPrice=  addFoodLayout.findViewById(R.id.editFoodPrice);
        editFoodDiscount=  addFoodLayout.findViewById(R.id.editFoodDiscount);
        buttonSelect = addFoodLayout.findViewById(R.id.buttonSelect);
        buttonUpload = addFoodLayout.findViewById(R.id.buttonUpload);

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        alertDialog.setView(addFoodLayout);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton(Common.CREATE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                if(newFood!=null){
                    foodList.push().setValue(newFood);
                    //Toast.makeText(FoodList.this, "Category " +newFood.getName() +" added!", Toast.LENGTH_SHORT).show();
                    Snackbar.make(rootLayout,"New Food "+newFood.getName()+" added!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setNegativeButton(Common.CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadListFood(String categoryId) {

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("menuId").equalTo(categoryId)
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.foodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.foodImage);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //TODO
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void uploadImage() {

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Uploading");
        mDialog.show();

        String imageName = UUID.randomUUID().toString();
        final StorageReference imageFolder = storageReference.child("image/"+imageName);
        imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mDialog.dismiss();
                Toast.makeText(FoodList.this,"Image Uploaded",Toast.LENGTH_SHORT).show();
                imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        newFood = new Food(editFoodName.getText().toString(), uri.toString(),
                                editFoodDescription.getText().toString(),editFoodPrice.getText().toString(),
                                editFoodDiscount.getText().toString(),categoryId);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mDialog.dismiss();
                Toast.makeText(FoodList.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                mDialog.setMessage("Uploading "+ progress+"%");
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select a image"),Common.PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Common.PICK_IMAGE_REQUEST && resultCode== RESULT_OK
                &&data!=null && data.getData()!=null){

            saveUri = data.getData();
            buttonSelect.setText("Image Selected");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE)){
            showUpdateFoodDialog(adapter.getRef(item.getOrder()).getKey(),
                    adapter.getItem(item.getOrder()));

        }else  if(item.getTitle().equals(Common.DELETE)){

            deleteFood(adapter.getRef(item.getOrder()).getKey());

        }
        return super.onContextItemSelected(item);
    }

    private void deleteFood(String key) {
        foodList.child(key).removeValue();
    }

    private void showUpdateFoodDialog(final String key, final Food item) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(FoodList.this);
        alertDialog.setTitle("Edit Food");
        alertDialog.setMessage("Please fill information");

        LayoutInflater inflater = this.getLayoutInflater();
        View addFoodLayout = inflater.inflate(R.layout.add_new_food_layout,null);

        editFoodName=  addFoodLayout.findViewById(R.id.editFoodName);
        editFoodDescription=  addFoodLayout.findViewById(R.id.editFoodDescription);
        editFoodPrice=  addFoodLayout.findViewById(R.id.editFoodPrice);
        editFoodDiscount=  addFoodLayout.findViewById(R.id.editFoodDiscount);
        buttonSelect = addFoodLayout.findViewById(R.id.buttonSelect);
        buttonUpload = addFoodLayout.findViewById(R.id.buttonUpload);

        //set default value
        editFoodName.setText(item.getName());
        editFoodDescription.setText(item.getDescription());
        editFoodPrice.setText(item.getPrice());
        editFoodDiscount.setText(item.getDiscount());



        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage(item);
            }
        });

        alertDialog.setView(addFoodLayout);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton(Common.CREATE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                //foodList.push().setValue(newFood);
                item.setName(editFoodName.getText().toString());
                item.setPrice(editFoodPrice.getText().toString());
                item.setDescription(editFoodDescription.getText().toString());
                item.setDiscount(editFoodDiscount.getText().toString());

                foodList.child(key).setValue(item);

                //Toast.makeText(FoodList.this, "Category " +newFood.getName() +" added!", Toast.LENGTH_SHORT).show();
                Snackbar.make(rootLayout,"Food "+item.getName()+" updated!", Snackbar.LENGTH_SHORT).show();

            }
        });

        alertDialog.setNegativeButton(Common.CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private void changeImage(final Food item) {

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Uploading");
        mDialog.show();

        String imageName = UUID.randomUUID().toString();
        final StorageReference imageFolder = storageReference.child("image/"+imageName);
        imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mDialog.dismiss();
                Toast.makeText(FoodList.this,"Image Uploaded",Toast.LENGTH_SHORT).show();
                imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //newCategory = new Category(editName.getText().toString(), uri.toString());
                        item.setImage(uri.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mDialog.dismiss();
                Toast.makeText(FoodList.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                mDialog.setMessage("Uploading "+ progress+"%");
            }
        });
    }
}
