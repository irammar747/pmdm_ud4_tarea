package dam.pmdm.spyrothedragon.guide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import dam.pmdm.spyrothedragon.databinding.WelcomeGuideBinding;

public class GuideWelcomeFragment extends Fragment {
    private WelcomeGuideBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = WelcomeGuideBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

}
