package server;

public interface AuthService {
    String getNicknameByLoginAndPassword(String login, String pass);
}
