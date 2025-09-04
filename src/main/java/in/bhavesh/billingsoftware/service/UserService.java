package in.bhavesh.billingsoftware.service;

import in.bhavesh.billingsoftware.io.UserRequest;
import in.bhavesh.billingsoftware.io.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);

    String getUserRole(String email);

    List<UserResponse> readUsers();

    void deleteUser(String id);
}
