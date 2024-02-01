package com.vvh.attendancesv.service;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import java.io.ByteArrayOutputStream;

import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

public class FaceRecognizeServiceImpl implements FaceRecognizeService{

    private final CascadeClassifier cascadeClassifier = new CascadeClassifier("src/main/resources/static/resourcedetector/haarcascades/haarcascade_frontalface_alt2.xml");
    Mat srcImg;
    RectVector rectVector;
    BytePointer bytePointer;
    LBPHFaceRecognizer lbphFaceRecognizer;
    public FaceRecognizeServiceImpl(){
        srcImg = new Mat();
        rectVector = new RectVector();
        bytePointer = new BytePointer();
        lbphFaceRecognizer = LBPHFaceRecognizer.create();
        lbphFaceRecognizer.read("src/main/resources/static/training/classifierLBPH.yml");
        lbphFaceRecognizer.setThreshold(70);
    }

    @Override
    public String recognize(ByteArrayOutputStream byteArrayOutputStream) {

        if(byteArrayOutputStream.toByteArray().length == 0)
            return null;

        //Convert byteArray to Mat opencv
        bytePointer.put(byteArrayOutputStream.toByteArray());
        srcImg = imdecode(new Mat(bytePointer), IMREAD_ANYDEPTH|IMREAD_ANYCOLOR);

        //ImageGray to recognized
        Mat imgGray = new Mat();
        cvtColor(srcImg, imgGray, COLOR_BGR2GRAY);
        cascadeClassifier.detectMultiScale(imgGray, rectVector);

        int prediction = 0;
        for(Rect rect: rectVector.get()){
            rectangle(srcImg, rect, new Scalar(0, 255, 0, 3), 1,0,0);

            Mat faceToRecognized = new Mat(imgGray, rect);
            resize(faceToRecognized, faceToRecognized, new Size(160, 160));

            IntPointer label = new IntPointer(1);
            DoublePointer confidence = new DoublePointer(1);
            lbphFaceRecognizer.predict(faceToRecognized, label, confidence);

            prediction = label.get(0);
            if(prediction == -1){
                rectangle(srcImg, rect, new Scalar(255, 0, 0, 3), 1,0,0);
            }else{
                rectangle(srcImg, rect, new Scalar(0, 255, 0, 3), 1,0,0);
            }
        }
        return null;
    }

    @Override
    public void run() {

    }
}
