package com.mehiretab.gadsleaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;
import java.util.Locale;

public class LearningLeaderAdapter extends RecyclerView.Adapter<LearningLeaderAdapter.LearningLeaderViewHolder> {

    private List<ApiLearningResponse> learningResponses;

    public LearningLeaderAdapter(List<ApiLearningResponse> learningResponses) {
        this.learningResponses = learningResponses;
    }

    @NonNull
    @Override
    public LearningLeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning_leaders,
                parent, false);
        return new LearningLeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearningLeaderViewHolder holder, int position) {
        holder.bind(this.learningResponses.get(position));
    }

    @Override
    public int getItemCount() {
        return this.learningResponses.size();
    }

    static class LearningLeaderViewHolder extends RecyclerView.ViewHolder {

        private final ShapeableImageView imageView;
        private final MaterialTextView name;
        private final MaterialTextView hoursCountry;

        public LearningLeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.item_learning_leaders_img);
            this.name = itemView.findViewById(R.id.item_learning_leaders_name);
            this.hoursCountry = itemView.findViewById(R.id.item_learning_leaders_hours_country);
        }

        void setImageView(String imageUrl) {
            Glide.with(imageView)
                    .load(imageUrl)
                    .into(this.imageView);
        }

        void setName(String name) {
            this.name.setText(name);
        }

        void setHoursCountry(int hours, String country) {
            this.hoursCountry.setText(String.format(Locale.getDefault(), "%d learning hours, %s", hours, country));
        }

        public void bind(ApiLearningResponse apiLearningResponse) {
            this.setImageView(apiLearningResponse.getBadgeUrl());
            this.setName(apiLearningResponse.getName());
            this.setHoursCountry(apiLearningResponse.getHours(), apiLearningResponse.getCountry());
        }
    }

}
