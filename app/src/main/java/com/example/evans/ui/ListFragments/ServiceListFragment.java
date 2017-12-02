package com.example.evans.ui.ListFragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.evans.R;
import com.example.evans.data.Service;
import com.example.evans.ui.Adapters.ServiceAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceListFragment extends Fragment {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;  // how we can get access to view elements
    private ArrayList<Service> _services = new ArrayList<>();
    private ServiceListFragmentListener _hostActivityListener;

    public ServiceListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_service_list, container, false);
        _addFloatingBtn = (FloatingActionButton) _rootView.findViewById(R.id.floating_add_btn);

        // Set the onClickListener for the floating button.
        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateService();
            }
        });


        super.onCreate(savedInstanceState);

        ListView listView = (ListView) _rootView.findViewById(R.id.service_list);

        ServiceAdapter adapter = new ServiceAdapter(getActivity(), R.layout.service_adapter, _services);

        listView.setAdapter(adapter);
        return _rootView;
    }

    public void setServices(Map<String, Service> services) {

        _services.addAll(services.values());
    }

    /**
     * We want to make sure that the activity that uses this fragment
     * has implemented our InteractionWithServiceFragment interface. We
     * check for this by trying to cast the activity to an instance of
     * InteractionWithServiceFragment, if it fails then that means that the
     * interface wasn't implemented. We have to say something about that!
     * @param activity: the host activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            _hostActivityListener = (ServiceListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement " +
                    "InteractionWithServiceFragmentListener");
        }
    }

    /**
     * For now we just want to let the host activity take care of it by calling it's
     * onAddService method it better had implemented our interface
     */
    public void onCreateService() {
        _hostActivityListener.onAddService();
    }

    /**
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface ServiceListFragmentListener {
        void onClickService(Service service);
        void onAddService();
    }
}
