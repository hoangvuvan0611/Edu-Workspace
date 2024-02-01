package com.vvh.attendancesv.util;

import com.vvh.attendancesv.service.FaceRecognizeService;
import com.vvh.attendancesv.service.FaceRecognizeServiceImpl;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyWebSocketHandler extends BinaryWebSocketHandler {

    //Store multi ByteArray of an image
    ByteArrayOutputStream byteArrayOutputStream;

    //Limit number of threads was created
    ExecutorService executorService;

    FaceRecognizeService faceRecognizeService;

    public MyWebSocketHandler(){
        byteArrayOutputStream  = new ByteArrayOutputStream();
        executorService = Executors.newFixedThreadPool(5);
        faceRecognizeService = new FaceRecognizeServiceImpl();
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {

        byteArrayOutputStream.write(message.getPayload().array());
        if(message.isLast()){
            executorService.execute(faceRecognizeService);
            String result = faceRecognizeService.recognize(byteArrayOutputStream);
            if(result != null){
                session.sendMessage(new BinaryMessage(result.getBytes()));
            }
            byteArrayOutputStream.reset();
        }
    }
}
