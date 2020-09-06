package com.mehiretab.gadsleaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textview.MaterialTextView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SkillIqLeaderFragment extends Fragment {

    private MaterialTextView errorMsg;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CompositeDisposable compositeDisposable;

    public SkillIqLeaderFragment() {
        this.compositeDisposable = new CompositeDisposable();
    }

    public static SkillIqLeaderFragment newInstance() {
        return new SkillIqLeaderFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skill_iq_leader, container, false);

        this.errorMsg = view.findViewById(R.id.skill_iq_leader_error_msg);
        this.recyclerView = view.findViewById(R.id.skill_iq_leader_recyclerview);
        this.swipeRefreshLayout = view.findViewById(R.id.skill_iq_leader_swipeRefreshLayout);
        this.swipeRefreshLayout.setRefreshing(true);
        this.swipeRefreshLayout.setOnRefreshListener(this::fetchData);

        fetchData();

        return view;
    }

    private void fetchData() {
        this.errorMsg.setVisibility(View.GONE);
        this.swipeRefreshLayout.setRefreshing(true);
        this.compositeDisposable.add(ApiBuilder.getInstance().getApi(Api.class).getSkillIqResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiSkillIqResponses -> {
                    SkillIqLeaderAdapter skillIqLeaderAdapter = new SkillIqLeaderAdapter(apiSkillIqResponses);
                    this.recyclerView.setAdapter(skillIqLeaderAdapter);
                    skillIqLeaderAdapter.notifyDataSetChanged();

                    this.errorMsg.setVisibility(View.GONE);
                    this.swipeRefreshLayout.setRefreshing(false);
                }, throwable -> {
                    this.errorMsg.setVisibility(View.VISIBLE);
                    this.swipeRefreshLayout.setRefreshing(false);
                }));
    }

    @Override
    public void onDestroy() {
        this.compositeDisposable.dispose();
        super.onDestroy();
    }
}