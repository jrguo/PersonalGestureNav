package com.jrguo2.personalgesturenav.settings;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jrguo2.personalgesturenav.feature.R;
import com.jrguo2.personalgesturenav.gestures.GesturesTypes;
import com.jrguo2.personalgesturenav.utils.Util;

public class ActionSettingsFragment extends Fragment {

    public static Fragment INSTANCE;

    public static Fragment getInstance() {
        if(INSTANCE != null)
            return INSTANCE;

        Bundle bundle = new Bundle();
        ActionSettingsFragment actionSettingsFragment = new ActionSettingsFragment();
        actionSettingsFragment.setArguments(bundle);
        INSTANCE = actionSettingsFragment;
        return actionSettingsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_actions, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout layout = view.findViewById(R.id.baseLinearLayout);

        createActionMenu(layout, GesturesTypes.SHORT_LEFT, "Short Left");

    }

    private void createActionMenu(LinearLayout layout, GesturesTypes gestures, String gestureName){
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // Width of TextView
                ViewGroup.LayoutParams.WRAP_CONTENT);

        addTextArea(layout,gestureName,"title",gestureName,R.style.SettingsSeekBarTitleTextAreaSettings, lp, 0,0,0,0);
    }


    private TextView addTextArea(LinearLayout layout, String name, String type, String text, int style, ViewGroup.LayoutParams lp, int...padding){
        TextView textView = new TextView(getActivity().getApplicationContext());
        textView.setLayoutParams(lp);
        textView.setTextAppearance(style);
        textView.setId((name + type).hashCode());
        textView.setText(text);
        layout.addView(textView);

        textView.setScrollBarStyle(style);

        Util.SCALE = (int) getResources().getDisplayMetrics().density;

        if(padding.length > 0)
            textView.setPadding(Util.dpToPixels(padding[0]),Util.dpToPixels(padding[1]), Util.dpToPixels(padding[2]),Util.dpToPixels(padding[3]));

        return textView;
    }

    private void addDivider(LinearLayout layout, int height){
        View viewDivider = new View(getActivity().getApplicationContext());
        viewDivider.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        layout.addView(viewDivider);
    }
}
