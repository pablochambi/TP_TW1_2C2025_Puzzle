package com.tallerwebi.presentacion;

import com.tallerwebi.infraestructura.SudokuApiService;
import com.tallerwebi.dominio.SudokuResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SudokuController {

    @Autowired
    private SudokuApiService sudokuApiService;

    @GetMapping("/sudoku")
    public ModelAndView getSudoku(@RequestParam(defaultValue = "easy") String difficulty) {
        ModelAndView mav = new ModelAndView("sudoku");
        SudokuResponse sudoku = sudokuApiService.getSudokuFromApi(difficulty);
        mav.addObject("sudoku", sudoku);
        mav.addObject("difficulty", difficulty);
        return mav;
    }
}