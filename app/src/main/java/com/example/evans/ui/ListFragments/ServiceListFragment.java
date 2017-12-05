package com.example.evans.ui.ListFragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.evans.R;
import com.example.evans.data.MainController;
import com.example.evans.data.Service;
import com.example.evans.ui.Adapters.ServiceAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceListFragment extends Fragment {

    private FloatingActionButton _addFloatingBtn;
    private ArrayList<Service> _services = new ArrayList<>();
    private ServiceListFragmentListener _hostActivityListener;
    private MainController _mainController;

    private ListView _listViewService;


    public ServiceListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View _rootView;

        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_service_list, container, false);
        _addFloatingBtn = (FloatingActionButton) _rootView.findViewById(R.id.floating_add_btn);
        _mainController = new MainController();

        // Set the onClickListener for the floating button.
        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateService();
            }
        });

        _listViewService = (ListView) _rootView.findViewById(R.id.service_list);
        loadServices();



        _listViewService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"This is a Toast test", Toast.LENGTH_SHORT).show();
            }
        });

        return _rootView;
    }

    public void loadServices() {

        Map<String, Service> serviceMap = _mainController.getAvailableServices();

        _services.addAll(serviceMap.values());

        ServiceAdapter adapter = new ServiceAdapter(getActivity(), R.layout.service_adapter, _services);

        _listViewService.setAdapter(adapter);
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
