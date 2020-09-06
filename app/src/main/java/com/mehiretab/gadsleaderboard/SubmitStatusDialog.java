package com.mehiretab.gadsleaderboard;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class SubmitStatusDialog extends AppCompatDialogFragment {

    private Context context;
    private Status submitStatus;
    private OnSubmitListener onSubmitListener;

    public SubmitStatusDialog(Context context, Status status) {
        this.context = context;
        this.submitStatus = status;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_submit_status, container, false);

        ImageView statusImage = view.findViewById(R.id.submit_status_image);
        MaterialTextView statusText = view.findViewById(R.id.submit_status_text);
        ImageView closeBtn = view.findViewById(R.id.submit_status_close);
        MaterialButton yesBtn = view.findViewById(R.id.submit_status_yes_btn);

        switch (this.submitStatus) {
            case SUCCESS:
            case FAIL:
                statusImage.setVisibility(View.VISIBLE);
                closeBtn.setVisibility(View.GONE);
                yesBtn.setVisibility(View.GONE);
                break;
            case SUBMITTING:
            default:
                statusImage.setVisibility(View.GONE);
                closeBtn.setVisibility(View.VISIBLE);
                yesBtn.setVisibility(View.VISIBLE);
        }

        if (statusImage.getVisibility() == View.VISIBLE)
            statusImage.setImageDrawable(Status.getImage(this.context, this.submitStatus));
        statusText.setText(Status.getMessage(this.submitStatus));

        yesBtn.setOnClickListener(view1 -> {
            this.onSubmitListener.onSubmit();
            this.dismiss();
        });

        closeBtn.setOnClickListener(view12 -> this.dismiss());

        return view;
    }

    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    public enum Status {
        FAIL,
        SUCCESS,
        SUBMITTING;

        public static String getMessage(Status status) {
            switch (status) {
                case FAIL:
                    return "Submission not Successful";
                case SUCCESS:
                    return "Submission Successful";
                case SUBMITTING:
                default:
                    return "Are you sure ?";
            }
        }

        public static Drawable getImage(Context context, Status status) {
            switch (status) {
                case FAIL:
                    return ContextCompat.getDrawable(context, R.drawable.ic_error);
                case SUCCESS:
                    return ContextCompat.getDrawable(context, R.drawable.ic_success);
                case SUBMITTING:
                default:
                    return null;
            }
        }
    }

    public interface OnSubmitListener {
        void onSubmit();
    }
}
