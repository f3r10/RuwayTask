package com.tnb.f3r10.todoapp.addTodo;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.tnb.f3r10.todoapp.R;
import com.tnb.f3r10.todoapp.model.Todo;
import com.tnb.f3r10.todoapp.todoList.ui.adapter.DialogFragmentCloseListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTodoFragment extends DialogFragment implements DialogInterface.OnShowListener, AddTodoView {


    @Bind(R.id.editTxtTask)
    EditText editTxtTask;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    AddTodoPresenter presenter;
    String taskId;
    String titleTask;
    private DialogFragmentCloseListener dialogFragmentCloseListener;

    public AddTodoFragment() {
        // Required empty public constructor
        presenter = new AddTodoPresenterImpl(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null){
            taskId = getArguments().getString("ID");
            titleTask = getArguments().getString("TITLE");
            Log.d("AddTodoFragment" , "TITLE " + titleTask);
        }


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =  new  AlertDialog.Builder(getActivity())
                .setTitle(R.string.addtodo_message_title)
                .setPositiveButton(R.string.addtodo_message_add,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        })
                .setNegativeButton(R.string.addtodo_message_cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        });

        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.fragment_add_todo,null);
        ButterKnife.bind(this, view);
        setUpView();
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        return dialog;
    }

    private void setUpView() {
        if(titleTask !=null){
            editTxtTask.setText(titleTask);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showInput() {
        editTxtTask.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideInput() {
        editTxtTask.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void succesAddTodo() {
        dialogFragmentCloseListener.addTodoActionFinish();
        dismiss();
    }

    @Override
    public void errorAddTodo() {
        dismiss();
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {

            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.addTodoTask(editTxtTask.getText().toString(), taskId);
                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
        presenter.onShow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dialogFragmentCloseListener = (DialogFragmentCloseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement EditNameDialogListener");
        }
    }
}
