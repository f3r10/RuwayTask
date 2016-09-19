package com.tnb.f3r10.todoapp.addTodo.ui;


import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.tnb.f3r10.todoapp.R;
import com.tnb.f3r10.todoapp.addTodo.AddTodoPresenter;
import com.tnb.f3r10.todoapp.addTodo.AddTodoPresenterImpl;
import com.tnb.f3r10.todoapp.model.Todo;
import com.tnb.f3r10.todoapp.todoList.ui.adapter.DialogFragmentCloseListener;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.etDate)
    EditText etDate;
    @Bind(R.id.editTxtNote)
    EditText editTxtNote;
    @Bind(R.id.txtPriority)
    TextView txtPriority;
    @Bind(R.id.spinnerPriority)
    Spinner spinnerPriority;
    private DialogFragmentCloseListener dialogFragmentCloseListener;
    private int mYear, mMonth, mDay;
    final Calendar c = Calendar.getInstance();

    public AddTodoFragment() {
        // Required empty public constructor
        presenter = new AddTodoPresenterImpl(this);
    }

    Todo todo;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            todo = Parcels.unwrap(getActivity().getIntent().getParcelableExtra("TODO_KEY"));
        }


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.addTodo_message_title)
                .setPositiveButton(R.string.addtodo_message_add,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                .setNegativeButton(R.string.addtodo_message_cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.fragment_add_todo, null);
        ButterKnife.bind(this, view);
        setUpView();
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        return dialog;
    }

    private void setUpView() {
        if (todo != null) {
            editTxtTask.setText(todo.getTodoName());
            editTxtNote.setText(todo.getNotes());
            String formatDate = DateFormat.getDateInstance().format(todo.getTodoDate());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String formatedDate = sdf.format(todo.getTodoDate());

            Date currentDate = new Date();
            try {
                currentDate = sdf.parse(formatedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mDay = currentDate.getDate();
            mMonth =  currentDate.getMonth();
            mYear =  currentDate.getYear() + 1900;


            etDate.setText(formatDate);
            spinnerPriority.setSelection(todo.getPriority());
        }else{
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            Log.d("Add " , mDay + "");
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
    public void succesUpdateTodo(Todo todo) {
        dialogFragmentCloseListener.updateTodoActionFinish(todo);
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

                    int priority = Todo.HIGH;
                    switch (spinnerPriority.getSelectedItem().toString()){
                        case "HIGH":
                            priority = Todo.HIGH;
                            break;
                        case "MEDIUM":
                            priority = Todo.MEDIUM;
                            break;
                        case "LOW":
                            priority = Todo.LOW;
                    }
                    int   day  = mDay;
                    int   month= mMonth;
                    int   year = mYear;

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    Date selectDate = calendar.getTime();
               /*     SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String formatedDate = sdf.format(selectDate);
                    Log.d("ADD ", "Date: " + formatedDate);*/
                    if(todo == null){
                        presenter.addTodoTask(editTxtTask.getText().toString(), selectDate, editTxtNote.getText().toString(), priority, Todo.TODO_DO);
                    }else{
                        presenter.updateTodoTask(editTxtTask.getText().toString(), selectDate, editTxtNote.getText().toString(), priority, todo.getId());
                    }

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


    @OnClick(R.id.etDate)
    public void selectDate() {
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in EditText
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        mYear = year;
                        etDate.setText(dayOfMonth + "-"
                                + (monthOfYear + 1 ) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }


    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }


}
