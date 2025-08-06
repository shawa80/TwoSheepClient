package com.siliconandsynapse.aclient.game;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.siliconandsynapse.aclient.MainActivity;
import com.siliconandsynapse.aclient.NetworkService;

public class BaseGameFragment extends Fragment {

    private NetworkService.OnConnectFailureListener onFailure;

    public BaseGameFragment(@LayoutRes int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var act = (MainActivity) getActivity();
        var netService = NetworkService.getService();
        onFailure = (service, message) -> act.runOnUiThread(() -> {
            var toast = Toast.makeText(act , message, Toast.LENGTH_SHORT);
            toast.show();
            act.showlogin();
        });
        netService.onConnectFailure.add(onFailure);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        var netService = NetworkService.getService();
        netService.onConnectFailure.add(onFailure);
    }
}

