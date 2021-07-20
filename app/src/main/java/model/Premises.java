package model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.sql.Blob;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Premises {

    private Blob qr_image;
    private String premise_id;
    private LatLng latLng;
    private boolean status;

    // ---------- Constructor, Getters & Setters ---------- //

    public Premises() {
    }

    public Premises(Blob qr_image, String premise_id, LatLng latLng, boolean status) {
        this.qr_image = qr_image;
        this.premise_id = premise_id;
        this.latLng = latLng;
        this.status = status;
    }

    public Blob getQr_image() {
        return qr_image;
    }

    public void setQr_image(Blob qr_image) {
        this.qr_image = qr_image;
    }

    public String getPremise_id() {
        return premise_id;
    }

    public void setPremise_id(String premise_id) {
        this.premise_id = premise_id;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // -------------------- End -------------------- //


    // --------------- Functions starts here --------------- //
    public void add_visitor(User user) {
        // TODO: add user to database during check-in
    }

    public void updateStatus() {
        status = status ? false : true;
    }

    public List<User> getUserList() {
        // TODO: query database to find all user list for a premise

        List<User> userList = new List<User>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable @org.jetbrains.annotations.Nullable Object o) {
                return false;
            }

            @NonNull
            @NotNull
            @Override
            public Iterator<User> iterator() {
                return null;
            }

            @NonNull
            @NotNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @NotNull
            @Override
            public <T> T[] toArray(@NonNull @NotNull T[] a) {
                return null;
            }

            @Override
            public boolean add(User user) {
                return false;
            }

            @Override
            public boolean remove(@Nullable @org.jetbrains.annotations.Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull @NotNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull @NotNull Collection<? extends User> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull @NotNull Collection<? extends User> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull @NotNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull @NotNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public User get(int index) {
                return null;
            }

            @Override
            public User set(int index, User element) {
                return null;
            }

            @Override
            public void add(int index, User element) {

            }

            @Override
            public User remove(int index) {
                return null;
            }

            @Override
            public int indexOf(@Nullable @org.jetbrains.annotations.Nullable Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(@Nullable @org.jetbrains.annotations.Nullable Object o) {
                return 0;
            }

            @NonNull
            @NotNull
            @Override
            public ListIterator<User> listIterator() {
                return null;
            }

            @NonNull
            @NotNull
            @Override
            public ListIterator<User> listIterator(int index) {
                return null;
            }

            @NonNull
            @NotNull
            @Override
            public List<User> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        return userList;
    }
}
// -------------------- End -------------------- //
