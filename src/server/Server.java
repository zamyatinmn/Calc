package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;


public class Server {
    private List<ClientHandler> clients;
    private AuthService authService;

    public Server() {
        clients = new Vector<>();
        authService = new SimpleAuthService();
        ServerSocket server = null;
        Socket socket;

        final int PORT = 8189;

        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился ");
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert server != null;
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(String nick, String msg){
        for (ClientHandler c:clients) {
            c.sendMsg(nick + ": " + msg);
        }
    }

    public void sendTargetMsg(ClientHandler sender, String nick, String msg){
        for (ClientHandler c:clients) {
            if (c.getNick().equals(nick)) {
                c.sendMsg(sender.getNick() + "->" + nick + ": " + msg);
                sender.sendMsg(sender.getNick() + "->" + nick + ": " + msg);
                return;
            }
            sender.sendMsg("Пользователя с ником " + nick + " не найдено");
        }
    }

    public void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
        broadcastClientList();
    }

    public AuthService getAuthService() {
        return authService;
    }
    
    public boolean isLoginAuthorized(String login){
        for (ClientHandler c: clients) {
            if (c.getLogin().equals(login)){
                return true;
            }
        }
        return false;
    }

    private void broadcastClientList(){
        StringBuilder sb = new StringBuilder("/clientlist ");
        for (ClientHandler c: clients) {
            sb.append(c.getNick()).append(" ");
        }
        String msg = sb.toString();
        for (ClientHandler c: clients) {
            c.sendMsg(msg);
        }
    }
}
