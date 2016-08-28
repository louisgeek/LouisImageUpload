package com.louis.louisimageupload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by louisgeek on 2016/6/7.
 */
public class MyDialogFragmentProgress extends DialogFragment {

    private final static   String TITLE_KEY="TitleKey";

    ProgressBar mProgressBar;
    TextView mMyTitle;
    public static MyDialogFragmentProgress newInstance(String title) {
        MyDialogFragmentProgress myDialogFragment = new MyDialogFragmentProgress();
        Bundle args = new Bundle();
        // 自定义的标题
        args.putString(TITLE_KEY, title);
        myDialogFragment.setArguments(args);
        return myDialogFragment;
    }

    /**
     * 在onCreateView中使用  getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);即可去掉
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialogfrag_content_progress,container,false);

        mProgressBar= (ProgressBar) view.findViewById(R.id.id_pb);
        mProgressBar.setMax(100);

        mMyTitle= (TextView) view.findViewById(R.id.id_my_title);

        String title = getArguments().getString(TITLE_KEY);
        this.getDialog().setTitle(title);
        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //return super.onCreateView(inflater, container, savedInstanceState);
        return  view;
    }

    public void updateProgress(int progress){
        //if (mProgressBar!=null) {
            mProgressBar.setProgress(progress);
            mMyTitle.setText(String.valueOf(progress));
       // }
    }

}
