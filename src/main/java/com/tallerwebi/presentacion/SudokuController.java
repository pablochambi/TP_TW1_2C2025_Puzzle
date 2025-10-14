package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.infraestructura.SudokuApiService;
import com.tallerwebi.dominio.SudokuResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class SudokuController {


    private final SudokuApiService sudokuApiService;
    private final ServicioLogin service;

    public SudokuController(SudokuApiService sudokuApiService, ServicioLogin service) {
        this.sudokuApiService = sudokuApiService;
        this.service = service;
    }


    @GetMapping("/sudoku")
    public ModelAndView getSudoku(@RequestParam(defaultValue = "easy") String difficulty, HttpSession session) {
        ModelMap model = new ModelMap();
        Long id_usuario = (Long) session.getAttribute("id_usuario");
        Usuario usuario = service.consultarUsuarioPorId(id_usuario);
        model.put("usuario",usuario);
        ModelAndView mav = new ModelAndView("sudoku",model);


        SudokuResponse sudoku = sudokuApiService.getSudokuFromApi(difficulty);
        mav.addObject("sudoku", sudoku);
        mav.addObject("difficulty", difficulty);


        return mav;
    }
}