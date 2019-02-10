package com.example.whoward3.caseTracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.CaseHolder> {
    private List<Case> cases = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public CaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_item, parent, false);
        return new CaseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseHolder holder, int i) {
        Case currentCase = cases.get(i);
        holder.textViewPerson.setText(currentCase.getPerson());
        holder.textViewTribe.setText(currentCase.getTribe());
        holder.textViewCaseType.setText(currentCase.getType());
        holder.textViewOpenDate.setText(currentCase.getOpenDate());
        //holder.textViewNote.setText(currentCase.getCaseNotes());
    }

    @Override
    public int getItemCount() {
        return cases.size();
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
        notifyDataSetChanged();
    }

    public Case getCaseAt(int position) {
        return cases.get(position);
    }

    class CaseHolder extends RecyclerView.ViewHolder { //View holder, I'm not going to show all fields on view
        private TextView textViewPerson;
        private TextView textViewTribe;
        private TextView textViewCaseType;
        //private TextView textViewCaseSubtype;
        //private TextView textViewEventCount;
        private TextView textViewOpenDate;
        //private TextView textViewCloseDate;
        //private TextView textViewNote;

        public CaseHolder(View itemView) {
            super(itemView);
            textViewPerson = itemView.findViewById(R.id.text_view_person);
            textViewTribe = itemView.findViewById(R.id.text_view_tribe);
            textViewCaseType = itemView.findViewById(R.id.text_view_caseType);
            textViewOpenDate = itemView.findViewById(R.id.text_view_openDate);
            //textViewNote = itemView.findViewById(R.id.text_view_note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(cases.get(position));
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
