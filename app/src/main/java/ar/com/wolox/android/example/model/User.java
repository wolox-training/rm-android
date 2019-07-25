package ar.com.wolox.android.example.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class User {

    @SerializedName("id")
    @Expose private Integer id;
    @SerializedName("username")
    @Expose private String username;
    @SerializedName("email")
    @Expose private String email;
    @SerializedName("password")
    @Expose private String password;
    @SerializedName("picture")
    @Expose private String picture;
    @SerializedName("cover")
    @Expose private String cover;
    @SerializedName("description")
    @Expose private String description;
    @SerializedName("location")
    @Expose private String location;
    @SerializedName("name")
    @Expose private String name;
    @SerializedName("phone")
    @Expose private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(@NonNull String picture) {
        this.picture = picture;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(@NonNull String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

}