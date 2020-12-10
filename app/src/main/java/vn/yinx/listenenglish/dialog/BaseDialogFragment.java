package vn.yinx.listenenglish.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public abstract class BaseDialogFragment extends DialogFragment {
    private Context mContext = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        requireDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
    }
    
    protected abstract void init();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(mContext == null){
            mContext = context;
        }
    }

    protected  <T extends View> T findViewById(int id){
        return requireView().findViewById(id);
    }
}
