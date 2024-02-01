package com.vvh.attendancesv.service;

import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.Queue;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;

public class FaceTrainingServiceImpl implements FaceTrainingService{

    private final CascadeClassifier cascade = new CascadeClassifier("src/main/resources/static/haarcascades/haarcascade_frontalface_alt2.xml");

    @Override
    public void trainingLBPH() {
        List<Mat> matList = registerFace();
    }

    public List<Mat> registerFace(){
        File[] files = readDirectory("src/main/resources/static/metadata");
        List<Mat> matList = new ArrayList<>();

        assert files != null;
        for(File img: files) {
            Mat matImg = imread(img.getAbsolutePath());

            RectVector rectVector = new RectVector();
            cascade.detectMultiScale(matImg, rectVector);

            for (Rect rect : rectVector.get()) {
                rectangle(matImg, rect, new Scalar(255, 255, 0, 3), 1, 0, 0);

                Mat facePerson = new Mat(matImg, rect);
                resize(facePerson, facePerson, new Size(160, 160));
                cvtColor(facePerson, facePerson, COLOR_BGRA2GRAY);
                imwrite("src/main/resources/static/training/" + img.getName(),facePerson);
                matList.add(facePerson);
            }
        }
        return matList;
    }

    public File[] readDirectory(String path){
        //mention the directory the faces has been saved
        File directory = new File(path);

        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg")
                        || name.endsWith(".pgm")
                        || name.endsWith(".png");
            }
        };
        return directory.listFiles(filenameFilter);
    }
}
