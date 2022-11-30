package Validation.validate;

import Validation.result.ResultHolder;
import Validation.util.Pair;

import java.io.File;
import java.util.List;

import static Validation.gui.MyFrame.getLastThreeDir;

public class Validator {

    private final ResultHolder<File> finalResult;

    private int groupIndex;

    private int programIndex;

    ResultHolder<File> currentHolder;

    List<List<File>> allFiles;

    private int validateIndex;

    private List<File> comparedFiles;

    private List<String[]> inequalPairs;

    public Validator(ResultHolder<File> result) {
        allFiles = result.disjointLists();
        finalResult = new ResultHolder<>();
        groupIndex = 0;
        programIndex = 1;
        currentHolder = new ResultHolder<>();
        validateIndex = 0;
        currentHolder.addRepresentative(allFiles.get(groupIndex).get(0));
        comparedFiles = currentHolder.getRepresentatives();
        System.out.println(allFiles);
    }

    public void setInequalPairs(List<String[]> inequalPairs) {
        this.inequalPairs = inequalPairs;
    }

    public Pair<File> getNextProgramPair() {
        try {
            System.out.println(groupIndex + ", " + programIndex + ", " + validateIndex);
            File first = allFiles.get(groupIndex).get(programIndex);
            File second = comparedFiles.get(validateIndex);
            return new Pair<>(first, second);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void validatePair(Pair<File> filePair) {
        currentHolder.addElement(filePair.first(), filePair.second());
        nextProgram();
    }

    public void invalidatePair(Pair<File> filePair) {
        if (validateIndex < comparedFiles.size() - 1) {
            validateIndex ++;
        }
        else {
            currentHolder.addRepresentative(filePair.first());
            comparedFiles = currentHolder.getRepresentatives();
            nextProgram();
        }
    }

    private void nextProgram() {
        File thisFile = allFiles.get(groupIndex).get(programIndex);
        currentHolder.elements().forEach(element -> {
            if (!currentHolder.getRepresentativeOf(element).equals(currentHolder.getRepresentativeOf(thisFile))) {
                inequalPairs.add(new String[]{getLastThreeDir(thisFile.getAbsolutePath()), getLastThreeDir(element.getAbsolutePath())});
            }
        });
        List<File> filesInThisGroup = allFiles.get(groupIndex);
        if (programIndex < filesInThisGroup.size() - 1) {
            programIndex ++;
            validateIndex = 0;
        }
        else {
            groupIndex ++;
            programIndex = 1;
            finalResult.merge(currentHolder);
            currentHolder = new ResultHolder<>();
            validateIndex = 0;
            if (groupIndex < allFiles.size()) {
                currentHolder.addRepresentative(allFiles.get(groupIndex).get(0));
                comparedFiles = currentHolder.getRepresentatives();
            }
        }
    }

    public void suspectPair(Pair<File> filePair) {
        validatePair(filePair);
    }

    public ResultHolder<File> getFinalResult() {
        return finalResult;
    }

}
