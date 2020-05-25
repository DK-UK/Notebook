package com.example.notebook;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class bottomSheet extends BottomSheetDialogFragment {
public onItemClickDeleteListener listener;
int position;

    public bottomSheet(int position){
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet,container,false);
        final Button delete = view.findViewById(R.id.deleteBtn);
        Button cancel = view.findViewById(R.id.cancelBtn);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemDelete(position);
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public interface onItemClickDeleteListener{
        void onItemDelete(int position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (onItemClickDeleteListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    +" must implement BottomSheetListener");
        }
    }
}
