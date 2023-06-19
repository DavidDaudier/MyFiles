package com.example.myfiles.File;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfiles.R;

import java.io.File;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileViewHolder> {

    private Context context;
    private List<File> file;
    private OnFileSelectedListener listener;

    public FileAdapter(Context context, List<File> file, OnFileSelectedListener listener ) {
        this.context = context;
        this.file = file;
        this.listener = listener;
    }

    private boolean[] selection;

    void setSelection(boolean[] selection){
        if (selection!=null){
            this.selection = new boolean[selection.length];
            for (int i=0; i<selection.length; i++){
                this.selection[i] = selection[i];
            }
        }
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FileViewHolder(LayoutInflater.from(context).inflate(R.layout.file_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        holder.tvName.setText(file.get(position).getName());
        holder.getAdapterPosition();
        holder.tvName.setSelected(true);

        int items = 0;
        if(file.get(position).isDirectory()){
            File[] files = file.get(position).listFiles();
            for (File fichier : files){
                if (!fichier.isHidden()){
                    items += 1;
                }
            }
            if(items <= 1) {
                holder.tvSize.setText(String.valueOf(items) + " élément");
            }
            else if(items > 1){
                holder.tvSize.setText(String.valueOf(items) + " éléments");
            }
        }
        else {
            holder.tvSize.setText(Formatter.formatShortFileSize(context, file.get(position).length()));
        }

        if (file.get(position).getName().toLowerCase().endsWith(".jpeg")){
            holder.imgFile.setImageResource(R.drawable.picture_single);
        }
        else if (file.get(position).getName().toLowerCase().endsWith(".jpg")){
            holder.imgFile.setImageResource(R.drawable.picture_single);
        }
        else if (file.get(position).getName().toLowerCase().endsWith(".png")){
            holder.imgFile.setImageResource(R.drawable.picture_single);
        }
        else if (file.get(position).getName().toLowerCase().endsWith(".pdf")){
            holder.imgFile.setImageResource(R.drawable.pdf_single);
        }
        else if (file.get(position).getName().toLowerCase().endsWith(".doc")){
            holder.imgFile.setImageResource(R.drawable.doc_single);
        }
        else if (file.get(position).getName().toLowerCase().endsWith(".zip")){
            holder.imgFile.setImageResource(R.drawable.zip_single);
        }
        else if (file.get(position).getName().toLowerCase().endsWith(".mp3")){
            holder.imgFile.setImageResource(R.drawable.music_single);
        }
        else if (file.get(position).getName().toLowerCase().endsWith(".wav")){
            holder.imgFile.setImageResource(R.drawable.music_single);
        }
        else if (file.get(position).getName().toLowerCase().endsWith(".m4a")){
            holder.imgFile.setImageResource(R.drawable.voice_single);
        }
        else if (file.get(position).getName().toLowerCase().endsWith(".mp4")){
            holder.imgFile.setImageResource(R.drawable.video_single);
        }
        else if (file.get(position).getName().toLowerCase().endsWith(".apk")){
            holder.imgFile.setImageResource(R.drawable.ic_apk);
        }
        else {
            holder.imgFile.setImageResource(R.drawable.folder_items);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFileClicked(file.get(position));
            }
        });

        holder.container.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {

                listener.onFileLongClicked(file.get(position), position);


                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return file.size();
    }
}
