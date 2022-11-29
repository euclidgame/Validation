package Validation.validate;

import Validation.result.ResultHolder;

import java.io.File;

public class Validator {

    private final ResultHolder<File> judgementResult;

    private final ResultHolder<File> finalResult;

    public Validator(ResultHolder<File> result) {
        judgementResult = result;
        finalResult = new ResultHolder<>();
    }

}
