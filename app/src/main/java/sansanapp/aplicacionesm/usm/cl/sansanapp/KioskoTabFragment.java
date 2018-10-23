package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class KioskoTabFragment extends Fragment {
        private static final String TAB = "Tab1Fragment";

        private Button btnTEST;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.kiosko_tab_fragment,container,false);
            btnTEST = (Button) view.findViewById(R.id.btnTEST2);

            btnTEST.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "TESTING BUTTON CLICK 2",Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }
    }


