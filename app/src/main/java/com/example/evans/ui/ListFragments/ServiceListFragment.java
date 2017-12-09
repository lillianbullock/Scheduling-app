package com.example.evans.ui.ListFragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.evans.R;
import com.example.evans.data.MainController;
import com.example.evans.data.Service;
import com.example.evans.ui.Adapters.ServiceAdapter;

import java.util.ArrayList;

/**
 * {@link Fragment} subclass that lists all relevant appointments
 * uses the {@link ServiceAdapter} to display each item.
 */
public class ServiceListFragment extends Fragment {

    private FloatingActionButton _addFloatingBtn;
    private View _rootView;

    private ServiceListFragmentListener _hostActivityListener;
    private MainController _mainController;

    private final String TITLE = "Services";

    private ProgressBar _progressBar;
    private ArrayList<Service> _services = new ArrayList<>();
    private ServiceAdapter _serviceAdapter;
    private ListView _listViewService;


    public ServiceListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        _rootView = inflater.inflate(R.layout.fragment_service_list, container, false);
        _addFloatingBtn = _rootView.findViewById(R.id.floating_add_btn);
        _addFloatingBtn = _rootView.findViewById(R.id.floating_add_btn);
        _mainController = MainController.getInstance();
        _progressBar = _rootView.findViewById(R.id.service_list_progress_bar);
        _serviceAdapter = new ServiceAdapter(getActivity(), R.layout.service_adapter, _services);

        super.onCreate(savedInstanceState);

        _listViewService = (ListView) _rootView.findViewById(R.id.service_list);
        _listViewService.setAdapter(_serviceAdapter);

        // Set the onClickListener for the floating button.
        _addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateService();
            }
        });
        _listViewService = (ListView) _rootView.findViewById(R.id.service_list);


        _listViewService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service service = _serviceAdapter.getItem(position);
                _hostActivityListener.onClickService(service);
            }
        });

        loadServices();

        return _rootView;
    }

    private void loadServices(){
        _services.clear();
        _services.addAll(_mainController.getAvailableServices().values());
    }

    @Override
    public void onResume() {
        super.onResume();
        _hostActivityListener.showActionbar();
        _hostActivityListener.setAppbarTitle(TITLE);
    }


    /**
     * For now we just want to let the host activity take care of it by calling it's
     * onAddService method it better had implemented our interface
     */
    public void onCreateService() {
        _hostActivityListener.onAddService();
    }

    /**
     * Ensures parent activity has implemented the InteractionWithServiceListFragment interface
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
     * This interface must be implemented by the container Activity
     * This is how we'll be able to communicate with the parent activity.
     */
    public interface ServiceListFragmentListener {
        void setAppbarTitle(String title);
        void onClickService(Service service);
        void onAddService();
        void showActionbar();
    }
}
