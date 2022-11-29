package Validation.validate;

import Validation.result.ResultHolder;
import Validation.util.Pair;

import java.io.File;

public class Validator {

    private final ResultHolder<File> judgementResult;

    private final ResultHolder<File> finalResult;

    public Validator(ResultHolder<File> result) {
        judgementResult = result;
        finalResult = new ResultHolder<>();
    }

    public Pair<File> getNextProgramPair() {
        return null;
    }

    public void validatePair(Pair<File> filePair) {

    }

    public void invalidatePair(Pair<File> filePair) {

    }

    public void suspectPair(Pair<File> filePair) {
        // I decide to rely on a random number
    }

    public ResultHolder<File> getFinalResult() {
        return finalResult;
    }

}
