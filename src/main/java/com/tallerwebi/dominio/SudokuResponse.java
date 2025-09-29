package com.tallerwebi.dominio;

import java.util.Arrays;

public class SudokuResponse {
    private Integer[][] puzzle;
    private Integer[][] solution;

    public SudokuResponse() {
    }

    public Integer[][] getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Integer[][] puzzle) {
        this.puzzle = puzzle;
    }

    public Integer[][] getSolution() {
        return solution;
    }

    public void setSolution(Integer[][] solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "SudokuResponse{" +
                "puzzle=" + Arrays.deepToString(puzzle) +
                ", solution=" + Arrays.deepToString(solution) +
                '}';
    }
}