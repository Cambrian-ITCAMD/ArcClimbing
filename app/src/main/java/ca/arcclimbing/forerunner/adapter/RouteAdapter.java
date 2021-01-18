package ca.arcclimbing.forerunner.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ca.arcclimbing.forerunner.model.Route;

import ca.arcclimbing.forerunner.databinding.ViewRouteListItemBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RouteAdapter extends FirestoreRecyclerAdapter<Route, RouteAdapter.RouteViewHolder> {

    private OnRouteClickListener listener;

    public RouteAdapter(@NonNull FirestoreRecyclerOptions<Route> options, OnRouteClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull RouteViewHolder holder, int position, @NonNull Route model) {
        model.setDocumentId(getSnapshots().getSnapshot(position).getId());
        holder.bind(model, listener);
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RouteViewHolder.from(parent);
    }

    public interface OnRouteClickListener {
        void onRouteClick(Route route);
    }

    public static class RouteViewHolder extends RecyclerView.ViewHolder{

        private ViewRouteListItemBinding binding;

        private RouteViewHolder(@NonNull ViewRouteListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public static RouteViewHolder from(ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ViewRouteListItemBinding binding = ViewRouteListItemBinding.inflate(inflater, parent, false);
            return new RouteViewHolder(binding);
        }

        public void bind(Route route, OnRouteClickListener listener) {
            binding.itemName.setText(route.getName());
            binding.itemStatus.setText(route.getStatus());
            binding.itemColour.setText(route.getColour());
            binding.itemBarNumber.setText(route.getBarNumber());
            binding.itemGrade.setText(route.getGrade());

            binding.getRoot().setOnClickListener(view -> listener.onRouteClick(route));
        }
    }
}
