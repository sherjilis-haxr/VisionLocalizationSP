package org.firstinspires.ftc.teamcode.mechanisms;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;


public class BlobVision implements VisionProcessor {


    private Mat ycrcb;
    private Mat mask;
    private Mat kernel;
    private final Scalar lower = new Scalar(0, 130, 0);
    private final Scalar upper = new Scalar(255, 180, 100);

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        ycrcb = new Mat();
        mask = new Mat();
        kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(15, 15)); // (setup for later, not fully understood yet.)
    }
    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        List<MatOfPoint> contours = new ArrayList<>();

        Imgproc.cvtColor(frame, ycrcb, Imgproc.COLOR_RGB2YCrCb);
        Core.inRange(ycrcb, lower, upper, mask);
        Imgproc.cvtColor(mask, frame, Imgproc.COLOR_GRAY2RGB);
        Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint contour: contours) {
            double area = Imgproc.contourArea(contour);
            if (area < 100) {
                continue;
            }
            // GPTED this line bc i got tired
            Imgproc.drawContours(frame, List.of(contour), -1, new Scalar(0, 255, 0), 2);

        }

        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }
}