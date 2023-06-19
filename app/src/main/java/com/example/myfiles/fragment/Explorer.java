package com.example.myfiles.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfiles.File.FileAdapter;
import com.example.myfiles.File.FileOpener;
import com.example.myfiles.File.OnFileSelectedListener;
import com.example.myfiles.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Explorer extends Fragment implements OnFileSelectedListener {
    View view;
    private RecyclerView recyclerView;
    private List<File> fileList;
    private LinearLayout linearFichierRecents, linearImage, linearVideo, linearMusic, linearDocs,
            linearDownload, linearApks, internal_storage, sd_card_storage, categories, stockage,
            img_back, linearRecents_Files, bottomBar, linearRenommer, linearDetails, linearPartager,
            linearSupprimer, tv_categories, tv_stockages;
    private FileAdapter fileAdapter;

    File storage;
    String data;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.explorer, container, false);

        // ***** C A T E G O R I E S *****
            // Declaration des variables
            linearImage = view.findViewById(R.id.linearImage);
            linearVideo = view.findViewById(R.id.linearVideo);
            linearMusic = view.findViewById(R.id.linearMusic);
            linearDocs = view.findViewById(R.id.linearDocs);
            linearDownload = view.findViewById(R.id.linearDownload);
            linearApks = view.findViewById(R.id.linearApks);

            linearFichierRecents = view.findViewById(R.id.linearFichierRecents);
            categories = view.findViewById(R.id.categories);
            stockage = view.findViewById(R.id.stockage);
            tv_categories = view.findViewById(R.id.tv_categories);
            tv_stockages = view.findViewById(R.id.tv_stockages);

            img_back = view.findViewById(R.id.img_back);
            linearRecents_Files = view.findViewById(R.id.linearRecents_Files);

            bottomBar = view.findViewById(R.id.bottomBar);
            linearRenommer = view.findViewById(R.id.linearRenommer);
            linearDetails = view.findViewById(R.id.linearDetails);
            linearPartager = view.findViewById(R.id.linearPartager);
            linearSupprimer = view.findViewById(R.id.linearSupprimer);

            linearImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString("fileType", "image");
                    CategorizedFiles categorizedFragment = new CategorizedFiles();
                    categorizedFragment.setArguments(args);

                    getParentFragmentManager().beginTransaction().add(R.id.fragment_container, categorizedFragment).addToBackStack(null).commit();

                }

            });

            linearVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString("fileType", "video");
                    CategorizedFiles categorizedFragment = new CategorizedFiles();
                    categorizedFragment.setArguments(args);

                    getParentFragmentManager().beginTransaction().add(R.id.fragment_container, categorizedFragment).addToBackStack(null).commit();

                }
            });

            linearMusic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString("fileType", "music");
                    CategorizedFiles categorizedFragment = new CategorizedFiles();
                    categorizedFragment.setArguments(args);

                    getParentFragmentManager().beginTransaction().add(R.id.fragment_container, categorizedFragment).addToBackStack(null).commit();

                }
            });

            linearDocs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString("fileType", "docs");
                    CategorizedFiles categorizedFragment = new CategorizedFiles();
                    categorizedFragment.setArguments(args);

                    getParentFragmentManager().beginTransaction().add(R.id.fragment_container, categorizedFragment).addToBackStack(null).commit();

                }
            });

            linearDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString("fileType", "downloads");
                    CategorizedFiles categorizedFragment = new CategorizedFiles();
                    categorizedFragment.setArguments(args);

                    getParentFragmentManager().beginTransaction().add(R.id.fragment_container, categorizedFragment).addToBackStack(null).commit();
                }
            });

            linearApks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("fileType", "apk");
                CategorizedFiles categorizedFragment = new CategorizedFiles();
                categorizedFragment.setArguments(args);

                getParentFragmentManager().beginTransaction().add(R.id.fragment_container, categorizedFragment).addToBackStack(null).commit();

            }
        });


        // ***** S T O C K A G E S *****
            // Declaration des variables
            internal_storage = view.findViewById(R.id.internal_storage);
            sd_card_storage = view.findViewById(R.id.sd_card_storage);

            // Declaration des methodes
            internal_storage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InternalStorage internalStorage = new InternalStorage();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, internalStorage).addToBackStack(null).commit();
                }

            });

            sd_card_storage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SdCardStorage sdCardStorage = new SdCardStorage();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, sdCardStorage).addToBackStack(null).commit();
                }
            });

        runtimePermission();

        linearFichierRecents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRecentsFiles();
            }

        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearFichierRecents.setVisibility(View.VISIBLE);
                categories.setVisibility(View.VISIBLE);
                stockage.setVisibility(View.VISIBLE);
                tv_categories.setVisibility(View.VISIBLE);
                tv_stockages.setVisibility(View.VISIBLE);
                linearRecents_Files.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void displayRecentsFiles(){

        linearRecents_Files.setVisibility(View.VISIBLE);
        img_back.setVisibility(View.VISIBLE);
        linearFichierRecents.setVisibility(View.GONE);
        categories.setVisibility(View.GONE);
        stockage.setVisibility(View.GONE);
        tv_categories.setVisibility(View.GONE);
        tv_stockages.setVisibility(View.GONE);
        bottomBar.setVisibility(View.GONE);

        recyclerView = view.findViewById(R.id.recycler_recents);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fileList = new ArrayList<>();
        fileList.addAll(findFiles(Environment.getExternalStorageDirectory()));
        fileAdapter = new FileAdapter(getContext(), fileList, this);
        recyclerView.setAdapter(fileAdapter);


    }


    private void runtimePermission() {
        Dexter.withContext(getContext()).withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(new MultiplePermissionsListener(){

            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
              // displayFiles();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public ArrayList<File> findFiles(@NonNull File file){

        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        for (File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findFiles(singleFile));
            }
            else if(singleFile.getName().toLowerCase().endsWith(".jpeg") ||
                    singleFile.getName().toLowerCase().endsWith(".jpg") ||
                    singleFile.getName().toLowerCase().endsWith(".png") ||
                    singleFile.getName().toLowerCase().endsWith(".mp3") ||
                    singleFile.getName().toLowerCase().endsWith(".wav") ||
                    singleFile.getName().toLowerCase().endsWith(".mp4") ||
                    singleFile.getName().toLowerCase().endsWith(".pdf") ||
                    singleFile.getName().toLowerCase().endsWith(".doc") ||
                    singleFile.getName().toLowerCase().endsWith(".apk") ||
                    singleFile.getName().toLowerCase().endsWith(".zip"))
            {
                arrayList.add(singleFile);
            }
        }
        arrayList.sort(Comparator.comparing(File::lastModified).reversed());
        return arrayList;
    }

    private void displayFiles() {

        recyclerView = view.findViewById(R.id.recycler_internal_storage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fileList = new ArrayList<>();
        fileList.addAll(findFiles(Environment.getExternalStorageDirectory()));
        fileAdapter = new FileAdapter(getContext(), fileList, this);
        recyclerView.setAdapter(fileAdapter);
    }

    @Override
    public void onFileClicked(File file) {
        if (file.isDirectory()){
            Bundle bundle = new Bundle();
            bundle.putString("path", file.getAbsolutePath());
            InternalStorage internalStorageFragment = new InternalStorage();
            internalStorageFragment.setArguments(bundle);
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, internalStorageFragment).addToBackStack(null).commit();

        }
        else{
            try {
                FileOpener.openFile(getContext(), file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFileLongClicked(File file, int position) {

        bottomBar.setVisibility(View.VISIBLE);

        // Button Rename
        linearRenommer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (linearRenommer.isClickable() || linearRenommer.isFocused() || linearRenommer.isSelected()){
                    linearRenommer.setBackgroundColor(R.drawable.bg_bottom_bar_item_clicked);
                    linearDetails.setBackgroundColor(Color.BLACK);
                    linearPartager.setBackgroundColor(Color.BLACK);
                    linearSupprimer.setBackgroundColor(Color.BLACK);
                }
                else{
                    linearRenommer.setBackgroundColor(Color.BLACK);
                }

                final Dialog dialog = new Dialog(getContext());
                AlertDialog.Builder renameDialog = new AlertDialog.Builder(getContext());
                renameDialog.setTitle("Rénommer fichier '"+file.getName()+"' en : ");
                final EditText name = new EditText(getContext());
                renameDialog.setView(name);

                renameDialog.setPositiveButton("Rénommer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        String new_name = name.getEditableText().toString();
                        String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
                        File current = new File(file.getAbsolutePath());
                        File destination = new File(file.getAbsolutePath().replace(file.getName(), new_name)+extension);
                        if (current.renameTo(destination)){
                            fileList.set(position, destination);
                            fileAdapter.notifyItemChanged(position);
                            linearRenommer.setBackgroundColor(Color.BLACK);
                            Toast.makeText(getContext(), "Fichier rénommer avec succes!!!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), "Fichier non-rénommer", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                renameDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.cancel();
                        linearRenommer.setBackgroundColor(Color.BLACK);
                    }
                });
                AlertDialog alertDialog_rename = renameDialog.create();
                alertDialog_rename.show();
            }
        });


        //Button Details
        linearDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearDetails.isClickable() || linearDetails.isFocused() || linearDetails.isSelected()){
                    linearRenommer.setBackgroundColor(Color.BLACK);
                    linearDetails.setBackgroundColor(R.drawable.bg_bottom_bar_item_clicked);
                    linearPartager.setBackgroundColor(Color.BLACK);
                    linearSupprimer.setBackgroundColor(Color.BLACK);
                }
                else{
                    linearDetails.setBackgroundColor(Color.BLACK);
                }

                final Dialog dialog = new Dialog(getContext());
                AlertDialog.Builder detailDialog = new AlertDialog.Builder(getContext());
                detailDialog.setIcon(R.drawable.details);
                detailDialog.setTitle("Détails\n");
                final TextView detail = new TextView(getContext());
                detailDialog.setView(detail);
                Date lastModified = new Date(file.lastModified());
                SimpleDateFormat format = new SimpleDateFormat("dd/mm/yy hh:mm:ss");
                String formatDate = format.format(lastModified);

                detail.setText("\t\t Titre            \n\t\t  "+file.getName()+ "\n\n" +
                        "\t\t Taille           \n\t\t "+ Formatter.formatShortFileSize(getContext(), file.length())+ "\n\n" +
                        "\t\t Chemin       \n\t\t "+file.getAbsolutePath()+ "\n\n" +
                        "\t\t Date             \n\t\t "+formatDate);
                detail.setTextColor(Color.WHITE);

                detailDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        linearDetails.setBackgroundColor(Color.BLACK);
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog_details = detailDialog.create();
                alertDialog_details.show();
            }
        });


        //Button Share
        linearPartager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearPartager.isClickable() || linearPartager.isFocused() || linearPartager.isSelected()){
                    linearRenommer.setBackgroundColor(Color.BLACK);
                    linearDetails.setBackgroundColor(Color.BLACK);
                    linearPartager.setBackgroundColor(R.drawable.bg_bottom_bar_item_clicked);
                    linearSupprimer.setBackgroundColor(Color.BLACK);
                }
                else{
                    linearPartager.setBackgroundColor(Color.BLACK);
                }

                String fileName = file.getName();
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("*/*");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                startActivity(Intent.createChooser(share, "Partager "+fileName));
                linearPartager.setBackgroundColor(Color.BLACK);
            }
        });


        //Button Delete
        linearSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearSupprimer.isClickable() || linearSupprimer.isFocused() || linearSupprimer.isSelected()){
                    linearRenommer.setBackgroundColor(Color.BLACK);
                    linearDetails.setBackgroundColor(Color.BLACK);
                    linearPartager.setBackgroundColor(Color.BLACK);
                    linearSupprimer.setBackgroundColor(R.drawable.bg_bottom_bar_item_clicked);
                }
                else{
                    linearSupprimer.setBackgroundColor(Color.BLACK);
                }

                final Dialog dialog = new Dialog(getContext());
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
                deleteDialog.setIcon(R.drawable.delete);
                deleteDialog.setTitle("Voulez-vous supprimer " +file.getName()+" ?");
                deleteDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        file.delete();
                        fileList.remove(position);
                        fileAdapter.notifyDataSetChanged();
                        linearSupprimer.setBackgroundColor(Color.BLACK);
                        Toast.makeText(getContext(), "Suppression réussir", Toast.LENGTH_SHORT).show();
                    }
                });
                deleteDialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.cancel();
                        linearSupprimer.setBackgroundColor(Color.BLACK);
                    }
                });
                AlertDialog alertDialog_delete = deleteDialog.create();
                alertDialog_delete.show();
            }
        });



    }

}
