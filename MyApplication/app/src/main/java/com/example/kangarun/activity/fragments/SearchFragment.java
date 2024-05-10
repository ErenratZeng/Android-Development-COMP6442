package com.example.kangarun.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kangarun.R;
import com.example.kangarun.User;
import com.example.kangarun.UserListener;
import com.example.kangarun.activity.FriendProfileActivity;
import com.example.kangarun.adapter.UserAdapter;
import com.example.kangarun.databinding.FragmentSearchBinding;
import com.example.kangarun.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements UserListener {
    private FragmentSearchBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        String query = getActivity().getIntent().getStringExtra("query");
        binding.searchView.setQuery(query, false);
        searchUsers(query);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(), "Searching " + query, Toast.LENGTH_SHORT).show();
                searchUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return binding.getRoot();
    }

    private void searchUsers(String query) {
        if (query == null || query.isEmpty()) {
            // 如果 query 是 null 或为空，显示提示或返回
            Toast.makeText(getActivity(), "Please enter a search term", Toast.LENGTH_SHORT).show();
            return;
        }
        List<User> users = MainActivity.tree.searchPartial(query);

        if (!users.isEmpty()) {
            for (User user : users) {
                Log.d("treeRet", user.getUsername());
            }
            UserAdapter adapter = new UserAdapter(users, this);
            binding.userRecyclerView.setAdapter(adapter);
            binding.userRecyclerView.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Search success", Toast.LENGTH_SHORT).show();
        } else {
            binding.userRecyclerView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "No results found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getActivity(), FriendProfileActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
