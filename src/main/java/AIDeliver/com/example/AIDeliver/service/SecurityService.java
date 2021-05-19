package AIDeliver.com.example.AIDeliver.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
}

