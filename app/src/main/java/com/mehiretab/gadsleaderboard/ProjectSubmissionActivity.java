package com.mehiretab.gadsleaderboard;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProjectSubmissionActivity extends AppCompatActivity {

    // used to clean network observables
    private CompositeDisposable compositeDisposable;

    private TextInputEditText firstNameET;
    private TextInputEditText lastNameET;
    private TextInputEditText emailET;
    private TextInputEditText githubLinkET;
    private MaterialButton submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_submission);

        init();
    }

    private void init() {
        this.compositeDisposable = new CompositeDisposable();

        this.firstNameET = findViewById(R.id.project_submission_first_name);
        this.lastNameET = findViewById(R.id.project_submission_last_name);
        this.emailET = findViewById(R.id.project_submission_email);
        this.githubLinkET = findViewById(R.id.project_submission_github_link);
        this.submitBtn = findViewById(R.id.project_submission_submit_btn);

        this.submitBtn.setOnClickListener(view -> showDialog(SubmitStatusDialog.Status.SUBMITTING));
    }

    private void showDialog(SubmitStatusDialog.Status status) {
        SubmitStatusDialog dialog = new SubmitStatusDialog(this, status);
        dialog.show(getSupportFragmentManager(), SubmitStatusDialog.class.getName());
        dialog.setOnSubmitListener(this::startSubmission);
    }

    private void startSubmission() {
        try {
            String firstName = this.firstNameET.getText().toString();
            String lastName = this.lastNameET.getText().toString();
            String email = this.emailET.getText().toString();
            String githubLink = this.githubLinkET.getText().toString();
            ApiProjectSubmission apiProjectSubmission = ApiBuilderProjectSubmission.getInstance()
                    .getApi(ApiProjectSubmission.class);

            this.compositeDisposable.add(apiProjectSubmission.submitProject(firstName, lastName, email, githubLink)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aVoid -> showDialog(SubmitStatusDialog.Status.SUCCESS),
                            throwable -> showDialog(SubmitStatusDialog.Status.FAIL)));
        } catch (NullPointerException ex) {
            Toast.makeText(this, "Please insert all inputs", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        this.compositeDisposable.dispose();
        super.onDestroy();
    }
}