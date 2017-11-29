package com.ulyne.webSocket;

import com.ulyne.web.UploadFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * websocket 示例demo
 * Created by fanwei_last on 2017/11/28.
 */
@ServerEndpoint(value = "/webSocket")
@Component
public class WebSocketDemo {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArrayList<WebSocketDemo> webSocketSet = new CopyOnWriteArrayList<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭时调用
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用
     * @param message 客户端发过来的消息
     * @param session 可选.
     */
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("来自客户端的消息:" + message);
        //群发消息
        for (WebSocketDemo item : webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param erro
     */
    @OnError
    public void onErro(Session session, Throwable erro){
        System.out.println("发生错误");
        erro.printStackTrace();
    }

    /**
     * 向客户端发送信息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 在线人数+1
     */
    public static synchronized void addOnlineCount(){
        WebSocketDemo.onlineCount++;
    }

    /**
     * 在线人数-1
     */
    public static synchronized void subOnlineCount(){
        WebSocketDemo.onlineCount--;
    }

    /**
     * 获取在线人数
     * @return
     */
    public static synchronized int getOnlineCount(){
        return onlineCount;
    }
}
