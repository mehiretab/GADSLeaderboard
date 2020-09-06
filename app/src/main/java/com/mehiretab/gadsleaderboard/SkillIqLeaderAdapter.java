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

public class SkillIqLeaderAdapter extends RecyclerView.Adapter<SkillIqLeaderAdapter.SkillIqLeaderViewHolder> {

    private List<ApiSkillIqResponse> apiSkillIqResponses;

    public SkillIqLeaderAdapter(List<ApiSkillIqResponse> apiSkillIqResponses) {
        this.apiSkillIqResponses = apiSkillIqResponses;
    }

    @NonNull
    @Override
    public SkillIqLeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skill_iq_leaders, parent,
                false);
        return new SkillIqLeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillIqLeaderViewHolder holder, int position) {
        holder.bind(this.apiSkillIqResponses.get(position));
    }

    @Override
    public int getItemCount() {
        return this.apiSkillIqResponses.size();
    }

    static class SkillIqLeaderViewHolder extends RecyclerView.ViewHolder {

        private final ShapeableImageView imageView;
        private final MaterialTextView name;
        private final MaterialTextView skillIqCountry;

        public SkillIqLeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.item_skill_iq_leaders_img);
            this.name = itemView.findViewById(R.id.item_skill_iq_leaders_name);
            this.skillIqCountry = itemView.findViewById(R.id.item_skill_iq_leaders_skill_iq_country);
        }

        private void setImageView(String imageUrl) {
            Glide.with(this.imageView)
                    .load(imageUrl)
                    .into(this.imageView);
        }

        private void setName(String name) {
            this.name.setText(name);
        }

        private void setSkillIqCountry(int skillIq, String country) {
            this.skillIqCountry.setText(String.format(Locale.getDefault(), "%d Skill IQ Score, %s", skillIq, country));
        }

        public void bind(ApiSkillIqResponse apiSkillIqResponse) {
            this.setImageView(apiSkillIqResponse.getBadgeUrl());
            this.setName(apiSkillIqResponse.getName());
            this.setSkillIqCountry(apiSkillIqResponse.getScore(), apiSkillIqResponse.getCountry());
        }
    }

}
