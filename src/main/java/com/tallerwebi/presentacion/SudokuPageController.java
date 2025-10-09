package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SudokuPageController {

    @GetMapping("/sudoku-test")
    public String mostrarSudoku() {
        return "sudoku-test";
    }
}
