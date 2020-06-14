package server;

import client.Controller;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;
    private String login;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    socket.setSoTimeout(120000);
                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/reg ")) {
                            String[] token = str.split(" ");

                            if (token.length < 4) {
                                continue;
                            }

                            boolean succeed = server
                                    .getAuthService()
                                    .registration(token[1], token[2], token[3]);
                            if (succeed) {
                                sendMsg("Регистрация прошла успешно");
                            } else {
                                sendMsg("Регистрация  не удалась. \n" +
                                        "Возможно логин уже занят, или данные содержат пробел");
                            }
                        }

                        if (str.startsWith("/auth ")) {
                            String[] token = str.split(" ");

                            System.out.println(str);
                            if (token.length < 3) {
                                continue;
                            }

                            String newNick = server.getAuthService()
                                    .getNicknameByLoginAndPassword(token[1], token[2]);
                            login = token[1];
                            if (newNick != null) {
                                if (!server.isLoginAuthorized(login)){
                                    socket.setSoTimeout(0);
                                    sendMsg("/authok " + newNick);
                                    nick = newNick;

                                    server.subscribe(this);
                                    System.out.println("Клиент: " + nick + " подключился");
                                    break;
                                }else {
                                    sendMsg("Пользователь с таким логином уже авторизован");
                                }

                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }
                    }

                    //цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/")){
                            if (str.equals("/end")) {
                                sendMsg("/end");
                                break;
                            }

                            //Отправка сообщения одному клиенту
                            if (str.startsWith("/w")){
                                String[] token = str.split("\\s", 3);
                                if (token.length < 3){
                                    continue;
                                }
                                server.sendTargetMsg(this, token[1], token[2]);
                                continue;
                            }
                        }
                        server.broadcastMsg(nick, str);
                    }
                }
                catch (SocketTimeoutException e){
                    System.out.println("Таймаут бездействия: " + nick);
                }
                catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    server.unsubscribe(this);
                    System.out.println("Клиент отключился");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return login;
    }

    public String getNick() {
        return nick;
    }
}