package Validation;

import Validation.gui.MyFrame;
import Validation.result.ResultHolder;
import Validation.validate.Validator;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        ResultHolder<File> originalResult = new ResultHolder<>();
        File inputDir = new File(args[0]);
        File outputDir = new File(args[1]);
        File inequal = initialize(originalResult, inputDir);
        Validator validator = new Validator(originalResult);
        EventQueue.invokeLater(() -> {
            MyFrame frame = new MyFrame(validator);
            frame.setVisible(true);
        });
    }

    private static File initialize(ResultHolder<File> result, File inputDir) {
        File inequal = null;
        for (String file: Objects.requireNonNull(inputDir.list())) {
            if (file.equals("equal.csv")) {
                // readFile
                try {
                    File equal = new File(inputDir, file);
                    InputStreamReader br = new InputStreamReader(new FileInputStream(equal));
                    BufferedReader reader = new BufferedReader(br);
                    reader.readLine(); // omit first line
                    String lineStr;
                    while ((lineStr = reader.readLine()) != null) {
                        String[] files = lineStr.split(",");
                        String[] file1Str = files[0].split("/");
                        File file1 = new File(new File(inputDir, file1Str[1]), file1Str[2]);
                        String[] file2Str = files[1].split("/");
                        File file2 = new File(new File(inputDir, file2Str[1]), file2Str[2]);
                        if (!result.hasElement(file1)) {
                            result.addRepresentative(file1);
                        }
                        if (!result.hasElement(file2)) {
                            result.addRepresentative(file2);
                        }
                        result.union(file1, file2);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (file.equals("inequal.csv")) {
                inequal = new File(inequal, file);
            }
        }
        return inequal;
    }
}
