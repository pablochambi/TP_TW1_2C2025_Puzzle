package com.tallerwebi.presentacion;

import com.tallerwebi.infraestructura.SudokuApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SudokuController {

    @Autowired
    private SudokuApiService sudokuApiService;

    @GetMapping("/sudoku")
    public ModelAndView mostrarSudoku(){
        // ModelAndView mav = new ModelAndView("sudoku");

        //llama al serivio
        var sudoku = sudokuApiService.getSudoku();

        //pasa los datos a la vista
        ModelMap modelo = new ModelMap();
        modelo.put("sudoku", sudoku);


        return new ModelAndView("sudoku", modelo);
    }

}