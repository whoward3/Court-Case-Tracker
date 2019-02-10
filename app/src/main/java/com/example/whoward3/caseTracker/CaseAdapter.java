package com.example.whoward3.caseTracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.ExpenseHolder> {
    private List<Case> expens = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_item, parent, false);
        return new ExpenseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int i) {
        Case currentCase = expens.get(i);
        holder.textViewCaseType.setText(String.valueOf(currentCase.getEventCount()));
        holder.textViewTribe.setText(currentCase.getTribe());
        holder.textViewOpenDate.setText(currentCase.getOpenDate());
        holder.textViewPerson.setText(currentCase.getPerson());
        holder.textViewNote.setText(currentCase.getCaseNotes());
    }

    @Override
    public int getItemCount() {
        return expens.size();
    }

    public void setCases(List<Case> expens) {
        this.expens = expens;
        notifyDataSetChanged();
    }

    public Case getCaseAt(int position) {
        return expens.get(position);
    }

    class ExpenseHolder extends RecyclerView.ViewHolder {
        private TextView textViewPerson;
        private TextView textViewTribe;
        private TextView textViewOpenDate;
        private TextView textViewCaseType;
        private TextView textViewNote;


        public ExpenseHolder(View itemView) {
            super(itemView);
            textViewPerson = itemView.findViewById(R.id.text_view_person);
            textViewTribe = itemView.findViewById(R.id.text_view_tribe);
            textViewOpenDate = itemView.findViewById(R.id.text_view_openDate);
            textViewCaseType = itemView.findViewById(R.id.text_view_caseType);
           // textViewNote = itemView.findViewById(R.id.text_view_note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(expens.get(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Case aCase);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
