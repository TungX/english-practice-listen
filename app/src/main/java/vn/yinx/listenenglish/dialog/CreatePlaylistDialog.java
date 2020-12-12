package vn.yinx.listenenglish.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import vn.yinx.listenenglish.R;
import vn.yinx.listenenglish.util.Stores;
import vn.yinx.listenenglish.entity.Playlist;
import vn.yinx.listenenglish.fragment.FragmentPlaylist;

public class CreatePlaylistDialog extends BaseDialogFragment implements View.OnClickListener {
    private EditText editPlaylistName;
    private TextView tvError, btnCancel, btnConfirm;
    private FragmentPlaylist fragmentPlaylist;

    public CreatePlaylistDialog(FragmentPlaylist fragmentPlaylist) {
        this.fragmentPlaylist = fragmentPlaylist;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_enter_playlist_name, container, false);
    }

    @Override
    protected void init() {
        editPlaylistName = findViewById(R.id.edit_playlist_name);
        btnCancel = findViewById(R.id.tv_cancel);
        btnConfirm = findViewById(R.id.tv_confirm);
        tvError = findViewById(R.id.tv_error);

        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                return;
            case R.id.tv_confirm:
                if(editPlaylistName.getText().toString().trim().isEmpty()){
                    tvError.setText(R.string.not_blank);
                    tvError.setVisibility(View.VISIBLE);
                    return;
                }
                tvError.setVisibility(View.GONE);
                Playlist playlist = new Playlist();
                playlist.setName(editPlaylistName.getText().toString());
                try {
                    long id = playlist.create();
                    playlist.setId(id);
                    Stores.playlists.add(playlist);
                    fragmentPlaylist.addPlaylist(playlist);
                    dismiss();
                }catch (Exception e){
                    tvError.setText(e.getMessage());
                    tvError.setVisibility(View.VISIBLE);
                }
                return;
        }
    }
}
