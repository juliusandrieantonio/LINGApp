package com.example.lingapp.ui.UpdateUser;

public interface IUpdateProfile {
    void hasUser(boolean hasUser);

    void onChangeInfo(boolean verdict, String message);

    void reAuthenticate(boolean verdict);
}
