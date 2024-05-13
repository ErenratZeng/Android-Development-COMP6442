package com.example.kangarun.activity.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kangarun.R;
import com.example.kangarun.User;
import com.example.kangarun.utils.PermissionUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.io.ByteArrayOutputStream;

public class MapsFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "MapsFragment";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean permissionDenied = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private UiSettings mUiSettings;
    private LatLng mCurrentLocation;
    private List<LatLng> mPathPoints = new ArrayList<>();
    private Polyline mPolyline;
    private Timer mPathTimer = null;
    private Timer mDurationTimer = null;
    private long mStartTimeMillis = 0;
    private double distance = 0;
    private String duration;
    private String exerciseDate;
    private double calories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        Button startExerciseButton = rootView.findViewById(R.id.start_button);
        startExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startExerciseButton.getText().equals("Start")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    exerciseDate = simpleDateFormat.format(date);
                    startExerciseButton.setText(getString(R.string.stop));
                    Toast.makeText(getContext(), "Start Exercise!", Toast.LENGTH_SHORT).show();
                    startDrawingPath();
                    startTiming();
                } else if (startExerciseButton.getText().equals("Stop")) {
                    startExerciseButton.setText(getString(R.string.save));
                    Toast.makeText(getContext(), "Stop Exercise", Toast.LENGTH_SHORT).show();
                    stopDrawingPath();
                    stopTiming();
                    captureMapSnapshot();
                } else if (startExerciseButton.getText().equals("Save Exercise record")) {
                    Toast.makeText(getContext(), "Exercise Record Saved", Toast.LENGTH_SHORT).show();
                    Map<String, Object> exerciseRecord = new HashMap<>();
                    exerciseRecord.put("uid", User.getCurrentUserId());
                    exerciseRecord.put("date", exerciseDate);
                    exerciseRecord.put("distance", distance);
                    exerciseRecord.put("duration", duration);
                    exerciseRecord.put("calories", calories);
                    db.collection("exerciseRecord").document(User.getCurrentUserId() + exerciseDate)
                            .set(exerciseRecord)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                }
                            });
                    captureMapSnapshot();
                }
            }
        });

        return rootView;
    }

    private void startDrawingPath() {
        mPathTimer = new Timer();
        mPathTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mMap != null) {
                    getCurrentLocation();
                    if (mCurrentLocation != null) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updatePath();
                            }
                        });
                    }
                }
            }
        }, 0, 1000);
    }

    private void stopDrawingPath() {
        if (mPathTimer != null) {
            mPathTimer.cancel();
            mPathTimer = null;
        }
    }

    private void startTiming() {
        mStartTimeMillis = System.currentTimeMillis();
        mDurationTimer = new Timer();
        mDurationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long elapsedTimeMillis = System.currentTimeMillis() - mStartTimeMillis;
                long elapsedSeconds = elapsedTimeMillis / 1000;
                long secondsDisplay = elapsedSeconds % 60;
                long elapsedMinutes = elapsedSeconds / 60;
                duration = String.format("%02d:%02d", elapsedMinutes, secondsDisplay);

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateDurationTextView(duration);
                    }
                });
            }
        }, 0, 1000);
    }

    private void stopTiming() {
        if (mDurationTimer != null) {
            mDurationTimer.cancel();
            mDurationTimer = null;
        }
    }

    private float calculateDistance(LatLng start, LatLng end) {
        float[] results = new float[1];
        Location.distanceBetween(start.latitude, start.longitude, end.latitude, end.longitude, results);
        return results[0];
    }

    private void getCurrentLocation() {
        if (mFusedLocationClient != null) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            }
            mFusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
                if (location != null) {
                    mCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                }
            });
        }
    }

    private void updatePath() {
        if (mCurrentLocation != null) {
            if (!mPathPoints.isEmpty()) {
                LatLng lastPoint = mPathPoints.get(mPathPoints.size() - 1);
                distance += calculateDistance(lastPoint, mCurrentLocation);
            }
            mPathPoints.add(mCurrentLocation);
            if (mPathPoints.size() > 1) {
                if (mPolyline != null) {
                    mPolyline.remove();
                }

                PolylineOptions polylineOptions = new PolylineOptions()
                        .color(Color.BLUE)
                        .width(20);

                for (LatLng point : mPathPoints) {
                    polylineOptions.add(point);
                }

                mPolyline = mMap.addPolyline(polylineOptions);
            }
            updateDistanceTextView(distance);
        }
    }

    private void updateDurationTextView(String timeDisplay) {
        TextView durationTextView = requireView().findViewById(R.id.duration_text);
        durationTextView.setText(timeDisplay);
    }

    private void updateDistanceTextView(double distance) {
        TextView distanceTextView = requireView().findViewById(R.id.distance);
        distanceTextView.setText(String.format("%.2f km", distance / 1000));
        calories = calculateCalories(distance, System.currentTimeMillis() - mStartTimeMillis, 70);
        updateCaloriesTextView(calories);
    }

    private double calculateCalories(double distanceMeters, long timeMillis, double weightKg) {
        double distanceKm = distanceMeters / 1000.0;
        double timeHours = timeMillis / 3600000.0;
        double speedMinPer400m = (timeMillis / 60000.0) / (distanceMeters / 400.0);
        double K = 30 / speedMinPer400m;
        return weightKg * timeHours * K;
    }

    private void updateCaloriesTextView(double calories) {
        TextView caloriesTextView = requireView().findViewById(R.id.calories_text);
        caloriesTextView.setText(String.format("%.0f cal", calories));
    }

    private void captureMapSnapshot() {
        if (mMap == null) {
            Log.d("MapSnapshot", "Map not initialized");
            return;
        }
        mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                uploadMapSnapshotToFirebase(byteArray);
            }
        });
    }

    private void uploadMapSnapshotToFirebase(byte[] imageBytes) {
        String filePath = "exerciseRecord/" + User.getCurrentUserId() + exerciseDate + "/mapSnapshot.png";
        StorageReference fileRef = FirebaseStorage.getInstance().getReference().child(filePath);

        fileRef.putBytes(imageBytes)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("FirebaseStorage", "Snapshot uploaded successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseStorage", "Snapshot upload failed", e);
                    }
                });
    }

    private boolean isChecked(int id) {
        return ((CheckBox) requireView().findViewById(id)).isChecked();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(isChecked(R.id.zoom_buttons_toggle));

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
                if (location != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                }
            });
        } else {
            enableMyLocation();
        }
    }

    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(getContext(), R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void setZoomButtonsEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setZoomControlsEnabled(((CheckBox) v).isChecked());
    }

    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            return;
        }
        PermissionUtils.requestLocationPermissions((AppCompatActivity) requireActivity(), LOCATION_PERMISSION_REQUEST_CODE, true);
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();
        System.out.println(location);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
                .isPermissionGranted(permissions, grantResults,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
            enableMyLocation();
        } else {
            permissionDenied = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permissionDenied) {
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getChildFragmentManager(), "dialog");
    }
}





