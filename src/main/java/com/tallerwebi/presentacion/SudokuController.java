package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.interfaces.ServicioLogin;
import com.tallerwebi.infraestructura.SudokuApiService;
import com.tallerwebi.dominio.SudokuResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SudokuController {

    @Autowired
    private SudokuApiService sudokuApiService;

    @Autowired
    private ServicioLogin servicioLogin;

    @GetMapping("/sudoku")
    public ModelAndView getSudoku(@RequestParam(defaultValue = "easy") String difficulty) {
        ModelAndView mav = new ModelAndView("sudoku");
        SudokuResponse sudoku = sudokuApiService.getSudokuFromApi(difficulty);
        mav.addObject("sudoku", sudoku);
        mav.addObject("difficulty", difficulty);
        return mav;
    }


    @GetMapping("/sudoku/online")
    public ModelAndView buscarJugadoresEnLinea(@RequestParam(defaultValue = "easy") String difficulty, HttpServletRequest request) {
        Long id_usuario = (Long) request.getSession().getAttribute("id_usuario");

        if (id_usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();

        model.put("usuario", servicioLogin.consultarUsuarioDTOPorId(id_usuario));

        return new ModelAndView("buscar-jugadores-en-linea", model);
    }








}