package com.example.whoward3.expensetracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder> {
    private List<Expense> expenses = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new ExpenseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int i) {
        Expense currentExpense = expenses.get(i);
        holder.textViewAmount.setText(String.valueOf(currentExpense.getAmount()));
        holder.textViewCategory.setText(currentExpense.getCategory());
        holder.textViewDate.setText(currentExpense.getDate());
        holder.textViewName.setText(currentExpense.getName());
        holder.textViewNote.setText(currentExpense.getNote());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    public Expense getExpenseAt(int position) {
        return expenses.get(position);
    }

    class ExpenseHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewCategory;
        private TextView textViewDate;
        private TextView textViewAmount;
        private TextView textViewNote;


        public ExpenseHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewCategory = itemView.findViewById(R.id.text_view_category);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            textViewNote = itemView.findViewById(R.id.text_view_note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(expenses.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Expense expense);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
